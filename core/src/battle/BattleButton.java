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

	
//	private Application game;
	private BattleState bs;
	
	//Whose button is this?
	private Schmuck schmuck;
	
	//Displays the schmuck's name.
	private Label nameLabel;
	
	/*Used to make button flash when relevant for selection/targeting.
	 * flashing: Should the button currently be flashing on and off?
	 * flash: If the button is flashing on and off, is it currently on or off?
	 * permaFlash: Is the button flashing permanently
	 * flashInterval: How long has the current flash cycle been happening?
	 * flashDuration: How long will the button flash?
	 * flashSpeed: How fast is the button flashing.
	*/
	public boolean flashing, flash, permaFlash;
	public int flashInterval, flashDuration, flashSpeed;
	
	public BattleButton(Schmuck schmuck, Application game, final BattleState bs, boolean ally) {
		super(new SpriteDrawable(new Sprite(schmuck.getIcon(game,ally))));
//		this.game = game;
		this.bs = bs;
		this.schmuck = schmuck;
		
		this.align(Align.top);
		this.nameLabel = new Label(schmuck.getName() + "\n" + schmuck.getCurrentHp() + "/" + schmuck.getMaxHp(bs),
				bs.skin);
		this.nameLabel.setFontScale(0.75f);
		add(nameLabel);
//		this.debug();
		
		this.flashInterval = 0;
		this.flashSpeed = 0;
		this.flashDuration = 0;
		this.flashing = false;
		this.flash = false;
		this.permaFlash = false;
	}
	
	//Run in the BattleQueue upon initiating a new Schmuck.
	//Sets up input stuff.
	public void initButton() {
		this.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				if (bs.bt.getScenes().isEmpty()) {
					switch (bs.phase) {
					
					//Phase 0: Before round begins. All schmucks sitting in TOQ. Always just pull up info.
					case 0:
						bs.infoPanel((BattleButton) event.getListenerActor());
						break;
						
					//Phase 1: Selecting which ally in Action Group to make action.
					case 1:
						
						//Selected ally is in Action Group and is not currently selected to make action.
						if (bs.bq.actionAllies.contains((BattleButton) event.getListenerActor()) &&
								(BattleButton) event.getListenerActor() != bs.bq.actor) {
							
							//Skill buttons move to set location.
							if (bs.bq.actor != null) {
								for (int i = 0; i < schmuck.skills.length; i++) {
									bs.skillButtons.get(i).addAction(parallel(fadeIn(.5f),Actions.moveTo(300,-i*50+350,.5f,Interpolation.pow5Out)));
								}
							}
							
							//Selected schmuck becomes the current actor.
							bs.bq.actor = (BattleButton) event.getListenerActor();
							
							//Adjust buttons, update skill buttons and move into position.
							bs.bq.adjustButtons();
							for (int i = 0; i < schmuck.skills.length; i++) {
								bs.skillButtons.get(i).setSkill(schmuck.skills[i]);
								bs.skillButtons.get(i).setText(schmuck.skills[i].getName());
								bs.skillButtons.get(i).addAction(parallel(fadeIn(.5f),Actions.moveBy(0,400,.5f,Interpolation.pow5Out)));
							}
							
							//Immediately after selecting schmuck to make action, no battle buttons should be flashing.
							bs.bq.manageFlash(new ArrayList<BattleButton>());
						} else {
							//Every other schmuck should just look at info page.
							bs.infoPanel((BattleButton) event.getListenerActor());
						}
						break;
					//Phase 2: Selecting targets.
					case 2:
						
						//If target is valid, add them to list of targets.
						if (bs.targetable.contains((BattleButton) event.getListenerActor())) {
							bs.curAction.addTarget(((BattleButton) event.getListenerActor()).getSchmuck(), bs);
						} else {
							//Otherwise just look at info page.
							bs.infoPanel((BattleButton) event.getListenerActor());
						}
						break;
					}
				}
			}
		});
	}
	
	//Override drawing to allow for flashing
	public void draw (Batch batch, float parentAlpha) {
		if (flashing) {
			flashInterval++;
			if (flashInterval > flashSpeed) {
				flash = !flash;
				flashInterval = 0;
			}
			if (flash) {
				super.draw(batch, parentAlpha);
			}
			if (!permaFlash) {
				flashDuration--;
				if (flashDuration <=0) {
					flashing = false;
				}
			}
		} else {
			super.draw(batch, parentAlpha);
		}
	}
	
	public void updateNameLabel(){
		nameLabel.setText(schmuck.getName() + "\n" + schmuck.getCurrentHp() + "/" + schmuck.getMaxHp(bs));
	}
	
	//Getters and Setters for flashing and schmuck
	
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
