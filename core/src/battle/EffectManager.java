package battle;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class EffectManager {

	Application game;
	BattleState bs;
	
	public EffectManager(Application game, BattleState bs){
		this.game = game;
		this.bs = bs;
	}
	
	public void hpChange(Schmuck perp, Schmuck vic, int base, int elem){
		
		int finalchange = base;
		
		//Damage being inflicted
		if(finalchange < 0){
			
			//Apply Damage Amplification and Damage Resistance
			finalchange += base * (1 + (double)perp.getBuffedStat(14)/100);
			finalchange -= base * (1 + (double)vic.getBuffedStat(15)/100);
			
			//Apply Elemental Amplification and Elemental Resistance
			
			finalchange += base * (1 + (double)perp.getBuffedStat(5+elem)/50);
			finalchange -= base * (1 + (double)vic.getBuffedStat(8+elem)/100);
	
		}
		
		//Damage being healed
		else{
			finalchange += base * (1 + (double)perp.getBuffedStat(5+elem)/50);
		}
		
		
		
	}
	
	public void addStatus(Schmuck s, Status stat){
		if(stat.getId() == 0){
			//
		}
		
		boolean added = false;
		
		switch(stat.stackingEffect()){
		//Case 0: New stat is not applied.
		case 0:
			if(checkStatus(s,stat)){
				bs.bt.addScene(s.getName()+" is already under the effects of "+stat.getName()+"!");
			}
			else{
				s.getStatuses().add(stat);
				added = true;
			}
			break;
		}
		
		if(added){
			if(!stat.inflictText(s).equals("")){
				bs.bt.addScene(stat.inflictText(s));
			}
		}
	}
	
	public boolean checkStatus(Schmuck vic, Status st){
		boolean has = false;
		
		for(Status stat : vic.getStatuses()){
			if(stat.getId() == st.getId()){
				has = true;
			}
		}
		return has;
	}
	
	public void removeStatus(Schmuck s, Status stat){
		ArrayList<Status> removed = new ArrayList<Status>();
		for(Status st : s.getStatuses()){
			if(!st.perm && st.getName().equals(stat.getName())){
				removed.add(st);
			}
		}
		for(Status st : s.getStatusesChecked()){
			if(!st.perm && st.getName().equals(stat.getName())){
				removed.add(st);
			}
		}
		for(Status st : removed){
			if(!st.cureText(s).equals("")){
				bs.bt.addScene(st.cureText(s));
			}
			s.getStatuses().remove(st);
			s.getStatusesChecked().remove(st);
		}
	}
}
