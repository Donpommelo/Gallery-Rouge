package battle;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import abilities.Skill;
import party.Schmuck;
import states.BattleState;

public class InfoPanel extends TextButton{

	Schmuck schmuck;
	Label stats,skills;
	
	public InfoPanel(Skin skin) {
		super("", skin);
		this.clearChildren();
		this.top().left();
		stats = new Label("", skin);
		skills = new Label("", skin);
		skills.setWrap(true);
		this.add().expandX().height(200);
		this.add(stats).width(300).top().left();
		this.row();
		this.add(skills).expandY().colspan(2).top().left();
//		this.debug();
	}
	
	public void updateSchmuck(BattleState bs, Schmuck s){
		this.schmuck = s;
		stats.setText("Hp: "+s.getCurrentHp()+"/"+s.getMaxHp(bs)+"\n"+
		"Mp: "+s.getCurrentMp()+"/"+s.getMaxMp(bs)+"\n"+
		"Phys: "+s.getPhys(bs)+"\n"+
		"Spec: "+s.getSpec(bs)+"\n"+
		"Abstr: "+s.getAbstr(bs));
		
		String skill = "";
		for(Skill sk : s.getSkills()){
			if(sk.getName() != "Attack"){
				skill = skill + sk.getName()+": "+sk.getDescr()+"\n";
			}
		}
		skills.setText(skill);
	}

}
