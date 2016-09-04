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
	
	public Stage stage;
	public Skin skin;
	private Image qImage;

//	public Schmuck actor, opposing;
	public BattleAction curAction;
	public ArrayList<BattleAction> actions;
	
	public ArrayList<Schmuck> party, enemy,battlers;
	public ArrayList<BattleButton> targetable;
	public ArrayList<SkillButton> skillButtons;
	public Schmuck fieldDummy;
	
	private TextButton back;
	public TextButton nextRound,ded;
	public InfoPanel info;

	public boolean infoShowing, textlogShowing,dedShowing;
	public boolean partyReady,enemyReady;
	
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

	public int roundNum;
	
	public int ActionGroupSize;
	
	public BattleState(final Application game){
		super(game);
		this.game = game;
		this.stage = new Stage(new StretchViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		
		this.skin = new Skin();
		this.skin.addRegions(game.assets.get("ui/uiskin.atlas",TextureAtlas.class));
		this.skin.add("default-font",game.font24w);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		stage.clear();
		
		Texture backTexture = game.assets.get("ui/TOQ.png", Texture.class);
		qImage = new Image(backTexture);
		qImage.setPosition(stage.getWidth()/2-qImage.getWidth()/2, stage.getHeight()-qImage.getHeight());
		qImage.toBack();
		stage.addActor(qImage);
		
		bt = new BattleText("", this);
		em = new EffectManager(game, this);
		stm = new StatusManager(game, this);
		phase = 0;
		roundNum = 1;
		
		sr = new ShapeRenderer();
		sr.setProjectionMatrix(app.camera.combined);

		this.bq = new BattleQueue(this,game,party,enemy);
		
		for(Schmuck s : bq.all){
			stm.statusProcTime(2, this, null, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(2, this, null, fieldDummy, null, null, 0, 0, true, null);

		this.initSkillButtons();
		this.initMiscButtons();
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(infoShowing){
				info.addAction(moveBy(500,0,.5f,Interpolation.pow5Out));
				bq.adjustButtons();
				infoShowing = false;
			}
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(!bt.getScenes().isEmpty()){
			bt.render();
		}

		update(delta);

		stage.draw();
		if(partyReady && !enemyReady){
			//If vs computer, enemy is ready automatically
			enemyReady = true;
			bq.adjustButtons();
		}
		
		if(partyReady && enemyReady){
			actions.add(curAction);
			execActions();
		}
		if(bt.roundBegan){
			queueActions();
		}
		
	}
	
	public void update(float delta){
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
	
	public void initParties(ArrayList<Schmuck> party, ArrayList<Schmuck> enemy, int size, STATE screen){
		this.party = party;
		this.enemy = enemy;
		this.ActionGroupSize = size;
		this.nextState = screen;
		this.battlers = new ArrayList<Schmuck>();
		this.targetable = new ArrayList<BattleButton>();
		this.actions = new ArrayList<BattleAction>();
		
		this.fieldDummy = new Schmuck();
		
		for(Schmuck s : party){
			battlers.add(s);
		}
		
		for(Schmuck s : enemy){
			battlers.add(s);
		}
		
		partyReady = false;
		enemyReady = false;
		
		infoShowing = false;
		textlogShowing = false;
		dedShowing = false;
	}
	
	public void initSkillButtons(){
		skillButtons = new ArrayList<SkillButton>();
		
		for(int i = 0; i < 4; i++){
			skillButtons.add(new SkillButton(new StandardAttack(), skin,this));
			skillButtons.get(i).setPosition(300,-i*50-50);
			
			skillButtons.get(i).addListener(new ClickListener(){
				
				@Override
				public void clicked(InputEvent event, float x, float y){
					curAction = new BattleAction(bq.actor.getSchmuck(),new ArrayList<Schmuck>(),
							((SkillButton) event.getListenerActor()).getSkill());
					
					//make targets flashing
					
					switch(curAction.getSkill().targetType){
					case 0:
						phase = 2;
						break;
					case 1:
						for(BattleButton b: curAction.getSkill().getTargets(bq.actor, bq)){
							curAction.addTarget(b.getSchmuck(), ((SkillButton) event.getListenerActor()).bs);
						}
						phase = 2;
						break;
					case 2:
						break;
					}
					
					bq.manageFlash(curAction.checkReady(((SkillButton) event.getListenerActor()).bs));

				}
			});
			
			stage.addActor(skillButtons.get(i));
		}
	}
	
	private void initMiscButtons(){
		
		info = new InfoPanel(skin);
		info.setSize(500, 500);
		info.setPosition(stage.getWidth(), 0);
		
		nextRound = new TextButton("Round: "+roundNum+" Start?",skin);
		nextRound.setSize(250, 50);
		nextRound.setPosition(stage.getWidth()/2-nextRound.getWidth()/2, stage.getHeight()/2);
		nextRound.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(bt.getScenes().isEmpty()){
					bt.addScene("Round: "+roundNum+" Start!");
					nextRound.addAction(moveBy(0,500,.5f,Interpolation.pow5Out));
					bq.getDelegates();
				}
			}
		});
		
		back = new TextButton("Back", skin);
		back.setPosition(5, 55);
		back.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(bt.getScenes().isEmpty()){
					switch(phase){
					case 0:
						//nothing
						break;
					case 1:
						for(int i = 0; i < skillButtons.size(); i++){
							skillButtons.get(i).addAction(sequence(alpha(0), parallel(fadeIn(.5f),moveBy(0,-400,.5f,Interpolation.pow5Out))));
							bq.actor = null;
							bq.adjustButtons();
						}							
						bq.manageFlash(bq.actionAllies);
//						phase = 0;
						break;
					case 2:
						curAction = null;
						phase = 1;
						bq.manageFlash(new ArrayList<BattleButton>());
						break;
					}
				}
			}
		});
		
		ded = new TextButton("Ded", skin);
		ded.setPosition(stage.getWidth()-ded.getWidth(), stage.getHeight()-ded.getHeight());
		ded.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				dedShowing = !dedShowing;
				bq.adjustButtons();
			}
		});
		
		battleLabel = new TextButton("", skin);
		battleLabel.getLabel().setAlignment(Align.topLeft);

		battlePane = new ScrollPane(battleLabel);
		battlePane.setSize(stage.getWidth()-200, stage.getHeight());
		battlePane.setPosition(200, -7*stage.getHeight()/8);
		battlePane.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(textlogShowing == false){
					event.getListenerActor().addAction(parallel(moveBy(0,7*stage.getHeight()/8,.5f,Interpolation.pow5Out)));
					event.getListenerActor().toFront();
					textlogShowing = true;
				}
				else{
					battlePane.addAction(parallel(moveBy(0,-7*stage.getHeight()/8,.5f,Interpolation.pow5Out)));
					textlogShowing = false;
				}
			}
		});
		
		stage.addActor(back);
		stage.addActor(nextRound);
		stage.addActor(info);
		stage.addActor(battlePane);
		stage.addActor(ded);
	}
	
	public void execActions(){
		
		bq.manageFlash(new ArrayList<BattleButton>());
		
		partyReady = false;
		enemyReady = false;
		
		sortActions(actions);
		
		for(Schmuck s : bq.all){
			stm.statusProcTime(5, this, null, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(5, this, null, fieldDummy, null, null, 0, 0, true, null);
		
		bt.roundBegan = true;
		
		for(int i = 0; i < skillButtons.size(); i++){
			skillButtons.get(i).addAction(sequence(alpha(1), parallel(fadeOut(.5f),moveTo(300,-i*50-50,.5f,Interpolation.pow5Out))));
		}
		
	}
	
	public void queueActions(){
		if(bt.getScenes().isEmpty()){
			if(actions.isEmpty()){
				bt.addScene("Round: "+roundNum+" End!");
				bt.roundBegan = false;
				bq.endofRound();
			}
			else{
				bt.addScene(actions.get(0).getText(),actions.get(0));
				actions.remove(0);
			}
		}
		
	}
	
	public void endFight(boolean won){
		
		bt.getScenes().clear();
		
		if(won){
			
		}
		else{
			
		}
		
		for(Schmuck s : bq.all){
			stm.statusProcTime(3, this, null, s, null, null, 0, 0, won, null);
		}
		stm.statusProcTime(3, this, null, fieldDummy, null, null, 0, 0, won, null);

		
		game.states.setScreen(nextState);
	}
	
	public void runAction(BattleAction a){
		
		for(Schmuck s : bq.all){
			stm.statusProcTime(6, this, a, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(6, this, a, fieldDummy, null, null, 0, 0, true, null);

		a.run(this);
		
		em.mpChange(this, a.getUser(), a.getUser(), -a.getSkill().getCost(), 0);
		
		for(Schmuck s : bq.all){
			stm.statusProcTime(7, this, a, s, null, null, 0, 0, true, null);
		}
		stm.statusProcTime(7, this, a, fieldDummy, null, null, 0, 0, true, null);

	}
	
	public void updateText(){
		battleLabel.clear();
		
		int length = bt.textLog.size();
		ArrayList<String> result = new ArrayList<String>(length);

	    for (int i = length - 1; i >= 0; i--) {
	        result.add(bt.textLog.get(i));
	    }
	
		for(String s : result){
			battleLabel.add(s);
			battleLabel.row();
			battleLabel.left().top();
		}
	}
	
	public void sortActions(ArrayList<BattleAction> actions){
		int j;
		boolean flag = true;
		BattleAction temp;
		while (flag){
			flag=false;
			for(j=0; j<actions.size()-1; j++){
				if(actions.get(j) != null && actions.get(j+1) != null){
					if(actions.get(j).getUser().getInit(this) < actions.get(j+1).getUser().getInit(this)){
						temp = actions.get(j);
						actions.set(j,actions.get(j+1));
						actions.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
	}
	
	public void infoPanel(BattleButton examining){
		info.addAction(moveBy(-500,0,.5f,Interpolation.pow5Out));
		info.updateSchmuck(this,examining.getSchmuck());
		examining.addAction(Actions.moveTo(500, 350,.5f,Interpolation.pow5Out));
		info.toFront();
		examining.toFront();
		infoShowing = true;
	}
	
}
