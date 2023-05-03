package ce;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class App extends Application {



    @Override
    public void start(Stage stage) throws FileNotFoundException {

        GUI_Actions actions = new GUI_Actions();
        actions.isFilesFound = isFilesFound;
        actions.setFilePaths(filePaths);


        BorderPane borderPane = new BorderPane();
        Scene scene = new Scene(borderPane, actions.getSceneWidth(), actions.getSceneHeight());
        stage.setTitle("Team 6");
        stage.setScene(scene);
        stage.show();

        MenuBar mainMenuBar = new MenuBar();
        Menu mHelp = new Menu("Help");
        Menu mAdd = new Menu("Add a word");
        mainMenuBar.getMenus().addAll(mHelp, mAdd);

        MenuItem mAddItem = new MenuItem("Add");
        mAdd.getItems().add(mAddItem);

        mAddItem.setOnAction(e -> actions.popupMenu(stage, scene) );

        borderPane.setTop(mainMenuBar);


        VBox searchImageBox = new VBox();
        HBox textWithButton = new HBox();
        textWithButton.setAlignment(Pos.TOP_CENTER);

        TextField searchingText = new TextField();
        searchingText.setPrefWidth(400);
        searchingText.setMaxWidth(700);
        VBox.setMargin(textWithButton, new Insets(80,0,40,80));
        searchingText.minWidth(600);

        searchingText.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyCode.ENTER))
            {
                actions.searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
                actions.firstSearchScene(stage,searchingText,scene);
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> {
            actions.searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH),"",filePaths,false);
            actions.firstSearchScene(stage,searchingText,scene);
        });

        VBox imageBox = new VBox();
        VBox.setVgrow(imageBox, Priority.ALWAYS);
        imageBox.setAlignment(Pos.CENTER);
        VBox.setMargin(imageBox, new Insets(0,0,120,0));

        // To find absolute path of img file
        File file = new File("Team6_400.png");

        Image image = new Image(new FileInputStream(file.getAbsolutePath()));
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        Group imageGroup = new Group(imageView);


        imageBox.getChildren().add(imageGroup);


        textWithButton.getChildren().addAll(searchingText, searchButton);
        searchImageBox.getChildren().addAll(textWithButton, imageBox);


        borderPane.setCenter(searchImageBox);

    }

    private static final ArrayList<String> filePaths = new ArrayList<>();
    private static boolean isFilesFound;

    public static void searchAll(){

        // To read
        File folder = new File("GraphFiles");

        // To take all file inside Dictionary directory
        File[] files = folder.listFiles(File::isFile);

        if (files != null) {

            System.out.println(files.length + " File Found!");

            for (File file:
                    files) {
                filePaths.add(file.getPath());
            }
            isFilesFound = true;
        }
    }

    public static void main(String[] args) {
        searchAll();
        launch(args);
    }
}
