package battle;

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
	
	public void hpChange(BattleState bs,Schmuck perp, Schmuck vic, int base, int elem){
		
		int finalchange = base;
		//Damage being inflicted
		if(finalchange < 0){
			
			//Apply Damage Amplification and Damage Resistance
			finalchange += base * perp.getDamageAmp(bs);
			finalchange -= base * vic.getDamageRes(bs);
			
			//Apply Elemental Amplification and Elemental Resistance

			switch(elem){
			case 0:
				finalchange += base * perp.getPhysDamage(bs);
				finalchange -= base * vic.getPhysRes(bs);
				break;
			case 1:
				finalchange += base * perp.getSpecDamage(bs);
				finalchange -= base * vic.getSpecRes(bs);
				break;
			case 2:
				finalchange += base * perp.getAbstrDamage(bs);
				finalchange -= base * vic.getAbstrRes(bs);
				break;
			}
			

		}
		
		//Damage being healed
		else{
			switch(elem){
			case 0:
				finalchange += base * perp.getPhysDamage(bs);
				break;
			case 1:
				finalchange += base * perp.getSpecDamage(bs);
				break;
			case 2:
				finalchange += base * perp.getAbstrDamage(bs);
				break;
			}
		}

		for(Schmuck s : bs.bq.all){
			finalchange = bs.stm.statusProcTime(8, bs, null, s,perp, vic, finalchange, elem, true, null);
		}
		finalchange = bs.stm.statusProcTime(8, bs, null, bs.fieldDummy,perp, vic, finalchange, elem, true, null);

		if(finalchange >= 0){
			bs.bt.addScene(vic.getName()+" regenerated "+finalchange+" Hp!");
		}
		else{
			bs.bt.addScene(vic.getName()+" lost "+(-finalchange)+" Hp!");
		}
		vic.hpChange(bs,perp,finalchange);
	}
	
	public void mpChange(BattleState bs, Schmuck perp, Schmuck vic, int base, int elem){
		int finalchange = base;
		for(Schmuck s : bs.bq.all){
			finalchange = bs.stm.statusProcTime(9, bs, null, s,perp, vic, finalchange, elem, true, null);
		}
		finalchange = bs.stm.statusProcTime(9, bs, null, bs.fieldDummy,perp, vic, finalchange, elem, true, null);

		vic.mpChange(bs,perp,finalchange);
	}
	
	public void endofTurn(BattleState bs){
		for(Schmuck s : bs.bq.all){
			bs.stm.statusProcTime(4, bs, null, s, null, null, 0, 0, true, null);
		}
		bs.stm.statusProcTime(4, bs, null, bs.fieldDummy, null, null, 0, 0, true, null);

		for(Schmuck s : bs.bq.all){
			for(Status st : s.getStatuses()){
				if(st.decay){
					st.duration--;
					if(st.duration == 0){
						bs.stm.removeStatus(bs, s, st);
					}
				}
			}
		}
		
	}
}
