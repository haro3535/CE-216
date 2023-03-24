package ce;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        VBox mainBox = new VBox();
        MenuBar menuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        menuBar.getMenus().addAll(mHelp, mAdd);

        HBox allTBox = new HBox();

        VBox fTBox = new VBox();
        TextArea ftextA = new TextArea();
        VBox.setVgrow(ftextA, Priority.ALWAYS);
        MenuBar fTMenuBar = new MenuBar();
        Menu fFL = new Menu("");
        Label t = new Label("Turkish");
        t.setStyle("-fx-text-fill: blue;");
        fFL.setGraphic(t);
        Menu fAL = new Menu("˅");


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
