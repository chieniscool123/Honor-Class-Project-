<p align="center">
  <img width="300" height="300" alt="Academic_Planner_Logo" 
       src="https://github.com/user-attachments/assets/42e20c5b-98cf-489a-918d-1e07714886b7" />
</p>

# Academic Planner 
#### [Honor Capstone CS Project](https://www.everettcc.edu/programs/academic-resources/honors-program/featured-honors-students) at Everett Community College with Advisor [Eihab El Radie](https://www.linkedin.com/in/eihabelradie/)
#### The inspiration for this project came to me while working at a Vietnamese restaurant. Many of my coworkers were first-generation Vietnamese who dropped out of high school because of language barriers. They chose restaurant work since their dream careers required a degree, but they felt their English skills weren’t strong enough for college. Learning about their struggles made me reflect on my own privilege in speaking English—a privilege that allowed me to apply to college, receive financial aid, and communicate with professors. Having successfully adapted to my life in America, it was disheartening to see members of my community denied educational opportunities simply because of language challenges. This motivated me to design an academic planner that can make the transition into college more accessible for immigrant students, helping non-English speakers organize their studies, track goals, and build confidence in navigating higher education.

## Table of Contents: 
- [Description](#description)
- [Demo](#demo)
- [Installation](#installation)


## Description:
#### This JavaFX application is a personalized academic planner that generates a two-year schedule for computer science students transferring from Everett Community College to UW. Based on a user's starting math level and programming choices, it creates a customizable plan that enforces course prerequisites and prevents duplicate entries to ensure a logical and accurate academic roadmap.

### Key Features:
* **Dynamic Plan Generation:** Creates a two-year academic schedule based on the user's starting math, programming language, and start year.
* **Prerequisite Enforcement:** Automatically checks and enforces prerequisites for sequential courses like English and Chemistry.
* **Course Management:** Allows users to add, edit, delete, and move classes between quarters.
* **Duplicate Prevention:** Prevents the same course from being added more than once.
* **External Course Data:** Loads elective and science courses from external `.txt` files, making the catalog easy to update.

### Built With:
* **Core:** Java
* **User Interface:** JavaFX

### Target Audience:
* This tool is primarily designed for **Computer Science students** at Everett Community College (EvCC) who are planning their transfer to the University of Washington Bothell (UWB).
## Demo 


https://github.com/user-attachments/assets/eeaf679b-314a-4fa7-a3ea-017b23559dcb




## Installation:
### Step 1: Get Code & JavaFX
1.  **Clone the Repo:**
    ```bash
    git clone https://github.com/chieniscool123/Honor-Class-Project-.git
    ```
2.  **Download & Unzip JavaFX:** Get the [JavaFX SDK](https://gluonhq.com/products/javafx/). Remember the path to the `lib` folder (e.g., `C:\java\javafx-sdk-25\lib`).

---
### Step 2: Set Up Eclipse Project
1.  **New Project:** In Eclipse, go to **File > New > Java Project**.
2.  **Name:** Type `Honor_Class_Project_master`.
3.  **Location:** **Uncheck** "Use default location" and **Browse** to select the cloned `Honor-Class-Project--master` folder.
4.  **Configure Build Path:** Right-click the project > **Build Path > Configure Build Path...**.
    * **Libraries Tab:**
        * **Add JRE:** Click **Add Library...** > **JRE System Library** > **Workspace default JRE** (must be JDK 25).
        * **Add JavaFX:** Click **Add External JARs...** > navigate to the JavaFX `lib` folder and add all `.jar` files.
    * **Source Tab:**
        * Click **Add Folder...** > check the `resources` folder > **OK**.
5.  **Update `module-info.java`:** Open `src/module-info.java` and paste this in:
    ```java
    module Honor_Class_Project_maste {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics; 
        opens application to javafx.graphics, javafx.fxml;
    }
    ```
---
### Step 3: Create Run Configuration
1.  Go to **Run > Run Configurations...**.
2.  Right-click **Java Application** > **New Configuration**.
3.  **Name:** `RunPlanner`
4.  **Main Tab:**
    * **Project:** `Honor_Class_Project_master`
    * **Main class:** `application.Main`
5.  **Arguments Tab:**
    * In the **VM arguments** box, paste the line below.
    * **IMPORTANT:** Replace `"YOUR_PATH_TO_JAVAFX_LIB"` with your actual path.

    ```
    --module-path "YOUR_PATH_TO_JAVAFX_LIB;bin" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics
    ```
    * **Example:**
        `--module-path "C:\java\javafx-sdk-25\lib;bin" --add-modules javafx.controls,javafx.fxml --enable-native-access=javafx.graphics`

6.  Click **Apply**, then **Run**.

