package ce;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;


public class GUI_Actions {

    App mainApp = new App();
    String[] dictionaryLanguages = {"deu","ell","eng","fra","ita","swe","tur"};
    public boolean isFilesFound = false;
    private ArrayList<String> filePaths = new ArrayList<>();
    private final ArrayList<Functions> xmlMethodsArrayList = new ArrayList<>();
    private final ArrayList<Functions> xmlMethodsOverEnglish = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    private final ArrayList<LinkedList<String>> languageAndWord = new ArrayList<>();
    private final ArrayList<LinkedList<String>> meaningsOfWord = new ArrayList<>();
    StringBuilder buildText = new StringBuilder();
    private ArrayList<String> synonyms = new ArrayList<>();

    public void popupMenu (Stage stage, Scene scene){

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);


        Label usingAddWordLabel = new Label("In the first selection box, select the language of the word you wants " +
                "to enter and write this word in the space below. In the second selection box, write synonyms of the" +
                " word line by line if there are. In the second selection box, select the language of the meaning you" +
                " want to enter and enter the meaning in the space below. If you want to add more meaning, write line by line.");
        usingAddWordLabel.setWrapText(true);
        usingAddWordLabel.setPadding(new Insets(20,20,0,20));
        usingAddWordLabel.setPrefWidth(1000);
        usingAddWordLabel.setPrefHeight(90);

