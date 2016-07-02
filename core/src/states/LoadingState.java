package states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.grouge.Application;

import states.StateManager.STATE;

public class LoadingState extends State{

	private ShapeRenderer shapeRenderer;
    private float progress;
    
	public LoadingState(Application game) {
		super(game);
        this.shapeRenderer = new ShapeRenderer();
	}
	
	

	@Override
	public void show() {
		shapeRenderer.setProjectionMatrix(app.camera.combined);
        this.progress = 0f;
		queueAssets();
	}
	
	@Override
	public void update(float delta){
        progress = MathUtils.lerp(progress, app.assets.getProgress(), .1f);

        if(app.assets.update() && progress >= app.assets.getProgress() - .01f){
			this.getGame().states.setScreen(STATE.TITLE);
		}
	}
	
	public void render(float delta){
		 Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
	        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	        update(delta);

	        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
	        shapeRenderer.setColor(Color.BLACK);
	        shapeRenderer.rect(32, app.camera.viewportHeight / 2 - 8, app.camera.viewportWidth - 64, 16);

	        shapeRenderer.setColor(Color.BLUE);
	        shapeRenderer.rect(32, app.camera.viewportHeight / 2 - 8, progress * (app.camera.viewportWidth - 64), 16);
	        shapeRenderer.end();
	}
	
	private void queueAssets(){
		app.assets.load("test/Painting1.png", Texture.class);
		app.assets.load("charIcons/Bad Egg.png", Texture.class);
		
		app.assets.load("charBusts/Player-1.png", Texture.class);
		app.assets.load("charBusts/Player-5.png", Texture.class);

		app.assets.load("ui/uiskin.png", Texture.class);
		app.assets.load("ui/uiskin.atlas",TextureAtlas.class);
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
	        shapeRenderer.dispose();
	    }
}
