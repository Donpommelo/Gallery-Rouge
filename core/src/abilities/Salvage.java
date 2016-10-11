package abilities;

import java.util.ArrayList;

import party.Schmuck;
import states.BattleState;
import status.Incapacitation;

public class Salvage extends Skill{

	public final static String name = "Salvage";
	public final static String descr = "TEMP";
	public final static int id = 1;
	public final static int cost = 16;
	public final static double init = -.90;
	public final static int target = 0;
	public final static int numTargets = 0;

	public Salvage() {
		super(name, descr, id, cost, init, target,numTargets);
	}
	
	public void use(Schmuck user, ArrayList<Schmuck> target, BattleState bs){
		
		Boolean revived = false;
		
		for (int i = 0; i < bs.bq.ko.size(); i++) {
			
			if (bs.bq.getAllyTeam(user).contains(bs.bq.ko.get(i))) {
				Schmuck s = bs.bq.ko.get(i).getSchmuck();
				
				bs.bt.addScene(s.getName() + "'s life was salvaged from the deep!", true);

				bs.stm.removeStatus(bs, s, new Incapacitation(s,s));
				bs.em.hpChange(bs, user, s, s.getMaxHp(bs)/10, 2);
				bs.bq.ko.remove(s.getButton());
				bs.bq.toq.add(s.getButton());
				revived = true;
				i = bs.bq.ko.size();		
				
			}
		}
		
		if (!revived) {
			bs.bt.addScene(user.getName() + " found no lives to Salvage!", true);
		}
		
		bs.bq.adjustButtons();
	}
	
}
