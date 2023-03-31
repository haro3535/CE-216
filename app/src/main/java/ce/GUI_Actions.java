package ce;

import javafx.scene.Scene;
import javafx.scene.control.*;
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

        Scene scene = new Scene(mainBox, 800, 400);
        stage.setTitle("Add a word");
        stage.setScene(scene);
        stage.show();
    }

    public void firstChosenLanguage ( MenuItem menuItem, String string){
        firstChosenLanguage = menuItem;
        fChosenLanguage = string;
        mainApp.fFL = new Menu(fChosenLanguage);
    }

    public void backToMainScreen (Stage stage) throws IOException {
        mainApp.start(stage);
    }
}
