package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grouge.Application;

import dialog.Dialog;

public class CutsceneState extends State {
	
	private final Application game;
	
	public Dialog[] dialog;
	public StateManager.STATE nextState;
	
	public TextureRegion tr;
	public Image back, backDummy, speaker;
	public Label namePlate;
	public TextButton speech,speechDummy;
	
	public int currentIndex;
	public Skin skin;
	
	public CutsceneState(Application app) {
		super(app);
		this.game = app;
		this.stage = new Stage(new FitViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		
		this.skin = new Skin();
		this.skin.addRegions(game.assets.get("ui/uiskin.atlas",TextureAtlas.class));
		this.skin.add("default-font",game.font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		initButtons();
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
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		stage.draw();
	}
	
	@Override
	public void update(float delta){
		stage.act(delta);
	}
	
	private void initButtons(){
		speech = new TextButton("",skin,"default");
		speech.setSize(stage.getWidth(), stage.getHeight()/5);
		speech.setPosition(0,0);
		
		speech.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(currentIndex == dialog.length-1){
					game.states.setScreen(nextState);
				}
				else{
					nextDialog();
				}
			}
		});
		
		speechDummy = new TextButton("",skin,"default");
		speechDummy.setSize(stage.getWidth(), stage.getHeight()/5);
		speechDummy.setPosition(0,-stage.getHeight()/5);
		
		back = new Image();
		back.setSize(stage.getWidth(), stage.getHeight());
		back.setPosition(0,0);

		backDummy = new Image();
		backDummy.setSize(stage.getWidth(), stage.getHeight());
		backDummy.setPosition(0,0);
		
		namePlate = new Label("", skin);
		
		tr = new TextureRegion();
		speaker = new Image();
		
		stage.addActor(back);
		stage.addActor(backDummy);
		stage.addActor(speaker);
		stage.addActor(namePlate);
		stage.addActor(speech);
		stage.addActor(speechDummy);
		
		nextDialog();
	}

	public void setDialog(Dialog[] dialog, StateManager.STATE st){
		this.dialog = dialog;
		this.nextState = st;
		this.currentIndex = -1;
	}
	
	private void nextDialog(){
		currentIndex++;
		if(currentIndex != 0){
			speechDummy.setText(dialog[currentIndex-1].getText());
			speechDummy.addAction(moveTo(0, 0));
			speechDummy.addAction(moveBy(0, -stage.getHeight()/5, .5f, Interpolation.pow5Out));
			
		}
		back.setDrawable(new TextureRegionDrawable(new TextureRegion(dialog[currentIndex].getBground())));
		speech.setText(dialog[currentIndex].getText());
		
		speaker.setSize(dialog[currentIndex].getSpeaker().getWidth(), dialog[currentIndex].getSpeaker().getHeight());
		namePlate.setText(dialog[currentIndex].getName());
		
		tr.setRegion(dialog[currentIndex].getSpeaker());

		if(dialog[currentIndex].getMirror()){
			tr.flip(true, false);
			speaker.setPosition(stage.getWidth(), stage.getHeight()/5);
			speaker.addAction(moveBy(-dialog[currentIndex].getSpeaker().getWidth(), 0, .5f, Interpolation.pow5Out));
			namePlate.setPosition(stage.getWidth()-dialog[currentIndex].getSpeaker().getWidth(), stage.getHeight()/2);
		}
		else{
			speaker.setPosition(-dialog[currentIndex].getSpeaker().getWidth(), stage.getHeight()/5);
			speaker.addAction(moveBy(dialog[currentIndex].getSpeaker().getWidth(), 0, .5f, Interpolation.pow5Out));
			namePlate.setPosition(0, stage.getHeight()/2);
		}
		speaker.setDrawable(new TextureRegionDrawable(tr));

	}
	
	@Override
	public void dispose(){
		stage.dispose();
		skin.dispose();
		
	}
	
}
