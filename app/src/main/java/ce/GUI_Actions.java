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

        int meaningNumber = 1;
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


        if (meaningNumber==1) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(value);
            meaning1.setEditable(false);
            HBox.setMargin(meaning1, new Insets(0, 20, 80, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1);
        }
        if (meaningNumber==2) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1, meaningButtonBox2);
        }if (meaningNumber==3) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1, meaningButtonBox2, meaningButtonBox3);
        }if (meaningNumber==4) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox4.getChildren().addAll(meaning4, meaning4Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1, meaningButtonBox2,
                    meaningButtonBox3, meaningButtonBox4);
        }
        if (meaningNumber==5) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox4.getChildren().addAll(meaning4, meaning4Button);

            HBox meaningButtonBox5 = new HBox();

            TextArea meaning5 = new TextArea(language1);
            HBox.setMargin(meaning5, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning5,Priority.ALWAYS);
            meaning5.setPrefHeight(80);
            meaning5.setPrefWidth(350);

            Button meaning5Button = new Button("<--");
            HBox.setMargin(meaning5Button, new Insets(25, 10, 0, 0));
            meaning5Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox5.getChildren().addAll(meaning5, meaning5Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1, meaningButtonBox2,
                    meaningButtonBox3, meaningButtonBox4, meaningButtonBox5);
        }if (meaningNumber==6) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox4.getChildren().addAll(meaning4, meaning4Button);

            HBox meaningButtonBox5 = new HBox();

            TextArea meaning5 = new TextArea(language1);
            HBox.setMargin(meaning5, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning5,Priority.ALWAYS);
            meaning5.setPrefHeight(80);
            meaning5.setPrefWidth(350);

            Button meaning5Button = new Button("<--");
            HBox.setMargin(meaning5Button, new Insets(25, 10, 0, 0));
            meaning5Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox5.getChildren().addAll(meaning5, meaning5Button);

            HBox meaningButtonBox6 = new HBox();

            TextArea meaning6 = new TextArea(language1);
            HBox.setMargin(meaning6, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning6,Priority.ALWAYS);
            meaning6.setPrefHeight(80);
            meaning6.setPrefWidth(350);

            Button meaning6Button = new Button("<--");
            HBox.setMargin(meaning6Button, new Insets(25, 10, 0, 0));
            meaning6Button.setOnAction(event -> choosingLanguage(textField,stage, scene));

            meaningButtonBox6.getChildren().addAll(meaning6, meaning6Button);

            searchMeaningBox.getChildren().addAll(textWithButton, meaningButtonBox1, meaningButtonBox2,
                    meaningButtonBox3, meaningButtonBox4, meaningButtonBox5, meaningButtonBox6);
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
