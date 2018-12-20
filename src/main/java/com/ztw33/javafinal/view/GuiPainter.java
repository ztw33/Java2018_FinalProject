package com.ztw33.javafinal.view;

import java.util.concurrent.TimeUnit;

import com.ztw33.javafinal.space.BattleField;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class GuiPainter implements Runnable {
	private BattleField battleField;
	private Canvas battleFieldCanvas;
	
	boolean isKilled = false;
	
	public GuiPainter(Canvas canvas, BattleField field) {
		battleFieldCanvas = canvas;
		battleField = field;
	}
	
	public void drawBattleField() {
		//System.out.println(battleField);
		GraphicsContext gc = battleFieldCanvas.getGraphicsContext2D();
		
		battleField.guiDisplay(gc);
	}

	@Override
	public void run() {
		System.out.println("guiPainter is running");
		while (!isKilled) {
			synchronized (battleField) {
				drawBattleField();
				
			}
			try {
				TimeUnit.MILLISECONDS.sleep(50);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		System.out.println("gui线程退出");
	}
	
	public void kill() {
		isKilled = true;
	}
}
