package battle;

import java.util.ArrayList;

import abilities.Skill;
import party.Schmuck;
import states.BattleState;

public class BattleAction {

	public Schmuck user;
	public ArrayList<Schmuck> targets;
	public Skill skill;
	
	public BattleAction(Schmuck user, ArrayList<Schmuck> targets, Skill skill){
		this.user = user;
		this.targets = targets;
		this.skill = skill;
	}
	
	public void run(BattleState bs){
		skill.use(user, targets, bs);
		
	}

	public String getText(){
		return skill.useText(user);
	}
	
	public Schmuck getUser() {
		return user;
	}

	public void setUser(Schmuck user) {
		this.user = user;
	}

	public ArrayList<Schmuck> getTarget() {
		return targets;
	}

	public void setTarget(ArrayList<Schmuck> targets) {
		this.targets = targets;
	}
	
	public void addTarget(Schmuck target, BattleState bs){
		this.targets.add(target);
		checkReady(bs);
		
	}
	
	public ArrayList<BattleButton> checkReady(BattleState bs){
		if(this.getTarget().size() >= this.getSkill().numTargets){
			bs.partyReady = true;
		}
		
		bs.targetable.clear();
		for(BattleButton b : getSkill().getTargets(bs.bq.actor, bs.bq)){
			bs.targetable.add(b);
		}
		
		for(Schmuck s : bs.bq.all){
			bs.stm.statusProcTime(13, bs, this, s, null, null, 0, 0, true, null);
		}
		
		bs.stm.statusProcTime(13, bs, this, bs.fieldDummy, getUser(), null, 0, 0, true, null);
		
		return bs.targetable;

	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
}
