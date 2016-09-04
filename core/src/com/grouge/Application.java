package com.grouge;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import party.PartyManager;
import states.StateManager;

public class Application extends Game{

    public static final String TITLE = "Gallery Rouge";
    public static final float VERSION = -.5f;
    public static final int V_WIDTH = 960;
    public static final int V_HEIGHT = 720;

    public OrthographicCamera camera;
   

    //Managers
    public AssetManager assets;
    public StateManager states;
    public PartyManager party;

    
    //Batches
    public SpriteBatch batch;
    public ShapeRenderer sr;
    
    public BitmapFont font24,font24w;
    
    @Override
    public void create() {
       
        batch = new SpriteBatch();
        sr = new ShapeRenderer();
        
        camera = new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH, V_HEIGHT);
        
        initFonts();
        
    	assets = new AssetManager();
    	states = new StateManager(this);
        party = new PartyManager(this);
    }

    @Override
    public void render() {
        super.render();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void dispose() {
    	super.dispose();
        batch.dispose();
        sr.dispose();
        assets.dispose();
       
    }

    private void initFonts() {
       FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Arcon.ttf"));
       FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();

       params.size = 24;
       params.color = Color.BLACK;
       font24 = generator.generateFont(params);

       params.color = Color.WHITE;
       font24w = generator.generateFont(params);
    }
}