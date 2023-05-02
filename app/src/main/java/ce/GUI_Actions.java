package ce;

import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.w3c.dom.Node;

import java.io.File;
import java.io.IOException;
import java.util.*;


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
    String[] dictionaryLanguages = {"deu","ell","eng","fra","ita","swe","tur"};
    public boolean isFilesFound = false;
    private ArrayList<String> filePaths = new ArrayList<>();
    private final ArrayList<XML_Methods> xmlMethodsArrayList = new ArrayList<>();
    private final ArrayList<XML_Methods> xmlMethodsOverEnglish = new ArrayList<>();
    private final List<Thread> threads = new ArrayList<>();
    private final ArrayList<LinkedList<String>> languageAndWord = new ArrayList<>();
    StringBuilder buildText = new StringBuilder();

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

    public void buildTextBody(String lang, ArrayList<String> missingLanguages){

        for (XML_Methods objects:
             xmlMethodsArrayList) {

            if (objects.meaningTextContent != null) {
                if (objects.getSearchIn().equals(lang)) {
                    buildText.append(objects.meaningTextContent);
                }
            }
        }

        for (XML_Methods overEnglish:
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

        for (XML_Methods objects:
             xmlMethodsArrayList) {
            if (objects.getSearchIn().equals(lang) && objects.getMeanings().size() > 0) {
                missingLanguages.remove(objects.getFoundIn());
            }
        }


        ArrayList<String> missingFilePaths = new ArrayList<>();
        for (String rightL:
             missingLanguages) {
            missingFilePaths.add("Dictionary\\eng-".concat(rightL).concat(".xml"));
        }


        searchThreads(word,lang,missingFilePaths,true);
        buildTextBody(lang, missingLanguages);

    }

    public void searchThreads(String word,String lang,ArrayList<String> filePaths, boolean isOverEnglish){

        if (isOverEnglish) {
            ArrayList<LinkedList<String>> englishMeanings = new ArrayList<>();
            for (XML_Methods xmlMethods:
                 xmlMethodsArrayList) {
                if (xmlMethods.getSearchIn().equals(lang) && xmlMethods.getFoundIn().equals("eng") && xmlMethods.getMeanings().size() > 0) {
                    englishMeanings = xmlMethods.getMeanings();

                }
            }

            if (englishMeanings.size() > 0) {
                for (String paths:
                        filePaths) {
                    XML_Methods xmlMethods = new XML_Methods();
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

        }


        threads.clear();

        if (!isOverEnglish) {
            languagesSearchedIn();
        }

    }

    protected void languagesSearchedIn(){

        for (XML_Methods xmlClass:
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

    public ArrayList<String> getFilePaths() {
        return filePaths;
    }

    public void setFilePaths(ArrayList<String> filePaths) {
        this.filePaths = filePaths;
    }
}
