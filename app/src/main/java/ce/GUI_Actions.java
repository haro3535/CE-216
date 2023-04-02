package ce;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class GUI_Actions {

    App mainApp = new App();
    public MenuItem firstChosenLanguage;
    public MenuItem secondChosenLanguage;
    public String fChosenLanguage;
    public String sChosenLanguage;
    public String language1 = "a";
    public String language2 = "b";
    public String language3 = "c";
    public String language4 = "d";
    public String language5 = "e";
    public String language6 = "f";
    public String language7 = "g";

    public void popupMenu (Stage stage){
        VBox mainBox = new VBox();

        MenuBar fTMenuBar = new MenuBar();
        Menu fFL = new Menu("");
        Label t = new Label("Turkish");
        t.setStyle("-fx-text-fill: blue;");
        fFL.setGraphic(t);
        Menu fAL = new Menu("˅");


        fTMenuBar.getMenus().addAll(fFL, fAL);

        TextArea ftextA = new TextArea();
        VBox.setVgrow(ftextA, Priority.ALWAYS);

        MenuBar sTMenuBar = new MenuBar();
        Menu fSL = new Menu("");
        Label t2 = new Label("English");
        t2.setStyle("-fx-text-fill: blue;");
        fSL.setGraphic(t2);
        Menu sAL = new Menu("˅");

        sTMenuBar.getMenus().addAll(fSL, sAL);

        TextArea stextA = new TextArea();
        VBox.setVgrow(stextA, Priority.ALWAYS);

        HBox buttonsBox = new HBox();
        Button addButton = new Button("Add");
        Button backButton = new Button("Back");
        buttonsBox.getChildren().addAll(addButton, backButton);

        backButton.setOnAction(event -> {
            try {
                backToMainScreen(stage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        mainBox.getChildren().addAll(fTMenuBar, ftextA, sTMenuBar, stextA, buttonsBox);

        Scene scene = new Scene(mainBox, 500, 600);
        stage.setTitle("Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void searchingAction (TextField textField, Stage stage){
        int meaningNumber = 6;
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage) );

        borderPane.setTop(mainMenuBar);


        VBox searchMeaningBox = new VBox();
        HBox textWithButton = new HBox();

        TextField searchingText = new TextField(textField.getText());
        HBox.setHgrow(searchingText, Priority.ALWAYS);
        VBox.setMargin(textWithButton, new Insets(80,0,80,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> searchingAction(searchingText,stage));


        if (meaningNumber==1) {
            HBox meaningButtonBox1 = new HBox();

            TextArea meaning1 = new TextArea(language1);
            HBox.setMargin(meaning1, new Insets(0, 20, 80, 60));
            HBox.setHgrow(meaning1,Priority.ALWAYS);
            meaning1.setPrefHeight(80);
            meaning1.setPrefWidth(350);

            Button meaning1Button = new Button("<--");
            HBox.setMargin(meaning1Button, new Insets(25, 10, 0, 0));
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

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
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage));

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
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage));

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
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage));

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
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox4.getChildren().addAll(meaning4, meaning4Button);

            HBox meaningButtonBox5 = new HBox();

            TextArea meaning5 = new TextArea(language1);
            HBox.setMargin(meaning5, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning5,Priority.ALWAYS);
            meaning5.setPrefHeight(80);
            meaning5.setPrefWidth(350);

            Button meaning5Button = new Button("<--");
            HBox.setMargin(meaning5Button, new Insets(25, 10, 0, 0));
            meaning5Button.setOnAction(event -> choosingLanguage(textField,stage));

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
            meaning1Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox1.getChildren().addAll(meaning1, meaning1Button);

            HBox meaningButtonBox2 = new HBox();

            TextArea meaning2 = new TextArea(language1);
            HBox.setMargin(meaning2, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning2,Priority.ALWAYS);
            meaning2.setPrefHeight(80);
            meaning2.setPrefWidth(350);

            Button meaning2Button = new Button("<--");
            HBox.setMargin(meaning2Button, new Insets(25, 10, 0, 0));
            meaning2Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox2.getChildren().addAll(meaning2, meaning2Button);

            HBox meaningButtonBox3 = new HBox();

            TextArea meaning3 = new TextArea(language1);
            HBox.setMargin(meaning3, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning3,Priority.ALWAYS);
            meaning3.setPrefHeight(80);
            meaning3.setPrefWidth(350);

            Button meaning3Button = new Button("<--");
            HBox.setMargin(meaning3Button, new Insets(25, 10, 0, 0));
            meaning3Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox3.getChildren().addAll(meaning3, meaning3Button);

            HBox meaningButtonBox4 = new HBox();

            TextArea meaning4 = new TextArea(language1);
            HBox.setMargin(meaning4, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning4,Priority.ALWAYS);
            meaning4.setPrefHeight(80);
            meaning4.setPrefWidth(350);

            Button meaning4Button = new Button("<--");
            HBox.setMargin(meaning4Button, new Insets(25, 10, 0, 0));
            meaning4Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox4.getChildren().addAll(meaning4, meaning4Button);

            HBox meaningButtonBox5 = new HBox();

            TextArea meaning5 = new TextArea(language1);
            HBox.setMargin(meaning5, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning5,Priority.ALWAYS);
            meaning5.setPrefHeight(80);
            meaning5.setPrefWidth(350);

            Button meaning5Button = new Button("<--");
            HBox.setMargin(meaning5Button, new Insets(25, 10, 0, 0));
            meaning5Button.setOnAction(event -> choosingLanguage(textField,stage));

            meaningButtonBox5.getChildren().addAll(meaning5, meaning5Button);

            HBox meaningButtonBox6 = new HBox();

            TextArea meaning6 = new TextArea(language1);
            HBox.setMargin(meaning6, new Insets(0, 20, 0, 60));
            HBox.setHgrow(meaning6,Priority.ALWAYS);
            meaning6.setPrefHeight(80);
            meaning6.setPrefWidth(350);

            Button meaning6Button = new Button("<--");
            HBox.setMargin(meaning6Button, new Insets(25, 10, 0, 0));
            meaning6Button.setOnAction(event -> choosingLanguage(textField,stage));

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


        Scene scene = new Scene(borderPane, 500, 600);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();
    }

    public void choosingLanguage (TextField textField, Stage stage){
        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> popupMenu(stage) );

        borderPane.setTop(mainMenuBar);


        VBox searchMeaningBox = new VBox();
        HBox textWithButton = new HBox();

        TextField searchingText = new TextField(textField.getText());
        HBox.setHgrow(searchingText, Priority.ALWAYS);
        VBox.setMargin(textWithButton, new Insets(80,0,80,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> searchingAction(searchingText,stage));

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


        Scene scene = new Scene(borderPane, 500, 600);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();


    }

    public void backToMainScreen (Stage stage) throws IOException {
        mainApp.start(stage);
    }
}
