package battle;

import java.util.ArrayList;

import abilities.Skill;
import party.Schmuck;
import states.BattleState;

public class BattleAction {

	//Schmuck performing the action
	public Schmuck user;
	
	//List of schmucks being targets. Can be empty.
	public ArrayList<Schmuck> targets;
	
	//Skill being used
	public Skill skill;
	
	public BattleAction(Schmuck user, ArrayList<Schmuck> targets, Skill skill) {
		this.user = user;
		this.targets = targets;
		this.skill = skill;
	}
	
	//This is run when the battle action is run.
	//Run in the runAction method of BattleState.
	public void run(BattleState bs) {
		skill.use(user, targets, bs);
	}

	//Text displayed upon an ability being used.
	public String getText() {
		return skill.useText(user);
	}
	
	//This is run whenever a target for an ability is selected by the player.
	//Checks if player is done targeting.
	public ArrayList<BattleButton> checkReady(BattleState bs) {
		
		//Is the player done targeting? If so, begin action processing.
		if (this.getTarget().size() >= this.getSkill().numTargets) {
			bs.partyReady = true;
		}
		
		//Find available targets for next target selection.
		//This is necessary for abilities that require multiple targets out of different pools.
		bs.targetable.clear();
		for(BattleButton b : getSkill().getTargets(bs.bq.actor, bs)){
			bs.targetable.add(b);
		}
		
		//Run On-Targeting effects. These tend to change possible targets.
		//Example: Marked, Taunted, Fear.
		for(Schmuck s : bs.bq.all){
			bs.stm.statusProcTime(13, bs, this, s, null, null, 0, 0, true, null);
		}
		bs.stm.statusProcTime(13, bs, this, bs.fieldDummy, getUser(), null, 0, 0, true, null);
		
		
		
		//Return targetable schmucks.
		//atm this is just used to make valid targets flash
		return bs.targetable;
	}
	
	//Run when selecting a valid target during ability targeting phase.
	//Updates BattleAction targets and checks if player is done targeting.
	public void addTarget(Schmuck target, BattleState bs) {
		this.targets.add(target);
		
		bs.bq.manageFlash(bs.curAction.checkReady(bs));

	}
	
	public double getInit(BattleState bs){
		return user.getInit(bs) * (1 + skill.getInit());
	}
	
	//Getters and Setters for user, target and skill.
	
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
	
	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
}
