package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class ControlView implements FXComponent {
  private final AlternateMvcController controller;

  public ControlView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox box = new VBox();
    box.setAlignment(Pos.CENTER);

    // Puzzle Movement Buttons
    PuzzleMoveView puzzleMoveControls = new PuzzleMoveView(controller);
    box.getChildren().add(puzzleMoveControls.render());

    // Reset & Check Solution Buttons
    PuzzleSolutionView puzzleSolutionControls = new PuzzleSolutionView(controller);
    box.getChildren().add(puzzleSolutionControls.render());

    return box;
  }
}
