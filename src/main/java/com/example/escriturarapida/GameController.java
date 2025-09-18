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
    private int level = 1;
    private int timeLeft = 20;
    private int maxTime = 20;
    private String currentWord;
    private Timeline timer;
    private boolean gameActive = true;
    private int success = 0;
    private int failure = 0;

    /**
     * Word pools
     */
    private Random random = new Random();
    private ArrayList<String> easyWordsLeft;
    private ArrayList<String> mediumWordsLeft;
    private ArrayList<String> hardWordsLeft;

    private final List<String> easyWords = Arrays.asList(
            "casa", "perro", "gato", "agua", "fuego",
            "libro", "mesa", "silla", "puerta", "ventana",
            "flores", "cielo", "tierra", "verde", "azul"
    );

    private final List<String> mediumWords = Arrays.asList(
            "computadora", "refrigerador", "bicicleta", "televisión", "automóvil",
            "supermercado", "restaurante", "biblioteca", "universidad", "hospital",
            "refrigeradora", "microondas", "lavadora", "aspiradora", "impresora"
    );

    private final List<String> hardWords = Arrays.asList(
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
    private void iniatilizateWordsLeft() {
        easyWordsLeft = new ArrayList<>(easyWords);
        mediumWordsLeft = new ArrayList<>(mediumWords);
        hardWordsLeft = new ArrayList<>(hardWords);

        Collections.shuffle(easyWordsLeft, random);
        Collections.shuffle(mediumWordsLeft, random);
        Collections.shuffle(hardWordsLeft, random);
    }

    /**
     * Called automatically after FXML loading.
     * Initializes the game state, selects the first word, and starts the timer.
     */
    @FXML
    private void initialize() {

        iniatilizateWordsLeft();

        selectWord();
        updateScene();
        startTimer();


        entryTextField.setOnKeyPressed(this::keyHandling);


        entryTextField.requestFocus();
    }
    /**
     * ---------- WORD SELECTION ----------
     */

    /**
     * Selects the next word based on the current level.
     */
    private void selectWord() {
        if (level <= 15) {
            currentWord = getRandomWord(easyWordsLeft, easyWords);

        } else if (level <= 30) {
            currentWord = getRandomWord(mediumWordsLeft, mediumWords);

        } else {
            currentWord = getRandomWord(hardWordsLeft, hardWords);

        }
        /**
         * Returns a random word from the available pool. Replenishes the pool if empty.
         *
         * @param wordsLeft the mutable pool of remaining words
         * @param originalWords  the original full list for this difficulty
         * @return a random word
         */
    }
    private String getRandomWord(ArrayList<String> wordsLeft, List<String> originalWords) {

        if (wordsLeft.isEmpty()) {
            wordsLeft.addAll(originalWords);
            Collections.shuffle(wordsLeft, random);
        }


        int randomIndex = random.nextInt(wordsLeft.size());
        return wordsLeft.remove(randomIndex);
    }
    /**
     * ---------- TIMER MANAGEMENT ----------
     */
    /**
     * Starts the countdown timer for the current word.
     */
    private void startTimer() {
        stopTimer();

        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            timeLeft--;
            updateScene();

            if (timeLeft <= 0) {
                gameOver(false);
            }
        }));

        timer.setCycleCount(Timeline.INDEFINITE);
        timer.play();
    }
    /**
     * Stops the active timer, if any.
     */
    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
    }
    /**
     * Resets the timer to the maximum allowed time and restarts it.
     */
    private void resetTimer() {
        timeLeft = maxTime;
        startTimer();
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
    private void keyHandling(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && gameActive) {
            wordValidation();
        }
    }
    /**
     * Validates the player's typed word against the target word.
     * Updates score, level, and UI accordingly.
     */
    private void wordValidation() {
        String palabraEscrita = entryTextField.getText().trim();

        if (palabraEscrita.equals(currentWord)) {

            success++;
            level++;
            entryTextField.clear();


            int reduccion = ((level - 1) / 5) * 2;
            maxTime = Math.max(2, 20 - reduccion);


            selectWord();


            resetTimer();


            entryTextField.setStyle("-fx-background-color: #ccffcc; -fx-border-color: green;");



            Timeline feedback = new Timeline(new KeyFrame(Duration.millis(500), e -> {
                if (gameActive) {
                    entryTextField.setStyle("");
                }
            }));
            feedback.play();


            if (level > 45) {
                gameComplete();
            }

        } else {

            failure++;


            entryTextField.setStyle("-fx-background-color: #ffcccc; -fx-border-color: red;");



            Timeline feedback = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
                if (gameActive) {
                    entryTextField.setStyle("");
                }
            }));
            feedback.play();
        }

        updateScene();
    }
    /**
     * ---------- GAME STATE ----------
     */
    /**
     * Ends the game and loads the Game Over screen.
     *
     * @param win true if the player won, false otherwise
     */
    private void gameOver(boolean win) {
        gameActive = false;
        stopTimer();


        GameOverController.resultConfiguration(win, level, success, failure);


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
    private void gameComplete() {
        gameOver(true);
    }
    /**
     * Resets the game to its initial state.
     */
    private void resetGame() {
        level = 1;
        maxTime = 20;
        timeLeft = 20;
        gameActive = true;
        success = 0;
        failure = 0;
        entryTextField.clear();
        entryTextField.setStyle("");
        iniatilizateWordsLeft();

        selectWord();
        startTimer();
        updateScene();
        entryTextField.requestFocus();
    }
    /**
     * ---------- UI UPDATES ----------
     */
    /**
     * Updates labels and UI elements (level, time, word).
     */
    private void updateScene() {
        if (maxTimeLabel != null) {
            int tiempomaximo = maxTime;
            maxTimeLabel.setText("Tiempo max: " + tiempomaximo);
        }
        if (levelLabel != null) {
            String dificultad = getDificulty();
            levelLabel.setText("Nivel: " + level + " (" + dificultad + ")");
        }
        if (timeLabel != null) {
            timeLabel.setText(String.valueOf(timeLeft));

            // Cambiar color según el tiempo restante
            if (timeLeft <= 3) {
                timeLabel.setStyle("-fx-text-fill: red;");
            } else if (timeLeft <= 7) {
                timeLabel.setStyle("-fx-text-fill: orange;");
            } else {
                timeLabel.setStyle("-fx-text-fill: white;");
            }
        }
        if (wordLabel != null) {
            wordLabel.setText(currentWord);
        }
    }
    /**
     * Returns the difficulty label based on the current level.
     *
     * @return "Fácil", "Media", or "Difícil"
     */
    private String getDificulty() {
        if (level <= 15) {
            return "Fácil";
        } else if (level <= 30) {
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
    private void backToMenu(ActionEvent event) {
        // Detener el timer antes de salir
        stopTimer();
        gameActive = false;

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
    private void pauseGame(ActionEvent event) {
        if (timer != null) {
            if (gameActive) {
                stopTimer();
                gameActive = false;
            } else {
                startTimer();
                gameActive = true;
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

    public int getLevel() {
        return level;
    }
}
