package com.ztw33.javafinal.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartPageController {
/*
	@FXML
	private Button startBtn;
	@FXML
	private Button replayBtn;
	@FXML
	private Button quitBtn;*/
	
	@FXML
	private void handleStartGame() {
		System.out.println("按下了开始游戏");
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