        ChoiceBox<String> languageChoiceBox1 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox1, new Insets(10, 550, 0, 0));
        languageChoiceBox1.getItems().addAll(dictionaryLanguages);

        Label wordLabel = new Label("Word:");
        wordLabel.setPadding(new Insets(10,575,0,0));

        TextArea ftextA = new TextArea();
        VBox.setMargin(ftextA, new Insets(0, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(500);
        ftextA.setMaxWidth(610);

        Label synonymLabel = new Label("Synonyms:");
        synonymLabel.setPadding(new Insets(20,550,0,0));

        TextArea stextA = new TextArea();
        VBox.setMargin(stextA, new Insets(0, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(500);
        stextA.setMaxWidth(610);


        ChoiceBox<String> languageChoiceBox2 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox2, new Insets(30, 550, 0, 0));
        languageChoiceBox2.getItems().addAll(dictionaryLanguages);

        Label meaningsLabel = new Label("Meanings:");
        meaningsLabel.setPadding(new Insets(10,550,0,0));

        TextArea ttextA = new TextArea();
        VBox.setMargin(ttextA, new Insets(0, 20, 0, 20));
        ttextA.setPrefHeight(80);
        ttextA.setPrefWidth(500);
        ttextA.setMaxWidth(610);


        Button addMeaningButton = new Button("Add");
        VBox.setMargin(addMeaningButton, new Insets(10,20,0, 590));
        addMeaningButton.setOnAction(event -> {
            try {
                addAWordToTxt(languageChoiceBox1.getValue(),languageChoiceBox2.getValue(),ftextA.getText(),stextA,ttextA, stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });


        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonsBox.getChildren().add( backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainBox.getChildren().addAll(usingAddWordLabel, languageChoiceBox1 , wordLabel,ftextA, synonymLabel,stextA,
                languageChoiceBox2, meaningsLabel, ttextA, addMeaningButton);
        borderPane.setCenter(mainBox);

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void firstSearchScene (Stage stage, TextField textField, Scene scene){

        BorderPane borderPane = new BorderPane();


        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

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

        searchingText.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                xmlMethodsArrayList.clear();
                buildText = new StringBuilder();
                languageAndWord.clear();
                searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
                firstSearchScene(stage,searchingText,scene);
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> {
            xmlMethodsArrayList.clear();
            buildText = new StringBuilder();
            languageAndWord.clear();
            searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
            firstSearchScene(stage,searchingText,scene);
        });



        ListView<String> myListView = new ListView<>();
        for (LinkedList<String> lang:
             languageAndWord) {
            myListView.getItems().add(lang.getFirst().toUpperCase(Locale.ENGLISH));
            //myListView.getItems().add(lang.getFirst().toUpperCase() + " : " + lang.getLast());
        }
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
        selectButton.setOnAction(event -> {
            // TODO: ingilizce için burayı değiştiricen
            buildText = new StringBuilder();
            findMeaningOverEnglish(selectedLanguage[0].toLowerCase(Locale.ENGLISH),searchingText.getText().toLowerCase(Locale.ENGLISH));
            choosingLanguage(textField, stage, scene, selectedLanguage[0]);
        });


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

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);


        VBox searchMeaningBox = new VBox();
        HBox textWithButton = new HBox();
        searchMeaningBox.setAlignment(Pos.TOP_CENTER);
        textWithButton.setAlignment(Pos.TOP_CENTER);

        TextField searchingText = new TextField(textField.getText());
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(80,20,30,60));
        searchingText.minWidth(600);

        searchingText.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                xmlMethodsArrayList.clear();
                buildText = new StringBuilder();
                languageAndWord.clear();
                searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
                firstSearchScene(stage,searchingText,scene);
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> {
            xmlMethodsArrayList.clear();
            buildText = new StringBuilder();
            languageAndWord.clear();
            searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
            firstSearchScene(stage, searchingText, scene);
        });

        Label chosenLanguageLabel = new Label("Meanings: ");
        chosenLanguageLabel.setFont(new Font(20));
        chosenLanguageLabel.setPadding(new Insets(0,0,30,0));

        TextArea meaningsArea = new TextArea(String.valueOf(buildText));// TODO: buraya çıkarılacak anlamlar
        meaningsArea.setFont(new Font(15));
        meaningsArea.setWrapText(true);
        meaningsArea.setEditable(false);
        VBox.setMargin(meaningsArea, new Insets(0, 40, 80, 40));
        meaningsArea.setPrefWidth(500);
        meaningsArea.setMaxWidth(1200);
        meaningsArea.setMaxHeight(1000);
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

    public void editMeaning (Stage stage, Scene scene) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);


        VBox typeBox = new VBox();
        HBox textWithButton = new HBox();
        typeBox.setAlignment(Pos.TOP_CENTER);
        textWithButton.setAlignment(Pos.TOP_CENTER);

        Label typeLabel = new Label("Choose the language and type the word that you want to edit its meanings.");
        typeLabel.setWrapText(true);
        typeLabel.setPadding(new Insets(100,0,0,0));

        ChoiceBox<String> languageBox = new ChoiceBox<>();
        languageBox.getItems().addAll(dictionaryLanguages);
        HBox.setMargin(languageBox, new Insets(0,30,0,40));

        TextField searchingText = new TextField();
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(30,0,0,0));
        searchingText.minWidth(600);

        searchingText.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
                editChosingLanguage(stage,scene,searchingText.getText(),languageBox.getValue());
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,30,30));
        searchButton.setOnAction(event -> {
            searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
            editChosingLanguage(stage,scene, searchingText.getText(), languageBox.getValue());
        });

        // To find absolute path of img file
        File file = new File("synonymImage.png");

        Image image = new Image(new FileInputStream(file.getAbsolutePath()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Group imageGroup = new Group(imageView);

        textWithButton.getChildren().addAll(languageBox, searchingText, searchButton);
        typeBox.getChildren().addAll(typeLabel, textWithButton, imageGroup);

        borderPane.setCenter(typeBox);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonsBox.getChildren().add( backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Edit Meaning");
        stage.setScene(scene);
        stage.show();
    }

    public void editChosingLanguage (Stage stage, Scene scene, String word, String language1){
        BorderPane borderPane = new BorderPane();


        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox listBox = new VBox();
        listBox.setAlignment(Pos.TOP_CENTER);

        ListView<String> myListView = new ListView<>();
        for (LinkedList<String> lang:
                languageAndWord) {
            myListView.getItems().add(lang.getFirst().toUpperCase(Locale.ENGLISH));
            //myListView.getItems().add(lang.getFirst().toUpperCase() + " : " + lang.getLast());
        }
        myListView.setPrefHeight(350);
        myListView.setPrefWidth(630);
        myListView.setMaxWidth(630);

        Label selectMeaningLabel = new Label();
        VBox.setMargin(selectMeaningLabel, new Insets(40,0,30,0));
        selectMeaningLabel.setFont(new Font(20));

        String[] selectedLanguage = new String[1];
        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedLanguage[0] = myListView.getSelectionModel().getSelectedItem();
            selectMeaningLabel.setText(selectedLanguage[0]);
        });

        Button selectButton = new Button("Select");
        VBox.setMargin(selectButton, new Insets(20,585,0,0));
        selectButton.setOnAction(event -> {
            // TODO: meaning için bura değişecek
            buildText = new StringBuilder();
            showingMeanings(stage,scene,word,language1,selectedLanguage[0]);
        });

        listBox.getChildren().addAll(selectMeaningLabel,myListView, selectButton);



        borderPane.setCenter(listBox);

        HBox lastBox = new HBox();
        lastBox.setAlignment(Pos.CENTER);
        Label chooseLabel = new Label("Please select the language in which you want to edit the meanings of the word.");
        chooseLabel.setPadding(new Insets(0,165,0,0));
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,20,5,5));
        backButton.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        lastBox.getChildren().addAll(backButton, chooseLabel);
        borderPane.setBottom(lastBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Edit Meaning");
        stage.setScene(scene);
        stage.show();
    }

    public void showingMeanings (Stage stage, Scene scene, String word, String language1, String language2){
        BorderPane borderPane = new BorderPane();


        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox listBox = new VBox();
        listBox.setAlignment(Pos.TOP_CENTER);

        ListView<String> myListView = new ListView<>();
        myListView.getItems().add("New meaning");
        for (LinkedList<String> lang:
                languageAndWord) {
            myListView.getItems().add(lang.getFirst().toUpperCase(Locale.ENGLISH));
            //myListView.getItems().add(lang.getFirst().toUpperCase() + " : " + lang.getLast());
        }
        myListView.setPrefHeight(350);
        myListView.setPrefWidth(630);
        myListView.setMaxWidth(630);

        Label selectMeaningLabel = new Label();
        VBox.setMargin(selectMeaningLabel, new Insets(40,0,30,0));
        selectMeaningLabel.setFont(new Font(20));

        String[] selectedMeaning = new String[1];
        myListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMeaning[0] = myListView.getSelectionModel().getSelectedItem();
            selectMeaningLabel.setText(selectedMeaning[0]);
        });

        Button selectButton = new Button("Select");
        VBox.setMargin(selectButton, new Insets(20,585,0,0));
        selectButton.setOnAction(event -> {
            // TODO: meaning için bura değişecek
            buildText = new StringBuilder();
            editingChosenMeaning(stage,scene,word,selectedMeaning[0],language1,language2);
        });

        listBox.getChildren().addAll(selectMeaningLabel,myListView, selectButton);



        borderPane.setCenter(listBox);

        HBox lastBox = new HBox();
        lastBox.setAlignment(Pos.CENTER);
        Label chooseLabel = new Label("Please choose the meaning you want edit. If you want add meaning choose 'New Meaning'.");
        chooseLabel.setPadding(new Insets(0,100,0,0));
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,20,5,5));
        backButton.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });
        lastBox.getChildren().addAll(backButton, chooseLabel);
        borderPane.setBottom(lastBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Edit Meaning");
        stage.setScene(scene);
        stage.show();
    }

    public void editingChosenMeaning (Stage stage, Scene scene, String word, String meaning, String language1, String language2){
        BorderPane borderPane = new BorderPane();


        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);
        HBox textAndButtonBox = new HBox();
        textAndButtonBox.setAlignment(Pos.TOP_CENTER);
        VBox buttonsBox = new VBox();
        buttonsBox.setAlignment(Pos.TOP_CENTER);

        Label noteLabel = new Label("Add - Adding a new meaning.\n" +
                "Edit - Editing chosen meaning.\n" +
                "Remove - Removing chosen meaning.\n" +
                "Note: If you want to add more meaning write line by line.");
        noteLabel.setPadding(new Insets(50,40,50,0));

        TextArea textArea = new TextArea();
        textArea.setPrefHeight(300);
        if(!meaning.equals("New Meaning"))
            textArea.setText(meaning);

        Button addButton = new Button("Add");
        VBox.setMargin(addButton,new Insets(80,0,0,0));
        Button editButton = new Button("Edit");
        VBox.setMargin(editButton,new Insets(30,0,30,0));
        Button removeButton = new Button("Remove");

        HBox.setMargin(buttonsBox,new Insets(0,0,0,30));

        buttonsBox.getChildren().addAll(addButton,editButton,removeButton);
        textAndButtonBox.getChildren().addAll(textArea,buttonsBox);
        mainBox.getChildren().addAll(noteLabel,textAndButtonBox);
        borderPane.setCenter(mainBox);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonBox.getChildren().add( backButton);

        backButton.setOnAction(event -> showingMeanings(stage, scene, word,language1, language2));

        borderPane.setBottom(buttonBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Edit Meaning");
        stage.setScene(scene);
        stage.show();
    }

    public void findSynonym (Stage stage, Scene scene) throws FileNotFoundException {

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);


        VBox typeBox = new VBox();
        HBox textWithButton = new HBox();
        typeBox.setAlignment(Pos.TOP_CENTER);
        textWithButton.setAlignment(Pos.TOP_CENTER);

        Label typeLabel = new Label("Choose the language and type the word that you want to get synonyms.");
        typeLabel.setWrapText(true);
        typeLabel.setPadding(new Insets(100,0,0,0));

        ChoiceBox<String> languageBox = new ChoiceBox<>();
        languageBox.getItems().addAll(dictionaryLanguages);
        HBox.setMargin(languageBox, new Insets(0,30,0,40));

        TextField searchingText = new TextField();
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(30,0,0,0));
        searchingText.minWidth(600);

        searchingText.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
                synonyms(stage,scene, searchingText,languageBox.getValue());
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,30,30));
        searchButton.setOnAction(event -> {
            try {
                findWordSynonym(languageBox.getValue(), searchingText.getText());
            } catch (IOException e) {
                e.printStackTrace();
            }
            synonyms(stage,scene,searchingText,languageBox.getValue());
        });

        // To find absolute path of img file
        File file = new File("synonymImage.png");

        Image image = new Image(new FileInputStream(file.getAbsolutePath()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Group imageGroup = new Group(imageView);

        textWithButton.getChildren().addAll(languageBox, searchingText, searchButton);
        typeBox.getChildren().addAll(typeLabel, textWithButton, imageGroup);

        borderPane.setCenter(typeBox);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonsBox.getChildren().add( backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Find Synonym");
        stage.setScene(scene);
        stage.show();
    }

    public void synonyms (Stage stage, Scene scene, TextField textField, String language){
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);

        Label expLabel = new Label("Here are the synonyms of "+ textField.getText()+ " :");
        expLabel.setPadding(new Insets(50,0,0,0));


        String values = getSynonyms().toString();
        values = values.substring(1, values.length() - 1); // remove the square brackets
        values = values.replace(", ", "\n");// replace commas and spaces with newline characters
        try {
            values = new String(values.getBytes("ISO-8859-1"), "UTF-8"); // encode the string using UTF-8
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        TextArea synonymsArea = new TextArea(values);
        synonymsArea.setFont(new Font(15));
        synonymsArea.setWrapText(true);
        synonymsArea.setEditable(false);
        VBox.setMargin(synonymsArea, new Insets(20, 40, 20, 40));
        synonymsArea.setPrefWidth(300);
        synonymsArea.setMaxWidth(800);
        synonymsArea.setMaxHeight(1000);
        synonymsArea.setPrefHeight(400);

        mainBox.getChildren().addAll(expLabel,synonymsArea);
        borderPane.setCenter(mainBox);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonsBox.getChildren().add( backButton);

        backButton.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Find Synonym");
        stage.setScene(scene);
        stage.show();
    }

    public void helpMenu (Stage stage, Scene  scene, Integer integer){
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mManualItem = new MenuItem("User Manual");
        mManualItem.setOnAction(event -> helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                editMeaning(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mHelp.getItems().addAll(mManualItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

        borderPane.setTop(mainMenuBar);

        VBox mainBox = new VBox();
        mainBox.setAlignment(Pos.TOP_CENTER);



        TextArea textArea = new TextArea();// TODO: buraya eş anlamlar çıkarılacak
        if (integer == 1)
            textArea.setText("asdfhjgkhl");
        else
            textArea.setText("https://www.instagram.com/oguz_tavur/");
        textArea.setFont(new Font(15));
        textArea.setWrapText(true);
        textArea.setEditable(false);
        VBox.setMargin(textArea, new Insets(70, 40, 0, 40));
        textArea.setPrefWidth(300);
        textArea.setMaxWidth(800);
        textArea.setMaxHeight(1000);
        textArea.setPrefHeight(400);

        mainBox.getChildren().add(textArea);
        borderPane.setCenter(mainBox);

        HBox buttonsBox = new HBox();
        buttonsBox.setAlignment(Pos.CENTER);
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,605,5,5));
        buttonsBox.getChildren().add( backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage,scene);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        borderPane.setBottom(buttonsBox);

        scene.setRoot(borderPane);
        stage.setTitle("Team 6 - Help");
        stage.setScene(scene);
        stage.show();

    }

    public void backToMainScreen (Stage stage, Scene scene) throws IOException {
        mainApp.start(stage);

    }

    public void buildTextBody(String lang, ArrayList<String> missingLanguages){

        for (Functions objects:
             xmlMethodsArrayList) {

            if (objects.meaningTextContent != null) {
                if (objects.getSearchIn().equals(lang)) {
                    buildText.append(objects.meaningTextContent);
                }
            }
        }

        for (Functions overEnglish:
             xmlMethodsOverEnglish) {
            if (overEnglish.getSearchIn().equals("eng") && missingLanguages.contains(overEnglish.getFoundIn())) {
                buildText.append(overEnglish.meaningTextContent);
            }
        }
    }

    protected void findMeaningOverEnglish(String lang,String word){

        xmlMethodsOverEnglish.clear();

        ArrayList<String> missingLanguages = new ArrayList<>(Arrays.asList(dictionaryLanguages));
        missingLanguages.remove(lang);

        for (Functions objects:
             xmlMethodsArrayList) {
            if (objects.getSearchIn().equals(lang) && objects.getMeanings().size() > 0) {
                missingLanguages.remove(objects.getFoundIn());
            }
        }


        ArrayList<String> missingFilePaths = new ArrayList<>();
        for (String rightL:
             missingLanguages) {
            missingFilePaths.add("GraphFiles\\eng-".concat(rightL).concat(".txt")); // Burayı değiştirddim
        }


        searchThreads(word,lang,missingFilePaths,true);
        buildTextBody(lang, missingLanguages);

    }

    public void searchThreads(String word,String lang,ArrayList<String> filePaths, boolean isOverEnglish){

        if (isOverEnglish) {
            ArrayList<LinkedList<String>> englishMeanings = new ArrayList<>();
            for (Functions xmlMethods:
                 xmlMethodsArrayList) {
                if (xmlMethods.getSearchIn().equals(lang) && xmlMethods.getFoundIn().equals("eng") && xmlMethods.getMeanings().size() > 0) {
                    englishMeanings = xmlMethods.getMeanings();

                }
            }

            if (englishMeanings.size() > 0) {
                for (String paths:
                        filePaths) {
                    Functions xmlMethods = new Functions();
                    xmlMethods.setFilepath(paths);
                    xmlMethods.setWord(englishMeanings.get(0).get(0));
                    xmlMethodsOverEnglish.add(xmlMethods);

                    Thread thread = new Thread(xmlMethods);
                    thread.start();
                    threads.add(thread);
                }

                try {
                    for (Thread t:
                         threads) {
                        t.join();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        else {

            for (String filepath:
                    filePaths) {

                Functions xmlMethod = new Functions();
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

        }


        threads.clear();

        if (!isOverEnglish) {
            languagesSearchedIn();
        }

    }

    protected void languagesSearchedIn(){

        for (Functions xmlClass:
             xmlMethodsArrayList) {

            if (xmlClass.getMeanings().size() > 0) {



                boolean isLanguageExistInArray = false;
                for (LinkedList<String> strings : languageAndWord) {
                    if (strings.getFirst().equals(xmlClass.getSearchIn())) {
                        isLanguageExistInArray = true;
                        break;
                    }
                }

                if (!isLanguageExistInArray) {
                    //System.out.println("Found in: " + xmlClass.getSearchIn());
                    LinkedList<String> lw = new LinkedList<>();
                    // First, we are adding word's language
                    lw.add(xmlClass.getSearchIn());
                    // Then, we are adding meaning's language
                    lw.add(xmlClass.getFoundIn());
                    // Finally, we are adding the word itself
                    lw.add(xmlClass.getWord());
                    languageAndWord.add(lw);
                }
            }
        }

        //System.out.println(languageAndWord.size());
    }

    protected void addAWordToTxt(String language1, String language2, String aWord, TextArea textArea1, TextArea textArea2, Stage stage, Scene scene) throws IOException {
        String wLanguage = language1;
        String mLanguage = language2;
        String filePath = "GraphFiles/" + wLanguage + "-" + mLanguage + ".txt";
        String word = aWord; // the new word you want to add
        String[] meanings = textArea2.getText().split("\\n"); // the meanings of the new word
        String[] synonyms =  textArea1.getText().split("\\n"); // the synonyms of the new word

        try (RandomAccessFile rFile = new RandomAccessFile(filePath, "rw")) {
            StringBuilder newLine = new StringBuilder(word + ";");
            for (int i = 0; i < meanings.length; i++) {
                newLine.append(meanings[i]);
                if (i < meanings.length - 1) {
                    newLine.append("&");}
            }
            newLine.append(";");
            for (int j = 0; j < synonyms.length; j++){
                newLine.append(synonyms[j]);
                if (j< synonyms.length - 1){
                    newLine.append("&");
                }
            }
            newLine.append(";\n");

            String line;
            long lastLinePos = 0;
            while ((line = rFile.readLine()) != null) {
                if (line.compareTo(newLine.toString()) > 0) {
                    break;
                }
                lastLinePos = rFile.getFilePointer();
            }

            byte[] remainingBytes = new byte[(int) (rFile.length() - lastLinePos)];
            rFile.seek(lastLinePos);
            rFile.readFully(remainingBytes);
            rFile.seek(lastLinePos);
            rFile.writeBytes(newLine.toString());
            rFile.write(remainingBytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        backToMainScreen(stage, scene);
    }

    protected void findWordSynonym(String wLanguage, String sWord) throws IOException {
        String filePath;
        String searchWord = sWord;
        String language = wLanguage;
        ArrayList<String> synonymList = new ArrayList<>();
        Set<String> addedSynonyms = new HashSet<>();
        if (language.equals("deu")) {
            filePath = "GraphFiles/deu-eng.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(";");
                String word = parts[0];
                if (word.equals(searchWord)) {
                    String[] synonyms = parts[2].split("&");
                    for (String synonym : synonyms) {
                        if (!addedSynonyms.contains(synonym)) {
                            synonymList.add(synonym);
                            addedSynonyms.add(synonym);
                        }
                    }
                }
            }
        } else if (language.equals("eng")) {
            filePath = "GraphFiles/eng-deu.txt";
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(";");
                String word = parts[0];
                if (word.equals(searchWord)) {
                    String[] synonyms = parts[2].split("&");
                    for (String synonym : synonyms) {
                        if (!addedSynonyms.contains(synonym)) {
                            synonymList.add(synonym);
                            addedSynonyms.add(synonym);
                        }
                    }
                }
            }
        } else if (!language.equals("eng") && !language.equals("deu")) {
            filePath = "GraphFiles/" + language + "-eng.txt";
            Set<String> searchWordMeanings = new HashSet<>();
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            String[] lines = content.split("\n");
            for (String line : lines) {
                String[] parts = line.split(";");
                String word = parts[0];
                if (word.equals(searchWord)) {
                    String[] meanings = parts[1].split("&");
                    for (String meaning : meanings) {
                        searchWordMeanings.add(meaning);
                    }
                }
            }
            System.out.println("Words with the same meanings as " + searchWord + ":");
            for (String line : lines) {
                String[] parts = line.split(";");
                String word = parts[0];
                if (!word.equals(searchWord)) {
                    String[] meanings = parts[1].split("&");
                    for (String meaning : meanings) {
                        if (searchWordMeanings.contains(meaning)) {
                            if (searchWordMeanings.contains(meaning)) {
                                if (!addedSynonyms.contains(word)) {
                                    synonymList.add(word);
                                    addedSynonyms.add(word);
                                }
                                break;
                            }
                        }
                    }
                }
            }
        }
        setSynonyms(synonymList);
    }

    public ArrayList<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;
    }

    public ArrayList<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(ArrayList<String> synonyms) {
        this.synonyms = synonyms;
    }
}
