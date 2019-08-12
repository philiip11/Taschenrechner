package sample;

import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.svg.SVGGlyph;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Taschenrechner");
        primaryStage.getIcons().add(new Image(this.getClass().getResourceAsStream("/icon.png")));
        JFXDecorator decorator = new JFXDecorator(primaryStage, root);
        decorator.setCustomMaximize(true);
        decorator.setGraphic(new SVGGlyph(""));
        Scene scene = new Scene(decorator, 600, 900, true, SceneAntialiasing.BALANCED);
        final ObservableList<String> stylesheets = scene.getStylesheets();
        stylesheets.addAll(getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-design.css").toExternalForm(),
                getClass().getResource("/css/custom.css").toExternalForm(),
                getClass().getResource("/css/jfoenix-main-demo.css").toExternalForm(),
                getClass().getResource("/css/taschenrechner.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
