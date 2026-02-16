package nexus;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import nexus.exception.NexusException;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Nexus nexus;

    private final Image userImage = new Image(this.getClass().getResourceAsStream("/images/student-user.png"));
    private final Image nexusImage = new Image(this.getClass().getResourceAsStream("/images/nexus-chatbot.png"));

    /**
     * Initializes the main window components and bindings.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        scrollPane.setFitToWidth(true);
    }

    /**
     * Injects the Nexus instance.
     * @param n Nexus instance.
     */
    public void setNexus(Nexus n) {
        nexus = n;
        String firstGreeting = "// INITIALIZING NEXUS... [OK] \n// LOADING DATABANK... [OK]";
        String secondGreeting = "[NEXUS]: Greetings. I am Nexus, your personal chatbot.\nHow can I assist you today?";

        dialogContainer.getChildren().add(
                DialogBox.getNexusDialog(firstGreeting, nexusImage, "greeting")
        );

        dialogContainer.getChildren().add(
                DialogBox.getNexusDialog(secondGreeting, nexusImage, "greeting")
        );
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Nexus' reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();

        if (input.trim().isEmpty()) {
            return;
        }

        try {
            String response = nexus.getResponse(input);
            String commandType = nexus.getCommandType();

            dialogContainer.getChildren().addAll(
                    DialogBox.getUserDialog(input, userImage),
                    DialogBox.getNexusDialog(response, nexusImage, commandType)
            );
        } catch (NexusException e) {
            // return ui.printError(e.getMessage());
            dialogContainer.getChildren().add(DialogBox.getNexusDialog(e.getMessage(), nexusImage, "Error"));
        }

        userInput.clear();

        if (input.equalsIgnoreCase("bye")) {
            PauseTransition delay = new PauseTransition(Duration.seconds(1));

            delay.setOnFinished(event -> Platform.exit());

            delay.play();
        }
    }
}
