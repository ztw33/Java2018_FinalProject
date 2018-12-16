package com.ztw33.javafinal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
	public static void main(String[] args) {
        launch(args);
    }

	public void start(Stage primaryStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("main.fxml"));
		
		/*StackPane root=new StackPane();
	    root.setAlignment(Pos.CENTER);
	    root.setPadding(new Insets(25, 25, 25, 25));*/
	    
        Scene scene = new Scene(root,1100,600);
		scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
    }
}
