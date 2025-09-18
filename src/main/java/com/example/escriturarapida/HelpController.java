
package com.example.escriturarapida;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Controller for the Help screen.
 *
 * <p>This class manages the Help view, which provides instructions
 * or information about the game. It allows the player to return
 * to the main menu.</p>
 *
 * <p>Main responsibilities:</p>
 * <ul>
 *   <li>Loading the Main Menu when the player chooses to go back.</li>
 * </ul>
 *
 * @author
 *   Juan Manuel Muñoz
 * @version
 *   1.0
 */

public class HelpController {
    /**
     * Returns to the Main Menu screen when the player clicks "Back".
     *
     * @param event button click event
     */
    @FXML
    private void volver(ActionEvent event) {
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