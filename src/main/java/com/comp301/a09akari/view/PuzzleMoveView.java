package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PuzzleMoveView implements FXComponent {
  private final AlternateMvcController controller;

  public PuzzleMoveView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox panel = new HBox();
    panel.setAlignment(Pos.CENTER);
    panel.setSpacing(10);

    // Previous Puzzle Button
    Button prev = new Button("<< Previous");
    prev.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());
    prev.getStyleClass().add("button-controls");
    panel.getChildren().add(prev);

    // Random Puzzle Button
    Button rand = new Button("Random");
    rand.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());
    rand.getStyleClass().add("button-controls");
    panel.getChildren().add(rand);

    // Next Puzzle Button
    Button next = new Button("Next >>");
    next.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());
    next.getStyleClass().add("button-controls");
    panel.getChildren().add(next);

    return panel;
  }
}
