package com.comp301.a09akari.model;

import java.util.ArrayList;
import java.util.List;

public class ModelImpl implements Model {

  private final PuzzleLibrary library;
  private int activePuzzleIndex;
  private List<ModelObserver> observers;
  private int[][] lampLocations; // 1 = Lamp Present | 0 = Lamp absent

  public ModelImpl(PuzzleLibrary library) {
    if (library == null) {
      throw new IllegalArgumentException();
    }
    this.library = library;
    this.activePuzzleIndex = 0;
    this.observers = new ArrayList<>();
    this.lampLocations = new int[library.getPuzzle(0).getHeight()][library.getPuzzle(0).getWidth()];
  }

  @Override
  public void addLamp(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    } else if (!isLampPresent(r, c)) {
      toggleLamp(r, c);
      notifyObservers();
    }
  }

  @Override
  public void removeLamp(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    } else if (isLampPresent(r, c)) {
      toggleLamp(r, c);
      notifyObservers();
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }

    // Going through the column:
    for (int i = 0; i < puzzle.getHeight(); i++) {
      if (isLampPresent(i, c) && checkNoWallsBetweenColumn(i, r, c)) {
        return true;
      }
    }

    // Going through the row:
    for (int j = 0; j < puzzle.getWidth(); j++) {
      if (isLampPresent(r, j) && checkNoWallsBetweenRow(j, r, c)) {
        return true;
      }
    }
    return false;
  }

  private boolean checkNoWallsBetweenColumn(int i, int r, int c) {
    Puzzle puzzle = getActivePuzzle();

    while (i < r) {
      if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        return false;
      }
      i++;
    }
    while (i > r) {
      if (puzzle.getCellType(i, c) != CellType.CORRIDOR) {
        return false;
      }
      i--;
    }
    return true;
  }

  private boolean checkNoWallsBetweenRow(int j, int r, int c) {
    Puzzle puzzle = getActivePuzzle();

    while (j < c) {
      if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        return false;
      }
      j++;
    }
    while (j > c) {
      if (puzzle.getCellType(r, j) != CellType.CORRIDOR) {
        return false;
      }
      j--;
    }
    return true;
  }

  @Override
  public boolean isLamp(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
    return lampLocations[r][c] == 1;
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }

    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }

    // Going through the column:
    for (int i = 0; i < puzzle.getHeight(); i++) {
      if (isLampPresent(i, c) && checkNoWallsBetweenColumn(i, r, c) && i != r) {
        return true;
      }
    }

    // Going through the row:
    for (int j = 0; j < puzzle.getWidth(); j++) {
      if (isLampPresent(r, j) && checkNoWallsBetweenRow(j, r, c) && j != c) {
        return true;
      }
    }
    return false;
  }

  @Override
  public Puzzle getActivePuzzle() {
    return library.getPuzzle(activePuzzleIndex);
  }

  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    if (index >= getPuzzleLibrarySize()) {
      throw new IllegalArgumentException();
    }
    activePuzzleIndex = index;
    resetPuzzle();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    resetLamps();
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    Puzzle puzzle = getActivePuzzle();
    int rows = puzzle.getHeight();
    int cols = puzzle.getWidth();
    for (int r = 0; r < rows; r++) {
      for (int c = 0; c < cols; c++) {
        switch (puzzle.getCellType(r, c)) {
          case CORRIDOR:
            if (!isLit(r, c) || (isLamp(r, c) && isLampIllegal(r, c))) {
              return false;
            }
            break;
          case CLUE:
            if (!isClueSatisfied(r, c)) {
              return false;
            }
            break;
        }
      }
    }
    return true;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    if (r >= puzzle.getHeight() || c >= puzzle.getWidth() || r < 0 || c < 0) {
      throw new IndexOutOfBoundsException();
    }
    if (puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }

    int adjacentLamps = 0;

    // Check cells above, below, left, and right of the clue
    if (r > 0 && isLampPresent(r - 1, c)) {
      adjacentLamps++;
    }
    if (r < puzzle.getHeight() - 1 && isLampPresent(r + 1, c)) {
      adjacentLamps++;
    }
    if (c > 0 && isLampPresent(r, c - 1)) {
      adjacentLamps++;
    }
    if (c < puzzle.getWidth() - 1 && isLampPresent(r, c + 1)) {
      adjacentLamps++;
    }

    int clueValue = puzzle.getClue(r, c);
    return adjacentLamps == clueValue;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private boolean isLampPresent(int r, int c) {
    return lampLocations[r][c] == 1;
  }

  private void toggleLamp(int r, int c) {
    if (lampLocations[r][c] == 0) {
      lampLocations[r][c] = 1;
    } else {
      lampLocations[r][c] = 0;
    }
  }

  private void resetLamps() {
    lampLocations = new int[getActivePuzzle().getHeight()][getActivePuzzle().getWidth()];
  }
}
