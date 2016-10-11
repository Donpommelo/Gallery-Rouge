package mission;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.grouge.Application;

import party.Schmuck;
import states.MapState;

public class SchmuckButton extends Button{

	private Application game;
	private MapState ms;
	private Schmuck schmuck;
	private Label nameLabel;
	
	public SchmuckButton(Schmuck schmuck, Application game, MapState ms){
		super(new SpriteDrawable(new Sprite(schmuck.getIcon(game))));
		this.game = game;
		this.ms = ms;
		this.schmuck = schmuck;
		
		this.align(Align.top);
		this.nameLabel = new Label(schmuck.getName(), ms.skin);
		this.nameLabel.setFontScale(0.75f);
		add(nameLabel);
		this.debug();
		
		initButton();
	}
	
	public void initButton(){
		this.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				Schmuck me = ((SchmuckButton)(event.getListenerActor())).getSchmuck();
				if (ms.unselected.contains(me)) {
					ms.unselected.remove(me);
					ms.selected.add(me);
				} else if(ms.selected.contains(me)){
					ms.selected.remove(me);
					ms.unselected.add(me);
				}
				
				ms.allies.clear();
				ms.usedAllies.clear();
				
				for(Schmuck s : ms.selected){
					ms.usedAllies.add(new SchmuckButton(s,game,ms));
					ms.usedAllies.row();
				}
				for(Schmuck s : ms.unselected){
					ms.allies.add(new SchmuckButton(s,game,ms));
					ms.allies.row();
				}
			}
		});
	}
	
	public void draw (Batch batch, float parentAlpha) {
		super.draw(batch, parentAlpha);
	}
	
	public Schmuck getSchmuck() {
		return schmuck;
	}
	
	public void setSchmuck(Schmuck schmuck) {
		this.schmuck = schmuck;
	}
	
}
