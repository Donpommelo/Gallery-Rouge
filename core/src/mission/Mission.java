package mission;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.grouge.Application;

import party.Schmuck;
import states.MapState;

public class Mission extends Button{

	private Application game;
	private MapState ms;
	private int charCap;
	private String name;
	private int mapX,mapY;
	//0: Undiscovered 1: Discovered but inaccessible 2: Discovered and accessible 3: Completed 4:Can be Redone
	private int phase;
	private ArrayList<Schmuck> enemy;
	
	public Mission(Application game, MapState ms, int cc, int x,int y, String name, ArrayList<Schmuck> enemy){
		super(new SpriteDrawable(new Sprite(game.assets.get("charIcons/Bad Egg.png", Texture.class))));
		this.game = game;
		this.ms = ms;
		this.charCap = cc;
		this.mapX = x;
		this.mapY = y;
		this.name = name;
		this.enemy = enemy;
		add(new Label(name,ms.skin));
		
		this.setX(mapX);
		this.setY(mapY);
		this.setDebug(true);
		
		initiateButton();
	}
	
	private void initiateButton(){
		this.addListener(new ClickListener(){
			
			public void clicked(InputEvent event, float x, float y){
				if(!ms.missionSelected){
					game.camera.translate(mapX-game.camera.position.x, mapY-game.camera.position.y);
					ms.missionSelect((Mission) event.getListenerActor());
				}
			}
			
		});
	}
	
	public void initiateMission(){
		
	}

	public int getCharCap() {
		return charCap;
	}

	public void setCharCap(int charCap) {
		this.charCap = charCap;
	}

	public int getMapX() {
		return mapX;
	}

	public void setMapX(int mapX) {
		this.mapX = mapX;
	}

	public int getMapY() {
		return mapY;
	}

	public void setMapY(int mapY) {
		this.mapY = mapY;
	}

	public int getPhase() {
		return phase;
	}

	public void setPhase(int phase) {
		this.phase = phase;
	}
	
}
