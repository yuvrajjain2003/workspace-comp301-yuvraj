package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.AlternateMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {

    // Creating the Puzzle Library
    PuzzleLibrary library = new PuzzleLibraryImpl();

    // Adding the Sample Puzzles to the Library
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_06));

    // Model
    Model model = new ModelImpl(library);

    // Controller
    AlternateMvcController controller = new ControllerImpl(model);

    // View
    View view = new View(controller);

    // Make scene
    Scene scene = new Scene(view.render(), 1200, 800);

    // load the CSS file
    scene.getStylesheets().add("main.css");

    // Setting the scene
    stage.setScene(scene);

    // Refresh view when model changes
    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    stage.setTitle("Akari - Yuvraj Jain");
    stage.show();
  }
}
