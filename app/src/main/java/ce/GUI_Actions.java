package ce;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class GUI_Actions {

    App mainApp = new App();
    private double sceneWidth = 650;
    private double sceneHeight = 600;
    public MenuItem firstChosenLanguage;
    public MenuItem secondChosenLanguage;
    public String fChosenLanguage;
    public String sChosenLanguage;
    public String language1 = "Turkish";
    public String language2 = "English";
    public String language3 = "German";
    public String language4 = "Italian";
    public String language5 = "Modern Greek";
    public String language6 = "French";
    public String language7 = "Swedish";
    public boolean isFilesFound = false;
    private final ArrayList<String> filePaths = new ArrayList<>();
    private final ArrayList<XML_Methods> xmlMethodsArrayList = new ArrayList<>();
    private final ArrayList<Node> nodeeeeesss  = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    private final ArrayList<String> textBody = new ArrayList<>();
    StringBuilder meaningTextContent = new StringBuilder();

    public void popupMenu (Stage stage, Scene scene){

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);


        Label usingAddWordLabel = new Label("In the first selection box, select the language of the word you wants " +
                "to enter and write this word in the space below. In the second selection box, select the language of the " +
                "meaning you want to enter and enter the meaning in thes space below. If you want to add more meaning, press " +
                "the \"Add meaning\"s button at the bottom. You can add up to 3 meanings at once.");
        usingAddWordLabel.setWrapText(true);
        usingAddWordLabel.setPadding(new Insets(20,20,0,20));
        usingAddWordLabel.setPrefWidth(1000);
        usingAddWordLabel.setPrefHeight(90);

        ChoiceBox<String> languageChoiceBox1 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox1, new Insets(10, 520, 0, 20));
        languageChoiceBox1.getItems().addAll(language1, language2, language3, language4, language5, language6, language7);

        TextArea ftextA = new TextArea();
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(500);
        ftextA.setMaxWidth(610);

        ChoiceBox<String> languageChoiceBox2 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox2, new Insets(10, 520, 0, 20));
        languageChoiceBox2.getItems().addAll(language1, language2, language3, language4, language5, language6, language7);

        TextArea stextA = new TextArea();
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(500);
        stextA.setMaxWidth(610);

        Button addMeaningButton = new Button("Add Meaning");
        VBox.setMargin(addMeaningButton, new Insets(10,20,0, 540));
        addMeaningButton.setOnAction(event -> addMeaning(ftextA,stextA,languageChoiceBox1,languageChoiceBox2,stage, scene) );



        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0,5,5,5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0,0,5,5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainBox.getChildren().addAll(usingAddWordLabel, languageChoiceBox1 ,ftextA, languageChoiceBox2, stextA, addMeaningButton);
        borderPane.setCenter(mainBox);

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void firstSearchScene (Stage stage, TextField textField, Scene scene){

        int meanNumber = 2;
        BorderPane borderPane = new BorderPane();


        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        borderPane.setTop(mainMenuBar);


        VBox searchMeanBox = new VBox();
        searchMeanBox.setAlignment(Pos.TOP_CENTER);
        HBox textWithButton = new HBox();
        textWithButton.setAlignment(Pos.TOP_CENTER);

        TextField searchingText = new TextField(textField.getText());
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(80,0,30,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> firstSearchScene(stage,searchingText,scene));


        String[] languageList = {"Modern Greece", "Turkish ", "fr"};
        ListView<String> myListView = new ListView<>();
        myListView.getItems().addAll(languageList);
        myListView.setPrefHeight(250);
        myListView.setPrefWidth(630);
        myListView.setMaxWidth(630);

        Label selectLanguageLabel = new Label();
        VBox.setMargin(selectLanguageLabel, new Insets(0,0,30,0));
        selectLanguageLabel.setFont(new Font(20));
        selectLanguageLabel.setTextFill(Color.DARKTURQUOISE);

        String[] selectedLanguage = new String[1];
        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLanguage[0] = myListView.getSelectionModel().getSelectedItem();
            selectLanguageLabel.setText(selectedLanguage[0]);
        });

        Button selectButton = new Button("Select");
        VBox.setMargin(selectButton, new Insets(20,585,0,0));
        selectButton.setOnAction(event -> choosingLanguage(textField, stage, scene,
                selectedLanguage[0]));


        textWithButton.getChildren().addAll(searchingText, searchButton);
        searchMeanBox.getChildren().addAll(textWithButton, selectLanguageLabel,myListView, selectButton);



        borderPane.setCenter(searchMeanBox);

        HBox lastBox = new HBox();
        lastBox.setAlignment(Pos.CENTER);
        Label chooseLabel = new Label("Please choose the language you want.");
        chooseLabel.setPadding(new Insets(0,380,0,0));
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,20,5,5));
        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        lastBox.getChildren().addAll(backButton, chooseLabel);
        borderPane.setBottom(lastBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();
    }


    public void choosingLanguage (TextField textField, Stage stage, Scene scene, String string){

        searchThreads("-aktig");

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        borderPane.setTop(mainMenuBar);


        VBox searchMeaningBox = new VBox();
        HBox textWithButton = new HBox();
        searchMeaningBox.setAlignment(Pos.TOP_CENTER);
        textWithButton.setAlignment(Pos.TOP_CENTER);

        TextField searchingText = new TextField(textField.getText());
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(80,20,80,60));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> firstSearchScene(stage, searchingText, scene));

        Label chosenLanguageLabel = new Label("Meanings: ");
        chosenLanguageLabel.setPadding(new Insets(0,520,0,0));

        TextArea meaningsArea = new TextArea(nodeeeeesss.get(1).getTextContent());
        meaningsArea.setFont(new Font(15));
        meaningsArea.setWrapText(true);
        meaningsArea.setEditable(false);
        VBox.setMargin(meaningsArea, new Insets(0, 40, 80, 40));
        meaningsArea.setPrefWidth(500);
        meaningsArea.setMaxWidth(630);
        meaningsArea.setPrefHeight(400);


        textWithButton.getChildren().addAll(searchingText,searchButton);
        searchMeaningBox.getChildren().addAll(textWithButton, chosenLanguageLabel, meaningsArea);

        HBox lastBox = new HBox();
        lastBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        backButton.setOnAction(event -> firstSearchScene(stage, textField, scene));
        lastBox.getChildren().addAll(backButton);
        borderPane.setBottom(lastBox);

        borderPane.setCenter(searchMeaningBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();


    }

    public void addMeaning (TextArea textArea1, TextArea textArea2, ChoiceBox<String> choiceBox1,ChoiceBox<String> choiceBox2,
                            Stage stage, Scene scene) {
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage, scene));

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);

        Label usingAddWordLabel = new Label("In the first selection box, select the language of the word you wants " +
                "to enter and write this word in the space below. In the second selection box, select the language of the " +
                "meaning you want to enter and enter the meaning in thes space below. If you want to add more meaning, press " +
                "the \"Add meaning\"s button at the bottom. You can add up to 3 meanings at once.");
        usingAddWordLabel.setWrapText(true);
        usingAddWordLabel.setPadding(new Insets(20,20,0,20));
        usingAddWordLabel.setPrefWidth(1000);
        usingAddWordLabel.setPrefHeight(90);

        Label chosenLanguageLabel1 = new Label( choiceBox1.getValue()+":");
        chosenLanguageLabel1.setPadding(new Insets(10, 605, 0, 20));

        TextArea ftextA = new TextArea(textArea1.getText());
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(500);
        ftextA.setMaxWidth(610);

        Label chosenLanguageLabel2 = new Label(choiceBox2.getValue()+":");
        chosenLanguageLabel2.setPadding(new Insets(10, 605, 0, 20));

        TextArea stextA = new TextArea(textArea2.getText());
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(500);
        stextA.setMaxWidth(610);

        TextArea ttextA = new TextArea();
        VBox.setMargin(ttextA, new Insets(0, 20, 0, 20));
        ttextA.setPrefHeight(80);
        ttextA.setPrefWidth(500);
        ttextA.setMaxWidth(610);


        Button addMeaningButton = new Button("Add Meaning");
        addMeaningButton.setOnAction(event -> addMeaningAgain(textArea1,textArea2, ttextA,choiceBox1,choiceBox2,stage, scene));
        VBox.setMargin(addMeaningButton, new Insets(10, 20, 0, 540));


        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0, 5, 5, 5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0, 0, 5, 5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainBox.getChildren().addAll(usingAddWordLabel, chosenLanguageLabel1, ftextA, chosenLanguageLabel2, stextA, ttextA,addMeaningButton);
        borderPane.setCenter(mainBox);

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void addMeaningAgain (TextArea textArea1, TextArea textArea2, TextArea textArea3,ChoiceBox<String> choiceBox1,
                                 ChoiceBox<String> choiceBox2, Stage stage, Scene scene){
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage, scene));

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);

        Label usingAddWordLabel = new Label("In the first selection box, select the language of the word you wants " +
                "to enter and write this word in the space below. In the second selection box, select the language of the " +
                "meaning you want to enter and enter the meaning in thes space below. If you want to add more meaning, press " +
                "the \"Add meaning\"s button at the bottom. You can add up to 3 meanings at once.");
        usingAddWordLabel.setWrapText(true);
        usingAddWordLabel.setPadding(new Insets(20,20,0,20));
        usingAddWordLabel.setPrefWidth(1000);
        usingAddWordLabel.setPrefHeight(90);

        Label chosenLanguageLabel1 = new Label( choiceBox1.getValue()+":");
        chosenLanguageLabel1.setPadding(new Insets(10, 605, 0, 20));

        TextArea ftextA = new TextArea(textArea1.getText());
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(500);
        ftextA.setMaxWidth(610);

        Label chosenLanguageLabel2 = new Label( choiceBox2.getValue()+":");
        chosenLanguageLabel2.setPadding(new Insets(10, 605, 0, 20));

        TextArea stextA = new TextArea(textArea2.getText());
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(500);
        stextA.setMaxWidth(610);

        TextArea ttextA = new TextArea(textArea3.getText());
        VBox.setMargin(ttextA, new Insets(0, 20, 0, 20));
        ttextA.setPrefHeight(80);
        ttextA.setPrefWidth(500);
        ttextA.setMaxWidth(610);

        TextArea fotextA = new TextArea();
        VBox.setMargin(fotextA, new Insets(0, 20, 0, 20));
        fotextA.setPrefHeight(80);
        fotextA.setPrefWidth(500);
        fotextA.setMaxWidth(610);



        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0, 5, 5, 5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0, 0, 5, 5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainBox.getChildren().addAll(usingAddWordLabel, chosenLanguageLabel1, ftextA, chosenLanguageLabel2, stextA, ttextA,fotextA);
        borderPane.setCenter(mainBox);

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void backToMainScreen (Stage stage, Scene scene) throws IOException {
        setSceneWidth(scene.getWidth());
        setSceneHeight(scene.getHeight());
        System.out.println(getSceneHeight());
        mainApp.start(stage);

    }

    public void searchThreads(String word){

        for (String filepath:
             filePaths) {

            XML_Methods xmlMethod = new XML_Methods();
            xmlMethod.setWord(word);
            xmlMethod.setFilepath(filepath);
            xmlMethodsArrayList.add(xmlMethod);

            Thread thread = new Thread(xmlMethod);
            thread.start();
            threads.add(thread);
        }

        try {
            for (Thread thread:
                    threads) {

                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mergeMeanings();

    }

    public void mergeMeanings(){

        System.out.println("Oğuz");

        for (XML_Methods xmlClass:
             xmlMethodsArrayList) {

            if (xmlClass.getMeanings().size()>0) {


                nodeeeeesss.add(xmlClass.getFoundEntries());

                for (LinkedList<String> linkedList:
                        xmlClass.getMeanings()) {
                    int meaningCounter = 1;
                    for (String meaning:
                         linkedList) {
                        meaningTextContent.append(meaningCounter).append(". ").append(meaning);
                        meaningTextContent.append("\n");
                        System.out.println("Berke");
                        meaningCounter++;
                    }
                    meaningTextContent.append("--------\n");
                    System.out.println("Ali");
                }
                meaningTextContent.append("+++++++++++++++++++++++++++++++\n");
            }
        }
    }

    public void searchAll(){

        // To read
        File folder = new File("Dictionary");

        // To take all file inside Dictionary directory
        File[] files = folder.listFiles(File::isFile);

        if (files != null) {

            System.out.println(files.length + " File Found!");

            for (File file:
                    files) {
                if (file.getName().contains(".xml")) {
                    filePaths.add(file.getPath());
                }
            }
            isFilesFound = true;
        }
    }

    public double getSceneWidth() {
        return sceneWidth;
    }

    public void setSceneWidth(double sceneWidth) {
        this.sceneWidth = sceneWidth;
    }

    public double getSceneHeight() {
        return sceneHeight;
    }

    public void setSceneHeight(double sceneHeight) {
        this.sceneHeight = sceneHeight;
    }
}
