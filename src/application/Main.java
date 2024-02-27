package application;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
import java.util.Optional;
import java.util.stream.Collectors;

public class Main extends Application {
    
	private Stage window;
    private Scene ResourcesScene, AcdemicPlanningScene;

    
    
    
 // choice box for question scene
    private ChoiceBox<String> question1ChoiceBoxUni = new ChoiceBox<>();
    private ChoiceBox<String> question2ChoiceBoxMath = new ChoiceBox<>();
    private ChoiceBox<String> question3ChoiceBoxLanguage = new ChoiceBox<>();
    private ChoiceBox<String> question4ChoiceBoxYear = new ChoiceBox<>();
    private ChoiceBox<String> question5ChoiceBoxSummer = new ChoiceBox<>();
    
    
    private List<TitledPane> titledPanes = new ArrayList<>(); // keeping a list of the newly added tile panes so I can removed the last one 
 
 // keeping a list of the removed categories for elective Classes
    static List<String> removedHumanitiesClasses = new ArrayList<>(); 
    static List<String> removedSocialScieneClasses = new ArrayList<>();
 
 // keeping a list of all classes. No duplicate allowed
 private List<Button> allClasses = new ArrayList<>();

// tracking the location and text of the paste button
    private int pasteIndex = -1;
    private String copyClass = "";
    
// Hash map for elective. Key for categories and values for classes. 
    private static Map<String, String> humanitiesClasses = new HashMap<>();
    private static Map<String, String> socialScienceClasses = new HashMap<>();
 

// Choices available for some of the choice boxes
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
            Image logoImage = new Image("/trojanLogo.jpg");
           
   // scene 1
  // set up for Resources scene
            VBox layoutForResourcesScene = new VBox(20);
            
            Button scheduleBtn = new Button("Academic Plan");
           Button financialAidBtn = new Button("Financial Aid");
           Button tutoringBtn = new Button("Resources");
            scheduleBtn.setOnAction(e -> {
                window.setScene(AcdemicPlanningScene);
                window.centerOnScreen();
            });

           window.setTitle("Program options");
          window.getIcons().add(logoImage);

            layoutForResourcesScene.getChildren().addAll(tutoringBtn, scheduleBtn, financialAidBtn);
            layoutForResourcesScene.setAlignment(javafx.geometry.Pos.CENTER);

            
  // Financial aid Button
            financialAidBtn.setOnAction(e -> {
            	 getHostServices().showDocument("https://docs.google.com/document/d/1cFtJcR0PRTcCXPra3C6ik0mOVMIcUih_7BE7WBhb2NM/edit?usp=sharing");
            });  
            
            
            
 // scene 2 
// set up for Academic Planning scene 
            VBox layoutforAcdemicPlanningScene = new VBox(20);
            Button buildAPlanBtn = new Button("Create your plan");
            Button goBackToResourcesSceneBtn = new Button("Back");
            buildAPlanBtn.setOnAction(e -> academicPlan());
            goBackToResourcesSceneBtn.setOnAction(e -> {
                primaryStage.setScene(ResourcesScene);
                primaryStage.centerOnScreen();
            });

            ResourcesScene = new Scene(layoutForResourcesScene, 300, 250);
            AcdemicPlanningScene = new Scene(layoutforAcdemicPlanningScene, 400, 450);

            
  // Choice Box for Academic Planning Scene
            Label question1Uni = new Label("What university are you transferring to?");
            question1ChoiceBoxUni.getItems().addAll("UW Bothell");
            VBox question1BoxUni = new VBox(10, question1Uni, question1ChoiceBoxUni);
            question1BoxUni.setAlignment(Pos.CENTER);

            Label question2Math = new Label("What math level would you like to start at?");
            question2ChoiceBoxMath.getItems().addAll(mathLevels);
            VBox question2BoxMath = new VBox(10, question2Math, question2ChoiceBoxMath);
            question2BoxMath.setAlignment(Pos.CENTER);

            Label question3Language = new Label("C++ or Java?");
            question3ChoiceBoxLanguage.getItems().addAll("C++", "Java");
            VBox question3BoxLanguage = new VBox(10, question3Language, question3ChoiceBoxLanguage);
            question3BoxLanguage.setAlignment(Pos.CENTER);

            Label question4Year = new Label("What year are starting?");
            question4ChoiceBoxYear.getItems().addAll("2024", "2025", "2026", "2027", "2028", "2029", "2030");
            VBox question4BoxYear = new VBox(10, question4Year, question4ChoiceBoxYear);
            question4BoxYear.setAlignment(Pos.CENTER);

