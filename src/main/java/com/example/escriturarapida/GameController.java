package com.example.escriturarapida;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
/**
 * Controller for the typing-speed game UI.
 *
 * <p>This class is responsible for handling the core logic of the
 * typing game: word selection, timer management, user input validation,
 * scoring, difficulty progression, and UI updates. It interacts with
 * the JavaFX UI defined in FXML.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Randomly selecting words according to difficulty.</li>
 *   <li>Starting, resetting, and stopping the timer.</li>
 *   <li>Validating typed words and updating score.</li>
 *   <li>Increasing difficulty as levels progress.</li>
 *   <li>Handling game over and game completion states.</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */
public class GameController {
    /**
     * ---------- FXML UI ELEMENTS ----------
     */
    @FXML
    private Label levelLabel;
    @FXML
    private Label timeLabel;
    @FXML
    private Label wordLabel;
    @FXML
    private TextField entryTextField;
    @FXML
    private Label maxTimeLabel;


    /**
     * ---------- GAME STATE VARIABLES ----------
     */
    private int nivel = 1;
    private int tiempoRestante = 20;
    private int tiempoMaximo = 20;
    private String palabraActual;
    private Timeline timer;
    private boolean juegoActivo = true;
    private int success = 0;
    private int failure = 0;

    /**
     * Word pools
     */
    private Random random = new Random();
    private ArrayList<String> palabrasDisponiblesFaciles;
    private ArrayList<String> palabrasDisponiblesMedias;
    private ArrayList<String> palabrasDisponiblesDificiles;

    private final List<String> palabrasFaciles = Arrays.asList(
            "casa", "perro", "gato", "agua", "fuego",
            "libro", "mesa", "silla", "puerta", "ventana",
            "flores", "cielo", "tierra", "verde", "azul"
    );

    private final List<String> palabrasMedias = Arrays.asList(
            "computadora", "refrigerador", "bicicleta", "televisión", "automóvil",
            "supermercado", "restaurante", "biblioteca", "universidad", "hospital",
            "refrigeradora", "microondas", "lavadora", "aspiradora", "impresora"
    );

    private final List<String> palabrasDificiles = Arrays.asList(
            "electroencefalograma", "otorrinolaringología", "esternocleidomastoideo", "fotoestimulación", "desoxirribonucleico",
            "neurofibromatosis", "electromiográficos", "anticonstitucionalísimamente", "electrocardiografía", "psiconeuroendocrinología",
            "cardiotocografía", "neuropsiquiatría", "colangiopancreatografía", "espectrofotofluorometría", "magnetohidrodinámica"
    );
    /**
     * ---------- INITIALIZATION ----------
     */

    /**
     * Initializes the word pools for easy, medium, and hard difficulties.
     * Shuffles them to ensure randomness.
     */
    private void inicializarPalabrasDisponibles() {
        palabrasDisponiblesFaciles = new ArrayList<>(palabrasFaciles);
        palabrasDisponiblesMedias = new ArrayList<>(palabrasMedias);
        palabrasDisponiblesDificiles = new ArrayList<>(palabrasDificiles);

        Collections.shuffle(palabrasDisponiblesFaciles, random);
        Collections.shuffle(palabrasDisponiblesMedias, random);
        Collections.shuffle(palabrasDisponiblesDificiles, random);
    }

    /**
     * Called automatically after FXML loading.
     * Initializes the game state, selects the first word, and starts the timer.
     */
    @FXML
    private void initialize() {

        inicializarPalabrasDisponibles();

        seleccionarPalabra();
        actualizarInterfaz();
        iniciarTimer();


        entryTextField.setOnKeyPressed(this::manejarTeclaPresionada);


        entryTextField.requestFocus();
    }
    /**
     * ---------- WORD SELECTION ----------
     */

    /**
     * Selects the next word based on the current level.
     */
    private void seleccionarPalabra() {
        if (nivel <= 15) {
            palabraActual = obtenerPalabraAleatoria(palabrasDisponiblesFaciles, palabrasFaciles);

        } else if (nivel <= 30) {
            palabraActual = obtenerPalabraAleatoria(palabrasDisponiblesMedias, palabrasMedias);

        } else {
            palabraActual = obtenerPalabraAleatoria(palabrasDisponiblesDificiles, palabrasDificiles);

        }
        /**
         * Returns a random word from the available pool. Replenishes the pool if empty.
         *
         * @param palabrasDisponibles the mutable pool of remaining words
         * @param palabrasOriginales  the original full list for this difficulty
         * @return a random word
         */
    }
    private String obtenerPalabraAleatoria(ArrayList<String> palabrasDisponibles, List<String> palabrasOriginales) {

        if (palabrasDisponibles.isEmpty()) {
            palabrasDisponibles.addAll(palabrasOriginales);
            Collections.shuffle(palabrasDisponibles, random);
        }


        int indiceAleatorio = random.nextInt(palabrasDisponibles.size());
        return palabrasDisponibles.remove(indiceAleatorio);
    }
    /**
     * ---------- TIMER MANAGEMENT ----------
     */
    /**
     * Starts the countdown timer for the current word.
     */
    private void iniciarTimer() {
        detenerTimer();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            tiempoRestante--;
            actualizarInterfaz();

            if (tiempoRestante <= 0) {
                gameOver(false);
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    /**
     * Stops the active timer, if any.
     */
    private void detenerTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    /**
     * Resets the timer to the maximum allowed time and restarts it.
     */
    private void reiniciarTimer() {
        tiempoRestante = tiempoMaximo;
        iniciarTimer();
    }
    /**
     * ---------- INPUT HANDLING ----------
     */
    /**
     * Handles key press events in the text field.
     * Validates input on ENTER.
     *
     * @param event key event
     */
    private void manejarTeclaPresionada(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && juegoActivo) {
            validarPalabra();
        }
    }
    /**
     * Validates the player's typed word against the target word.
     * Updates score, level, and UI accordingly.
     */
    private void validarPalabra() {
        String palabraEscrita = entryTextField.getText().trim();

        if (palabraEscrita.equals(palabraActual)) {

            success++;
            nivel++;
            entryTextField.clear();


            int reduccion = ((nivel - 1) / 5) * 2;
            tiempoMaximo = Math.max(2, 20 - reduccion);


            seleccionarPalabra();


            reiniciarTimer();


            entryTextField.setStyle("-fx-background-color: #ccffcc; -fx-border-color: green;");



            Timeline feedback = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                if (juegoActivo) {
                    entryTextField.setStyle("");
                }
            }));
            feedback.play();


