package abilities;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class SkillButton extends TextButton{

	public Skill skill;
	
	public SkillButton(Skill skill, Skin skin) {
		super(skill.name, skin);
		this.skill = skill;
	}

	public Skill getSkill() {
		return skill;
	}
	
}
