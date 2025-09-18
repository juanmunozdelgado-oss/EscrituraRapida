package com.example.escriturarapida;

import javafx.application.Platform;
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
 * Controller for the Game Over screen.
 *
 * <p>This class handles the display of the game result (victory or defeat),
 * provides options to retry, view statistics, or return to the main menu.
 * It uses static variables to receive the game outcome from the
 * {@link GameController}.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Displaying whether the player won or lost.</li>
 *   <li>Allowing the player to restart the game.</li>
 *   <li>Allowing the player to view statistics.</li>
 *   <li>Returning to the main menu.</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */

public class GameOverController {
    /**
     * ---------- FXML UI ELEMENTS ----------
     */
    @FXML
    private Label resultadoLabel;
    /**
     * ---------- GAME RESULT VARIABLES ----------
     */
    private static boolean juegoGanado = false;
    private static int nivelAlcanzado = 1;
    private static int palabrasCorrectas = 0;
    private static int errores = 0;
    /**
     * ---------- INITIALIZATION ----------
     */

    /**
     * Called automatically after FXML loading.
     * Updates the result label based on the game outcome.
     */
    @FXML
    private void initialize() {
        actualizarResultado();
    }
    /**
     * Configures the game result before showing the Game Over screen.
     *
     * @param ganado    true if the game was won
     * @param nivel     last level reached
     * @param correctas number of correct words typed
     * @param fallos    number of incorrect attempts
     */
    public static void configurarResultado(boolean ganado, int nivel, int correctas, int fallos) {
        juegoGanado = ganado;
        nivelAlcanzado = nivel;
        palabrasCorrectas = correctas;
        errores = fallos;
    }
    /**
     * Updates the label text and style depending on win or loss.
     */
    private void actualizarResultado() {
        if (resultadoLabel != null) {
            if (juegoGanado) {
                resultadoLabel.setText("¡Ganaste!");
                resultadoLabel.setStyle("-fx-text-fill: #00FF00;"); // Verde para victoria
            } else {
                resultadoLabel.setText("Perdiste");
                resultadoLabel.setStyle("-fx-text-fill: #FF0000;"); // Rojo para derrota
            }
        }
    }
    /**
     * ---------- UI BUTTON HANDLERS ----------
     */

    /**
     * Restarts the game by loading the Game Menu.
     *
     * @param event button click event
     */
    @FXML
    private void reintentar(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Opens the statistics screen after updating with game data.
     *
     * @param event button click event
     */
    @FXML
    private void verEstadisticas(ActionEvent event) {
        try {
            StatsController.actualizarStats(palabrasCorrectas, errores);

            Parent root = FXMLLoader.load(getClass().getResource("StatsMenu.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns to the main menu screen.
     *
     * @param event button click event
     */
    @FXML
    private void salir(ActionEvent event) {
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