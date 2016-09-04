package battle;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class StatusManager {
	Application game;
	BattleState bs;
	
	public StatusManager(Application game, BattleState bs){
		this.game = game;
		this.bs = bs;
	}
	
public void addStatus(Schmuck perp, Schmuck vic, Status stat){
		
		boolean added = false;
		
		switch(stat.stackingEffect()){
		//Case 0: New stat is not applied.
		case 0:
			if(checkStatus(vic,stat)){
				bs.bt.addScene(vic.getName()+" is already under the effects of "+stat.getName()+"!");
			}
			else{
				added = true;
			}
			break;
		//Case 1: status is added no matter what
		case 1:
			added = true;
			break;
		}
		
		if(added){
			if(!stat.inflictText(vic).equals("")){
				bs.bt.addScene(stat.inflictText(vic));
			}
			
			vic.getStatuses().add(stat);
			
			for(Schmuck sc : bs.bq.all){
				statusProcTime(0, bs, null, sc, perp, vic, 0, 0, true, stat);
			}
			statusProcTime(0, bs, null, bs.fieldDummy, perp, vic, 0, 0, true, stat);
		}
	}
	
	public boolean checkStatus(Schmuck vic, Status st){
		boolean has = false;
		
		for(Status stat : vic.getStatuses()){
			if(stat.getName() == st.getName()){
				has = true;
			}
		}
		return has;
	}
	
	public void removeStatus(BattleState bs, Schmuck s, Status stat){
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
			
			for(Schmuck sc : bs.bq.all){
				statusProcTime(1, bs, null, sc, null, null, 0, 0, true, st);
			}
			statusProcTime(1, bs, null, bs.fieldDummy, null, null, 0, 0, true, st);
		}
	}
	
	public int statusProcTime(int procTime, BattleState bs, BattleAction a, Schmuck obs,Schmuck schmuck1,
			Schmuck schmuck2, int amount, int elem, boolean won, Status st){
		int finalamount = amount;
		ArrayList<Status> oldChecked = new ArrayList<Status>();
		for(Status s : obs.statusesChecked){
			obs.statuses.add(0,s);
			oldChecked.add(s);
		}
		obs.statusesChecked.clear();
		while(!obs.statuses.isEmpty()){
			Status tempStatus = obs.statuses.get(0);
		//	if(!bs.stm.checkStatus(obs, new Incapacitation(obs,obs))){
		//		if(!bs.bp.stm.checkStatus(this, new Purified(this,0))){
					switch(procTime){
					//Case 0: When any status is inflicted
					case 0:
						tempStatus.onStatusInflict(bs,schmuck1,schmuck2,st);
						break;
					//Case 1: When any status is cured
					case 1:
						tempStatus.onStatusCure(bs,schmuck1,schmuck2,st);
						break;
					//Case 2: At start of fights when Battle Queue is initiated
					case 2:
						tempStatus.fightStart(bs);
						break;
					//Case 3: At end of fights before Battle State is cleared.
					case 3:
						tempStatus.fightEnd(bs);
						break;
					//Case 4: At the end of rounds after actions are done processing.
					case 4:
						tempStatus.postRound(bs);
						break;
					//Case 5: Upon the Action Group being formed.
					case 5:
						tempStatus.postDelegation(bs);
						break;
					//Case 6: Before any action is run.
					case 6:
						tempStatus.preAction(bs, a);
						break;
					//Case 7: After any action is run.
					case 7:
						tempStatus.onAction(bs, a);
						break;
					//Case 8: When any schmuck's current Hp is changed
					case 8:
						finalamount = tempStatus.onHpChange(bs, schmuck1, schmuck2, finalamount, elem);
						break;
					//Case 9: When any schmuck's current Mp is changed
					case 9:
						finalamount= tempStatus.onMpChange(bs, schmuck1, schmuck2, finalamount, elem);
						break;
					//Case 10: When any schmuck is incapacitated
					case 10: 
						tempStatus.onDeath(bs, schmuck1, schmuck2);
						break;
					//Case 11: When any schmuck becomes insane
					case 11:
						tempStatus.onInsanity(bs, schmuck1, schmuck2);
						break;
					//Case 12: When any schmuck's buffed stats are called.
					case 12:
						finalamount = tempStatus.statChange(bs,schmuck2, finalamount, elem);
						break;
					//Case 13: Whenever a move looks for targets
					case 13:
						tempStatus.onTargetAcquire(bs,a);
						break;
		//			}
		//		}
			}
			if(obs.statuses.contains(tempStatus)){
				obs.statuses.remove(tempStatus);
				obs.statusesChecked.add(tempStatus);
			}
		}
		for(Status s : obs.statusesChecked){
			if(!oldChecked.contains(s)){
				obs.statuses.add(s);
			}
		}
		obs.statusesChecked.clear();
		for(Status s : oldChecked){
			obs.statusesChecked.add(s);
		}
		
/*		if(obs == bs.fieldDummy){
			adjustAll(bs);
		}*/
		
		return finalamount;
		
	}
	
	public void adjustAll(BattleState bs){
		bs.bq.all.clear();
		for(BattleButton b : bs.bq.toq){
			bs.bq.all.add(b.getSchmuck());
		}
		for(BattleButton b : bs.bq.actionq){
			bs.bq.all.add(b.getSchmuck());
		}
		for(BattleButton b : bs.bq.ko){
			bs.bq.all.add(b.getSchmuck());
		}
	}
}
