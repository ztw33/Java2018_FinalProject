package com.ztw33.javafinal.view;

import java.net.URL;
import java.util.ResourceBundle;

import com.ztw33.javafinal.space.CalabashWorld;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
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
	
	CalabashWorld calabashWorld = new CalabashWorld();
	
	public MainWindowController() {
		
		
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		calabashWorld.displayBattleField(gc);
	}
	
	@FXML
	private void handleChangeCalabashFmt() {
		System.out.println("按下了变换阵型（葫芦娃）");
		calabashWorld.goodsChangeFormation();
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, battleFieldCanvas.getWidth(), battleFieldCanvas.getHeight());
		calabashWorld.displayBattleField(gc);
	}
	
	@FXML
	private void handleChangeMonsterFmt() {
		System.out.println("按下了变换阵型（妖精）");
		calabashWorld.badsChangeFormation();
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		gc.clearRect(0, 0, battleFieldCanvas.getWidth(), battleFieldCanvas.getHeight());
		calabashWorld.displayBattleField(gc);
	}
	
	@FXML
	private void handleReplayGame() {
		System.out.println("按下了游戏回放");
	}
	
	@FXML
	private void handleStartBattle() {
		System.out.println("按下了开始战斗");
	}
	
	class KeyBoredHandler implements EventHandler<KeyEvent> {
        public void handle(KeyEvent event) {
        	System.out.println("按下了"+event.getCode()+"键");
        	if(event.getCode()==KeyCode.W) {
        		
        	}
            System.out.println(event.getCode());
        }
    }
}
