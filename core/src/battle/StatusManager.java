package battle;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.BattleState;
import status.Incapacitation;
import status.Purification;
import status.Status;

public class StatusManager {
	
	/*
	 *Its like the EffectManager except for statuses. This adds, removes, activates and processes statuses.
	 */
	
	Application game;
	BattleState bs;
	
	public StatusManager(Application game, BattleState bs) {
		this.game = game;
		this.bs = bs;
	}
	
	//Add a status to a schmuck
	public void addStatus(Schmuck perp, Schmuck vic, Status stat) {
		
		//Does the status get added after initial processing?
		boolean added = false;
		
		//What happens if the status is applied to a Schmuck that already has it?
		//Add more stacking effects if you want.
		switch (stat.stackingEffect()) {
		
		//Case 0: New stat is not applied.
		case 0:
			if(checkStatus(vic,stat)) {
				bs.bt.addScene(vic.getName()+" is already under the effects of "+stat.getName()+"!", false);
			} else {
				added = true;
			}
			break;
			
		//Case 1: status is added no matter what
		case 1:
			added = true;
			break;
		}
		
		//If status is added, add the status.
		if (added) {
			
			//Add status inflict text if not blank.
			if (!stat.inflictText(vic).equals("")) {
				bs.bt.addScene(stat.inflictText(vic), false);
			}
			
			//Add the status.
			vic.getStatuses().add(stat);

			//Run all on-Status-Inflict statuses for all schmucks and field effects.
			for (Schmuck sc : bs.bq.all) {
				statusProcTime(0, bs, null, sc, perp, vic, 0, 0, true, stat);
			}
			statusProcTime(0, bs, null, bs.fieldDummy, perp, vic, 0, 0, true, stat);
		}
	}
	
	//Checks if a schmuck as a particular status.
	public boolean checkStatus(Schmuck vic, Status st){
		boolean has = false;
		
		//Status checking is based on name. Don't have diffrernt statuses with the same name.
		//Maybe use id later if that's going to be a thing?
		for (Status stat : vic.getStatuses()) {
			if (stat.getName() == st.getName()) {
				has = true;
			}
		}
		return has;
	}
	
	//Remove a status from a schmuck.
	//Should there be a "perp" for this? Will there be effects for removing other's statuses?
	public void removeStatus(BattleState bs, Schmuck s, Status stat) {
		
		//Do typical nonpermanent status removal stuff.
		//Note that this will remove all statuses will the same name if there are more than one.
		ArrayList<Status> removed = new ArrayList<Status>();
		for (Status st : s.getStatuses()) {
			if (!st.perm && st.getName().equals(stat.getName())) {
				removed.add(st);
			}
		}
		for (Status st : s.getStatusesChecked()) {
			if (!st.perm && st.getName().equals(stat.getName())) {
				removed.add(st);
			}
		}
		
		//Add status removal text if not blank and remove status.
		for (Status st : removed) {
			if (!st.cureText(s).equals("")) {
				bs.bt.addScene(st.cureText(s), false);
			}
			
			s.getStatuses().remove(st);
			s.getStatusesChecked().remove(st);
			
			//Run all on-Status-Remove statuses for all schmucks and field effects.
			for (Schmuck sc : bs.bq.all) {
				statusProcTime(1, bs, null, sc, null, null, 0, 0, true, st);
			}
			statusProcTime(1, bs, null, bs.fieldDummy, null, null, 0, 0, true, st);
		}
	}
	
	public Status getStatus(BattleState bs, Schmuck s, Status stat) {
		Status returned = new Purification(1,s,s);
		
		for (Status st : s.getStatuses()) {
			if (st.getName().equals(stat.getName())) {
				returned = st;
			}
		}
		for (Status st : s.getStatusesChecked()) {
			if (st.getName().equals(stat.getName())) {
				returned = st;
			}
		}
		
		return returned;
	}
	
