package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.Model;
import com.comp301.a09akari.model.Puzzle;

import java.util.Random;

public class ControllerImpl implements AlternateMvcController {

  private final Model model;

  public ControllerImpl(Model model) {
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    model.resetPuzzle();
    int activePuzzleIndex = model.getActivePuzzleIndex();
    int librarySize = model.getPuzzleLibrarySize();
    if (activePuzzleIndex + 1 < librarySize) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    } else {
      model.setActivePuzzleIndex(0);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    model.resetPuzzle();
    int activePuzzleIndex = model.getActivePuzzleIndex();
    int librarySize = model.getPuzzleLibrarySize();
    if (activePuzzleIndex - 1 >= 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    } else {
      model.setActivePuzzleIndex(librarySize - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    model.resetPuzzle();
    int librarySize = model.getPuzzleLibrarySize();
    int randomInt = new Random().nextInt(librarySize);
    model.setActivePuzzleIndex(randomInt);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.isLamp(r, c)) {
      model.removeLamp(r, c);
    } else {
      model.addLamp(r, c);
    }
  }

  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  @Override
  public int getActivePuzzleIndex() {
    return model.getActivePuzzleIndex();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return model.getPuzzleLibrarySize();
  }
}
