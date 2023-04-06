package ce;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class GUI_Actions {

    App mainApp = new App();
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
    private final ArrayList<String> filePaths = new ArrayList<>();
    private final ArrayList<XML_Methods> xmlMethodsArrayList = new ArrayList<>();
    private final ArrayList<Thread> threads = new ArrayList<>();

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

        Label usingAddWordLabel = new Label("""
                In the first selection box, select the language of the word you want\s
                to enter and write this word in the space below. In the second selection box,
                select the language of the meaning you want to enter and enter the meaning in the\s
                space below. If you want to add more meaning, press the "Add meaning"\s
                button at the bottom. You can add up to 3 meanings at once.""");

        usingAddWordLabel.setPadding(new Insets(20,20,0,20));

        ChoiceBox<String> languageChoiceBox1 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox1, new Insets(10, 20, 0, 20));
        languageChoiceBox1.getItems().addAll(language1, language2, language3, language4, language5, language6, language7);

        TextArea ftextA = new TextArea();
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(350);

        ChoiceBox<String> languageChoiceBox2 = new ChoiceBox<>();
        VBox.setMargin(languageChoiceBox2, new Insets(10, 20, 0, 20));
        languageChoiceBox2.getItems().addAll(language1, language2, language3, language4, language5, language6, language7);

        TextArea stextA = new TextArea();
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(350);

        Button addMeaningButton = new Button("Add Meaning");
        VBox.setMargin(addMeaningButton, new Insets(10,20,0, 390));
        addMeaningButton.setOnAction(event -> addMeaning(ftextA,stextA,languageChoiceBox1,languageChoiceBox2,stage, scene) );



        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0,5,5,5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0,0,5,5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
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

    public void searchingAction (TextField textField, Stage stage, Scene scene){
        for (String filePath:
             filePaths) {

            XML_Methods xmlMethods = new XML_Methods();
            xmlMethods.setWord(textField.getText());
            xmlMethods.setFilepath(filePath);

            xmlMethodsArrayList.add(xmlMethods);

            Thread thread = new Thread(xmlMethods);
            threads.add(thread);
            thread.start();

        }

        for (Thread t:
             threads) {

            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        String value = "";

        for (XML_Methods methods:
             xmlMethodsArrayList) {

            if (methods.getFoundEntries().size()>0) {
                value = methods.getFoundEntries().get(0).getTextContent();
            }

        }

        int meaningNumber = 4;
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

        textWithButton.setAlignment(Pos.TOP_CENTER);

        TextField searchingText = new TextField(textField.getText());
        VBox.setMargin(textWithButton, new Insets(80,0,80,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> searchingAction(searchingText,stage, scene));

        searchMeaningBox.getChildren().add(textWithButton);


        for (int i = 0; i < meaningNumber; i++) {
            HBox meaningButtonBox = new HBox();

            TextArea meaning = new TextArea(value);
            meaning.setEditable(false);
            HBox.setMargin(meaning, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning,Priority.ALWAYS);
            meaning.setPrefHeight(80);
            meaning.setPrefWidth(350);

            Button meaningButton = new Button("<--");
            HBox.setMargin(meaningButton, new Insets(25, 10, 0, 0));
            meaningButton.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox.getChildren().addAll(meaning, meaningButton);

            searchMeaningBox.getChildren().add( meaningButtonBox);

        }


        textWithButton.getChildren().addAll(searchingText, searchButton);

        borderPane.setCenter(searchMeaningBox);

        HBox lastBox = new HBox();
        Label chooseLabel = new Label("Please choose the meaning of the language you want.");
        chooseLabel.setPadding(new Insets(0,0,0,50));
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,0,5,5));
        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
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

    public void choosingLanguage (TextField textField, Stage stage, Scene scene){
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

        TextField searchingText = new TextField(textField.getText());
        HBox.setHgrow(searchingText, Priority.ALWAYS);
        VBox.setMargin(textWithButton, new Insets(80,0,80,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> searchingAction(searchingText,stage, scene));

        Label chosenLanguageLabel = new Label(language1 + " meanings: ");
        chosenLanguageLabel.setPadding(new Insets(0,0,0,40));

        TextArea meaningsArea = new TextArea();
        VBox.setMargin(meaningsArea, new Insets(0, 40, 80, 40));
        VBox.setVgrow(meaningsArea, Priority.ALWAYS);

        textWithButton.getChildren().addAll(searchingText,searchButton);
        searchMeaningBox.getChildren().addAll(textWithButton, chosenLanguageLabel, meaningsArea);

        HBox lastBox = new HBox();
        Button backButton = new Button("Back");
        HBox.setMargin(backButton, new Insets(0,0,5,5));
        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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

        Label usingAddWordLabel = new Label("""
                In the first selection box, select the language of the word you want\s
                to enter and write this word in the space below. In the second selection box,
                select the language of the meaning you want to enter and enter the meaning in the\s
                space below. If you want to add more meaning, press the "Add meaning"\s
                button at the bottom. You can add up to 3 meanings at once.""");

        usingAddWordLabel.setPadding(new Insets(20, 20, 0, 20));

        Label chosenLanguageLabel1 = new Label( choiceBox1.getValue()+":");
        chosenLanguageLabel1.setPadding(new Insets(10, 20, 0, 20));

        TextArea ftextA = new TextArea();
        ftextA.setText(textArea1.getText());
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(350);

        Label chosenLanguageLabel2 = new Label(choiceBox2.getValue()+":");
        chosenLanguageLabel2.setPadding(new Insets(10, 20, 0, 20));

        TextArea stextA = new TextArea();
        stextA.setText(textArea2.getText());
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(350);

        TextArea ttextA = new TextArea();
        VBox.setMargin(ttextA, new Insets(0, 20, 0, 20));
        ttextA.setPrefHeight(80);
        ttextA.setPrefWidth(350);


        Button addMeaningButton = new Button("Add Meaning");
        addMeaningButton.setOnAction(event -> addMeaningAgain(textArea1,textArea2, ttextA,choiceBox1,choiceBox2,stage, scene));
        VBox.setMargin(addMeaningButton, new Insets(10, 20, 0, 390));


        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0, 5, 5, 5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0, 0, 5, 5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
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

        Label usingAddWordLabel = new Label("""
                In the first selection box, select the language of the word you want\s
                to enter and write this word in the space below. In the second selection box,
                select the language of the meaning you want to enter and enter the meaning in the\s
                space below. If you want to add more meaning, press the "Add meaning"\s
                button at the bottom. You can add up to 3 meanings at once.""");

        usingAddWordLabel.setPadding(new Insets(20, 20, 0, 20));

        Label chosenLanguageLabel1 = new Label( choiceBox1.getValue()+":");
        chosenLanguageLabel1.setPadding(new Insets(10, 20, 0, 20));

        TextArea ftextA = new TextArea(textArea1.getText());
        VBox.setMargin(ftextA, new Insets(20, 20, 0, 20));
        ftextA.setPrefHeight(80);
        ftextA.setPrefWidth(350);

        Label chosenLanguageLabel2 = new Label( choiceBox2.getValue()+":");
        chosenLanguageLabel2.setPadding(new Insets(10, 20, 0, 20));

        TextArea stextA = new TextArea(textArea2.getText());
        VBox.setMargin(stextA, new Insets(20, 20, 0, 20));
        stextA.setPrefHeight(80);
        stextA.setPrefWidth(350);

        TextArea ttextA = new TextArea(textArea3.getText());
        VBox.setMargin(ttextA, new Insets(0, 20, 0, 20));
        ttextA.setPrefHeight(80);
        ttextA.setPrefWidth(350);

        TextArea fotextA = new TextArea();
        VBox.setMargin(fotextA, new Insets(0, 20, 0, 20));
        fotextA.setPrefHeight(80);
        fotextA.setPrefWidth(350);



        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        HBox.setMargin(addButton, new Insets(0, 5, 5, 5));
        Button backButton = new Button("Back");
        HBox.setMargin(addButton, new Insets(0, 0, 5, 5));
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
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

    public void backToMainScreen (Stage stage) throws IOException {
        mainApp.start(stage);
    }

    public void searchAll(){

        File folder = new File("Dictionary");

        File[] files = folder.listFiles(File::isFile);

        if (files != null) {

            for (File file:
                    files) {
                filePaths.add(file.getPath());
            }
        }
    }
}
