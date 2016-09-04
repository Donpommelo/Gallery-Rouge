package states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grouge.Application;

import mission.Mission;
import mission.MissionManager;
import mission.SchmuckButton;
import party.Schmuck;

public class MapState extends State{

	private Stage stage;
	public Skin skin;

	private OrthographicCamera cam;
	
	private Image background;
	private ShapeRenderer srend;
	
	private TextButton back;

	public ScrollPane party, missionPanel;
	public Table allies, usedAllies;
	
	public MissionManager mm;
	
	public boolean missionSelected;
	public ArrayList<Schmuck> unselected,selected;
	
	public MapState(Application game){
		super(game);
		this.srend = new ShapeRenderer();
		this.stage = new Stage(new FitViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);
		srend.setProjectionMatrix(app.camera.combined);

		Texture backTexture = app.assets.get("test/Liar Seeker Stranger Keeper.png", Texture.class);
		background = new Image(backTexture);
		cam = app.camera;
		
		selected = new ArrayList<Schmuck>();
		unselected = new ArrayList<Schmuck>();
		
		background.addListener(new DragListener(){
			
			public float prevX,prevY;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				prevX = x;
				prevY = y;
				return true;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				if(!missionSelected){
					cam.translate(prevX-x, prevY-y);
				}
			}
		});
		
		stage.addActor(background);
		background.setPosition(stage.getWidth()/2-background.getWidth()/2, stage.getHeight()/2-background.getHeight()/2);
		background.addAction(sequence(alpha(0f),fadeIn(2f)));
		
		this.skin = new Skin();
		this.skin.addRegions(app.assets.get("ui/uiskin.atlas",TextureAtlas.class));
		this.skin.add("default-font",app.font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
		allies = new Table();
		usedAllies = new Table();

		party = new ScrollPane(allies);
		party.setHeight(200);
		party.setVisible(false);
		
		missionPanel = new ScrollPane(usedAllies);
		missionPanel.setHeight(200);
		missionPanel.setVisible(false);

		stage.addActor(party);
		stage.addActor(missionPanel);
		
		missionSelected = false;
		
		this.mm = new MissionManager(app,this);
		
		for(Mission m : mm.initMissions()){
			stage.addActor(m);
		}
		
		initButtons();
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		srend.begin(ShapeRenderer.ShapeType.Filled);
		
		srend.end();
		
		stage.draw();
		
		app.batch.begin();

		app.batch.end();
	}
	
	public void initButtons(){
		back = new TextButton("Back", skin);
		back.setPosition(5, 55);
		back.addListener(new ClickListener(){
			
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(missionSelected){
					missionSelected = false;
					party.setVisible(false);
					missionPanel.setVisible(false);
					back.setVisible(false);
				}
				
			}
		});
		
		back.setVisible(false);
		
		stage.addActor(back);

	}
	
	public void missionSelect(){
		
		unselected.clear();
		selected.clear();
		allies.clear();
		usedAllies.clear();
		
		for(Schmuck s : app.party.getParty()){
			unselected.add(s);
			allies.add(new SchmuckButton(s,app,this));
			allies.row();
		}
		
		missionSelected = true;
		party.setPosition(cam.position.x-stage.getWidth()/2, cam.position.y);
		party.setVisible(true);
		
		missionPanel.setPosition(cam.position.x+stage.getWidth()/2-missionPanel.getWidth(), cam.position.y);
		missionPanel.setVisible(true);
		
		back.setPosition(cam.position.x-stage.getWidth()/2, cam.position.y-stage.getHeight()/2);
		back.setVisible(true);
	}
	
	public void update(float delta){
		stage.act(delta);
	}

	@Override
	public void resize(int width, int height) {
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
		srend.dispose();
	}

}