	/*
	 * This is the big thing that makes all the statuses work properly.
	 * This is called whenever a situation might proc a status.
	 * procTime: What is causing the status to proc. Decides which function of the status to run.
	 * bs: BattleState that this status effect is occurring in.
	 * a: Used in statuses that effect a certain action such as pre-Action or on-Action effects. Can be null.
	 * obs: Schmuck owner of the statuses being iterated through.
	 * schmuck1/2: used for statuses affecting schmucks: on-Hp-Change. on-Status-Inflict etc. Can be null.
	 * amount: int affected the status. Used for statuses like on-Hp-Change that can change the amount of Hp changed. Can be null.
	 * elem: Another int for statuses that care about an element like on-Hp-Change. Can be null.
	 * won: did you win the fight? Only used for end-of-fight statuses. Can be null.
	 * st: Status used for status-affecting statuses like on-Status-Removal. Can be null.
	 */
	public int statusProcTime(int procTime, BattleState bs, BattleAction a, Schmuck obs, Schmuck schmuck1,
			Schmuck schmuck2, int amount, int elem, boolean won, Status st){
		
		//Amount of something changeable by the status like Hp changed for on-Hp-Changed effects
		int finalamount = amount;
		
		//Used when status effects activate other statuses. oldchecked records the statuses procced by the previous status.
		ArrayList<Status> oldChecked = new ArrayList<Status>();
		
		//If the obs has statuses checked already, this indicates a nested status activation.
		//Checked statuses are actually the checked statuses of the previous status and should be recorded and reset.
		for(Status s : obs.statusesChecked){
			obs.statuses.add(0,s);
			oldChecked.add(s);
		}
		obs.statusesChecked.clear();
		
		//Iterate through all unchecked statuses.
		while(!obs.statuses.isEmpty()){
			
			//tempStatuse: the status we are currently processing.
			Status tempStatus = obs.statuses.get(0);
			
			//Eventually when these effects are coded, check to see if the status should be procced at all.
			if (!bs.stm.checkStatus(obs, new Incapacitation(obs,obs)) || tempStatus.runWhenDead()) {
				if (!bs.stm.checkStatus(obs, new Purification(1,obs,obs))) {
			
					//Activate a specific function of that status depending on the procTime
					switch (procTime) {
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
						finalamount = tempStatus.statChange(bs, schmuck2, finalamount, elem);
						break;
					//Case 13: Whenever a move looks for targets
					case 13:
						tempStatus.onTargetAcquire(bs,a);
						break;
					}
				}
			}
			
			//If tempStatus activation has not resulted in its own removal, mark it as checked.
			if (obs.statuses.contains(tempStatus)) {
				obs.statuses.remove(tempStatus);
				obs.statusesChecked.add(tempStatus);
			}
		}
		
		//By this point, statuses should be empty and all remaining statuses should have been added to statusesChecked.
		//Re-add these statuses back to statuses unless you were performing a nested status activation. 
		//In the case of a nested statuse activation, set statuses to their checked status before current activation.
		for (Status s : obs.statusesChecked) {
			if (!oldChecked.contains(s)) {
				obs.statuses.add(s);
			}
		}
		
		obs.statusesChecked.clear();
		for (Status s : oldChecked) {
			obs.statusesChecked.add(s);
		}
		
		return finalamount;
	}
	
	//adjustAll adjusts Schmuck groups to account for changes that occurred during the round
	//These changes include new summons and destroyed summons.
	public void adjustAll(BattleState bs) {
		bs.bq.all.clear();
		for (BattleButton b : bs.bq.toq) {
			bs.bq.all.add(b.getSchmuck());
		}
		for (BattleButton b : bs.bq.actionq) {
			bs.bq.all.add(b.getSchmuck());
		}
		for (BattleButton b : bs.bq.ko) {
			bs.bq.all.add(b.getSchmuck());
		}
	}
}
