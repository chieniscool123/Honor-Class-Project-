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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {
    Stage window;
    Scene scene1, scene2, questionScene;
    ChoiceBox<String> question1ChoiceBox = new ChoiceBox<>();
    ChoiceBox<String> question2ChoiceBox = new ChoiceBox<>();
    ChoiceBox<String> question3ChoiceBox = new ChoiceBox<>();
    private Button electiveButton;

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

    private static Map<String, String> humanitiesClasses = new HashMap<>();
    private static Map<String, String> socialScienceClasses = new HashMap<>(); // Added Social Science classes map

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

            Label question3Label = new Label("C++ or Java?");
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
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                int quarterNumber = row * 3 + col + 1;
                TitledPane titledPane = new TitledPane();
                titledPane.setText("Quarter " + quarterNumber);

                VBox panel = createPanel(quarterNumber);

                Button addButton = new Button();
                Button pasteButton = createPasteButton(panel);

                if (row < 2) {
                    Button electiveButton = createElectiveButton();
                    panel.getChildren().add(electiveButton);
                }
                addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                addButton.setOnAction(e -> newButton(addButton));

                panel.getChildren().addAll(addButton, pasteButton);

                titledPane.setContent(panel);
                gridPane.add(titledPane, col, row);
            }
        }

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        Scene scene = new Scene(gridPane, 600, 550);
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
        Button pasteBtn = new Button();
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

            copyClass = "";
        } else {
            System.out.println("Nothing to paste. Copy a button first.");
        }
    }

    private Button createElectiveButton() {
        Button electiveButton = new Button("Elective");
        electiveButton.setOnAction(e -> handleElectiveButtonAction(electiveButton));
        return electiveButton;
    }

    private void handleElectiveButtonAction(Button electiveButton) {
        Stage electiveStage = new Stage();
        electiveStage.setTitle("Select Elective");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        // First ChoiceBox for selecting class credits
        ChoiceBox<String> classCreditChoiceBox = new ChoiceBox<>();
        classCreditChoiceBox.getItems().addAll("Humanities", "Social Science");
        HBox classCreditBox = createChoiceBox("Select your class credits:", classCreditChoiceBox);

        // Second ChoiceBox for selecting class categories
        ChoiceBox<String> classCategoryChoiceBox = new ChoiceBox<>();
        HBox classCategoryBox = createChoiceBox("Select your class categories:", classCategoryChoiceBox);

        // Third ChoiceBox for selecting classes
        ChoiceBox<String> classChoiceBox = new ChoiceBox<>();
        HBox classBox = createChoiceBox("Select your classes:", classChoiceBox);

        // Add an event listener to classCreditChoiceBox to update the available class categories in classCategoryChoiceBox based on the selected class credits
        classCreditChoiceBox.setOnAction(e -> {
            String selectedClassCredit = classCreditChoiceBox.getValue();
            if ("Humanities".equals(selectedClassCredit)) {
                String filePath = "Humanities.txt";
                humanitiesClasses = readHumanitiesFile(filePath);

                // Populate classCategoryChoiceBox with the keys from humanitiesClasses (sorted alphabetically)
                classCategoryChoiceBox.getItems().clear();
                classCategoryChoiceBox.getItems().addAll(humanitiesClasses.keySet().stream().sorted().collect(Collectors.toList()));
            } else if ("Social Science".equals(selectedClassCredit)) {
                String filePath = "SocialScience.txt";
                socialScienceClasses = readSocialScienceFile(filePath);

                // Populate classCategoryChoiceBox with the keys from socialScienceClasses (sorted alphabetically)
                classCategoryChoiceBox.getItems().clear();
                classCategoryChoiceBox.getItems().addAll(socialScienceClasses.keySet().stream().sorted().collect(Collectors.toList()));
            }
        });

        // Add an event listener to classCategoryChoiceBox to update the available classes in classChoiceBox based on the selected category
        classCategoryChoiceBox.setOnAction(e -> {
            String selectedCategory = classCategoryChoiceBox.getValue();
            if (socialScienceClasses.containsKey(selectedCategory)) {
                // Populate classChoiceBox with the values (classes) from the selected category
                classChoiceBox.getItems().clear();
                classChoiceBox.getItems().addAll(Arrays.asList(socialScienceClasses.get(selectedCategory).split(", ")));
            }
        });
        
        classCategoryChoiceBox.setOnAction(e -> {
            String selectedCategory = classCategoryChoiceBox.getValue();
            if (humanitiesClasses.containsKey(selectedCategory)) {
                // Populate classChoiceBox with the values (classes) from the selected category
                classChoiceBox.getItems().clear();
                classChoiceBox.getItems().addAll(Arrays.asList(humanitiesClasses.get(selectedCategory).split(", ")));
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> setElectiveButton(electiveButton, classCategoryChoiceBox, classChoiceBox));

        layout.getChildren().addAll(classCreditBox, classCategoryBox, classBox, nextButton);

        Scene electiveScene = new Scene(layout, 400, 300);
        electiveStage.setScene(electiveScene);
        electiveStage.show();
    }

    private void setElectiveButton(Button electiveButton, ChoiceBox<String> classCategoryChoiceBox, ChoiceBox<String> classChoiceBox) {
        String selectedCategory = classCategoryChoiceBox.getValue();
        String selectedClass = classChoiceBox.getValue();
        if (selectedCategory != null && selectedClass != null) {
            electiveButton.setText(selectedCategory + " " + selectedClass);
        }
    }

    private HBox createChoiceBox(String labelText, ChoiceBox<String> choiceBox) {
        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);

        Label label = new Label(labelText);

        hbox.getChildren().addAll(label, choiceBox);
        return hbox;
    }

    private static Map<String, String> readHumanitiesFile(String filePath) {
        Map<String, String> humanitiesClasses = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentCategory = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String category = parts[0].trim();
                    String classes = parts[1].trim();
                    humanitiesClasses.put(category, classes);
                    currentCategory = category;
                } else if (currentCategory != null) {
                    String classes = line.trim();
                    String existingClasses = humanitiesClasses.get(currentCategory);
                    humanitiesClasses.put(currentCategory, existingClasses + ", " + classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return humanitiesClasses;
    }

    private static Map<String, String> readSocialScienceFile(String filePath) {
        Map<String, String> socialScienceClasses = new HashMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            String currentCategory = null;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2) {
                    String category = parts[0].trim();
                    String classes = parts[1].trim();
                    socialScienceClasses.put(category, classes);
                    currentCategory = category;
                } else if (currentCategory != null) {
                    String classes = line.trim();
                    String existingClasses = socialScienceClasses.get(currentCategory);
                    socialScienceClasses.put(currentCategory, existingClasses + ", " + classes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return socialScienceClasses;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
