package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.AlternateMvcController;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

public class View implements FXComponent {

  private final AlternateMvcController controller;

  public View(AlternateMvcController controller) {
    this.controller = controller;
  }

  @Override
  public Parent render() {
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(20);

    // Title View
    TitleView title = new TitleView(controller);
    layout.getChildren().add(title.render());

    // Game View
    GameView board = new GameView(controller);
    layout.getChildren().add(board.render());

    // Controls View
    ControlView controls = new ControlView(controller);
    layout.getChildren().add(controls.render());

    return layout;
  }
}
