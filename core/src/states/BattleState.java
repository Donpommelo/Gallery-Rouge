package states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.grouge.Application;

import abilities.StandardAttack;
import battle.BattleAction;
import battle.BattleButton;
import battle.BattleQueue;
import battle.BattleText;
import battle.EffectManager;
import battle.InfoPanel;
import battle.StatusManager;
import abilities.SkillButton;
import party.Schmuck;
import states.StateManager.STATE;

public class BattleState extends State{

	private final Application game;
	
	//stage: stage where all actors in Battle State are displayed
	public Stage stage;
	
	//skin: BattleState's ui skin
	public Skin skin;
	
	//qImage: Image of TOQ texture.
	private Image qImage;

	//curAction: The action being built/executed by the player.
	//actions: List of actions to be carried out.
	public BattleAction curAction;
	public ArrayList<BattleAction> actions;
	
	/* List of Schmucks. These are created at the start of fight and not modified. Not used for calculations and processing
	 * as it does not account for summons.
	 * party: Allied schmucks.
	 * enemy: enemy schmucks.
	 * battlers: all schmucks.
	 */
	public ArrayList<Schmuck> party, enemy, battlers;
	
	//targetable: List of battlebuttons that are valid targets for the current selected ability.
	public ArrayList<BattleButton> targetable;
	
	//skillbuttons: List of skillbuttons for the abilities of the currently selected schmuck
	public ArrayList<SkillButton> skillButtons;
	
	//fieldDummy: Dummy Schmuck that holds the battle's field effects
	public Schmuck fieldDummy;
	
	//back: Back button for cancling character, ability, target selection.
	private TextButton back;
	
	//nextRound: Button for beginning next round.
	//ded: button for pulling up and examining ded Schmucks.
	//info: Info about selected schmuck. Goes away if clicked.
	public TextButton nextRound,ded;
	public InfoPanel info;

	/*
	 * infoShowing: Is the info panel for a schmuck showing?
	 * textlogShowing: Is the text log pulled up?
	 * dedShowing: Are ded Scmhucks showing?
	 */
	public boolean infoShowing, textlogShowing,dedShowing;
	
	//partyReady: Has the player decided their move?
	//enemyReady: Has the opposing team decided their move. Automatically true if enemy is ai.
	public boolean partyReady,enemyReady;
	
	//nextState: The state that begins when this BattleState finishes.
	private STATE nextState;
	
	public ShapeRenderer sr;
	
	public BattleQueue bq;
	public BattleText bt;
	public EffectManager em;
	public StatusManager stm;
	private TextButton battleLabel;
	private ScrollPane battlePane;
	
//	0: Selecting character 1: Selecting Action 2: Selecting Target 3: Execution
	public int phase;

	//roundNum: The number of rounds of combat so far.
	public int roundNum;
	
	//ActionGroupSize: The number of schmucks allowed in the action group.
	public int ActionGroupSize;
	
