package mission;

import java.util.ArrayList;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.grouge.Application;

import party.Schmuck;
import states.MapState;

public class Mission extends Button{

	private Application game;
	private MapState ms;
	private int charCap;
	private int x,y;
	//0: Undiscovered 1: Discovered but inaccessible 2: Discovered and accessible 3: Completed 4:Can be Redone
	private int phase;
	private ArrayList<Schmuck> enemy;
	
	public Mission(Application game, MapState ms, int cc, int x,int y,ArrayList<Schmuck> enemy){
		this.game = game;
		this.ms = ms;
		this.charCap = cc;
		this.x = x;
		this.y = y;
		this.enemy = enemy;
		
		initiateButton();
	}
	
	private void initiateButton(){
		this.addListener(new ClickListener(){
			
			public void clicked(InputEvent event, float x, float y){
			
				game.camera.translate(x-game.camera.position.x, y-game.camera.position.y);
			}
			
		});
	}
	
	public void initiateMission(){
		
	}
}
