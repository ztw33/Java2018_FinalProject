package com.ztw33.javafinal;


import org.junit.Test;

import com.ztw33.javafinal.event.BattleEvent;
import com.ztw33.javafinal.thing.creature.bad.Bad;
import com.ztw33.javafinal.thing.creature.bad.Minion;
import com.ztw33.javafinal.thing.creature.bad.Snake;
import com.ztw33.javafinal.thing.creature.good.Brother1;
import com.ztw33.javafinal.thing.creature.good.Brother2;
import com.ztw33.javafinal.thing.creature.good.Good;

import javafx.embed.swing.JFXPanel;
public class BattleEventTest
{
    @Test
    public void testBattleEvent1() {
    	new JFXPanel();
    	Good brother1 = new Brother1();
    	Bad minion = new Minion(0);
    	BattleEvent battleEvent = new BattleEvent(brother1, minion);
    	long start = System.currentTimeMillis();       
    	battleEvent.run();
    	long end = System.currentTimeMillis();
    	System.out.print(brother1.getName()+"hp:"+brother1.getHP()+" 小喽啰hp:"+minion.getHP()+"\n");
    	System.out.println("战斗用时: "+(end-start)+" ms.");
    }
    
    @Test
    public void testBattleEvent2() {
    	new JFXPanel();
    	Good brother2 = new Brother2();
    	Bad snake = new Snake();
    	BattleEvent battleEvent = new BattleEvent(brother2, snake);
    	long start = System.currentTimeMillis();  
    	battleEvent.run();
    	long end = System.currentTimeMillis();
    	System.out.print(brother2.getName()+"hp:"+brother2.getHP()+" "+snake.getName()+"hp:"+snake.getHP()+"\n");
    	System.out.println("战斗用时: "+(end-start)+" ms.");
    }
}
