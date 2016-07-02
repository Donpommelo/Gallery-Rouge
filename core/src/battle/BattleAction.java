package battle;

import abilities.Skill;
import party.Schmuck;
import states.BattleState;

public class BattleAction {

	public Schmuck user,target;
	public Skill skill;
	
	public BattleAction(Schmuck user, Schmuck target, Skill skill){
		this.user = user;
		this.target = target;
		this.skill = skill;
	}
	
	public void run(BattleState bs){
		skill.use(user, target, bs);
	}

	public String getText(){
		return user.getName()+" used "+skill.getName()+"!";
	}
	
	public Schmuck getUser() {
		return user;
	}

	public void setUser(Schmuck user) {
		this.user = user;
	}

	public Schmuck getTarget() {
		return target;
	}

	public void setTarget(Schmuck target) {
		this.target = target;
	}

	public Skill getSkill() {
		return skill;
	}

	public void setSkill(Skill skill) {
		this.skill = skill;
	}
	
}
