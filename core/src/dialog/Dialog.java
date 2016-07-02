package dialog;

import com.badlogic.gdx.graphics.Texture;
import com.grouge.Application;

public class Dialog {

	private Application game;
	private Texture bground;
	private Texture speaker;
	private String name, text;
	private Boolean mirror;

	public Dialog(String bground, String speaker, String name, String text, Boolean mirror, Application game){
		this.game = game;
		
		this.bground = game.assets.get(bground, Texture.class);
		this.speaker = game.assets.get(speaker, Texture.class);
		this.name = name;
		this.text = text;
		this.mirror = mirror;
	}

	public Application getGame() {
		return game;
	}

	public Texture getBground() {
		return bground;
	}

	public Texture getSpeaker() {
		return speaker;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public Boolean getMirror() {
		return mirror;
	}
	
}
