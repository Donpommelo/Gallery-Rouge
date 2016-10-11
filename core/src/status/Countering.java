package status;

import battle.BattleAction;
import party.Schmuck;
import states.BattleState;

public class Countering extends Status{
	
	final static int id = 2;
	final static String name = "Countering";
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public Countering(int duration,Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	public void onAction(BattleState bs, BattleAction ba){
		if (vic.equals(bs.bq.getOpposingActor(ba.user).getSchmuck()) && ba.getSkill().getTargetType() == 1) {
			bs.bt.addScene(vic.getName()+" countered the attack!", this);
		}
	}

	public void postAnimRun(BattleState bs){
		bs.em.hpChange(bs, vic, bs.bq.getOpposingActor(vic).getSchmuck(),-vic.getPhys(bs), 0);
	}

	public String inflictText(Schmuck s){
		return s.getName()+" is preparing to Counter!";
	}
}
