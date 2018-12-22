package com.ztw33.javafinal.view;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.ztw33.javafinal.space.CalabashWorld;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

public class MainWindowController implements Initializable {

	@FXML
	private Pane pane;
	@FXML
	private Button changeFmtBtn_Calabash;
	@FXML
	private Button changeFmtBtn_Monster;
	@FXML
	private Button replayGameBtn;
	@FXML
	private Button startBattleBtn;
	@FXML
	private Button restartGameBtn;
	@FXML
	private Canvas battleFieldCanvas;
	
	@FXML
	private Button saveLogBtn;
	@FXML
	private Button discardBtn;
	
	private CalabashWorld calabashWorld;
	
	
	
	public MainWindowController() {
		
		
	}
	
	public void initialize(URL url, ResourceBundle rb) {
		calabashWorld = new CalabashWorld(battleFieldCanvas, saveLogBtn, discardBtn);
		pane.setFocusTraversable(true);
		/* test */
		saveLogBtn.setVisible(true);
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
	private void handleRestartGame() {
		System.out.println("按下了重新开始");
		changeFmtBtn_Calabash.setDisable(false);
		changeFmtBtn_Monster.setDisable(false);
		startBattleBtn.setDisable(false);
		replayGameBtn.setDisable(false);
		
		saveLogBtn.setVisible(false);
		discardBtn.setVisible(false);
		
		calabashWorld.killAllTheThread();
		calabashWorld = new CalabashWorld(battleFieldCanvas, saveLogBtn, discardBtn);
	}
	
	@FXML
	private void handleReplayGame() {
		System.out.println("游戏回放");
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("回放记录");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("游戏记录文件", "*.rcd");
		fileChooser.getExtensionFilters().add(fileExtensions);
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); 
		fileChooser.setInitialDirectory(new File(currentPath));
		File file = fileChooser.showOpenDialog(null);
		ReplayPainter replayPainter = new ReplayPainter(file, battleFieldCanvas);
		ExecutorService replayGuiThread = Executors.newSingleThreadExecutor();
		replayGuiThread.execute(replayPainter);
	}
	
	@FXML
	private void handleStartBattle() {
		System.out.println("开始战斗");
		/* 开始战斗后不可变阵，也不可再点击开始战斗，也不可回放 */
		changeFmtBtn_Calabash.setDisable(true);
		changeFmtBtn_Monster.setDisable(true);
		replayGameBtn.setDisable(true);
		startBattleBtn.setDisable(true);
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
    	} else if (event.getCode() == KeyCode.S) {
    		handleStartBattle();
    	} else if (event.getCode() == KeyCode.L) {
    		
    	}
	}
	
	public void killAllThread() {
		System.out.println("killAllThread");
		calabashWorld.killAllTheThread();
	}
	
	@FXML
	private void handleSaveLog() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("保存记录");
		FileChooser.ExtensionFilter fileExtensions = new FileChooser.ExtensionFilter("游戏记录文件", "*.rcd");
		fileChooser.getExtensionFilters().add(fileExtensions);
		// 将初始目录设置为项目当前路径 
		String currentPath = Paths.get(".").toAbsolutePath().normalize().toString(); 
		fileChooser.setInitialDirectory(new File(currentPath));
		File file = fileChooser.showSaveDialog(null);
		System.out.println(file.getPath());
		
		try {
			calabashWorld.saveGameLog(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	private void handleDiscard() {
		handleRestartGame();
	}
}
