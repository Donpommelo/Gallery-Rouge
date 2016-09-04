package battle;

import states.BattleState;
import status.Status;

public class BattleScene {
	
	public int preFrames,postFrames;
	public String text;
	public BattleAction ba;
	public Status st;
	
	public BattleScene(int preFrames,int postFrames, String text){
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
	}
	
	public BattleScene(int preFrames,int postFrames, String text, Status st){
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
		this.st = st;
	}
	
	public BattleScene(int preFrames,int postFrames, String text, BattleAction ba){
		this.preFrames = preFrames;
		this.postFrames = postFrames;
		this.text = text;
		this.ba= ba;
	}
	
	public void animateThing(BattleState bs){

	}

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
