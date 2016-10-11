package battle;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.BattleState;
import status.Status;

public class EffectManager {

	/*
	 * EffectManager manages battle effects.
	 */
	Application game;
	BattleState bs;
	
	public EffectManager(Application game, BattleState bs) {
		this.game = game;
		this.bs = bs;
	}
	
	//Called whenever a schmuck's Hp is changed
	public void hpChange(BattleState bs, Schmuck perp, Schmuck vic, int base, int elem) {
		
		//finalchange: the final amount of Hp changed.
		int finalchange = base;

		//Negative amounts of change = damage being taken
		if (finalchange < 0) {
			
			//Apply Damage Amplification and Damage Resistance
			finalchange += base * perp.getDamageAmp(bs);
			finalchange -= base * vic.getDamageRes(bs);
			
			//Apply Elemental Amplification and Elemental Resistance. (Feel free to add more damage types later)
			switch (elem) {
			
			//Case 0: Physical damage.
			case 0:
				finalchange += base * perp.getPhysDamage(bs);
				finalchange -= base * vic.getPhysRes(bs);
				break;
			//Case 1: Special damage;
			case 1:
				finalchange += base * perp.getSpecDamage(bs);
				finalchange -= base * vic.getSpecRes(bs);
				break;
			//Case 2: Abstract damage
			case 2:
				finalchange += base * perp.getAbstrDamage(bs);
				finalchange -= base * vic.getAbstrRes(bs);
				break;
			}
		} else {
			//If finalchange is positive then Hp is being healed.
			//Like Uplifted, healing scales with alignment too.
			switch (elem) {
			//Case 0: Physical heal
			case 0:
				finalchange += base * perp.getPhysDamage(bs);
				break;
			//Case 0: Special heal
			case 1:
				finalchange += base * perp.getSpecDamage(bs);
				break;
			//Case 2: Physical heal
			case 2:
				finalchange += base * perp.getAbstrDamage(bs);
				break;
			}
		}

		//Run all on-HpChange statuses for all schmucks and field effects.
		for (Schmuck s : bs.bq.all) {
			finalchange = bs.stm.statusProcTime(8, bs, null, s,perp, vic, finalchange, elem, true, null);
		}
		finalchange = bs.stm.statusProcTime(8, bs, null, bs.fieldDummy,perp, vic, finalchange, elem, true, null);
		
		//Finally, apply the change in Hp.
		vic.hpChange(bs,perp,finalchange);
		
		//Add text depending on whether final change is damage or healing.
		if (finalchange >= 0) {
			bs.bt.addScene(vic.getName()+" regenerated "+finalchange+" Hp!", false);
		} else {
			bs.bt.addScene(vic.getName()+" lost "+(-finalchange)+" Hp!", false);
		}
				
		vic.getButton().updateNameLabel();
		
		bs.bq.manageFlash(vic.getButton(), 50);
	}
	
	//Same thing as hpChange except fewer things modify it.
	public void mpChange(BattleState bs, Schmuck perp, Schmuck vic, int base, int elem) {
		
		//finalchange: the final amount of Hp changed.
		int finalchange = base;
		
		//Run all on-MpChange statuses for all schmucks and field effects.
		for (Schmuck s : bs.bq.all) {
			finalchange = bs.stm.statusProcTime(9, bs, null, s,perp, vic, finalchange, elem, true, null);
		}
		finalchange = bs.stm.statusProcTime(9, bs, null, bs.fieldDummy,perp, vic, finalchange, elem, true, null);
				
		vic.mpChange(bs,perp,finalchange);		
	}
	
	//Runs in the BattleQueue at the end of every round.
	public void endofTurn(BattleState bs) {
		
		//Run all end-of-round statuses for all schmucks and field effects.
		for (Schmuck s : bs.bq.all) {
			bs.stm.statusProcTime(4, bs, null, s, null, null, 0, 0, true, null);
		}
		bs.stm.statusProcTime(4, bs, null, bs.fieldDummy, null, null, 0, 0, true, null);

		//Decrease duration of decaying statuses and remove the ones that reach 0 duration.
		ArrayList<Status> toRemove = new ArrayList<Status>();
		
		for (Schmuck s : bs.bq.all) {
			for (Status st : s.getStatuses()) {
				if (st.decay) {
					st.duration--;
					if (st.duration == 0) {
						toRemove.add(st);
					}
				}
			}
			
			for (Status st : toRemove) {
				bs.stm.removeStatus(bs, s, st);
			}
		}
	}
}