            if (nivel > 45) {
                juegoCompletado();
            }

        } else {

            failure++;


            entryTextField.setStyle("-fx-background-color: #ffcccc; -fx-border-color: red;");



            Timeline feedback = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (juegoActivo) {
                    entryTextField.setStyle("");
                }
            }));
            feedback.play();
        }

        actualizarInterfaz();
    }
    /**
     * ---------- GAME STATE ----------
     */
    /**
     * Ends the game and loads the Game Over screen.
     *
     * @param ganado true if the player won, false otherwise
     */
    private void gameOver(boolean ganado) {
        juegoActivo = false;
        detenerTimer();


        GameOverController.configurarResultado(ganado, nivel, success, failure);


        try {
            Parent root = FXMLLoader.load(getClass().getResource("GameOver.fxml"));
            Stage stage = (Stage) entryTextField.getScene().getWindow();
            Scene scene = new Scene(root, 600, 600);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Called when the player completes all levels.
     */
    private void juegoCompletado() {
        gameOver(true); // true = ganó el juego
    }
    /**
     * Resets the game to its initial state.
     */
    private void reiniciarJuego() {
        nivel = 1;
        tiempoMaximo = 20;
        tiempoRestante = 20;
        juegoActivo = true;
        success = 0;
        failure = 0;
        entryTextField.clear();
        entryTextField.setStyle("");
        inicializarPalabrasDisponibles();

        seleccionarPalabra();
        iniciarTimer();
        actualizarInterfaz();
        entryTextField.requestFocus();
    }
    /**
     * ---------- UI UPDATES ----------
     */
    /**
     * Updates labels and UI elements (level, time, word).
     */
    private void actualizarInterfaz() {
        if (maxTimeLabel != null) {
            int tiempomaximo = tiempoMaximo;
            maxTimeLabel.setText("Tiempo max: " + tiempomaximo);
        }
        if (levelLabel != null) {
            String dificultad = obtenerDificultad();
            levelLabel.setText("Nivel: " + nivel + " (" + dificultad + ")");
        }
        if (timeLabel != null) {
            timeLabel.setText(String.valueOf(tiempoRestante));

            // Cambiar color según el tiempo restante
            if (tiempoRestante <= 3) {
                timeLabel.setStyle("-fx-text-fill: red;");
            } else if (tiempoRestante <= 7) {
                timeLabel.setStyle("-fx-text-fill: orange;");
            } else {
                timeLabel.setStyle("-fx-text-fill: white;");
            }
        }
        if (wordLabel != null) {
            wordLabel.setText(palabraActual);
        }
    }
    /**
     * Returns the difficulty label based on the current level.
     *
     * @return "Fácil", "Media", or "Difícil"
     */
    private String obtenerDificultad() {
        if (nivel <= 15) {
            return "Fácil";
        } else if (nivel <= 30) {
            return "Media";
        } else {
            return "Difícil";
        }
    }
    /**
     * ---------- UI BUTTON HANDLERS ----------
     */
    /**
     * Returns to the main menu, stopping the timer.
     *
     * @param event button click event
     */
    @FXML
    private void volverAlMenu(ActionEvent event) {
        // Detener el timer antes de salir
        detenerTimer();
        juegoActivo = false;

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
    /**
     * Toggles pause/resume state of the game.
     *
     * @param event button click event
     */
    @FXML
    private void pausarJuego(ActionEvent event) {
        if (timer != null) {
            if (juegoActivo) {
                detenerTimer();
                juegoActivo = false;
            } else {
                iniciarTimer();
                juegoActivo = true;
            }
        }
    }
    /**
     * ---------- GETTERS ----------
     */

    /** @return number of successful attempts */
    public int getSuccess() {
        return success;
    }
    /** @return number of failed attempts */
    public int getFailure() {
        return failure;
    }
    /** @return current level */

    public int getNivel() {
        return nivel;
    }
}
