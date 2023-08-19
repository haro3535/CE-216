package ce;

import javafx.application.Application;
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
        Scene scene = new Scene(borderPane, 650, 600);
        stage.setTitle("Team 6");
        File file = new File("T6DictionaryLogo.png");

        Image image = new Image(new FileInputStream(file.getAbsolutePath()));
        stage.getIcons().add(image);
        stage.setScene(scene);
        stage.show();

        MenuBar mainMenuBar = new MenuBar();
        Menu mAbout = new Menu("About");
        Menu mAdd = new Menu("Actions");
        mainMenuBar.getMenus().addAll(mAbout, mAdd);

        MenuItem mHelpItem = new MenuItem("Help");
        mHelpItem.setOnAction(event -> actions.helpMenu(stage,scene,1));

        MenuItem mContactItem = new MenuItem("Contacts");
        mContactItem.setOnAction(event -> actions.helpMenu(stage,scene,2));

        MenuItem mAddItem = new MenuItem("Add a word");
        mAddItem.setOnAction(e -> actions.popupMenu(stage, scene) );

        MenuItem mEditItem = new MenuItem("Edit Meaning");
        mEditItem.setOnAction(event -> {
            try {
                actions.editMeaning(stage,scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        MenuItem mFindSynonym = new MenuItem("Find Synonym");
        mFindSynonym.setOnAction(event -> {
            try {
                actions.findSynonym(stage, scene);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        mAbout.getItems().addAll(mHelpItem,mContactItem);
        mAdd.getItems().addAll(mAddItem,mEditItem,mFindSynonym);

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
                if (searchingText.getText().isEmpty()){
                    String st = "Please fill the text field.";
                    actions.handle(stage,st);
                }
                else {
                    actions.searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH), "", filePaths, false);
                    actions.firstSearchScene(stage, searchingText, scene);
                }
            }
        });

        Button searchButton = new Button("Search");
        HBox.setMargin(searchButton, new Insets(0,40,0,40));
        searchButton.setOnAction(event -> {
            if (searchingText.getText().isEmpty()){
                String st = "Please fill the text field.";
                actions.handle(stage,st);
            }
            else {
                actions.searchThreads(searchingText.getText().toLowerCase(Locale.ENGLISH), "", filePaths, false);
                actions.firstSearchScene(stage, searchingText, scene);
            }
        });

        VBox imageBox = new VBox();
        VBox.setVgrow(imageBox, Priority.ALWAYS);
        imageBox.setAlignment(Pos.CENTER);
        VBox.setMargin(imageBox, new Insets(0,0,120,0));

        // To find absolute path of img file
        File file1 = new File("Team6_400.png");

        Image image1 = new Image(new FileInputStream(file1.getAbsolutePath()));
        ImageView imageView = new ImageView(image1);
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

            //System.out.println(files.length + " File Found!");

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
