package com.example.escriturarapida;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the Main Menu.
 *
 * <p>This class manages the main menu interactions:
 * starting the game, opening the help screen, and exiting the application.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Handle button actions for navigation.</li>
 *   <li>Change the current scene to the corresponding menu or game screen.</li>
 *   <li>Exit the application safely.</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */

public class MenuController {
    /**
     * ---------- FXML UI ELEMENTS ----------
     */
    @FXML
    private Button helpButton;
    /**
     * ---------- BUTTON HANDLERS ----------
     */

    /**
     * Starts the game by loading the Game Menu screen.
     *
     * @param event button click event
     */
    @FXML
    private void play(ActionEvent event) {
        try {
            switchScene(event, "GameMenu.fxml","");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Opens the Help Menu screen.
     *
     * @param event button click event
     */
    @FXML
    private void help(ActionEvent event) {
        try {
            switchScene(event, "HelpMenu.fxml", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Exits the application.
     *
     * @param event button click event
     */
    @FXML
    private void exit(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }
    /**
     * ---------- SCENE MANAGEMENT ----------
     */

    /**
     * Utility method to change the current scene.
     *
     * @param event    the event that triggered the scene change
     * @param fxmlFile the FXML file to load
     * @param titulo   the window title to set
     * @throws IOException if the FXML file cannot be loaded
     */
    private void switchScene(ActionEvent event, String fxmlFile, String titulo) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 600, 600);
        stage.setTitle(titulo);
        stage.setScene(scene);
        stage.show();
    }
}