package application;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class Main extends Application {
    Stage window;
    Scene scene1, scene2, questionScene;
    ChoiceBox<String> question1ChoiceBox = new ChoiceBox<>();
    ChoiceBox<String> question2ChoiceBox = new ChoiceBox<>();
    ChoiceBox<String> question3ChoiceBox = new ChoiceBox<>();

    ArrayList<String> mathLevels = new ArrayList<>(Arrays.asList(
            "Math 091 (Int. Alg. I)",
            "Math 092 (Int. Alg. II)",
            "Math& 141 (Precalculus I)",
            "Math 142 (Precalculus II)",
            "Math& 151 (Calculus I)",
            "Math& 152 (Calculus II)",
            "Math& 163 (Calculus 3)",
            "Math& 146 (Intro. to Stats.)"));

    private VBox panel;
    private int pasteIndex = -1;

    // Empty string instance to store copied text
    private String copyClass = "";

    @Override
    public void start(Stage primaryStage) {
        try {
            window = primaryStage;

            Button button1 = new Button("Academic Plan");
            button1.setOnAction(e -> window.setScene(scene2));

            VBox layout1 = new VBox(20);
            layout1.getChildren().addAll(button1);
            scene1 = new Scene(layout1, 200, 200);

            VBox layout2 = new VBox(20);
            Button button2 = new Button("Create your plan");
            button2.setOnAction(e -> academicPlan());

            scene2 = new Scene(layout2, 400, 400);

            Label question1Label = new Label("What university are you transferring to?");
            question1ChoiceBox.getItems().addAll("UW Bothell");
            VBox question1Box = new VBox(10, question1Label, question1ChoiceBox);
            question1Box.setAlignment(Pos.CENTER);

            Label question2Label = new Label("What math level would you like to start at?");
            question2ChoiceBox.getItems().addAll(mathLevels);
            VBox question2Box = new VBox(10, question2Label, question2ChoiceBox);
            question2Box.setAlignment(Pos.CENTER);

            Label question3Label = new Label("C++ or Java ?");
            question3ChoiceBox.getItems().addAll("C++", "Java");
            VBox question3Box = new VBox(10, question3Label, question3ChoiceBox);
            question3Box.setAlignment(Pos.CENTER);

            layout2.getChildren().addAll(question1Box, question2Box, question3Box, button2);
            layout2.setAlignment(Pos.CENTER);
            layout2.setPadding(new Insets(20, 40, 20, 40));

            primaryStage.setScene(scene1);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void academicPlan() {
        HBox newSceneRoot = new HBox(20);
        String selectedUniversity = question1ChoiceBox.getValue();
        String selectedMathLevel = question2ChoiceBox.getValue();
        String selectedCSCourse = question3ChoiceBox.getValue();
        Button button = new Button();
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int quarterNumber = row * 3 + col + 1;
                TitledPane titledPane = new TitledPane();
                titledPane.setText("Quarter " + quarterNumber);

                VBox panel = createPanel(quarterNumber);

                Button addButton = new Button("Add");
                addButton.setOnAction(e -> newButton(addButton));
                panel.getChildren().addAll(addButton, createPasteButton(panel));

                titledPane.setContent(panel);
                gridPane.add(titledPane, col, row);
            }
        }

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(gridPane, 600, 400);
        window.setScene(scene);
        window.setTitle("TitledPane and Buttons Example");
        window.show();
    }

    private VBox createPanel(int quarterNumber) {
        String selectedUniversity = question1ChoiceBox.getValue();
        String selectedMathLevel = question2ChoiceBox.getValue();
        String selectedCSCourse = question3ChoiceBox.getValue();
        if (selectedUniversity != null && selectedUniversity.equals("UW Bothell")) {
            mathLevels.remove("Math& 163 (Calculus 3)");
        }

        panel = new VBox(10);
        int levelsPerQuarter = 1;

        int startIndex = (quarterNumber - 1) * levelsPerQuarter + mathLevels.indexOf(selectedMathLevel);
        int endIndex = Math.min(startIndex + levelsPerQuarter, mathLevels.size());

        for (int i = startIndex; i < endIndex; i++) {
            Button mathbtn = new Button(mathLevels.get(i));
            mathbtn.setOnAction(e -> handleButtonAction(mathbtn));
            panel.getChildren().add(mathbtn);
        }

        return panel;
    }

    private void handleButtonAction(Button button) {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        TextField textField = new TextField("Enter new text");
        Button changeTextBtn = new Button("Change Text");
        Button deleteBtn = new Button("Delete");
        Button moveBtn = new Button("Move");

        changeTextBtn.setOnAction(e -> {
            button.setText(textField.getText());
            stage.close();
        });

        deleteBtn.setOnAction(e -> {
            ((VBox) button.getParent()).getChildren().remove(button);
            stage.close();
        });

        moveBtn.setOnAction(e -> {
            copyClass = button.getText();
            ((VBox) button.getParent()).getChildren().remove(button);
            System.out.println("Text copied: " + copyClass);
        });

        vbox.getChildren().addAll(textField, changeTextBtn, deleteBtn, moveBtn);
        Scene scene = new Scene(vbox, 200, 150);
        stage.setScene(scene);
        stage.setTitle("Edit Button");
        stage.show();
    }

    public void newButton(Button button) {
        Button newBtn = new Button("New Button");
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);

        Scene currentScene = parentVBox.getScene();
        Scene newScene = new Scene(parentVBox, currentScene.getWidth(), currentScene.getHeight());

        window.setScene(newScene);
    }

    private Button createPasteButton(VBox parentVBox) {
        Button pasteBtn = new Button("Paste");
        pasteBtn.setOnAction(e -> {
            handlePasteButtonAction(parentVBox);
            pasteIndex = parentVBox.getChildren().indexOf(pasteBtn);
        });

        return pasteBtn;
    }

    private void handlePasteButtonAction(VBox parentVBox) {
        if (!copyClass.isEmpty() && pasteIndex != -1) {
            Button pasteBtn = new Button(copyClass);
            pasteBtn.setOnAction(e -> handleButtonAction(pasteBtn));
            parentVBox.getChildren().add(pasteIndex, pasteBtn);

            // Clear the copied text after pasting
            copyClass = "";
        } else {
            System.out.println("Nothing to paste. Copy a button first.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
