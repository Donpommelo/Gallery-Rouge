package abilities;

import java.util.ArrayList;

import battle.BattleButton;
import party.Schmuck;
import states.BattleState;
import status.abilitySpecific.CeramicGrenadeStatus;

public class CeramicGrenade extends Skill{

	public final static String name = "Ceramic Grenade";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 0;
	public final static double init = 0.0;
	public final static int target = 0;
	public final static int numTargets = 1;

	public CeramicGrenade() {
		super(name, descr, id, cost, init, target, numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		if (bs.stm.checkStatus(bs.fieldDummy, new CeramicGrenadeStatus(0, user, user))) {
			bs.stm.removeStatus(bs, bs.fieldDummy, new CeramicGrenadeStatus(0, user, user));
			bs.em.hpChange(bs, user, target.get(0), (int)(-user.getPhys(bs) * 2.25), 2);
		} else {
			bs.stm.addStatus(user, bs.fieldDummy, new CeramicGrenadeStatus(
					bs.bq.toq.indexOf(target.get(0).getButton()), user, bs.fieldDummy));
		}
	}
	
	public ArrayList<BattleButton> getTargets(BattleButton user, BattleState bs){
		if (!bs.stm.checkStatus(bs.fieldDummy, new CeramicGrenadeStatus(0, user.getSchmuck(), user.getSchmuck()))) {
			return bs.bq.toq;
		} else {
			ArrayList<BattleButton> targets = new ArrayList<BattleButton>();
			
			int index = bs.stm.getStatus(bs, bs.fieldDummy, 
					new CeramicGrenadeStatus(0, user.getSchmuck(), user.getSchmuck())).getExtraVar();
			
			if (index >= bs.bq.toq.size()) {
				index = bs.bq.toq.size() - 1;
			}
			
			targets.add(bs.bq.toq.get(index));
			
			return targets;
		}
	}

}
