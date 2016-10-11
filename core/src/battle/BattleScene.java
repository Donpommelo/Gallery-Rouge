package battle;

import states.BattleState;
import status.Status;

public class BattleScene {
	
	//pre/postFrames: The number of frames before/after action is run.
	public int preFrames,postFrames;
	
	//text: String displayed when scene is run.
	public String text;
	
	//ba: If scene is the result of an action, ba is that action.
	public BattleAction ba;
	
	//st: If scene is the result of a status, st is that status.
	public Status st;
	
	//Constructor for Scenes that contain only text.
	//Examples of this are all ability flavor texts, round bgein text etc
	public BattleScene(int preFrames, int postFrames, String text) {
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
	}
	
	
	//Constructor for Scenes that are the result of a status effect.
	public BattleScene(int preFrames,int postFrames, String text, Status st) {
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
		this.st = st;
	}
	
	//Constructor for Scenes that are the result of an ability being used.
	public BattleScene(int preFrames,int postFrames, String text, BattleAction ba) {
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
		this.ba= ba;
	}
	
	//Run in BattleText after preFrames duratioin is run.
	//Overridden by stuff that has animations
	public void animateThing(BattleState bs) {

	}

	//Getters for pre/postFrames, Text, Battle Action and statuses.
	//I'll create setters if I ever decide that I want effects that change these things without changing the whole scene.
	
	public int getPreFrames() {
		return preFrames;
	}
	
	public int getPostFrames() {
		return postFrames;
	}

	public String getText() {
		return text;
	}

	public BattleAction getBa() {
		return ba;
	}

	public Status getSt() {
		return st;
	}
	
}
