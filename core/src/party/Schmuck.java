package party;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.grouge.Application;

import abilities.Skill;
import abilities.StandardAttack;
import battle.BattleAction;
import battle.BattleButton;
import states.BattleState;
import status.Incapacitation;
import status.Insanity;
import status.Status;

public class Schmuck {

	/*
	 * A schmuck is a single unit in battle. They work pretty similar to Schmucks in Uplifted. 
	 */
	
	//Icon: Filename for image that appears in battle.
	//MenuSprite: Filename for image that appears in the menu and maybe dialog.
	public String Icon, MenuSprite;
	
	//Base Stats.
	public int[] baseStats = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	
	/**
	0: Bonus Hp
	1: Bonus Mp
	2: Phys Alignment
	3: Spec Alignment
	4: Abstr Alignment
	5: Bonus Phys Damage
	6: Bonus Spec Damage
	7: Bonus Abstr Damage
	8: Bonus Phys Resist
	9: Bonus Spec Resist
	10: Bonus Abstr Resist
	11: Bonus Init
	12: Bonus Hp Regen
	13: Bonus Mp Regen
	14: Bonus Damage Amp
	15: Bonus Damage Resist
	16: Base Element *NOTE* THINGS THAT MODIFY THIS STAT SHOULD SET IT SO 0,1,2. NO INCREASING OF DECREASING.
	17: Mp Cost Modifier
	**/
	
	/**Unimplemented Stuff
	18: Status duration modifier?
	19: 
	**/
	
	//tempStats: Current Hp and Mp.
	public int[] tempStats = {0,0};
	
	//Schmuck's list of skills.
	public Skill[] skills;
	
	//Schmuck's biographical information.
	public String name, bioShort, bioLong;
	
	//0: It 1: He 2: She
	public int pronoun;
	
	//Schmucks current statuses. statusesChecked is used for status processing.
	public ArrayList<Status> statuses, statusesChecked;
	
	//Schmucks button that appears in Battle State.
	BattleButton button;
	
	public Schmuck(String name, int pronoun, String bioShort, String bioLong, int[] start, Skill[] skills,
			String icon, String sprite){
		this.name = name;
		this.pronoun = pronoun;
		this.bioShort = bioShort;
		this.bioLong = bioLong;
		this.baseStats = start;
		this.skills = skills;
		this.Icon = icon;
		this.MenuSprite = sprite;
		this.statuses = new ArrayList<Status>();
		this.statusesChecked = new ArrayList<Status>();
		
		tempStats[0] = getMaxHp(null);
		tempStats[1] = getMaxMp(null);
	}
	
	//Constructor for field dummy schmuck that has statuses but nothing else.
	public Schmuck() {
		this.name = "The Battlefield";
		this.statuses = new ArrayList<Status>();
		this.statusesChecked = new ArrayList<Status>();
	}

	//Used for getting the action of an ai character. 
	//Default is attacking the opposing actor.
	public BattleAction getAction(BattleState bs) {
		return new BattleAction(this,new ArrayList<Schmuck>(), new StandardAttack());
	}
	
	//Run when a schmuck is initialized in the battlestate. All of the schmuck's instrinsic statuses will be added here.
	//I guess I could put other stuff here too, but I don't know what.
	public void addIntrinsics(){}
	
	
	//Get a schmuck's current Hp and Mp. No weird stuff here.
	public int getCurrentHp() {
		return tempStats[0];
	}
	
	public int getCurrentMp() {
		return tempStats[1];
	}
	
	//Called when changing a schmuck's Hp.
	public void hpChange(BattleState bs, Schmuck perp, int hp) {
		
		//Change Hp.
		tempStats[0] += hp;
		
		//Hp cannot go below 0 or above max Hp (Unless I want the effect to exist)
		if (tempStats[0] > getMaxHp(bs)) {
			tempStats[0] = getMaxHp(bs);
		}
		
		//Hp that reaches 0 results in incapacitation.
		if (tempStats[0] <= 0) {
			tempStats[0] = 0;
			bs.stm.addStatus(perp, this, new Incapacitation(perp,this));
		}
	}
	
	//Called when changing a schmuck's Mp.
	public void mpChange(BattleState bs, Schmuck perp, int mp) {
		
		//If a Schmuck is Insane, Mp costs are deducted from Hp instead.
		if (bs.stm.checkStatus(this, new Insanity(perp,this))) {
			hpChange(bs,perp,mp);
		} else {
			
			//Otherwise, remove Mp as normal. As with Hp, Mp cannot go below 0 or above Max Mp.
			tempStats[1] += mp;
			if (tempStats[1] > getMaxMp(bs)) {
				tempStats[1] = getMaxMp(bs);
			}
			
			//Hp that reaches 0 results in insanity.
			if (tempStats[1] <= 0) {
				tempStats[1] = 0;
				bs.stm.addStatus(perp,this, new Insanity(perp,this));
			}
		}
	}
	
	//Get a schmuck's base stats.
	public int getBaseStat(int statNum){
		return baseStats[statNum];
	}
	
