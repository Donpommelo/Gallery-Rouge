package abilities;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import states.BattleState;

public class SkillButton extends TextButton{

	public Skill skill;
	public BattleState bs;
	
	public SkillButton(Skill skill, Skin skin, BattleState bs) {
		super(skill.name, skin);
		this.skill = skill;
		this.bs = bs;
		this.setSize(150, 32);
	}
	
	public void setSkill(Skill skill) {
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}
	
}
