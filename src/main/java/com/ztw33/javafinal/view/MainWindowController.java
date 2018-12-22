package com.ztw33.javafinal.view;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ztw33.javafinal.space.CalabashWorld;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MainWindowController implements Initializable {

	@FXML
	private Button changeFmtBtn_Calabash;
	@FXML
	private Button changeFmtBtn_Monster;
	@FXML
	private Button replayGameBtn;
	@FXML
	private Button startBattleBtn;
	@FXML
	private Canvas battleFieldCanvas;
	@FXML
	private TextArea textArea;
	
	private CalabashWorld calabashWorld;
	
	
	
	public MainWindowController() {
		
		
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		calabashWorld = new CalabashWorld(battleFieldCanvas, textArea);
	}
	
	@FXML
	private void handleChangeCalabashFmt() {
		System.out.println("变换阵型（葫芦娃）");
		
		calabashWorld.goodsChangeFormation();
	}
	
	@FXML
	private void handleChangeMonsterFmt() {
		System.out.println("按下了变换阵型（妖精）");
		calabashWorld.badsChangeFormation();
	}
	
	@FXML
	private void handleReplayGame() {
		System.out.println("游戏回放");
	}
	
	@FXML
	private void handleStartBattle() {
		System.out.println("开始战斗");
		
		ExecutorService game = Executors.newSingleThreadExecutor();
		game.execute(calabashWorld);
		game.shutdown();
	}
	
	@FXML
	private void handleKeyPressEvent(KeyEvent event) {
		System.out.println("按下了"+event.getCode()+"键");
    	if(event.getCode() == KeyCode.LEFT) {
    		handleChangeCalabashFmt();
    	} else if (event.getCode() == KeyCode.RIGHT) {
    		handleChangeMonsterFmt();
    	} else if (event.getCode() == KeyCode.SPACE) {
    		handleStartBattle();
    	} else if (event.getCode() == KeyCode.L) {
    		
    	}
	}
	
	public void killAllThread() {
		System.out.println("killAllThread");
		calabashWorld.killAllTheThread();
	}
}
