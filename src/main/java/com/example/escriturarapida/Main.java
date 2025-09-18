package com.example.escriturarapida;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

/**
 * Entry point of the application.
 *
 * <p>This class launches the JavaFX application and loads
 * the Main Menu screen defined in {@code MainMenu.fxml}.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Initialize the primary {@link Stage}.</li>
 *   <li>Load and display the Main Menu scene.</li>
 *   <li>Configure window properties (size, style, resizability).</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */

public class Main extends Application {

    /**
     * Main method. Launches the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /**
     * Starts the application by loading the Main Menu scene.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 600);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }
}