	public BattleState(final Application game){
		super(game);
		this.game = game;
		this.stage = new Stage(new StretchViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		//Set up ui skins and stage.
		this.skin = new Skin();
		this.skin.addRegions(game.assets.get("ui/uiskin.atlas",TextureAtlas.class));
		this.skin.add("default-font",game.font24w);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		stage.clear();
		
		//Set up backgrounds and images
		Texture backTexture = game.assets.get("ui/TOQ.png", Texture.class);
		qImage = new Image(backTexture);
		qImage.setPosition(stage.getWidth()/2-qImage.getWidth()/2, stage.getHeight()-qImage.getHeight());
		qImage.toBack();
		stage.addActor(qImage);
		
		//Set up battle managers.
		bt = new BattleText("", this);
		em = new EffectManager(game, this);
		stm = new StatusManager(game, this);
		phase = 0;
		roundNum = 1;
		
		//For drawing geometric shapes.
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(app.camera.combined);

		//Battle Queue processes most combat stuff.
		this.bq = new BattleQueue(this,game,party,enemy);
		
		//Run all start-of-fight statuses for all schmucks and field effects.
		for (Schmuck s : bq.all) {
			stm.statusProcTime(2, this, null, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(2, this, null, fieldDummy, null, null, 0, 0, true, null);

		//initialize buttons
		this.initSkillButtons();
		this.initMiscButtons();
	}

	@Override
	public void render(float delta) {
		
		//If info panel is visible, clicking anywhere retracts it.
		if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			if (infoShowing) {
				info.addAction(moveBy(500,0,.5f,Interpolation.pow5Out));
				bq.adjustButtons();
				infoShowing = false;
			}
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		//Process any scenes that have not been processed yet.
		if (!bt.getScenes().isEmpty()) {
			bt.render();
		}

		update(delta);

		stage.draw();
		
		//If player's move is ready, ready opponent.
		if (partyReady && !enemyReady) {
			//If vs computer, enemy is ready automatically
			enemyReady = true;
		}
		
		//when both players are ready, begin the round.
		if (partyReady && enemyReady) {
			
			//If player has made an action (enemy has not dominated Action group), add it to action queue.
			if (curAction != null) {
				actions.add(curAction);
				curAction = null;
			}
			
			//atm ai selects a random character to attack
			if (!bq.actionEnemy.isEmpty()) {
				int rand = (int) (Math.random()*bq.actionEnemy.size());
				bq.opposing = bq.actionEnemy.get(rand);
				actions.add(bq.opposing.getSchmuck().getAction(this));
			}
			
			
			//Both actors move forwards and actions begin processing.
			bq.adjustButtons();
			execActions();
		}
		
		//If the round has begun, queueActions;
		if (bt.roundBegan) {
			queueActions();
		}
		
	}
	
	public void update(float delta) {
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height,false);
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {		
	}

	@Override
	public void hide() {		
	}

	@Override
	public void dispose() {	
	}
	
	//Initiate parties. This is called by the previous state before switching to this Battle State.
	//Variables necessary for BattleState creation are initialized here.
	public void initParties(ArrayList<Schmuck> party, ArrayList<Schmuck> enemy, int size, STATE screen) {
		this.party = party;
		this.enemy = enemy;
		this.ActionGroupSize = size;
		this.nextState = screen;
		this.battlers = new ArrayList<Schmuck>();
		this.targetable = new ArrayList<BattleButton>();
		this.actions = new ArrayList<BattleAction>();
		
		this.fieldDummy = new Schmuck();
		
		for (Schmuck s : party) {
			battlers.add(s);
		}
		
		for (Schmuck s : enemy) {
			battlers.add(s);
		}
		
		partyReady = false;
		enemyReady = false;
		
		infoShowing = false;
		textlogShowing = false;
		dedShowing = false;
	}
	
	//Initialize Skill Buttons
	public void initSkillButtons() {
		skillButtons = new ArrayList<SkillButton>();
		
		//Each Schmuck has 4 skills
		for (int i = 0; i < 4; i++) {
			skillButtons.add(new SkillButton(new StandardAttack(), skin,this));
			skillButtons.get(i).setPosition(300,-i*50-50);
			
			skillButtons.get(i).addListener(new ClickListener(){
				
				//When selected, each button creates a new battle action with user and skill decided.
				@Override
				public void clicked(InputEvent event, float x, float y) {
					curAction = new BattleAction(bq.actor.getSchmuck(),new ArrayList<Schmuck>(),
							((SkillButton) event.getListenerActor()).getSkill());
					
					//Targeting phase depends on skill target type. Feel free to add more target types later.
					switch (curAction.getSkill().targetType) {
					
					//Case 0: Select a set number of schmucks. Enter Targeting Phase
					case 0:
						phase = 2;
						break;
					//Case 1: Automatically targets a set number of schumucks
					case 1:
						for (BattleButton b: curAction.getSkill().getTargets(bq.actor, ((SkillButton) event.getListenerActor()).bs)) {
							curAction.addTarget(b.getSchmuck(), ((SkillButton) event.getListenerActor()).bs);
						}
						phase = 2;
						break;
					//Case 2: Select a set number of schmucks from KO Queue. Enter Targeting Phase
					case 2:
						dedShowing = true;
						phase = 2;
						break;
					}
					
					bq.manageFlash(curAction.checkReady(((SkillButton) event.getListenerActor()).bs));
				}
			});
			stage.addActor(skillButtons.get(i));
		}
	}
	
	//Initialize info panel, next round button, back button, ded button, and battle pane.
	private void initMiscButtons() {
		
		//Info panel is updated and moved whenever a schmuck is selected.
		info = new InfoPanel(skin);
		info.setSize(500, 500);
		info.setPosition(stage.getWidth(), 0);
		
		//Next round button appears before rounds and begins next round when clicked.
		nextRound = new TextButton("Round: "+roundNum+" Start?",skin);
		nextRound.setSize(250, 50);
		nextRound.setPosition(stage.getWidth()/2-nextRound.getWidth()/2, stage.getHeight()/2);
		nextRound.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (bt.getScenes().isEmpty()) {
					bt.addScene("Round: "+roundNum+" Start!", true);
					nextRound.addAction(moveBy(0,500,.5f,Interpolation.pow5Out));
					bq.getDelegates();
				}
			}
		});
		
		//back button cancels selection of schmuck, ability and targets.
		back = new TextButton("Back", skin);
		back.setPosition(5, 55);
		back.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (bt.getScenes().isEmpty()) {
					switch (phase) {
					
					//Phase 0: Schmuck is not selected. Nothing to cancel here.
					case 0:
						//nothing
						break;
					
					//Phase 1: If Schmuck has been selected, unselect it.
					case 1:
						if (bq.actor != null) {
							for (int i = 0; i < skillButtons.size(); i++) {
								skillButtons.get(i).addAction(sequence(alpha(0), parallel(fadeIn(.5f),moveBy(0,-400,.5f,Interpolation.pow5Out))));
							}			
							bq.actor = null;
							bq.adjustButtons();
							bq.manageFlash(bq.actionAllies);
						}
						break;
					//Phase 2: Unselect ability and targets.
					case 2:
						curAction = null;
						phase = 1;
						bq.manageFlash(new ArrayList<BattleButton>());
						break;
					}
				}
			}
		});
		
		//Ded button lets player look at incapacitated schmucks.
		ded = new TextButton("Ded", skin);
		ded.setPosition(stage.getWidth()-ded.getWidth(), stage.getHeight()-ded.getHeight());
		ded.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dedShowing = !dedShowing;
				bq.adjustButtons();
			}
		});
		
		//BattleLabel/Pane display battle text. Moves up and down when clicked and can be scrolled through to view battle log.
		battleLabel = new TextButton("", skin);
		battleLabel.getLabel().setAlignment(Align.topLeft);

		battlePane = new ScrollPane(battleLabel);
		battlePane.setSize(stage.getWidth()-200, stage.getHeight());
		battlePane.setPosition(200, -7*stage.getHeight()/8);
		battlePane.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				if (textlogShowing == false) {
					event.getListenerActor().addAction(parallel(moveBy(0,7*stage.getHeight()/8,.5f,Interpolation.pow5Out)));
					event.getListenerActor().toFront();
					textlogShowing = true;
				} else {
					battlePane.addAction(parallel(moveBy(0,-7*stage.getHeight()/8,.5f,Interpolation.pow5Out)));
					textlogShowing = false;
				}
			}
		});
		
		stage.addActor(back);
		stage.addActor(nextRound);
		stage.addActor(info);
		stage.addActor(battlePane);
	//	stage.addActor(ded);
	}
	
	//Called automcatically when both teams are ready to perform actions.
	public void execActions() {
		
		//No buttons should be flashing
		bq.manageFlash(new ArrayList<BattleButton>());
		
		//Reset readiness booleans
		partyReady = false;
		enemyReady = false;
		
		//Sort actions according to initiative of user.
		sortActions(actions);
		
		//Run post-delegation statuses for all schmucks and field effects.
		for (Schmuck s : bq.all) {
			stm.statusProcTime(5, this, null, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(5, this, null, fieldDummy, null, null, 0, 0, true, null);
		
		//The round has now begun.
		bt.roundBegan = true;
		
		//Move skill buttons back down.
		for(int i = 0; i < skillButtons.size(); i++){
			skillButtons.get(i).addAction(sequence(alpha(1), parallel(fadeOut(.5f),moveTo(300,-i*50-50,.5f,Interpolation.pow5Out))));
		}
		
	}
	
	//Automatically run when both parties are ready.
	public void queueActions() {
		
		//When scenes are done processing, begin the next action.
		if (bt.getScenes().isEmpty()) {
			if (actions.isEmpty()) {
				//If all the actions are done, begin the next round
				bt.addScene("Round: "+roundNum+" End!", true);
				bt.roundBegan = false;
				bq.endofRound();
			} else {
				if (bq.all.contains(actions.get(0).getUser())) {
					bt.addScene(actions.get(0).getText(),actions.get(0));
				}
				actions.remove(0);
			}
		}
	}
	
	//Called when one team is entirely incapacitated. won = whether player won.
	public void endFight(boolean won){
		
		//Clear scenes.
		bt.getScenes().clear();
		
		//End of fight processing. Get loot and stuff
		if (won) {
			
		} else {
			
		}
		
		//Run end-of-fight statuses for all schmucks and field effects.
		for (Schmuck s : bq.all) {
			stm.statusProcTime(3, this, null, s, null, null, 0, 0, won, null);
		}
		stm.statusProcTime(3, this, null, fieldDummy, null, null, 0, 0, won, null);

		//Move to the designated next state
		game.states.setScreen(nextState);
	}
	
	//Called to run a single action.
	public void runAction(BattleAction a) {
		
		if (a.getSkill().getTargetType() == 1 && bq.getOpposingActor(a.getUser()) == null) {
			bt.addScene("The Ability Failed!", true);
		} else {
			//run the action.
			a.run(this);
		}	
		
		//Meter cost is deducted. Maybe move this before action running?
		em.mpChange(this, a.getUser(), a.getUser(), -a.getSkill().getCost(), 0);
		
		//Run post-Action statuses for all schmucks and field effects.
		for (Schmuck s : bq.all) {
			stm.statusProcTime(7, this, a, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(7, this, a, fieldDummy, null, null, 0, 0, true, null);

	}
	
	//Text is updated whenever new text is added to the battle log.
	public void updateText() {
		battleLabel.clear();
		
		int length = bt.textLog.size();
		ArrayList<String> result = new ArrayList<String>(length);

	    for (int i = length - 1; i >= 0; i--) {
	        result.add(bt.textLog.get(i));
	    }
	
		for (String s : result) {
			battleLabel.add(s);
			battleLabel.row();
			battleLabel.left().top();
		}
	}
	
	//sort actions i nthe action queue based on the initiative of the user.
	public void sortActions(ArrayList<BattleAction> actions) {
		int j;
		boolean flag = true;
		BattleAction temp;
		while (flag) {
			flag = false;
			for (j = 0; j < actions.size() - 1; j++) {
				if (actions.get(j) != null && actions.get(j + 1) != null) {
					if (actions.get(j).getInit(this) < actions.get(j+1).getInit(this)) {
						temp = actions.get(j);
						actions.set(j,actions.get(j+1));
						actions.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
	}
	
	//When clicking a schmuck to look at info, update info panel with that schmuck's information
	public void infoPanel(BattleButton examining){
		info.addAction(moveBy(-500,0,.5f,Interpolation.pow5Out));
		info.updateSchmuck(this,examining.getSchmuck());
		examining.addAction(Actions.moveTo(500, 350,.5f,Interpolation.pow5Out));
		info.toFront();
		examining.toFront();
		infoShowing = true;
	}
	
}