            Label question5Summer = new Label("Summer");
            question5ChoiceBoxSummer.getItems().addAll("Yes", "No");
            VBox question5BoxSummer = new VBox(10, question5Summer, question5ChoiceBoxSummer);
            question5BoxSummer.setAlignment(Pos.CENTER);

            layoutforAcdemicPlanningScene.getChildren().addAll(question1BoxUni, question2BoxMath, question3BoxLanguage, question4BoxYear, question5BoxSummer, buildAPlanBtn, goBackToResourcesSceneBtn);
            layoutforAcdemicPlanningScene.setAlignment(Pos.CENTER);
            layoutforAcdemicPlanningScene.setPadding(new Insets(20, 40, 20, 40));

            primaryStage.setScene(ResourcesScene);
            primaryStage.centerOnScreen();
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    
    
 // Schedule scene
    public void academicPlan() {

    	
    	// array list for all the seasons
    	ArrayList<String> quarter = new ArrayList<>();
        quarter.add("Fall");
        quarter.add("Winter");
        quarter.add("Spring");
        quarter.add("Summer");
   BorderPane borderPane = new BorderPane();
   
        
// basic grid pane setting
        GridPane gridPane = new GridPane();
        gridPane.setHgap(60);
        gridPane.setVgap(20);
        

      

        
 // Text area and Menu Bar
        MenuBar menuBar = new MenuBar();

        VBox menuBarAndTextLayout = new VBox();
        Image aboutMenuLogo = new Image("/me.png");
       
        Menu fileMenu = new Menu("File");
        Menu editMenu = new Menu("Edit");
        Menu helpMenu = new Menu("Help");
        Menu aboutMenu = new Menu("About");
        MenuItem addQuarter = new MenuItem("Add new quarter");
        MenuItem removeQuarter = new MenuItem("Remove quarter");
        MenuItem createNewPlan = new MenuItem("Create new Plan");
        MenuItem linkToElectiveClasses = new MenuItem("List of electives");
        MenuItem aboutTheIdea = new MenuItem("The Idea");
        MenuItem aboutTheProject = new MenuItem("The Project");
        MenuItem aboutMe = new MenuItem("Background");

        fileMenu.getItems().addAll(createNewPlan);
        editMenu.getItems().addAll(addQuarter, removeQuarter);
        aboutMenu.getItems().addAll(aboutTheIdea,aboutTheProject,aboutMe);
        helpMenu.getItems().addAll(linkToElectiveClasses);
        TextArea textArea = new TextArea();
        textArea.setStyle("-fx-font-weight: bold;");
        textArea.setText("Rule 1: Must have 2 years of foreign language in high school\n"
                + "Rule 2: Math 146 is RECOMMENDED\n"
                + "Rule 3: CS244, CS291, and STEM298 are recommended\n"
                + "Rule 4: Only select 5 elective class. Side note: UWB don't recognize our diversity elective\n"
                );
        textArea.setEditable(false);
        textArea.setPrefSize(365, 200);
        menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu, aboutMenu );
        
       
        menuBarAndTextLayout.getChildren().addAll(textArea);

        
   // About menu layout      
        aboutTheProject.setOnAction(e-> {
        	Stage stage = new Stage();
        	TextArea whoAmIText = new TextArea(
        	"My name is Chien. I am a former student at EvCC and UW Bothell.\n" 
        	+"I built this program in order to automate the advising process\n"
        	+"for CS students. \n" 
        	+"My ultimate goal for this project is to make college education \n"
        	+ "more accessible to ESL students"
        			
        			);
        	Scene whoAmIScene = new Scene(whoAmIText, 380 , 105);
            stage.setTitle("Who Am I");
            stage.getIcons().addAll(aboutMenuLogo);
            stage.setScene(whoAmIScene);
        	stage.show();       
        	});
        
       aboutMe.setOnAction(e -> {
        	Stage stage = new Stage();
        	TextArea whoAmIText = new TextArea(
        	"	I am a Vietnamese immgrant. I was born in Dong Nai, Vietnam\n\n" 
        	+"Me and my family came here when I was 10 years old. During this\n\n"
        	+"time, I played a lot of video game such as League of Legends, CSGO,\n\n "
        	+ "and The Witcher 3. Wanting a better battle station to compete with\n\n"
        	+ "other players, I saved up some money during Vietnamese New Year and\n\n"
        	+ "build my first PC. I instanntly fell in love with the process of \n\n"
        	+"researching and experimenting with computer parts. I graduated\n\n"
        	+"highschool with a ComptiaA++ certificate and started learning how to \n\n"
        	+ "code in college because I wanna build my own game someday."
        	
        			
        			);
        	Scene whoAmIScene = new Scene(whoAmIText, 410 , 320);
            stage.setTitle("About me");
            stage.getIcons().addAll(aboutMenuLogo);

            stage.setScene(whoAmIScene);
        	stage.show();       
        	});
        
