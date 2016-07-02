package states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.moveBy;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.parallel;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.grouge.Application;

import abilities.Skill;
import abilities.StandardAttack;
import battle.BattleAction;
import battle.BattleButton;
import battle.BattleQueue;
import battle.BattleText;
import abilities.SkillButton;
import party.Schmuck;
import states.StateManager.STATE;

public class BattleState extends State{

	private final Application game;
	
	public Stage stage;
	public Skin skin;
	
//	public Schmuck actor, opposing;
	public Skill skillChosen;
	public BattleAction partyAction,enemyAction;
	
	public ArrayList<Schmuck> party, enemy,battlers;
	public ArrayList<SkillButton> skillButtons;
	
	private TextButton back;

	public TextButton nextRound;
	
	public boolean infoShowing;

	private boolean textlogShowing;
	public int prevX,prevY;
	public BattleButton examining;
	public TextButton info;
	
	private STATE nextScreen;
	
	public BattleQueue bq;
	public BattleText bt;
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
		this.skin.add("default-font",game.font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		stage.clear();
		
		bt = new BattleText("", this);

		phase = 0;
		roundNum = 1;
				
		this.bq = new BattleQueue(this,game,party,enemy);

		this.initSkillButtons();
		this.initMiscButtons();
	}

	@Override
	public void render(float delta) {
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
			if(infoShowing){
				info.addAction(moveBy(500,0,.5f,Interpolation.pow5Out));
				examining.addAction(Actions.moveTo(prevX, prevY,.5f,Interpolation.pow5Out));
//				examining.addAction(Actions.moveTo(bq.getCoord(bq.toq.indexOf(examining))[0],bq.getCoord(bq.toq.indexOf(examining))[1],.5f,Interpolation.pow5Out));
				examining.toBack();
				infoShowing = false;
			}
		}
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		if(bt.getScenes().isEmpty()){
			update(delta);
		}
		else{
			bt.render();
		}
	
		stage.draw();
		
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
		this.nextScreen = screen;
		this.battlers = new ArrayList<Schmuck>();
		
		for(Schmuck s : party){
			battlers.add(s);
		}
		
		for(Schmuck s : enemy){
			battlers.add(s);
		}
		
	}
	
	public void initSkillButtons(){
		skillButtons = new ArrayList<SkillButton>();
		
		for(int i = 0; i < 4; i++){
			skillButtons.add(new SkillButton(new StandardAttack(), skin));
//			skillButtons.get(i).setSize(50, 32);
			skillButtons.get(i).setPosition(300,-i*50-50);
			
			skillButtons.get(i).addListener(new ClickListener(){
				
				@Override
				public void clicked(InputEvent event, float x, float y){
					skillChosen = ((SkillButton) event.getListenerActor()).getSkill();
					switch(skillChosen.targetType){
					case 0:
						partyAction = new BattleAction(bq.actor.getSchmuck(),bq.actor.getSchmuck(),skillChosen);
						execActions();
						break;
					case 1:
						phase = 2;
						break;
					case 2:
						
						break;
					}
				}
			});
			
			stage.addActor(skillButtons.get(i));
		}
	}
	
	private void initMiscButtons(){
		info = new TextButton("",skin);
		info.setSize(500, 500);
		info.setPosition(stage.getWidth(), 0);
		
		nextRound = new TextButton("Round: "+roundNum+" Start?",skin);
		nextRound.setSize(250, 50);
		nextRound.setPosition(stage.getWidth()/2-nextRound.getWidth()/2, stage.getHeight()/2);
		nextRound.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				bt.addScene("Round: "+roundNum+" Start!");
				nextRound.addAction(moveBy(0,500,.5f,Interpolation.pow5Out));
				bq.getDelegates();
			}
		});
		
		back = new TextButton("Back", skin);
		back.setPosition(5, 55);
		back.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				switch(phase){
				case 0:
					//nothing
					break;
				case 1:
					for(int i = 0; i < skillButtons.size(); i++){
						skillButtons.get(i).addAction(sequence(alpha(0), parallel(fadeIn(.5f),moveBy(0,-200,.5f,Interpolation.pow5Out))));
						bq.actor = null;
						bq.adjustButtons();
					}							

					phase = 0;
					break;
				case 2:
					phase = 1;
					break;
				}
			}
		});
		
		battleLabel = new TextButton("", skin);
		//	battleLabel.setSize(stage.getWidth(), stage.getHeight());
		//	battleLabel.setPosition(0, -5*stage.getHeight()/6);
		battleLabel.getLabel().setAlignment(Align.topLeft);

		battlePane = new ScrollPane(battleLabel);
		battlePane.setSize(stage.getWidth()-200, stage.getHeight());
		battlePane.setPosition(200, -7*stage.getHeight()/8);
		battlePane.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(textlogShowing == false){
					event.getListenerActor().addAction(parallel(moveBy(0,7*stage.getHeight()/8,.5f,Interpolation.pow5Out)));
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
	}
	
	public void execActions(){
		
		//bq.actor = partyAction.getUser();
		
		if(!bq.actionEnemy.isEmpty()){
			int rand = (int) (Math.random()*bq.actionEnemy.size());
			bq.opposing = bq.actionEnemy.get(rand);
			
			enemyAction = bq.opposing.getSchmuck().getAction(this);
			
			if(bq.opposing.getSchmuck().getBuffedStat(3) >= bq.actor.getSchmuck().getBuffedStat(3)){
				bt.addScene(enemyAction.getText(),enemyAction);
				bt.addScene(partyAction.getText(),partyAction);
			}
			else{
				bt.addScene(partyAction.getText(),partyAction);	
				bt.addScene(enemyAction.getText(),enemyAction);
			}
			
			
		}
		else{
			bt.addScene(partyAction.getText(),partyAction);	
		}
		
		for(int i = 0; i < skillButtons.size(); i++){
			skillButtons.get(i).addAction(sequence(alpha(0), parallel(fadeIn(.5f),moveBy(0,-200,.5f,Interpolation.pow5Out))));
		}
		
		bq.endofRound();
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
}
