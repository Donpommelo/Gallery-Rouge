package party;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.grouge.Application;

import abilities.Skill;
import abilities.StandardAttack;
import battle.BattleAction;
import states.BattleState;
import status.Status;

public class Schmuck {

	public String Icon, MenuSprite;
	public int[] baseStats = {0,0,0,0,0};
	
	//0: Bonus Hp
	//1: Bonus Mp
	//2: Phys Alignment
	//3: Spec Alignment
	//4: Abstr Alignment
	//5: Bonus Phys Damage
	//6: Bonus Spec Damage
	//7: Bonus Abstr Damage
	//8: Bonus Phys Resist
	//9: Bonus Spec Resist
	//10: Bonus Abstr Resist
	//11: Bonus Init
	//12: Bonus Hp Regen
	//13: Bonus Mp Regen
	//14: Bonus Damage Amp
	//15: Bonus Damage Resist
	//16: Base Element

	public int[] tempStats = {0,0};
	public double PhysSpecSplit;
	
	public Skill[] skills;
	
	public int Lvl;
	public int exp = 0;
	
	String name, bioShort,bioLong;
	
	ArrayList<Status> statuses, statusesChecked;
	
	public Schmuck(String name, String bioShort,String bioLong, int[] start, Skill[] skills,
			String icon, String sprite){
		this.name = name;
		this.bioShort = bioShort;
		this.bioLong = bioLong;
		this.baseStats = start;
		this.skills = skills;
		this.Icon = icon;
		this.MenuSprite = sprite;
		this.statuses = new ArrayList<Status>();
		tempStats[0] = baseStats[0];
		tempStats[1] = baseStats[1];
	}
	
	public BattleAction getAction(BattleState bs){
		return new BattleAction(this,this,new StandardAttack());
	}
	
	public int getCurrentHp(){
		return tempStats[0];
	}
	
	public int getCurrentMp(){
		return tempStats[1];
	}
	
	public void hpChange(int hp){
		tempStats[0] += hp;
		
		if(tempStats[0] > getBuffedStat(0)){
			tempStats[0] = getBuffedStat(0);
		}
		
		if(tempStats[0] <= 0){
			tempStats[0] = 0;
			//Inflict incapacitated + remove statuses.
		}
	}
	
	public void mpChange(int mp){
		tempStats[1] += mp;
		if(tempStats[1] > getBuffedStat(1)){
			tempStats[1] = getBuffedStat(1);
		}
		
		if(tempStats[1] <= 0){
			tempStats[1] = 0;
		}
	}
	
	public int getBaseStat(int statNum){
		return baseStats[statNum];
	}
	
	public int getBuffedStat(int statNum){
		
		//Testing thing
		if(statNum == 3){
			return (int) (Math.random()*10);
		}
		
		int stat = getBaseStat(statNum);
		
		for(Status st : statuses){
			stat = st.statChange(statNum);
		}
		
		return stat;
	}

	public Texture getIcon(Application game) {
		return game.assets.get(Icon);
	}

	public Texture getMenuSprite(Application game) {
		return game.assets.get(MenuSprite);
	}
	
	public String getName(){
		return name;
	}
	
	public String getBioShort(){
		return bioShort;
	}
	
	public String getBioLong(){
		return bioLong;
	}
	
	public Skill[] getSkills(){
		return skills;
	}
	
	public ArrayList<Status> getStatuses(){
		return statuses;
	}
	
	public ArrayList<Status> getStatusesChecked(){
		return statusesChecked;
	}
}
