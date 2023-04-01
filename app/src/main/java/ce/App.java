package ce;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    public Menu fFL;
    @Override
    public void start(Stage stage) {

        GUI_Actions actions = new GUI_Actions();

        VBox mainBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        menuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> actions.popupMenu(stage) );

        HBox allTBox = new HBox();

        VBox fTBox = new VBox();
        TextArea ftextA = new TextArea();
        VBox.setVgrow(ftextA, Priority.ALWAYS);
        MenuBar fTMenuBar = new MenuBar();
        Menu fAL = new Menu("˅");
        MenuItem fA = new MenuItem(actions.language1);
        MenuItem fB = new MenuItem(actions.language2);
        MenuItem fC = new MenuItem(actions.language3);
        MenuItem fD = new MenuItem(actions.language4);
        MenuItem fE = new MenuItem(actions.language5);
        MenuItem fF = new MenuItem(actions.language6);
        MenuItem fG = new MenuItem(actions.language7);

        fA.setOnAction(event -> actions.firstChosenLanguage(fA, actions.language1));
        fB.setOnAction(event -> actions.firstChosenLanguage(fB, actions.language2));
        fC.setOnAction(event -> actions.firstChosenLanguage(fC, actions.language3));
        fD.setOnAction(event -> actions.firstChosenLanguage(fD, actions.language4));
        fE.setOnAction(event -> actions.firstChosenLanguage(fE, actions.language5));
        fF.setOnAction(event -> actions.firstChosenLanguage(fF, actions.language6));
        fG.setOnAction(event -> actions.firstChosenLanguage(fG, actions.language7));

        fAL.getItems().addAll(fA,fB,fC,fD,fE,fF,fG);
        fFL = new Menu();
        /*Label t = new Label(actions.fChosenLanguage);
        t.setStyle("-fx-text-fill: blue;");
        fFL.setGraphic(t);*/


        Button button = new Button("Button");
        VBox.setMargin(button, new Insets(80));
        button.setLayoutX(200);


        fTMenuBar.getMenus().addAll(fFL, fAL);
        fTBox.getChildren().addAll(fTMenuBar, ftextA, button);



        VBox sTBox = new VBox();
        TextArea stextA = new TextArea();
        VBox.setVgrow(stextA, Priority.ALWAYS);
        MenuBar sTMenuBar = new MenuBar();
        Menu fSL = new Menu("");
        Label t2 = new Label("English");
        t2.setStyle("-fx-text-fill: blue;");
        fSL.setGraphic(t2);
        Menu sAL = new Menu("˅");


        TextArea synonyms = new TextArea();
        VBox.setVgrow(synonyms, Priority.ALWAYS);


        sTMenuBar.getMenus().addAll(fSL, sAL);
        sTBox.getChildren().addAll(sTMenuBar,stextA,synonyms);

        Button change = new Button("←→");






        allTBox.getChildren().addAll(fTBox, change, sTBox);
        mainBox.getChildren().addAll(menuBar,allTBox);






        Scene scene = new Scene(mainBox, 1000, 600);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
