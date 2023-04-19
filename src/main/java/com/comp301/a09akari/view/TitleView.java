package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class TitleView implements FXComponent {
  private final AlternateMvcController controller;

  public TitleView(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox head = new VBox();
    head.setAlignment(Pos.CENTER);

    // Title
    Label title = new Label("Akari Light Up");
    title.getStyleClass().add("title");
    head.getChildren().add(title);

    // Subtitle
    String puzzleNum = String.valueOf(controller.getActivePuzzleIndex() + 1);
    String librarySize = String.valueOf(controller.getPuzzleLibrarySize());
    String string = "Puzzle " + puzzleNum + "/" + librarySize;
    Label subtitle = new Label(string);
    subtitle.getStyleClass().add("subtitle");
    head.getChildren().add(subtitle);

    // Victory Label
    Label victory = new Label("Congratulations! You solved the puzzle!");
    victory.getStyleClass().add("victory");
    if (controller.isSolved()) {
      head.getChildren().add(victory);
    }
    return head;
  }
}