       aboutTheIdea.setOnAction(e -> {
        	Stage stage = new Stage();
        	TextArea whoAmIText = new TextArea(
        	"	The inspiration for the project came to me while I was working.\n\n" 
        	+"at a Vietnamese restraunt. Many of my coworkers are first-generation \n\n"
        	+"Vietnamese who dropped out of high school due to language barriers.\n\n "
        	+ "They chose to work in restaurants since their dream jobs required a degree\n\n"
        	+ ", and they felt their English proficiency wasn't good enough for college.\n\n"
        	+ "Learning about this made me aware of my privilege in speaking English, \n\n"
        	+"a privilege that allowed me to apply to college, get financial aid, and \n\n"
        	+"communicate with my professors. Having successfully adapted to my life in\n\n"
        	+ "America, it was disheartening for me to see my fellow Vietnamese\n\n"
        	+ "A being denied an education because of their lack of English proficiency.\n\n"
        	+ " This inspired me to use my computer science background to help immigrant \n\n"
        	+"students overcome language barriers in education.\n\n"
        	
        			);
        	Scene whoAmIScene = new Scene(whoAmIText, 430 , 440);
            stage.setTitle("About me");
            stage.getIcons().addAll(aboutMenuLogo);

            stage.setScene(whoAmIScene);
        	stage.show();       
        	});
    // Help menu hyperlink
        linkToElectiveClasses.setOnAction(event -> {
            getHostServices().showDocument("https://www.everettcc.edu/files/programs/aas-dta-current-classes.pdf");
        });
     

        
        
   // Layout for school image
        VBox schoolImageLayout = new VBox();
        Image image = new Image(getClass().getResourceAsStream("/UWBothell.png"));

        // Create an ImageView and set the image
        ImageView imageView = new ImageView(image);

        // Set the fit width and fit height properties to make the image bigger
        imageView.setFitWidth(375); // Set the desired width
        imageView.setFitHeight(250); // Set the desired height

        // Add the ImageView to the VBox
        schoolImageLayout.getChildren().add(imageView);


 // getting the years and summer choices
        int selectedYear = Integer.parseInt(question4ChoiceBoxYear.getValue());
        String selectedSummer = question5ChoiceBoxSummer.getValue();
        int summerOn = selectedSummer.equals("Yes") ? 1 : 0; // summer on = 1 if yes and = 0 if no. 
    	
//  Starting position for the manually added row and column
        int[] newCol = {0};
        int[] newRow = {3};



        // Make sure there is 5 elective button only
        int electiveCount = 0;

