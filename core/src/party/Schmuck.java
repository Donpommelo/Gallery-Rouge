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
	//17: Mp Cost Modifier
	//18: 

	public int[] tempStats = {0,0};
	public Skill[] skills;
	
	public String name, bioShort,bioLong;
	
	public ArrayList<Status> statuses, statusesChecked;
	
	BattleButton button;
	
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
		this.statusesChecked = new ArrayList<Status>();
		
		tempStats[0] = getMaxHp(null);
		tempStats[1] = getMaxMp(null);
	}
	
	public Schmuck() {
		this.statuses = new ArrayList<Status>();
		this.statusesChecked = new ArrayList<Status>();
	}

	public BattleAction getAction(BattleState bs){
		return new BattleAction(this,new ArrayList<Schmuck>(),new StandardAttack());
	}
	
	public void addIntrinsics(){}
	
	public int getCurrentHp(){
		return tempStats[0];
	}
	
	public int getCurrentMp(){
		return tempStats[1];
	}
	
	public void hpChange(BattleState bs, Schmuck perp, int hp){
		tempStats[0] += hp;
		if(tempStats[0] > getMaxHp(bs)){
			tempStats[0] = getMaxHp(bs);
		}
		
		if(tempStats[0] <= 0){
			tempStats[0] = 0;
			bs.stm.addStatus(perp,this, new Incapacitation(perp,this));
		}
	}
	
	public void mpChange(BattleState bs, Schmuck perp, int mp){
		if(bs.stm.checkStatus(this, new Insanity(perp,this))){
			hpChange(bs,perp,mp);
		}
		else{
			tempStats[1] += mp;
			if(tempStats[1] > getMaxMp(bs)){
				tempStats[1] = getMaxMp(bs);
			}
			
			if(tempStats[1] <= 0){
				tempStats[1] = 0;
				bs.stm.addStatus(perp,this, new Insanity(perp,this));
			}
		}
	}
	
	public int getBaseStat(int statNum){
		return baseStats[statNum];
	}
	
	public int getBuffedStat(int statNum, BattleState bs){
		
		//Testing thing
		if(statNum == 3){
			return (int) (Math.random()*20);
		}
		
		int stat = getBaseStat(statNum);
		
		if(bs != null){
			for(Schmuck s : bs.battlers){
				stat = bs.stm.statusProcTime(12, bs, null, s, this, this, stat, statNum, true, null);
			}
			stat = bs.stm.statusProcTime(12, bs, null, bs.fieldDummy, this, this, stat, statNum, true, null);
		}
		else{
			for(Status st : statuses){
				stat = st.statChange(stat, statNum);
			}
		}
		
		return stat;
	}
	
	public int getMaxHp(BattleState bs){
		return getBuffedStat(0, bs) + getBuffedStat(2, bs);
	}
	
	public int getMaxMp(BattleState bs){
		return getBuffedStat(1, bs) + getBuffedStat(4, bs);
	}
	
	public int getPhys(BattleState bs){
		return getBuffedStat(2, bs);
	}
	
	public int getSpec(BattleState bs){
		return getBuffedStat(3, bs);
	}
	
	public int getAbstr(BattleState bs){
		return getBuffedStat(4, bs);
	}
	
	public double getPhysDamage(BattleState bs){
		return 1+(double)(getBuffedStat(2, bs)+getBuffedStat(5, bs))/100;
	}
	
	public double getSpecDamage(BattleState bs){
		return 1+(double)(getBuffedStat(3, bs)+getBuffedStat(6, bs))/100;
	}
	
	public double getAbstrDamage(BattleState bs){
		return 1+(double)(getBuffedStat(4, bs)+getBuffedStat(7, bs))/100;
	}
	
	public double getPhysRes(BattleState bs){
		return 1+(double)(getBuffedStat(2, bs)+getBuffedStat(8, bs))/100;
	}
	
	public double getSpecRes(BattleState bs){
		return 1+(double)(getBuffedStat(3, bs)+getBuffedStat(9, bs))/100;
	}
	
	public double getAbstrRes(BattleState bs){
		return 1+(double)(getBuffedStat(4, bs)+getBuffedStat(10, bs))/100;
	}
	
	public double getInit(BattleState bs){
		return getBuffedStat(3, bs) * (1 + (double)getBuffedStat(11,bs)/100);
	}

	public int getHpRegen(BattleState bs){
		return getBuffedStat(12, bs);
	}
	
	public int getMpRegen(BattleState bs){
		return getBuffedStat(13, bs);
	}
	
	public double getDamageAmp(BattleState bs){
		return (1 + (double)getBuffedStat(14,bs)/100);
	}
	
	public double getDamageRes(BattleState bs){
		return (1 + (double)getBuffedStat(15,bs)/100);
	}
	
	public int getElemAlign(BattleState bs){
		return getBuffedStat(16, bs);
	}
	
	public double getMpCostMod(BattleState bs){
		return (1 + (double)getBuffedStat(17,bs)/100);
	}
	
	public Texture getIcon(Application game) {
		return game.assets.get(Icon);
	}
	
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
