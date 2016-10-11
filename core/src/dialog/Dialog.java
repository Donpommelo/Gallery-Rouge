package dialog;

import com.badlogic.gdx.graphics.Texture;
import com.grouge.Application;

public class Dialog {

	/*
	 * A dialog thing. Each "dialog" is a single person saying a single thing with a single background.
	 * An actual conversation consists of a list of these playing in order as the player advances dialog.
	 */
	
	private Application game;
	
	//bground: texture in the background of the dialog.
	private Texture bground;
	
	//speaker: texture representing the person talking.
	private Texture speaker;
	
	//name: name that appears over the speaker.
	//text: hte stuff that the speaker says appearing in the the text box that the bottom of the screen.
	private String name, text;
	
	//mirror: Whether the speaker appears on the left or right.
	private Boolean mirror;

	public Dialog(String bground, String speaker, String name, String text, Boolean mirror, Application game) {
		this.game = game;
		this.bground = game.assets.get(bground, Texture.class);
		this.speaker = game.assets.get(speaker, Texture.class);
		this.name = name;
		this.text = text;
		this.mirror = mirror;
	}

	//Getters for game, background, speaker, name, text and mirror.
	
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
