package com.ztw33.javafinal.view;

import java.io.IOException;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class StartPageController {

	@FXML
	private Button startBtn;
	@FXML
	private Button replayBtn;
	@FXML
	private Button quitBtn;
	
	public void init(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("startpage.fxml"));
		root.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());	
		Scene scene = new Scene(root,1100,600);
		stage.setScene(scene);
		stage.show();
		
	}
	
	
	@FXML
	private void handleStartGame() throws IOException {
		System.out.println("按下了开始游戏");
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getClassLoader().getResource("mainwindow.fxml"));
		Parent root = fxmlLoader.load();
		
		//Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("mainwindow.fxml"));
		root.getStylesheets().add(getClass().getClassLoader().getResource("mainwindow.css").toExternalForm());
        Scene scene = new Scene(root,1100,600);
		Stage stage = (Stage)startBtn.getScene().getWindow();
		stage.setScene(scene);
		stage.show();
		
		
		MainWindowController mainWindowController = (MainWindowController)fxmlLoader.getController();
		stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.out.println("监听到窗口关闭");
                mainWindowController.killAllThread();
            }
        });
	}
	
	@FXML
	private void handleReplayGame() {
		System.out.println("按下了游戏回放");
	}
	
	@FXML
	private void handleQuitGame() {
		System.out.println("按下了退出游戏");
		System.exit(0);
	}
}
