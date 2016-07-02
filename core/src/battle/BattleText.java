package battle;

import java.util.ArrayList;

import states.BattleState;
import status.Status;

public class BattleText{

	public ArrayList<String> textLog;
	private ArrayList<BattleScene> scenes;
	
	private BattleState bs;
	
	private Boolean actionRun;
	
	private int frames; 
	
	public BattleText(String name, BattleState bs){
		this.bs = bs;
		scenes = new ArrayList<BattleScene>();
		textLog = new ArrayList<String>();
		actionRun = false;
		frames = 0;
	}
	
	public void render(){
		if(!scenes.isEmpty()){
			if(!actionRun){
				textLog.add(scenes.get(0).getText());
				scenes.get(0).animateThing(bs);
				bs.updateText();
				actionRun = true;
				frames = 0;
			}
			else{
				if(frames >= scenes.get(0).getFrames()){
					
					if(scenes.get(0).getBa() != null){
						scenes.get(0).getBa().run(bs);
					}
					actionRun = false;
					scenes.remove(0);
				}
				else{
					frames++;
				}

			}
			
		}
	}
	
	public ArrayList<BattleScene> getScenes(){
		return scenes;
	}
	
	public void addScene(String text){
		scenes.add(new BattleScene(10,text));
	}
	
	public void addScene(String text, BattleAction ba){
		scenes.add(new BattleScene(100,text,ba));
	}
	
	public void addScene(String text, Status st){
		scenes.add(new BattleScene(25,text,st));
	}
	
}
