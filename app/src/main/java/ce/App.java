package ce;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {

    @Override
    public void start(Stage stage) throws FileNotFoundException {

        GUI_Actions actions = new GUI_Actions();

        BorderPane borderPane = new BorderPane();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> actions.popupMenu(stage) );

        borderPane.setTop(mainMenuBar);


        VBox searchImageBox = new VBox();
        HBox textWithButton = new HBox();

        TextField searchingText = new TextField();
        HBox.setHgrow(searchingText, Priority.ALWAYS);
        VBox.setMargin(textWithButton, new Insets(80,0,80,80));
        searchingText.minWidth(600);

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> actions.searchingAction(searchingText,stage));

        HBox imageBox = new HBox();

        // To find absolute path of img file
        File file = new File("Team_61.png");

        Image image = new Image(new FileInputStream(file.getAbsolutePath()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Group imageGroup = new Group(imageView);
        VBox.setMargin(imageBox, new Insets(0,0,0,100));


        imageBox.getChildren().add(imageGroup);


        textWithButton.getChildren().addAll(searchingText, searchButton);
        searchImageBox.getChildren().addAll(textWithButton, imageBox);


        borderPane.setCenter(searchImageBox);

        Scene scene = new Scene(borderPane, 500, 600);
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
