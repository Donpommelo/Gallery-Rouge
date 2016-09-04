package battle;

import java.util.ArrayList;

import states.BattleState;
import status.Status;

public class BattleText{

	public ArrayList<String> textLog;
	private ArrayList<BattleScene> scenes;
	
	private BattleState bs;
	
	private Boolean sceneBegan,actionRun;
	public Boolean roundBegan;
	
	private int frames; 
	
	public BattleText(String name, BattleState bs){
		this.bs = bs;
		scenes = new ArrayList<BattleScene>();
		textLog = new ArrayList<String>();
		actionRun = false;
		sceneBegan = false;
		roundBegan = false;
		frames = 0;
	}
	
	public void render(){
		if(!scenes.isEmpty()){
			if(!sceneBegan){
				textLog.add(scenes.get(0).getText());
				scenes.get(0).animateThing(bs);
				bs.updateText();
				sceneBegan = true;
				actionRun = false;
				frames = 0;
			}
			else{
				if(!actionRun){
					if(frames >= scenes.get(0).getPreFrames()){
						
						if(scenes.get(0).getBa() != null){
							bs.runAction(scenes.get(0).getBa());
						}
						if(scenes.get(0).getSt() != null){
							scenes.get(0).getSt().postAnimRun(bs);
						}
						actionRun = true;
						frames = 0;
					}
					else{
						frames++;
					}
				}
				else{
					if(frames >= scenes.get(0).getPostFrames()){
						scenes.remove(0);
						sceneBegan = false;
						if(scenes.isEmpty()){
							bs.stm.adjustAll(bs);
						}
					}
					else{
						frames++;
					}
				}
			}
			
		}
	}
	
	public ArrayList<BattleScene> getScenes(){
		return scenes;
	}
	
	public void addScene(String text){
		scenes.add(new BattleScene(25,25,text));
	}
	
	public void addScene(String text, BattleAction ba){
		scenes.add(new BattleScene(75,75,text,ba));
	}
	
	public void addScene(String text, Status st){
		scenes.add(new BattleScene(75,75,text,st));
	}
	
}
