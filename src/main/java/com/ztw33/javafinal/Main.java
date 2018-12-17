package com.ztw33.javafinal;

import com.ztw33.javafinal.view.StartPageController;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	
	//private StartPageController startPageController;
	
	public static void main(String[] args) {
        launch(args);
    }

	public void start(Stage primaryStage) throws Exception {
		StartPageController startPageController = new StartPageController();
		startPageController.init(primaryStage);
    }
}
