package com.comp301.a09akari.model;

public class PuzzleImpl implements Puzzle {
  private final int[][] board;

  public PuzzleImpl(int[][] board) {
    // Your constructor code here
    this.board = board;
  }

  @Override
  public int getWidth() {
    return board[0].length;
  }

  @Override
  public int getHeight() {
    return board.length;
  }

  @Override
  public CellType getCellType(int r, int c) {
    if (r >= getHeight() || c >= getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    int cellValue = board[r][c];
    if (cellValue >= 0 && cellValue <= 4) {
      return CellType.CLUE;
    } else if (cellValue == 5) {
      return CellType.WALL;
    } else if (cellValue == 6) {
      return CellType.CORRIDOR;
    } else {
      throw new IllegalStateException();
    }
  }

  @Override
  public int getClue(int r, int c) {
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
    return board[r][c];
  }
}
