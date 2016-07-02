package states;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.fadeIn;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.sequence;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.grouge.Application;

import party.Schmuck;

public class MapState implements Screen{

	private final Application game;
	private Stage stage;
	
	private OrthographicCamera cam;
	
	private Image back;
	private Skin skin;
	private ShapeRenderer srend;
	private float progress;
	
	public ScrollPane party;
	public Label allies;
	
	public MapState(final Application game){
		this.game = game;
		this.srend = new ShapeRenderer();
		this.stage = new Stage(new FitViewport(Application.V_WIDTH,Application.V_HEIGHT,game.camera));
	}
	
	@Override
	public void show() {
		Gdx.input.setInputProcessor(stage);

		srend.setProjectionMatrix(game.camera.combined);
		this.progress = 0f;
		Texture backTexture = game.assets.get("test/Painting1.png", Texture.class);
		back = new Image(backTexture);
		
		cam = game.camera;
		
		back.addListener(new DragListener(){
			
			public float prevX,prevY;
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				prevX = x;
				prevY = y;
				return true;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y, int pointer){
				cam.translate(prevX-x, prevY-y);
			}
		});
		
		stage.addActor(back);
		back.setPosition(stage.getWidth()/2-back.getWidth()/2, stage.getHeight()/2-back.getHeight()/2);
		back.addAction(sequence(alpha(0f),fadeIn(2f)));
		
		allies = new Label("", skin);
		
		party = new ScrollPane(allies);
		
//		for(Schmuck s : game.party.getParty()){
			
//		}
		
		this.skin = new Skin();
		this.skin.addRegions(game.assets.get("ui/uiskin.atlas",TextureAtlas.class));
		this.skin.add("default-font",game.font24);
		this.skin.load(Gdx.files.internal("ui/uiskin.json"));
		
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		update(delta);
		
		srend.begin(ShapeRenderer.ShapeType.Filled);
		srend.setColor(Color.BLACK);
		srend.rect(32,game.camera.viewportHeight/2 - 8, game.camera.viewportWidth - 64, 16);
		
		srend.setColor(Color.GREEN);
		srend.rect(32,game.camera.viewportHeight/2 - 8, (game.camera.viewportWidth - 64) * progress, 16);

		srend.end();
		
		stage.draw();
		
		game.batch.begin();

		game.batch.end();
	}
	
	private void update(float delta){
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
