package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class PuzzleSolutionView implements FXComponent {
  private final AlternateMvcController controller;

  public PuzzleSolutionView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox panel = new HBox();
    panel.setSpacing(10);
    panel.setAlignment(Pos.CENTER);

    // Adding the Reset Puzzle Button
    Button reset = new Button("Reset");
    reset.getStyleClass().add("button-controls");
    reset.setOnAction((ActionEvent actionEvent) -> controller.clickResetPuzzle());
    panel.getChildren().add(reset);

    //        // Adding the Check Solution Puzzle Button
    //        Button check = new Button("Check Solution");
    //        check.getStyleClass().add("button-controls");
    //        check.setOnAction((ActionEvent event) -> controller.isSolved());
    //        panel.getChildren().add(check);

    return panel;
  }
}
