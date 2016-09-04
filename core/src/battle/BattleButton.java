package battle;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
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
	
	public boolean flashing,flash;
	public int flashInterval;
	
	public BattleButton(Schmuck schmuck, Application game, final BattleState bs, boolean ally){
		super(new SpriteDrawable(new Sprite(schmuck.getIcon(game,ally))));
		this.game = game;
		this.bs = bs;
		this.schmuck = schmuck;
		
		this.align(Align.top);
		this.nameLabel = new Label(schmuck.getName()+"\n"+schmuck.getCurrentHp()+"/"+schmuck.getMaxHp(bs),bs.skin);
		this.nameLabel.setFontScale(0.75f);
		add(nameLabel);
//		this.debug();
		
		this.flashInterval = 0;
		this.flashing = false;
		this.flash = false;
	}
	
	public void initButton(){
		this.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(bs.bt.getScenes().isEmpty()){
					switch(bs.phase){
					case 0:
						bs.infoPanel((BattleButton) event.getListenerActor());
						break;
					case 1:
						if(bs.bq.actionAllies.contains((BattleButton) event.getListenerActor()) &&
								(BattleButton) event.getListenerActor() != bs.bq.actor){
							
							if(bs.bq.actor != null){
								for(int i = 0; i<schmuck.skills.length;i++){
									bs.skillButtons.get(i).addAction(parallel(fadeIn(.5f),Actions.moveTo(300,-i*50+350,.5f,Interpolation.pow5Out)));
								}
							}
							
							bs.bq.actor = (BattleButton) event.getListenerActor();
							bs.bq.adjustButtons();
							for(int i = 0; i<schmuck.skills.length;i++){
								bs.skillButtons.get(i).setSkill(schmuck.skills[i]);
								bs.skillButtons.get(i).setText(schmuck.skills[i].getName());
								bs.skillButtons.get(i).addAction(parallel(fadeIn(.5f),Actions.moveBy(0,400,.5f,Interpolation.pow5Out)));
							}
							bs.bq.manageFlash(new ArrayList<BattleButton>());
						}
						else{
							bs.infoPanel((BattleButton) event.getListenerActor());
						}
						break;
					case 2:
						if(bs.targetable.contains((BattleButton) event.getListenerActor())){
							bs.curAction.addTarget(((BattleButton) event.getListenerActor()).getSchmuck(), bs);
						}
						else{
							bs.infoPanel((BattleButton) event.getListenerActor());
						}
						break;
					}
				}
			}
		});
	}
	
	public void draw (Batch batch, float parentAlpha) {
//		updateImage();
		if(flashing){
			flashInterval++;
			if(flashInterval > 10){
				flash = !flash;
				flashInterval = 0;
			}
			if(flash){
				super.draw(batch, parentAlpha);
			}
		}
		else{
			super.draw(batch, parentAlpha);
		}
	}
	
	public boolean isFlashing() {
		return flashing;
	}

	public void setFlashing(boolean flashing) {
		this.flashing = flashing;
	}

	public Schmuck getSchmuck() {
		return schmuck;
	}
	
	public void setSchmuck(Schmuck schmuck) {
		this.schmuck = schmuck;
	}
	
}