 // For loop to create a academic plan based on choiceBoxes results. Both will have two rows of quarters equal to two years of school/
        for (int row = 0; row < 2; row++) {
        	
 // How many quarter per year will based on whether the user chooses summer or not. 
            for (int col = 0; col < (3 + summerOn); col++) {
                int quarterNumber = row * 3 + col + 1;
                
 // Automatically created tilePane
 // Season and year are auto adjusted based on the "quarter" ArrayList and question4ChoiceBoxYear
                TitledPane titledPane = new TitledPane();
                titledPane.setText(quarter.get(col) + " " + selectedYear);
             
// Summer Plan
                if (quarter.get(col).equalsIgnoreCase("Summer")) {
                	
  //  keeping track of the season using the first index value
                    int[] seasonChangedVariable = {0};
                    
 // getting the selected Year from choice box #4
                    int selectedYr = Integer.parseInt(question4ChoiceBoxYear.getValue());
                    
 // adding two years to the starting years since that is where the newly added quarter will start
                    int[] yearChangeVariable = {selectedYr + 2};

 // add Quarter button for summer plan
                    addQuarter.setOnAction(e -> {
                        TitledPane newQuarterTilePane = new TitledPane();
                        titledPanes.add(newQuarterTilePane);
                        System.out.println(titledPanes.size());

// adding text to the the tilePane
                        newQuarterTilePane.setText(quarter.get(seasonChangedVariable[0]) + " " + yearChangeVariable[0]);
                        
// Moving to the next index and getting the next season within the "quarter" arrayList
// if index value is equal to 4. Reset the value of seasonChangedVariable to 0 and add 1 to the year
                        seasonChangedVariable[0]++;
                       if (seasonChangedVariable[0] == 4) {
                            seasonChangedVariable[0] = 0;
                            yearChangeVariable[0]++;
                        }
                        
 // Quarter layout 
                       	VBox newQuarterLayout = createPanel(10);
                       	Button addButton = new Button();
                      

                        addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                        addButton.setOnAction(event -> showAddOptions(addButton));
                        Button pasteButton = createPasteButton(newQuarterLayout);
                        pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                        setButtonColor(addButton, "#F0F0F0", "#e74c3c");
                        setButtonColor(pasteButton, "#F0F0F0", "#e74c3c");
                        newQuarterLayout.getChildren().addAll(addButton, pasteButton);
                        newQuarterTilePane.setPrefWidth(190);
                        newQuarterTilePane.setPrefHeight(250);
                        newQuarterTilePane.setContent(newQuarterLayout);
                        
                        // setting the position of the new quarter 
                        //change column after every successful addition 
                        // once 4 is added on one row. Move on to the next row
                        gridPane.add(newQuarterTilePane, newCol[0], newRow[0]);
                        newCol[0]++; 
                        if (newCol[0] == 4) {
                        	newRow[0]++;
                            newCol[0] = 0;
                        }
                    });
                    removeQuarter.setOnAction(e -> {
                        int lastIndex = titledPanes.size() - 1;
                        if (lastIndex >= 0) {
                            TitledPane lastTitledPane = titledPanes.get(lastIndex);

                            // Check if the content is a VBox
                            if (lastTitledPane.getContent() instanceof VBox) {
                                VBox vbox = (VBox) lastTitledPane.getContent();

                                // Check if the VBox contains any children
                                if (vbox.getChildren().size() > 2) {
                                    // Show an error message or take appropriate action
                                    boolean yesToRemove = showErrorWithIgnoreOption("Cannot delete the TitledPane because it contains children.");
                                    
                                    if (!yesToRemove) {
                                        // User clicked "Cancel" or closed the dialog
                                        return;  // Do not proceed with deletion
                                    }
                                }
                            }

                            // Remove the last TitledPane
                            gridPane.getChildren().remove(lastTitledPane);
                            titledPanes.remove(lastIndex);
                            newCol[0]--;
                            seasonChangedVariable[0]--;

                            if (seasonChangedVariable[0] < 0) {
                                seasonChangedVariable[0] = 0;
                            }
                         if (newCol[0] < 0) {
                                newCol[0] = 0;
                            }
                        }
                    });


                    
                    // send you back to AcdemicPlanningScene 
                    createNewPlan.setOnAction(e -> {
                        Platform.runLater(() -> {
                            window.setScene(AcdemicPlanningScene);
                            window.centerOnScreen();
                        });
                    });

                    VBox panel = new VBox(10);
                    Button addButton = new Button();
                    Button pasteButton = createPasteButton(panel);
                    setButtonColor(addButton, "#F0F0F0", "#e74c3c");
                    setButtonColor(pasteButton, "#F0F0F0", "#e74c3c");
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
                    int selectedYr = Integer.parseInt(question4ChoiceBoxYear.getValue());
                    int[] yearChangeVariable = {selectedYr + 2};

                    addQuarter.setOnAction(e -> {
                        TitledPane newQuarterTilePane = new TitledPane();
                        titledPanes.add(newQuarterTilePane);
                        newQuarterTilePane.setText(quarter.get(seasonChangedVariable[0]) + " " + yearChangeVariable[0]);
                        seasonChangedVariable[0]++;
                        if (seasonChangedVariable[0] == 3) {
                            seasonChangedVariable[0] = 0;
                            yearChangeVariable[0]++;
                        }

                        VBox newQuarterLayout = createPanel(10);

                        Button addButton = new Button();
                        Button pasteButton = createPasteButton(newQuarterLayout);
                        setButtonColor(addButton, "#F0F0F0", "#e74c3c");
                        setButtonColor(pasteButton, "#F0F0F0", "#e74c3c");
                        addButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/add (1).png"))));
                        pasteButton.setGraphic(new ImageView(new Image(getClass().getResourceAsStream("/copy (1).png"))));

                        addButton.setOnAction(event -> showAddOptions(addButton));

                        newQuarterLayout.getChildren().addAll(addButton, pasteButton);

                        newQuarterTilePane.setPrefWidth(190);
                        newQuarterTilePane.setPrefHeight(250);
                        newQuarterTilePane.setContent(newQuarterLayout);
                        gridPane.add(newQuarterTilePane, newCol[0], newRow[0]);
                        newCol[0]++;
                        if (newCol[0] == 3) {
                        	newRow[0]++;
                            newCol[0] = 0;
                        }
                    });
                    
                    
                    createNewPlan.setOnAction(e -> {
                        Platform.runLater(() -> {
                            window.setScene(AcdemicPlanningScene);
                            window.centerOnScreen();
                        });
                    });

                    removeQuarter.setOnAction(e -> {
                        int lastIndex = titledPanes.size() - 1;
                        if (lastIndex >= 0) {
                            TitledPane lastTitledPane = titledPanes.get(lastIndex);

                            // Check if the content is a VBox
                            if (lastTitledPane.getContent() instanceof VBox) {
                                VBox vbox = (VBox) lastTitledPane.getContent();

                                // Check if the VBox contains any children
                                if (vbox.getChildren().size() > 2) {
                                    // Show an error message or take appropriate action
                                    boolean yesToRemove = showErrorWithIgnoreOption("Cannot delete the TitledPane because it contains children.");
                                    
                                    if (!yesToRemove) {
                                        // User clicked "Cancel" or closed the dialog
                                        return;  // Do not proceed with deletion
                                    }
                                }
                            }

                            // Remove the last TitledPane
                            gridPane.getChildren().remove(lastTitledPane);
                            titledPanes.remove(lastIndex);
                            newCol[0]--;
                            seasonChangedVariable[0]--;

                            if (seasonChangedVariable[0] < 0) {
                                seasonChangedVariable[0] = 0;
                            }
                                if (newCol[0] < 0) {
                                    newCol[0] = 0;
                                }
                        }
                    });



                    VBox panel = createPanel(quarterNumber);
                    Button addButton = new Button();
                    Button pasteButton = createPasteButton(panel);
                    setButtonColor(addButton, "#F0F0F0", "#e74c3c");
                    setButtonColor(pasteButton, "#F0F0F0", "#e74c3c");
                    
                  if (electiveCount < 5) {
                        Button electiveButton = createElectiveButton();
                        panel.getChildren().add(electiveButton);
                        electiveCount++;
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

        gridPane.add(menuBarAndTextLayout, 3 + summerOn, 0);
        
        gridPane.add(schoolImageLayout, 3 + summerOn, 1, 1, 1);

        gridPane.setPadding(new Insets(10, 10, 10, 10));
        borderPane.setTop(menuBar);
        borderPane.setCenter(gridPane);
     // Adjusting the size of the Academic Planning scene based on "Summer" question 
        Scene scene;
        if (summerOn == 1) {
            scene = new Scene(new ScrollPane(borderPane), 1450, 570);
            
        } else {
            scene = new Scene(new ScrollPane(borderPane), 1200, 570);
        }
        window.setScene(scene);
        window.setTitle("Academic Planning");
        scene.getWindow().centerOnScreen();

        window.show();
    }


    
    
    // Auto add math and cs button
    private VBox createPanel(int quarterNumber) {
        String selectedUniversity = question1ChoiceBoxUni.getValue();
        String selectedMathLevel = question2ChoiceBoxMath.getValue();
        String selectedCSCourse = question3ChoiceBoxLanguage.getValue();

  // adding math classess
        if (selectedUniversity != null && selectedUniversity.equals("UW Bothell")) {
            mathLevels.remove("Math& 163 (Calculus 3)");
        }

        VBox panel = new VBox(10);

        if (selectedMathLevel != null ) {
        	
            int levelsPerQuarter = 1;
            int startIndex = (quarterNumber - 1) * levelsPerQuarter + mathLevels.indexOf(selectedMathLevel); // calculate the starting index for the first math button
            int endIndex = Math.min(startIndex + levelsPerQuarter, mathLevels.size()); // calculate the last index for the last math button

            for (int i = startIndex; i < endIndex; i++ ) {
                Button mathButton = new Button(mathLevels.get(i));
                mathButton.setOnAction(e -> handleButtonAction(mathButton));
                panel.getChildren().add(mathButton);
            }
        }

        
  // adding cs classess 
        if (selectedCSCourse != null) {
            ArrayList<String> selectedClasses;
            if ("C++".equals(selectedCSCourse)) {
                selectedClasses = cPlusPlusClasses;
            } else if ("Java".equals(selectedCSCourse)) {
                selectedClasses = javaClasses;
            } else {
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


 
    
 // add button scene
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

        final Button finalButton = button;  

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

    // regular button method. Some of the other button might also implement this method
    private void addRegularClassButton(Button button) {
        
    	VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        Stage stage = new Stage();
        VBox vbox = new VBox(10);
        TextField textField = new TextField("Enter new text");
        Button changeTextBtn = new Button("Change Text");
        Button deleteBtn = new Button("Delete");
        Button moveBtn = new Button("Move");

      
        
        changeTextBtn.setOnAction(e -> {
            String buttonText = textField.getText();
            if (isDuplicateButton(buttonText)) { 				// Check for duplicates before adding the new button
            	showError("Duplicate button: " + buttonText);   // Show an error message if there is duplicate
            									}
             else {
                Button newBtn = new Button(buttonText);
                newBtn.setOnAction(actionEvent -> handleButtonAction(newBtn));
                allClasses.add(button);									// add button to list
                allClasses.set(allClasses.indexOf(button), newBtn);    // Replace the old button with the new one in the list
                parentVBox.getChildren().add(index, newBtn);           // adding the new button into the original position
                stage.close();
            }
        });

      
        deleteBtn.setOnAction(e -> {
            ((VBox) button.getParent()).getChildren().remove(button);
            allClasses.remove(button);             // Remove the button from the list when deleted
            stage.close();
        });

     
        moveBtn.setOnAction(e -> {
            copyClass = button.getText();
            ((VBox) button.getParent()).getChildren().remove(button);
            System.out.println("Text copied: " + copyClass);
            stage.close();
        });

   
        vbox.getChildren().addAll(textField, changeTextBtn, deleteBtn, moveBtn);
        Scene scene = new Scene(vbox, 200, 170);
        stage.setScene(scene);
        stage.setTitle("Add Regular Class");
        stage.show();
    }
    
    
    
  // handling regular button
    private void handleButtonAction(Button button) {

         Stage stage = new Stage();
         VBox vbox = new VBox(10);
         TextField textField = new TextField("Enter new text");
         Button changeTextBtn = new Button("Change Text");
         Button deleteBtn = new Button("Delete");
         Button moveBtn = new Button("Move");

         changeTextBtn.setOnAction(e -> {
        	    Button existingButton = button;
        	    existingButton.setText(textField.getText());         	    // Update the text of the existing button
        	    handleButtonAction(existingButton);
                allClasses.set(allClasses.indexOf(button), existingButton);
                stage.close();
        	});


        deleteBtn.setOnAction(e -> {
        	   ((VBox) button.getParent()).getChildren().remove(button);
        	   allClasses.remove(button);
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

   
    
    // handling paste button
    private void handlePasteButtonAction(VBox parentVBox) {
        if (!copyClass.isEmpty() && pasteIndex != -1) {
            Button pasteBtn = new Button(copyClass);
            pasteBtn.setOnAction(e -> handleButtonAction(pasteBtn)); 
            parentVBox.getChildren().add( pasteIndex, pasteBtn); // paste the new button on to its new location at  #pasteIndex Location

            copyClass = "";
        } else {
            System.out.println("Nothing to paste. Copy a button first.");
        }
    }

    private Button createPasteButton(VBox parentVBox) {
        Button pasteBtn = new Button();
        pasteBtn.setOnAction(e -> {
            handlePasteButtonAction(parentVBox);
            pasteIndex = parentVBox.getChildren().indexOf(pasteBtn); // captures the position of the paste button
        });

        return pasteBtn;
    }


    private void addElectiveButton(Button button) {
        Button electiveButton = createElectiveButton();
        electiveButton.setOnAction(e -> handleElectiveButtonAction(electiveButton));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, electiveButton);
 


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

        classCategoryChoiceBox.setOnAction(e -> {
            String selectedCategory = classCategoryChoiceBox.getValue();
            if (socialScienceClasses.containsKey(selectedCategory)) {
                // Populate classChoiceBox with the values (classes) from the selected category
                classChoiceBox.getItems().clear();
                classChoiceBox.getItems().addAll(Arrays.asList(socialScienceClasses.get(selectedCategory).split(", ")));
            } else if (humanitiesClasses.containsKey(selectedCategory)) {
                // Populate classChoiceBox with the values (classes) from the selected category
                classChoiceBox.getItems().clear();
                classChoiceBox.getItems().addAll(Arrays.asList(humanitiesClasses.get(selectedCategory).split(", ")));
            }
        });

        Button nextButton = new Button("Next");
        nextButton.setOnAction(e -> {
            String selectedCategory = classCategoryChoiceBox.getValue();
            String selectedClass = classChoiceBox.getValue();

            if (selectedCategory != null && selectedClass != null) {
                String buttonText = selectedCategory + " " + selectedClass;

                if (!isDuplicateButton(buttonText)) {
                    setElectiveButton(electiveButton, classCategoryChoiceBox, classChoiceBox, classCreditChoiceBox);
                    removedHumanitiesClasses.add(selectedCategory);
                    removedSocialScieneClasses.add(selectedCategory);
                    electiveStage.close(); // Close the elective stage
                } else {
                    showError("Duplicate elective detected. Please select a different combination.");
                }
            } else {
                showError("Please select class credits, category, and class before proceeding.");
            }
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
     
        allClasses.remove(electiveButton);


    }
    private void setElectiveButton(Button electiveButton, ChoiceBox<String> classCategoryChoiceBox, ChoiceBox<String> classChoiceBox, ChoiceBox<String> classCreditChoiceBox) {
        String selectedCategory = classCategoryChoiceBox.getValue();
        String selectedClass = classChoiceBox.getValue();
        String selectedCredit = classCreditChoiceBox.getValue();
        String classCredit = "";
        
        if (selectedCredit == "Social Science") {
        	classCredit = "SS";
        }
        else {
        	classCredit = "HUM";

        }
        if (selectedCategory != null && selectedClass != null) {
        	electiveButton.setText( "(" + classCredit + ") " + selectedCategory + " " + selectedClass);
            allClasses.add(electiveButton);
            System.out.println(allClasses);
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
                    if (removedHumanitiesClasses.contains(category)) {
                        continue;
                    }
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
                    if (removedSocialScieneClasses.contains(category)) {
                        continue;
                    }
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
            String selectedScienceType = scienceTypeChoiceBox.getValue();
            String selectedScienceCourse = scienceCourseChoiceBox.getValue();

            if (selectedScienceType != null && selectedScienceCourse != null) {
    			String scienceType = "";
                if (selectedScienceType == "Life Science" ) {
                	scienceType = "LS";
                }
                else 
                {
                	scienceType = "PS";
                }
              String buttonText = ("(" + scienceType +") " + selectedScienceCourse);

                if (!isDuplicateButton(buttonText)) {
                setScienceButton(button, scienceTypeChoiceBox, scienceCourseChoiceBox);
                
                stage.close(); 
               
                } else {
                    showError("Duplicate elective detected. Please select a different combination.");
                }
            } else {
                showError("Please select class credits, category, and class before proceeding.");
            }
        });
        Button removeButton = new Button("Remove");
        removeButton.setOnAction(e -> {
            removeScienceButton(button);
            

            stage.close(); // Close the science stage
        });

        scienceTypeBox.setAlignment(Pos.CENTER_LEFT);
        scienceCourseBox.setAlignment(Pos.CENTER_LEFT);

        scienceTypeChoiceBox.setPrefWidth(411); 
        scienceCourseChoiceBox.setPrefWidth(400); 

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
        allClasses.remove(scienceButton);
        System.out.println(allClasses);

    }
    private void setScienceButton(Button scienceButton, ChoiceBox<String> scienceTypeChoiceBox, ChoiceBox<String> scienceCourseChoiceBox) {
        String selectedScienceType = scienceTypeChoiceBox.getValue();
        String selectedScienceCourse = scienceCourseChoiceBox.getValue();
        String scienceType ="";
    
    
        if (selectedScienceType != null && selectedScienceCourse != null) {
        			
            if (selectedScienceType == "Life Science" ) {
            	scienceType = "LS";
            }
            else 
            {
            	scienceType = "PS";
            }
                scienceButton.setText ("(" + scienceType +") " + selectedScienceCourse);

                allClasses.add(scienceButton);
                System.out.println(allClasses);
            }}
    

    private void addScienceButton(Button button) {
        Button newBtn = new Button("New Science");
        newBtn.setOnAction(e -> handleScienceButtonAction(newBtn));
        
        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);

    }
    
    

    private boolean englishCheck = true;
 private boolean englishCheck2 = true;
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
            englishCheck2 = false;
            englishCheck = false;
            englishStage.close();
        });
        secondRow.getChildren().addAll(label2, english101Btn);

        // Third Row
        HBox thirdRow = new HBox(10);
        Label label3 = new Label("2nd English quarter: ");
        Button english102Btn = new Button("English 102");
        english102Btn.setOnAction(e -> {
            addEnglishCourseButton(button, "English 102");
            englishCheck = true;
            englishStage.close();
        });
        Text orText = new Text("or");
        Button english235Btn = new Button("English 235");
        english235Btn.setOnAction(e -> {
            addEnglishCourseButton(button, "English 235");
            englishCheck = true;

            englishStage.close();
        });
        
        english101Btn.setDisable(!englishCheck2);
        english102Btn.setDisable(englishCheck);
        english235Btn.setDisable(englishCheck);

        thirdRow.getChildren().addAll(label3, english102Btn, orText, english235Btn);

        vbox.getChildren().addAll(firstRow, secondRow, thirdRow);
        Scene scene = new Scene(vbox, 310, 90);
        englishStage.setScene(scene);
        englishStage.setTitle("English Courses");
        englishStage.show();
    }


    private void addEnglishCourseButton(Button button, String course) {

    	
    	if (!isDuplicateButton(course)) {
            Button newBtn = new Button(course);
            newBtn.setOnAction(e -> handleButtonAction(newBtn));

            VBox parentVBox = (VBox) button.getParent();
            int index = parentVBox.getChildren().indexOf(button);
            parentVBox.getChildren().add(index, newBtn);
            allClasses.add(newBtn);
            System.out.println(allClasses);
        } else {
            showError("Duplicate button! " + course + " already added.");
        }
    }
    private boolean chemistryCheck = true;

    private void addChemistryButton(Button button) {
        Stage chemistryStage = new Stage();
        VBox vbox = new VBox(10);

        // First Row
        HBox firstRow = new HBox(10);
        Label label1 = new Label("Select Chemistry course available:");
        firstRow.getChildren().addAll(label1);

        // Third Row
        HBox thirdRow = new HBox(10);
        Label label3 = new Label("2nd Chemistry quarter: ");
        Button chem161Btn = new Button("CHEM161");
        chem161Btn.setDisable(chemistryCheck); // Initially disable CHEM161 button

        chem161Btn.setOnAction(e -> {
            addChemistryCourseButton(button, "CHEM161");
            
            chemistryStage.close();
        });
        thirdRow.getChildren().addAll(label3, chem161Btn);

        // Second Row
        
        HBox secondRow = new HBox(10);
        Label label2 = new Label("1st Chemistry quarter: ");
        Button chem140Btn = new Button("CHEM&140");
        chem140Btn.setDisable(!chemistryCheck);

        chem140Btn.setOnAction(e -> {
            addChemistryCourseButton(button, "CHEM&140");
            chemistryCheck = false;
            chemistryStage.close();
        });
        secondRow.getChildren().addAll(label2, chem140Btn);

        
     
   
        vbox.getChildren().addAll(firstRow, secondRow, thirdRow);
        Scene scene = new Scene(vbox, 310, 90);
        chemistryStage.setScene(scene);
        chemistryStage.setTitle("Chemistry Courses");
        chemistryStage.show();
    }

    private void addChemistryCourseButton(Button button, String course) {
        if (!isDuplicateButton(course)) {
            Button newBtn = new Button(course);
            newBtn.setOnAction(e -> handleButtonAction(newBtn));

            VBox parentVBox = (VBox) button.getParent();
            int index = parentVBox.getChildren().indexOf(button);
            parentVBox.getChildren().add(index, newBtn);
            allClasses.add(newBtn);
            System.out.println(allClasses);
        } else {
            showError("Duplicate button! " + course + " already added.");
        }
    }
    
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
        if (!isDuplicateButton(project)) {
        newBtn.setOnAction(e -> handleButtonAction(newBtn));

        VBox parentVBox = (VBox) button.getParent();
        int index = parentVBox.getChildren().indexOf(button);
        parentVBox.getChildren().add(index, newBtn);
        allClasses.add(newBtn);
        System.out.println(allClasses);
        }
     else {
        showError(project + " button already existed");
    }
    }
    

    private boolean isDuplicateButton(String buttonText) {
        // Remove spaces, colons, and convert to lower case
        String cleanedButtonText = buttonText.replaceAll("[\\s:]", "").toLowerCase();

        for (Button existingButton : allClasses) {
            // Remove spaces, colons, and convert to lower case
            String cleanedExistingButtonText = existingButton.getText().replaceAll("[\\s:]", "").toLowerCase();

            // Check if the cleaned texts match
            if (cleanedExistingButtonText.equals(cleanedButtonText)) {
                return true; // Duplicate found
            }
        }
        return false; // No duplicate
    }


    // Helper method to show an error message (you can customize this part)
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    public static boolean showErrorWithIgnoreOption(String message) {
    	Alert alert = new Alert(AlertType.CONFIRMATION);
    	alert.setTitle("Confirmation Dialog");
    	alert.setHeaderText("Look, a Confirmation Dialog");
    	alert.setContentText("Are you ok with this?");

    	Optional<ButtonType> result = alert.showAndWait();
    	if (result.get() == ButtonType.OK){
return true;
    	} else {

    		return false;    	}
    }

    private void setButtonColor(Button button, String normalColor, String hoverColor) {
        // Set the initial background color of the button
        button.setStyle("-fx-background-color: " + normalColor + ";");

        // Hover effect
        button.setOnMouseEntered(event -> button.setStyle("-fx-background-color: " + hoverColor + "; -fx-text-fill: white;"));
        button.setOnMouseExited(event -> button.setStyle("-fx-background-color: " + normalColor + "; -fx-text-fill: black;"));
    }


    public static void main(String[] args) {
        launch(args);
    }
}
