package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.model.CellType;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;

public class GameView implements FXComponent {

  private final AlternateMvcController controller;

  public GameView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    TilePane board = new TilePane();

    // Determining size of current puzzle.
    int cols = controller.getActivePuzzle().getWidth();
    int rows = controller.getActivePuzzle().getHeight();

    // Tile Size & Padding
    int TILE_SIZE = 50;
    int PADDING = 2;
    int tilePaneLimit = TILE_SIZE + PADDING;

    // Creating a board with specified dimensions.
    board.setPrefColumns(cols);
    board.setPrefRows(rows);
    board.setHgap(PADDING);
    board.setVgap(PADDING);

    // Preventing board from resizing.
    board.setMinWidth(cols * tilePaneLimit);
    board.setMinHeight(rows * tilePaneLimit);
    board.setMaxWidth(cols * tilePaneLimit);
    board.setMaxHeight(rows * tilePaneLimit);

    // Generating the board with appropriate colors
    Label label;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < cols; col++) {
        CellType cellType = controller.getActivePuzzle().getCellType(row, col);
        switch (cellType) {
          case CORRIDOR:
            Button button = new Button();
            int finalRow = row;
            int finalCol = col;
            button.setOnAction((ActionEvent event) -> controller.clickCell(finalRow, finalCol));
            if (controller.isLamp(row, col)) {
              if (controller.isLampIllegal(row, col)) {
                button.getStyleClass().add("illegal-lamp-corridor");
              } else {
                button.getStyleClass().add("lamp-corridor");
              }
            } else if (controller.isLit(row, col)) {
              button.getStyleClass().add("lit-corridor");
            } else {
              button.getStyleClass().add("empty-corridor");
            }
            button.setPrefSize(TILE_SIZE, TILE_SIZE);
            board.getChildren().add(button);
            break;
          case CLUE:
            String clue = String.valueOf(controller.getActivePuzzle().getClue(row, col));
            label = new Label(clue);
            if (controller.isClueSatisfied(row, col)) {
              label.getStyleClass().add("clue-satisfied");
            } else {
              label.getStyleClass().add("clue");
            }
            label.setPrefSize(TILE_SIZE, TILE_SIZE);
            board.getChildren().add(label);
            break;
          case WALL:
            label = new Label();
            label.getStyleClass().add("wall");
            label.setPrefSize(TILE_SIZE, TILE_SIZE);
            board.getChildren().add(label);
            break;
        }
      }
    }

    return board;
  }
}
