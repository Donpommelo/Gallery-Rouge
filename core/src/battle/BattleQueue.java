package battle;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.BattleState;

public class BattleQueue {

	Application game;
	BattleState bs;
	
	public int actionqSize;
	
	public ArrayList<Schmuck> party, enemy, battlers;
	public ArrayList<BattleButton> toq, actionq, actionAllies, actionEnemy;
	public BattleButton actor, opposing;
	
	public BattleQueue(BattleState bs, Application game, ArrayList<Schmuck> party, ArrayList<Schmuck> enemy){
		this.game = game;
		this.bs = bs;
		this.party = party;
		this.enemy = enemy;
		
		this.battlers = new ArrayList<Schmuck>();
		this.toq = new ArrayList<BattleButton>();
		this.actionq = new ArrayList<BattleButton>();
		
		for(Schmuck s : party){
			battlers.add(s);
			toq.add(new BattleButton(s,game, bs));
		}
		
		for(Schmuck s : enemy){
			battlers.add(s);
		}
		
		initButtons();
	}
	
	private void initButtons(){
		
		toq = sortButtons(toq);
		
		for(int i = 0; i < battlers.size(); i++){
		}
		
		for(BattleButton b : toq){
			bs.stage.addActor(b);
		}
	}
	
	public void getDelegates(){
		for(int i = 0; i < actionqSize; i++){
			actionq.add(toq.get(0));
			toq.remove(0);
		}
		
		for(BattleButton b: actionq){
			if(party.contains(b)){
				actionAllies.add(b);
			}
			else{
				actionEnemy.add(b);
			}
		}
		
	}
	
	public void endofRound(){
		actionAllies.clear();
		actionEnemy.clear();
		
	}
	
	public void adjustButtons(){
		
	}
	
	public ArrayList<BattleButton> sortButtons(ArrayList<BattleButton> buttons){
		int j;
		boolean flag = true;
		BattleButton temp;
		while (flag){
			flag=false;
			for(j=0; j<buttons.size()-1; j++){
				if(buttons.get(j) != null && buttons.get(j+1) != null){
					if(buttons.get(j).getSchmuck().getBuffedStat(3) < buttons.get(j+1).getSchmuck().getBuffedStat(3)){
						temp = buttons.get(j);
						buttons.set(j,buttons.get(j+1));
						buttons.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
		
		return buttons;
	}
}
