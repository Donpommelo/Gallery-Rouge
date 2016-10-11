package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grouge.Application;

import dialog.Dialog;
import party.*;
import party.enemy.*;
import states.StateManager.STATE;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;

public class TitleState extends State{

	
	private final Application game;
	
	private Image back;
	private Skin skin;
	
	private TextButton buttonPlay, buttonExit;
	
	public TitleState(final Application app) {
		super(app);
		this.game = app;
		this.stage = new Stage(new FitViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		stage.clear();
		
		Texture backTexture = game.assets.get("test/Painting1.png", Texture.class);
		back = new Image(backTexture);
		
		stage.addActor(back);
		back.setPosition(stage.getWidth() / 2 - back.getWidth() / 2, stage.getHeight() / 2 - back.getHeight() / 2);
		back.addAction(sequence(alpha(0f),fadeIn(2f)));
		
		this.skin = new Skin();
		this.skin.addRegions(game.assets.get("ui/uiskin.atlas", TextureAtlas.class));
		this.skin.add("default-font", game.font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		initButtons();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		stage.draw();
		
		game.batch.begin();
		game.font24.draw(game.batch, "Gallery Rouge", 120, 120);
		game.batch.end();
	}
	
	public void update(float delta) {
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, false);
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
		stage.dispose();
//		skin.dispose();
	}
	
	public void initButtons(){
		buttonPlay = new TextButton("PLAY",skin,"default");
		buttonPlay.setSize(120, 40);
		buttonPlay.setPosition(stage.getWidth() / 2 - buttonPlay.getWidth() / 2,
				stage.getHeight() / 2 - buttonPlay.getHeight() / 2);
		buttonPlay.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));
		
		buttonPlay.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				
				Dialog[]d = new Dialog[5];
				d[0] = new Dialog("test/Painting1.png","charBusts/Player-1.png","Operator","Woah! Where are we? This doesn't look like Anchor Co!",false,game);
				d[1] = new Dialog("test/Painting1.png","charBusts/Player-5.png","Pen Pal","Hmm. You are right. Things seem much too... unfinished.",true,game);
				d[2] = new Dialog("test/Painting1.png","charBusts/Player-1.png","Operator","You think Suite 521 might be somewhere around here?",false,game);
				d[3] = new Dialog("test/Painting1.png","charBusts/Player-5.png","Pen Pal","Well, we may as well look.",true,game);
				d[4] = new Dialog("test/Painting1.png","charBusts/Player-1.png","Operator","Very well. Which floor for you, then?",false,game);
				
				game.states.getCutsceneScreen().setDialog(d, STATE.BATTLE);
			//	game.states.setScreen(STATE.CUTSCENE);

			//	ArrayList<Schmuck> party = new ArrayList<Schmuck>();
				ArrayList<Schmuck> enemy = new ArrayList<Schmuck>();

				enemy.add(new GalleryGremlin());
				enemy.add(new GalleryGremlin());
				enemy.add(new GalleryGremlin());
				enemy.add(new GalleryGremlin());
				enemy.add(new Vandal());
				enemy.add(new Vandal());
				enemy.add(new Vandal());
				
//				game.party.addSchmuck(new Polisher());
//				game.party.addSchmuck(new Candleman());
//				game.party.addSchmuck(new Glassblower());
//				game.party.addSchmuck(new Docent());
				game.party.addSchmuck(new Enforcer());
				game.party.addSchmuck(new Conservator());
				game.party.addSchmuck(new Salvager());
				game.party.addSchmuck(new Janitor());
				game.party.addSchmuck(new Potter());
				game.party.addSchmuck(new Dioramist());
				
				game.states.getBattleState().initParties(game.party.getParty(), enemy, 5, STATE.TITLE);
			//	game.states.setScreen(STATE.BATTLE);
				
				game.states.setScreen(STATE.MAP);
			}
		});
		
		buttonExit = new TextButton("EXIT",skin,"default");
		buttonExit.setSize(120, 40);
		buttonExit.setPosition(stage.getWidth()/2-buttonExit.getWidth() / 2,
				stage.getHeight() / 2 - 3 * buttonExit.getHeight() / 2);
		buttonExit.addAction(sequence(alpha(0), parallel(fadeIn(.5f), moveBy(0, -20, .5f, Interpolation.pow5Out))));

		buttonExit.addListener(new ClickListener() {
			
			@Override
			public void clicked(InputEvent event, float x, float y) {
				Gdx.app.exit();
			}
		});
		
		stage.addActor(buttonPlay);
		stage.addActor(buttonExit);

	}

}