	//Get a schmuck's buffed stats.
	//This is used for any battle calculations and is a bit more complicated.
	public int getBuffedStat(int statNum, BattleState bs) {
		
		//stat is the stat returned at the end of processing. Starts of as corresponding base stat but can be modified.
		int stat = getBaseStat(statNum);
		
		//If in battle, activate statuses.
		if (bs != null) {
			
			//Run all on-Stat-Call statuses for all schmucks and field effects.
			for (Schmuck s : bs.battlers) {
				stat = bs.stm.statusProcTime(12, bs, null, s, this, this, stat, statNum, true, null);
			}
			stat = bs.stm.statusProcTime(12, bs, null, bs.fieldDummy, this, this, stat, statNum, true, null);
		} else {
			//If not in battle, only check schmuck's own statuses.
			for (Status st : statuses) {
				stat = st.statChange(stat, statNum);
			}
		}
		
		return stat;
	}
	
	//These functions get stats that are used for calculations. The call the above getBuffed Stat.
	//Most just call a single buffed stat, but some perform more complicated calculations.

	//Max Hp scales to schmuck's Max Hp and Physical Alignment
	public int getMaxHp(BattleState bs) {
		return getBuffedStat(0, bs) + getBuffedStat(2, bs);
	}
	
	//Max Hp scales to schmuck's Max Mp and Abstract Alignment
	public int getMaxMp(BattleState bs) {
		return getBuffedStat(1, bs) + getBuffedStat(4, bs);
	}
	
	public int getPhys(BattleState bs) {
		return getBuffedStat(2, bs);
	}
	
	public int getSpec(BattleState bs) {
		return getBuffedStat(3, bs);
	}
	
	public int getAbstr(BattleState bs) {
		return getBuffedStat(4, bs);
	}
	
	public double getPhysDamage(BattleState bs) {
		return 1+(double)(getBuffedStat(2, bs)+getBuffedStat(5, bs))/100;
	}
	
	public double getSpecDamage(BattleState bs) {
		return 1 + (double)(getBuffedStat(3, bs) + getBuffedStat(6, bs)) / 100;
	}
	
	public double getAbstrDamage(BattleState bs) {
		return 1 + (double)(getBuffedStat(4, bs) + getBuffedStat(7, bs)) / 100;
	}
	
	public double getPhysRes(BattleState bs) {
		return 1 + (double)(getBuffedStat(2, bs) + getBuffedStat(8, bs)) / 100;
	}
	
	public double getSpecRes(BattleState bs) {
		return 1 + (double)(getBuffedStat(3, bs) + getBuffedStat(9, bs)) / 100;
	}
	
	public double getAbstrRes(BattleState bs) {
		return 1 + (double)(getBuffedStat(4, bs) + getBuffedStat(10, bs)) / 100;
	}
	
	//initiative scales to user's Initiative and Special Alignment.
	public double getInit(BattleState bs) {
		return getBuffedStat(3, bs) * (1 + (double)getBuffedStat(11,bs)/100);
	}

	public int getHpRegen(BattleState bs) {
		return getBuffedStat(12, bs);
	}
	
	public int getMpRegen(BattleState bs) {
		return getBuffedStat(13, bs);
	}
	
	public double getDamageAmp(BattleState bs) {
		return (1 + (double)getBuffedStat(14,bs)/100);
	}
	
	public double getDamageRes(BattleState bs) {
		return (1 + (double)getBuffedStat(15,bs)/100);
	}
	
	public int getElemAlign(BattleState bs) {
		return getBuffedStat(16, bs);
	}
	
	public double getMpCostMod(BattleState bs) {
		return (1 + (double)getBuffedStat(17,bs)/100);
	}
	
//	0:pronoun	1:possessive	2:plural possessive
	public String getPronoun(int i) {
		switch(pronoun){
		case 0:
			switch(i){
			case 0:
				return "it";
			case 1:
				return "its";
			case 2:
				return "their";
			case 3:
				return "it";
			}
			break;
		case 1:
			switch(i){
			case 0:
				return "he";
			case 1:
				return "his";
			case 2:
				return "their";
			case 3:
				return "him";
			}
			break;
		case 2:
			switch(i){
			case 0:
				return "she";
			case 1:
				return "her";
			case 2:
				return "their";
			case 3:
				return "her";
			}
			break;
		}
		return "";
		
	}
	
	//Getters and setters for a bunch of stuff.
	
	//Returns a schmuck's battle icon
	public Texture getIcon(Application game) {
		return game.assets.get("charIcons/"+Icon);
	}
	
	//This changes a Schmuck's icon color border if they are an enemy.
	public Texture getIcon(Application game, boolean ally) {
		if(ally){
			return game.assets.get("charIcons/"+Icon);
		}
		else{
			return game.assets.get("charIcons/2"+Icon);
		}
	}

	public Texture getMenuSprite(Application game) {
		return game.assets.get(MenuSprite);
	}
	
	public Skill[] getSkills(){
		return skills;
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
	
	public ArrayList<Status> getStatuses(){
		return statuses;
	}
	
	public ArrayList<Status> getStatusesChecked(){
		return statusesChecked;
	}

	public BattleButton getButton() {
		return button;
	}

	public void setButton(BattleButton button) {
		this.button = button;
	}
}
