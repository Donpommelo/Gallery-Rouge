package mission;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import states.MapState;

public class MissionPanel extends Table{

	/*
	 * Info Panel for schmucks. Appears whenever selecting a Battle button for non-targeting/selection purposes.
	 * Very much a work in progress. 
	 */
	Label info;
	Mission m;
	MapState ms;
	
	TextButton play;
	
	public MissionPanel(Skin skin, MapState ms) {
		super(skin);
		this.ms = ms;
		this.clearChildren();
		this.top().left();
		info = new Label("", skin);
		this.background(new SpriteDrawable(new Sprite(ms.app.assets.get("test/so its a secret.png", Texture.class))));
		this.add(info).width(300).top().left();
		this.row();
		
		play = new TextButton("Play", skin);
		
		play.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				
				//Play gaem
				
			}
		});
		
		this.add(play);
		
	}
	
	public void updateMission(MapState ms, Mission m) {
		this.m = m;
		this.info.setText("AYAYAYAYAYYYAY CARAMBAS");
	}
	
	
}
