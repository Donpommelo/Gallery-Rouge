package battle;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class BattleText{

	/*Like in Uplifted, BattleText is kinda deceptively named as it processes not only text display but effects as well.
	 * This is because I wanted abilities + status effects to activate in synch with their text. 
	 */
	
	private BattleState bs;

	//textLog: List of strings displayed in the textbox.
	public ArrayList<String> textLog;
	
	//scenes: List of scenes that are yet to be played.
	private ArrayList<BattleScene> scenes;
	
	//sceneBegan: Has the scene began yet?
	//actionRun: Has the action related to the scene been run yet?
	//roundBegan: Has the current round of actions began yet?
	private Boolean sceneBegan,actionRun;
	public Boolean roundBegan;
	
	//frames: Counter of frames to keep track of action processing.
	private int frames; 
	
	public BattleText(String name, BattleState bs) {
		this.bs = bs;
		scenes = new ArrayList<BattleScene>();
		textLog = new ArrayList<String>();
		actionRun = false;
		sceneBegan = false;
		roundBegan = false;
		frames = 0;
	}
	
	//Run in the BattleState if there are any BattleScenes to process.
	public void render() {
		if (!scenes.isEmpty()) {
			
			//If the scene has not began yet, begin it.
			if (!sceneBegan) {
				
				if (scenes.get(0).getBa() != null) {
					
					//Run pre-Action statuses for all schmucks and field effects.
					//Note: these can modify the action they precede.
					for (Schmuck s : bs.bq.all) {
						bs.stm.statusProcTime(6, bs, scenes.get(0).getBa(), s, null, null, 0, 0, true, null);
					}
					bs.stm.statusProcTime(6, bs, scenes.get(0).getBa(), bs.fieldDummy, null, null, 0, 0, true, null);
				}


				//Scenes text appears and animation begins.
				textLog.add(scenes.get(0).getText());
				scenes.get(0).animateThing(bs);
				bs.updateText();
				
				//Scene begins but action has not run yet.
				sceneBegan = true;
				actionRun = false;
				frames = 0;
			} else {
				
				//Scene has begun but action has not occurred yet. We are in "preFrames" territory.
				if (!actionRun) {
					
					//Increment frames until we reach the designated number of preFrames.
					if (frames >= scenes.get(0).getPreFrames()) {
						
						//If the scene has a Battle Action, run it now.
						if (scenes.get(0).getBa() != null) {
							bs.runAction(scenes.get(0).getBa());
						}
						//If the scene has a status, run that too now. (It won't have both)
						if (scenes.get(0).getSt() != null) {
							scenes.get(0).getSt().postAnimRun(bs);
						}
						
						//action has now run. Reset number of frames for next stage of processing.
						actionRun = true;
						frames = 0;
					} else {
						//Scene has begun but action has not yet. Just keep incrementing frames until preFrames are over.
						frames++;
					}
				} else {
					
					//Scene has began and action has run. We are in "postFrames" phase of action processing.
					
					//Increment frames until we reach the designated number of postFrames.
					if (frames >= scenes.get(0).getPostFrames()) {
						
						//Remove completed scene. Set sceneBegan to false because next scene has not begun yet.
						scenes.remove(0);
						sceneBegan = false;
						
						//After scene is completed, adjust number of schmucks in "all" to account for new summons.
						if (scenes.isEmpty()) {
							bs.stm.adjustAll(bs);
						}
					} else {
						frames++;
					}
				}
			}
		}
	}
	
	//Functions for adding all the different types of scenes: scenes with only text, with statuses and with actions.
	//atm two input integers represents default frame number. Will change later probably.
	
	public void addScene(String text, Boolean end) {
		if (end || scenes.isEmpty()) {
			scenes.add(new BattleScene(25,25,text));
		} else {
			scenes.add(1, new BattleScene(25,25,text));
		}
	}
	
	public void addScene(String text, Status st) {
		scenes.add(new BattleScene(75,75,text,st));
	}
	
	public void addScene(String text, BattleAction ba) {
		scenes.add(new BattleScene(75,75,text,ba));
	}
	
	
	
	//Replace a skill in a certain scene. Used by move-restricting things.
	public void skillReplace(int index, BattleAction ba){
		BattleAction oldAction = scenes.get(index).getBa();
		
		if (oldAction != null) {
			BattleScene newScene = new BattleScene(75, 75, ba.getText(), ba);
			scenes.set(index, newScene);
		}
	}
	
	//Getter for scenes
	
	public ArrayList<BattleScene> getScenes() {
		return scenes;
	}
}
