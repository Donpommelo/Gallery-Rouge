package battle;

import states.BattleState;
import status.Status;

public class BattleScene {
	
	public int frames;
	public String text;
	public BattleAction ba;
	public Status st;
	
	public BattleScene(int frames, String text){
		this.frames = frames;
		this.text = text;
	}
	
	public BattleScene(int frames, String text, Status st){
		this.frames = frames;
		this.text = text;
		this.st = st;
	}
	
	public BattleScene(int frames, String text, BattleAction ba){
		this.frames = frames;
		this.text = text;
		this.ba= ba;
	}
	
	public void animateThing(BattleState bs){

	}

	public int getFrames() {
		return frames;
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
