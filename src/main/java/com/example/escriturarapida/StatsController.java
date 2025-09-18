package com.example.escriturarapida;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.io.IOException;


/**
 * Controller for the Statistics screen.
 *
 * <p>This class displays the player's performance after a game,
 * showing the number of correct words typed and mistakes made.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Display updated statistics passed from the {@link GameController}.</li>
 *   <li>Provide navigation back to the Main Menu.</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */

public class StatsController {
    /**
     * ---------- FXML UI ELEMENTS ----------
     */
    @FXML
    private Label rightWordsLabel;

    @FXML
    private Label errorsLabel;
    /**
     * ---------- STATIC DATA FIELDS ----------
     */
    private static int rightWords = 0;
    private static int errors = 0;
    /**
     * ---------- INITIALIZATION ----------
     */


    /**
     * Initializes the statistics screen and updates the labels.
     */
    @FXML
    private void initialize() {
        updateStats();
    }
    /**
     * ---------- STATIC METHODS ----------
     */

    /**
     * Updates the statistics with data passed from the game.
     *
     * @param correctas number of correctly typed words
     * @param fallos    number of mistakes made
     */
    public static void updateStats(int correctas, int fallos) {
        rightWords = correctas;
        errors = fallos;
    }
    /**
     * ---------- PRIVATE METHODS ----------
     */

    /**
     * Refreshes the statistics labels displayed on screen.
     */
    private void updateStats() {
        if (rightWordsLabel != null) {
            rightWordsLabel.setText("Palabras Correctas: " + rightWords);
        }
        if (errorsLabel != null) {
            errorsLabel.setText("Errores: " + errors);
        }
    }
    /**
     * ---------- BUTTON HANDLERS ----------
     */

    /**
     * Returns to the Main Menu screen.
     *
     * @param event button click event
     */
    @FXML
    private void exit(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}