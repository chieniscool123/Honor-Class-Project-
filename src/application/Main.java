package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main extends Application {
    private Stage window;
    private Scene scene1, scene2;
    private ChoiceBox<String> question1ChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> question2ChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> question3ChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> question4ChoiceBox = new ChoiceBox<>();
    private ChoiceBox<String> question5ChoiceBox = new ChoiceBox<>();
    private List<TitledPane> titledPanes = new ArrayList<>();
    private int pasteIndex = -1;
    private String copyClass = "";
    private static Map<String, String> humanitiesClasses = new HashMap<>();
    private static Map<String, String> socialScienceClasses = new HashMap<>();

    private ArrayList<String> mathLevels = new ArrayList<>(Arrays.asList(
            "Math 091 (Int. Alg. I)",
            "Math 092 (Int. Alg. II)",
            "Math& 141 (Precalculus I)",
            "Math 142 (Precalculus II)",
            "Math& 151 (Calculus I)",
            "Math& 152 (Calculus II)",
            "Math& 163 (Calculus 3)",
            "Math& 146 (Intro. to Stats.)"));

    private ArrayList<String> cPlusPlusClasses = new ArrayList<>(Arrays.asList(
            "CS 110 Intro to CS",
            "CS& 131 C++ I",
            "CS 132 C++ II"));

    private ArrayList<String> javaClasses = new ArrayList<>(Arrays.asList(
            "CS 110 Intro to CS",
            "CS& 141 Java I",
            "CS 143 Java II"));

    @Override
    public void start(Stage primaryStage) {
        try {
            window = primaryStage;

            VBox layout1 = new VBox(20);
            Button button1 = new Button("Academic Plan");
            button1.setOnAction(e -> {
                window.setScene(scene2);
                window.centerOnScreen();
            });

            layout1.getChildren().addAll(button1);

            VBox layout2 = new VBox(20);
            Button button2 = new Button("Create your plan");
            Button button3 = new Button("Back");
            button2.setOnAction(e -> academicPlan());
            button3.setOnAction(e -> {
                primaryStage.setScene(scene1);
                primaryStage.centerOnScreen();
            });

            scene1 = new Scene(layout1, 200, 200);
            scene2 = new Scene(layout2, 400, 450);

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

            Label question4Label = new Label("What year are starting?");
            question4ChoiceBox.getItems().addAll("2024", "2025", "2026", "2027", "2028", "2029", "2030");
            VBox question4Box = new VBox(10, question4Label, question4ChoiceBox);
            question4Box.setAlignment(Pos.CENTER);

            Label question5Label = new Label("Summer");
            question5ChoiceBox.getItems().addAll("Yes", "No");
            VBox question5Box = new VBox(10, question5Label, question5ChoiceBox);
            question5Box.setAlignment(Pos.CENTER);

            layout2.getChildren().addAll(question1Box, question2Box, question3Box, question4Box, question5Box, button2, button3);
            layout2.setAlignment(Pos.CENTER);
            layout2.setPadding(new Insets(20, 40, 20, 40));

            primaryStage.setScene(scene1);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void academicPlan() {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);
        ArrayList<String> quarter = new ArrayList<>();
        quarter.add("Fall");
        quarter.add("Winter");
        quarter.add("Spring");
        quarter.add("Summer");
        VBox buttonVbox = new VBox(20);
        Button addQuarterBtn = new Button("Add Quarter");
        Button removeQuarterBtn = new Button("Remove Quarter");
        Button backBtn = new Button("Back");
        buttonVbox.getChildren().addAll(addQuarterBtn, removeQuarterBtn, backBtn);
        buttonVbox.setAlignment(Pos.CENTER);

        TextArea textArea = new TextArea();
        textArea.setText("Rule 1 ");
        textArea.setEditable(false);
        textArea.setPrefSize(200, 100);

        int selectedYear = Integer.parseInt(question4ChoiceBox.getValue());
        String selectedSummer = question5ChoiceBox.getValue();
        int summerOn = selectedSummer.equals("Yes") ? 1 : 0;
    	
        //  Starting position for the manually added row and column
        int[] newCol = {0};
        int[] newRow = {3};

        // Adjusting the size of the Academic Planning scene based on "Summer" question 
        Scene scene;
        if (summerOn == 1) {
            scene = new Scene(new ScrollPane(gridPane), 1250, 560);
        } else {
            scene = new Scene(new ScrollPane(gridPane), 1000, 560);
        }

    
        // For loop to create a academic plan based on choiceBoxes results. Both will have two rows of quarters equal to two years of school/
        for (int row = 0; row < 2; row++) {
        	
        	// How many quarter per year will based on whether the user chooses summer or not. 
            for (int col = 0; col < (3 + summerOn); col++) {
                int quarterNumber = row * 3 + col + 1;
                
                // Automatically created tilePane
                // Season and year are auto adjusted based on the "quarter" ArrayList and question4ChoiceBox
                TitledPane titledPane = new TitledPane();
                titledPane.setText(quarter.get(col) + " " + selectedYear);

                // Summer Plan
                if (quarter.get(col).equalsIgnoreCase("Summer")) {
                	
                	//  keeping track of the season using the first index value
                    int[] seasonChangedVariable = {0};
                    
                    // getting the selected Year from choice box #4
                    int selectedYr = Integer.parseInt(question4ChoiceBox.getValue());
                    
                    // adding two years to the starting years since that is where the newly added quarter will start
                    int[] yearChangeVariable = {selectedYr + 2};

                    // add Quarter button for summer plan
                    addQuarterBtn.setOnAction(e -> {
                        TitledPane newTitledPane = new TitledPane();
                        titledPanes.add(newTitledPane);
                        
                        // adding text to the the tilePane
                        newTitledPane.setText(quarter.get(seasonChangedVariable[0]) + " " + yearChangeVariable[0]);
                        
                        // Moving to the next index and getting the next season within the "quarter" arrayList
                        // if index value is equal to 4. Reset the value of seasonChangedVariable to 0 and add 1 to the year
                        seasonChangedVariable[0]++;
                       if (seasonChangedVariable[0] == 4) {
                            seasonChangedVariable[0] = 0;
                            yearChangeVariable[0]++;
                        }
                        
                       	VBox panel2 = createPanel(10);
                       
                       	Button addButton = new Button();
                        addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                        addButton.setOnAction(event -> showAddOptions(addButton));

                        
                        Button pasteButton = createPasteButton(panel2);
                        pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));


                        panel2.getChildren().addAll(addButton, pasteButton);

                        newTitledPane.setPrefWidth(190);
                        newTitledPane.setPrefHeight(250);
                        newTitledPane.setContent(panel2);
                        
                        // setting the position of the new quarter 
                        //change column after every successful addition 
                        // once 4 is added on one row. Move on to the next row
                        gridPane.add(newTitledPane, newCol[0], newRow[0]);
                        newCol[0]++; 
                        if (newCol[0] == 4) {
                        	newRow[0]++;
                            newCol[0] = 0;
                        }
                    });

                    removeQuarterBtn.setOnAction(e -> {
                    	// remove the last tilePane from the list
                    	// sub tract 1 from the newCol and seasonChanged value
                    	// make sure seasonVar and newCol don't do below 1 
                        int lastIndex = titledPanes.size() - 1;
                        if (lastIndex >= 0) {
                            TitledPane lastTitledPane = titledPanes.get(lastIndex);
                            gridPane.getChildren().remove(lastTitledPane);
                            titledPanes.remove(lastIndex);
                            newCol[0]--;
                            seasonChangedVariable[0]--;
                            if (seasonChangedVariable[0] < 0) {
                                seasonChangedVariable[0] = 0;
                            } else if (newCol[0] < 0) {
                                newCol[0] = 0;
                            }

                        }});

                    
                    // send you back to scene2 
                    backBtn.setOnAction(e -> {
                        Platform.runLater(() -> {
                            window.setScene(scene2);
                            window.centerOnScreen();
                        });
                    });

                    VBox panel = new VBox(10);
                    Button addButton = new Button();
                    Button pasteButton = createPasteButton(panel);

                    addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                    pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                    addButton.setOnAction(e -> showAddOptions(addButton));
                    panel.getChildren().addAll(addButton, pasteButton);
                    titledPane.setPrefWidth(190);
                    titledPane.setPrefHeight(250);
                    titledPane.setContent(panel);
                    gridPane.add(titledPane, col, row);
                } else {
                    int[] seasonChangedVariable = {0};
                    int selectedYr = Integer.parseInt(question4ChoiceBox.getValue());
                    int[] yearChangeVariable = {selectedYr + 2};

                    addQuarterBtn.setOnAction(e -> {
                        TitledPane newTitledPane = new TitledPane();
                        titledPanes.add(newTitledPane);

                        newTitledPane.setText(quarter.get(seasonChangedVariable[0]) + " " + yearChangeVariable[0]);
                        seasonChangedVariable[0]++;
                        if (seasonChangedVariable[0] == 3) {
                            seasonChangedVariable[0] = 0;
                            yearChangeVariable[0]++;
                        }

                        VBox panel2 = createPanel(10);

                        Button addButton = new Button();
                        Button pasteButton = createPasteButton(panel2);

                        addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                        pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                        addButton.setOnAction(event -> showAddOptions(addButton));

                        panel2.getChildren().addAll(addButton, pasteButton);

                        newTitledPane.setPrefWidth(190);
                        newTitledPane.setPrefHeight(250);
                        newTitledPane.setContent(panel2);
                        gridPane.add(newTitledPane, newCol[0], newRow[0]);
                        newCol[0]++;
                        if (newCol[0] == 3) {
                        	newRow[0]++;
                            newCol[0] = 0;
                        }
                    });

                    removeQuarterBtn.setOnAction(e -> {
                        int lastIndex = titledPanes.size() - 1;
                        if (lastIndex >= 0) {
                            TitledPane lastTitledPane = titledPanes.get(lastIndex);
                            gridPane.getChildren().remove(lastTitledPane);
                            titledPanes.remove(lastIndex);
                            newCol[0]--;
                            seasonChangedVariable[0]--;
                            if (seasonChangedVariable[0] < 0) {
                                seasonChangedVariable[0] = 0;
                            } else if (newCol[0] < 0) {
                                newCol[0] = 0;
                            }

                        }
                    });

                    backBtn.setOnAction(e -> {
                        Platform.runLater(() -> {
                            window.setScene(scene2);
                            window.centerOnScreen();
                        });
                    });

                    VBox panel = createPanel(quarterNumber);
                    Button addButton = new Button();
                    Button pasteButton = createPasteButton(panel);

                    if (row < 2) {
                        Button electiveButton = createElectiveButton();
                        panel.getChildren().add(electiveButton);
                    }

                    addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                    pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                    addButton.setOnAction(e -> showAddOptions(addButton));
                    panel.getChildren().addAll(addButton, pasteButton);

                    titledPane.setPrefWidth(190);
                    titledPane.setPrefHeight(250);
                    titledPane.setContent(panel);

                    gridPane.add(titledPane, col, row);
                }
            }
            selectedYear++;
        }

        gridPane.add(textArea, 3 + summerOn, 0);
        gridPane.add(buttonVbox, 3 + summerOn, 1, 1, 1);

        gridPane.setPadding(new Insets(10, 10, 10, 10));

        window.setScene(scene);
        window.setTitle("Academic Planning");
        scene.getWindow().centerOnScreen();

        window.show();
    }



    private VBox createPanel(int quarterNumber) {
        String selectedUniversity = question1ChoiceBox.getValue();
        String selectedMathLevel = question2ChoiceBox.getValue();
        String selectedCSCourse = question3ChoiceBox.getValue();

        if (selectedUniversity != null && selectedUniversity.equals("UW Bothell")) {
            mathLevels.remove("Math& 163 (Calculus 3)");
        }

        VBox panel = new VBox(10);

        // Check if Math classes are selected
        if (selectedMathLevel != null ) {
        	
            int levelsPerQuarter = 1;
            int startIndex = (quarterNumber - 1) * levelsPerQuarter + mathLevels.indexOf(selectedMathLevel);
            int endIndex = Math.min(startIndex + levelsPerQuarter, mathLevels.size());

            for (int i = startIndex; i < endIndex; i++ ) {
                Button mathButton = new Button(mathLevels.get(i));
                mathButton.setOnAction(e -> handleButtonAction(mathButton));
                panel.getChildren().add(mathButton);
            }
        }

        // Check if CS classes are selected
        if (selectedCSCourse != null) {
            ArrayList<String> selectedClasses;
            if ("C++".equals(selectedCSCourse)) {
                selectedClasses = cPlusPlusClasses;
            } else if ("Java".equals(selectedCSCourse)) {
                selectedClasses = javaClasses;
            } else {
                // Handle other cases or set a default value
                selectedClasses = new ArrayList<>();
            }

            if (selectedClasses != null && !selectedClasses.isEmpty()) {
                int levelsPerQuarter = 1;
                int startIndex = (quarterNumber - 1) * levelsPerQuarter;
                int endIndex = Math.min(startIndex + levelsPerQuarter, selectedClasses.size());

                for (int i = startIndex; i < endIndex; i++) {
       
                    Button classButton = new Button(selectedClasses.get(i));
                    classButton.setOnAction(e -> handleButtonAction(classButton));
                    panel.getChildren().add(classButton);
                }
            }
        }

        return panel;
    }



    private void showAddOptions(Button button) {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        Label label = new Label("Choose an option:");

        Button regularClassesBtn = new Button("Regular Classes");
        Button electiveBtn = new Button("Elective");
        Button scienceBtn = new Button("Science");
        Button englishBtn = new Button("English");
        Button chemistryBtn = new Button("Chemistry");
        Button csBtn = new Button("Recommended CS Class");

        final Button finalButton = button;  // Create a final variable

        regularClassesBtn.setOnAction(e -> {
            addRegularClassButton(finalButton);
            stage.close();
        });

        electiveBtn.setOnAction(e -> {
            addElectiveButton(finalButton);
            stage.close();
        });

        scienceBtn.setOnAction(e -> {
            addScienceButton(finalButton);
            stage.close();
        });

        englishBtn.setOnAction(e -> {
            addEnglishButton(finalButton);
            stage.close();
        });
        chemistryBtn.setOnAction(e -> {
            addChemistryButton(finalButton);
            stage.close();
        });
        csBtn.setOnAction(e -> {
            addCSButton(finalButton);
            stage.close();
        });
        
        vbox.getChildren().addAll(label, regularClassesBtn, electiveBtn, scienceBtn, englishBtn, chemistryBtn, csBtn);
        Scene scene = new Scene(vbox, 200, 240);
        stage.setScene(scene);
        stage.setTitle("Add Options");
        stage.show();
    }
    private void addRegularClassButton(Button button) {
        Button newBtn = new Button("New Button");
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);

    }

    private void addElectiveButton(Button button) {
        Button electiveButton = createElectiveButton();
        electiveButton.setOnAction(e -> handleElectiveButtonAction(electiveButton));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, electiveButton);

    }

    private void addScienceButton(Button button) {
        Button newBtn = new Button("New Science");
        newBtn.setOnAction(e -> handleScienceButtonAction(newBtn));
        
        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);
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
        Scene scene = new Scene(vbox, 200, 170);
        stage.setScene(scene);
        stage.setTitle("Edit Button");
        stage.show();
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

    private Button createPasteButton(VBox parentVBox) {
        Button pasteBtn = new Button();
        pasteBtn.setOnAction(e -> {
            handlePasteButtonAction(parentVBox);
            pasteIndex = parentVBox.getChildren().indexOf(pasteBtn);
        });

        return pasteBtn;
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
        nextButton.setOnAction(e -> {
            setElectiveButton(electiveButton, classCategoryChoiceBox, classChoiceBox);
            electiveStage.close(); // Close the elective stage
        });
        Button removeButton = new Button("Remove Elective");
        removeButton.setOnAction(e -> {
            removeElectiveButton(electiveButton);
            electiveStage.close(); // Close the elective stage
        });

        layout.getChildren().addAll(classCreditBox, classCategoryBox, classBox, nextButton, removeButton);

        Scene electiveScene = new Scene(layout, 400, 300);
        electiveStage.setScene(electiveScene);
        electiveStage.show();
    }

    private void removeElectiveButton(Button electiveButton) {
        VBox parentVBox = (VBox) electiveButton.getParent();
        parentVBox.getChildren().remove(electiveButton);
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
    
    private void handleScienceButtonAction(Button button) {
        Stage stage = new Stage();
        VBox vbox = new VBox(10);

        // First ChoiceBox for selecting between Life and Physical Science
        ChoiceBox<String> scienceTypeChoiceBox = new ChoiceBox<>();
        scienceTypeChoiceBox.getItems().addAll("Life Science", "Physical Science");
        HBox scienceTypeBox = createChoiceBox("Select Science Type:", scienceTypeChoiceBox);

        // Second ChoiceBox for selecting the course name based on the science type
        ChoiceBox<String> scienceCourseChoiceBox = new ChoiceBox<>();
        HBox scienceCourseBox = createChoiceBox("Select Science Course:", scienceCourseChoiceBox);

        // Add an event listener to scienceTypeChoiceBox to update the available course names in scienceCourseChoiceBox based on the selected science type
        scienceTypeChoiceBox.setOnAction(e -> {
            String selectedScienceType = scienceTypeChoiceBox.getValue();
            if ("Life Science".equals(selectedScienceType)) {
                // Populate scienceCourseChoiceBox with life science course names
                String filePath = "LifeScience.txt"; // Replace with the actual file path
                ArrayList<String> lifeScienceCourses = readCoursesFromFile(filePath);
                scienceCourseChoiceBox.getItems().clear();
                scienceCourseChoiceBox.getItems().addAll(lifeScienceCourses);
            } else if ("Physical Science".equals(selectedScienceType)) {
                // Populate scienceCourseChoiceBox with physical science course names
                String filePath = "PhysicalScience.txt"; // Replace with the actual file path
                ArrayList<String> physicalScienceCourses = readCoursesFromFile(filePath);
                scienceCourseChoiceBox.getItems().clear();
                scienceCourseChoiceBox.getItems().addAll(physicalScienceCourses);
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            setScienceButton(button, scienceTypeChoiceBox, scienceCourseChoiceBox);
            stage.close(); // Close the science stage
        });

        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            removeScienceButton(button);
            stage.close(); // Close the science stage
        });

        // Align the question and choice boxes to the left
        scienceTypeBox.setAlignment(Pos.CENTER_LEFT);
        scienceCourseBox.setAlignment(Pos.CENTER_LEFT);

        // Set the preferred width of the ChoiceBoxes
        scienceTypeChoiceBox.setPrefWidth(411); // Adjust the width as needed
        scienceCourseChoiceBox.setPrefWidth(400); // Adjust the width as needed

        // Create an HBox for buttons and center them
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(nextButton, removeButton);

        vbox.getChildren().addAll(scienceTypeBox, scienceCourseBox, buttonBox);
        Scene scene = new Scene(vbox, 530, 200);
        stage.setScene(scene);
        stage.setTitle("Add Science Options");
        stage.show();
    }



    private ArrayList<String> readCoursesFromFile(String filePath) {
        ArrayList<String> courses = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                courses.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return courses;
    }

    private void removeScienceButton(Button scienceButton) {
        VBox parentVBox = (VBox) scienceButton.getParent();
        parentVBox.getChildren().remove(scienceButton);
    }

    private void setScienceButton(Button scienceButton, ChoiceBox<String> scienceTypeChoiceBox, ChoiceBox<String> scienceCourseChoiceBox) {
        String selectedScienceType = scienceTypeChoiceBox.getValue();
        String selectedScienceCourse = scienceCourseChoiceBox.getValue();
        if (selectedScienceType != null && selectedScienceCourse != null) {
            scienceButton.setText( selectedScienceCourse);
        }
    }

 
    private void addEnglishButton(Button button) {
        Stage englishStage = new Stage();
        VBox vbox = new VBox(10);

        // First Row
        HBox firstRow = new HBox(10);
        Label label1 = new Label("Select English course available:");
        firstRow.getChildren().addAll(label1);

        // Second Row
        HBox secondRow = new HBox(10);
        Label label2 = new Label("1st English quarter: ");
        Button english101Btn = new Button("English 101");
        english101Btn.setOnAction(e -> {
            addEnglishCourseButton(button, "English 101");
            englishStage.close();
        });
        secondRow.getChildren().addAll(label2, english101Btn);

        // Third Row
        HBox thirdRow = new HBox(10);
        Label label3 = new Label("2nd English quarter: ");
        Button english102Btn = new Button("English 102");
        english102Btn.setOnAction(e -> {
            addEnglishCourseButton(button, "English 102");
            englishStage.close();
        });
        Text orText = new Text("or");
        Button english235Btn = new Button("English 235");
        english235Btn.setOnAction(e -> {
            addEnglishCourseButton(button, "English 235");
            englishStage.close();
        });
        thirdRow.getChildren().addAll(label3, english102Btn, orText, english235Btn);

        vbox.getChildren().addAll(firstRow, secondRow, thirdRow);
        Scene scene = new Scene(vbox, 310, 90);
        englishStage.setScene(scene);
        englishStage.setTitle("English Courses");
        englishStage.show();
    }


    private void addEnglishCourseButton(Button button, String course) {
        Button newBtn = new Button( course);
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);
    }
    private void addChemistryButton(Button button) {
        Button newBtn = new Button("CHEM&140");
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);
    }
 // ...

    private void addCSButton(Button button) {
        Stage csStage = new Stage();
        VBox vbox = new VBox(10);

        // First Row
        HBox firstRow = new HBox(10);
        Label personalProjectLabel2 = new Label("BS Req:");

        Button bsReqButton = new Button("CS 244");
      
        bsReqButton.setOnAction(e -> {
            addCSProjectButton(button, "CS 244");
            csStage.close();
        });
        firstRow.getChildren().addAll(personalProjectLabel2,bsReqButton);

        // Second Row
        HBox secondRow = new HBox(10);
        Label personalProjectLabel = new Label("Personal Project:");
        Button cs291Btn = new Button("CS291(Honor Capstone)");
        cs291Btn.setOnAction(e -> {
            addCSProjectButton(button, "CS291(Honor Capstone)");
            csStage.close();
        });
        Text orText = new Text("or");
        Button stem298Btn = new Button("Stem 298 (Interdisciplinary Design Project)");
        stem298Btn.setOnAction(e -> {
            addCSProjectButton(button, "Stem 298 (Interdisciplinary Design Project)");
            csStage.close();
        });
        secondRow.getChildren().addAll(personalProjectLabel, cs291Btn, orText, stem298Btn);

        vbox.getChildren().addAll(firstRow, secondRow);
        Scene scene = new Scene(vbox, 530, 70);
        csStage.setScene(scene);
        csStage.setTitle("Computer Science Requirements");
        csStage.show();
    }

    private void addCSProjectButton(Button button, String project) {
        Button newBtn = new Button(project);
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);
    }
    

    // ...

  
    public static void main(String[] args) {
        launch(args);
    }
}
