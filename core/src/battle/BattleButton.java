package battle;

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
import states.BattleState;

public class BattleButton extends Button{

	private Application game;
	private BattleState bs;
	private Schmuck schmuck;
	private Label nameLabel;
	private boolean blinking;
	private int blink;
	
	public BattleButton(Schmuck schmuck, Application game, final BattleState bs){
		super(new SpriteDrawable(new Sprite(schmuck.getIcon(game))));
		this.game = game;
		this.bs = bs;
		this.schmuck = schmuck;
		this.blinking = false;
		this.blink = 0;
		
		this.align(Align.top);
		this.nameLabel = new Label(schmuck.getName()+"\n"+schmuck.getCurrentHp()+"/"+schmuck.getBuffedStat(0),bs.skin);
		this.nameLabel.setFontScale(0.75f);
		add(nameLabel);

		addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				switch(bs.phase){
				case 0:
					
					break;
				case 1:
					break;
				case 2:
					
					break;
				}
			}
		});
	}
	
	public void draw (Batch batch, float parentAlpha) {
//		updateImage();
		super.draw(batch, parentAlpha);
	}
	
	public Schmuck getSchmuck() {
		return schmuck;
	}

	public void setSchmuck(Schmuck schmuck) {
		this.schmuck = schmuck;
	}
	
}
