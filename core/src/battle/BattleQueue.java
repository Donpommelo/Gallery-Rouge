package battle;

import java.util.ArrayList;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.grouge.Application;

import party.Schmuck;
import states.BattleState;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class BattleQueue {

	Application game;
	BattleState bs;
	
	/*List of all schmuck groups
	 * all: Every schmuck that is currently in the fight. This is the only updated schmuck list and is used for
	 *  status processing. All others represent starting groups.
	 *  EXTRA NOTE: "all" IS ONLY UPDATED AT THE END OF ROUNDS IN THE STATUSMANAGER FUNCTION: adjustAll(). tHIS IS TO PREVENT
	 *  WEIRD STUFF FROM HAPPENING. THIS ALSO MEANS THAT NEWLY SUMMONED SCHMUCKS WILL NOT HAVE THEIR STATUSES PROCCED. 
	 * party: Every starting ally schmuck.
	 * enemy: Every starting enemy schmuck
	 * battlers: Every starting schmuck.
	 */
	public ArrayList<Schmuck> all, party, enemy, battlers;
	
	/*List of all BattleButton groups
	 * team1/2: List of current ally/enemy buttons.
	 * toq: Buttons currently in the Turn Order Queue.
	 * actionq: Buttons currently in the Action Group.
	 * actionAllies: actionq Buttons that are allies.
	 * actionEnemy: action Buttons that are enemies.
	 * ko: Incapacitated, inactive buttons.
	 */
	public ArrayList<BattleButton> team1, team2, toq, actionq, actionAllies, actionEnemy, ko;
	
	/*
	 * actor,opposing: The two schmucks selected to perform actions.
	 */
	public BattleButton actor, opposing;
	
	public BattleQueue(BattleState bs, Application game, ArrayList<Schmuck> party, ArrayList<Schmuck> enemy) {
		this.game = game;
		this.bs = bs;
		this.party = party;
		this.enemy = enemy;
		
		this.battlers = new ArrayList<Schmuck>();
		this.all = new ArrayList<Schmuck>();
		this.team1= new ArrayList<BattleButton>();
		this.team2 = new ArrayList<BattleButton>();

		this.toq = new ArrayList<BattleButton>();
		this.actionq = new ArrayList<BattleButton>();
		this.actionAllies = new ArrayList<BattleButton>();
		this.actionEnemy = new ArrayList<BattleButton>();
		this.ko = new ArrayList<BattleButton>();

		//Initialize all starting allies and enemies.
		for (Schmuck s : party) {
			battlers.add(s);
			all.add(s);
			initSchmuck(s,0,true);
		}
		
		for (Schmuck s : enemy) {
			battlers.add(s);
			all.add(s);
			initSchmuck(s,0,false);
		}

		sortButtons(toq);
		adjustButtons();
	}
	
	//Run when player presses "Round: x" button.
	//Begins schmuck selection phase and creates new Action Group.
	public void getDelegates() {
		
		//Move first schmucks in toq to the Action Group
		for (int i = 0; i < Math.min(bs.ActionGroupSize, toq.size()); i++) {
			actionq.add(toq.get(0));
			toq.remove(0);
		}
		
		//Divide action group into allies and enemies.
		for(BattleButton b: actionq){
			if(team1.contains(b)){
				actionAllies.add(b);
			}
			else{
				actionEnemy.add(b);
			}
		}
		
		//Advance phase to phase 1: schmuck and ability selection phase.
		bs.phase = 1;
		
		//Check for action group domination (if either team has no representation in group)
		//If so, dominated group is automatically ready to make their nonexistant action.
		if(actionAllies.isEmpty()){
			bs.bt.addScene("Enemies dominated Action Group!", true);
			bs.partyReady = true;
		}
		if(actionEnemy.isEmpty()){
			bs.bt.addScene("Allies dominated Action Group!", true);
			bs.enemyReady = true;
		}
		
		//Move buttons to new positions and make selectable schmucks flash
		adjustButtons();
		manageFlash(actionAllies);
	}
	
	//Run at the end of round after both actors finish performing their actions.
	public void endofRound() {
		
		//Empty Action group and re-add to Turn Order Queue.
		actionAllies.clear();
		actionEnemy.clear();
		actor = null;
		opposing = null;
		
		sortButtons(actionq);
		toq.addAll(actionq);
		actionq.clear();
		
		//Move all buttons to new positions (back to toq)
		adjustButtons();
		
		//Advance round number and move back to pre-selection phase.
		bs.roundNum++;
		bs.phase = 0;
		
		//Next round button update and move to position
		bs.nextRound.setText("Round: "+bs.roundNum+" Start?");
		bs.nextRound.addAction(moveBy(0,-500,.5f,Interpolation.pow5Out));

		//Run all end of turn effects.
		bs.em.endofTurn(bs);
	}
	
	//Called frequently whenever buttons are moved from one group to another (toq, action group, ko, actors)
	public void adjustButtons() {
		
		//Move all buttons in Turn Order Queue into position.
		for (int i = 0; i < toq.size(); i++) {
			if (i < 10) {
				toq.get(i).addAction(Actions.moveTo(152 + 64 * i, bs.stage.getHeight() - 256, .5f, 
						Interpolation.pow5Out));
			}
			if (9 < i && i < 20) {
				toq.get(i).addAction(Actions.moveTo(728 - 64 * (i - 10), bs.stage.getHeight() - 176, .5f, 
						Interpolation.pow5Out));
			}
			if (i > 19) {
				toq.get(i).addAction(Actions.moveTo(152 + 64 * (i - 20), bs.stage.getHeight() - 96,.5f, 
						Interpolation.pow5Out));	
			}
		}
		
		//Move action group into position
		for (int i = 0; i < actionAllies.size(); i++) {
			if (actionAllies.get(i) != actor) {
				actionAllies.get(i).addAction(Actions.moveTo(10, 100 + 75 * i, .5f, Interpolation.pow5Out));
			} else {
				actionAllies.get(i).addAction(Actions.moveTo(120, 250, .5f, Interpolation.pow5Out));
			}
		}
		
		for (int i = 0; i < actionEnemy.size(); i++) {
			if (actionEnemy.get(i) != opposing) {
				actionEnemy.get(i).addAction(Actions.moveTo(bs.stage.getWidth() - 110, 100 + 75 * i, .5f,  
						Interpolation.pow5Out));
			} else {
				actionEnemy.get(i).addAction(Actions.moveTo(bs.stage.getWidth() - 220, 250, .5f, Interpolation.pow5Out));
			}
		}
		
		//Move ko'd chacters into position.
		if (bs.dedShowing) {
			for (int i = 0; i < ko.size(); i++) {
				ko.get(i).addAction(Actions.moveTo(bs.stage.getWidth() - ko.get(i).getWidth() - 100, 
						bs.stage.getHeight() - 100 * i - 100, .5f, Interpolation.pow5Out));
			}
		} else {
			for (int i = 0; i < ko.size(); i++) {
				ko.get(i).addAction(Actions.moveTo(bs.stage.getWidth(), bs.stage.getHeight(), .5f,Interpolation.pow5Out));
			}
		}
	}
	
	//Sort a list of buttons in order of initiative initiative.
	public void sortButtons (ArrayList<BattleButton> buttons) {
		int j;
		boolean flag = true;
		BattleButton temp;
		while (flag) {
			flag = false;
			for (j = 0; j < buttons.size() - 1; j++) {
				if (buttons.get(j) != null && buttons.get(j + 1) != null) {
					if (buttons.get(j).getSchmuck().getBuffedStat(3, bs) < buttons.get(j + 1).getSchmuck().getBuffedStat(3, bs)) {
						temp = buttons.get(j);
						buttons.set(j, buttons.get(j + 1));
						buttons.set(j+1, temp);
						flag = true;
					}
				}
			}
		}
	}
	
	//Return the ally or enemy team of an input schmuck
	//Useful for area-of-effect or aura stuff.
	public ArrayList<BattleButton> getAllyTeam(Schmuck s) {
		if (team1.contains(s.getButton())) {
			return team1;
		} else {
			return team2;
		}
	}
	
	public ArrayList<BattleButton> getEnemyTeam(Schmuck s) {
		if (team1.contains(s.getButton())) {
			return team2;
		} else {
			return team1;
		}
	}
	
	//Get the actor representing the opposite team of the input schmuck.
	public BattleButton getOpposingActor(Schmuck s) {
		if (team1.contains(s.getButton())) {
			return opposing;
		} else {
			return actor;
		}
	}
	
	//Initiate a schmuck that is newly added to the battle.
	public void initSchmuck(Schmuck s, int pos, Boolean ally) {
				
		//Give the new schmuck all of its intrinsic statuses
		s.addIntrinsics();
		
		//Connect button with schmuck. Initiate and add to stage.
		s.setButton(new BattleButton(s,game, bs,ally));
		s.getButton().initButton();
		bs.stage.addActor(s.getButton());

		//Select team and add to Turn Order Queue.
		if (ally) {
			team1.add(s.getButton());
		} else {
			team2.add(s.getButton());
		}
		
		toq.add(pos,s.getButton());
	}
	
	//Remove a schmuck from the battle. Used when temporary summons are incapacitated.
	public void removeSchmuck(Schmuck s) {
		s.getButton().remove();
				
		team1.remove(s.getButton());
		team2.remove(s.getButton());
		
		toq.remove(s.getButton());
		actionq.remove(s.getButton());
		actionAllies.remove(s.getButton());
		actionEnemy.remove(s.getButton());
		ko.remove(s.getButton());
	}
	
	//manageFlash manages flash.
	//All buttons in the input bb should flash after this is run.
	//Run with an empty list to make all buttons stop flashing
	public void manageFlash(ArrayList<BattleButton> bb) {
		for (BattleButton b : team1) {
			if (bb.contains(b)) {
				b.setFlashing(true);
			} else {
				b.setFlashing(false);
			}
			b.flashInterval = 0;
			b.flashSpeed = 10;
			b.flash = false;
			b.permaFlash = true;
		}
		for (BattleButton b : team2) {
			if (bb.contains(b)) {
				b.setFlashing(true);
			} else {
				b.setFlashing(false);
			}
			b.flashInterval = 0;
			b.flashSpeed = 10;
			b.flash = false;
			b.permaFlash = true;
		}
	}
	
	//Manages flashing for single, temporary buttons
	public void manageFlash(BattleButton b, int dura) {
		b.setFlashing(true);
		b.flashInterval = 0;
		b.flashSpeed = 5;
		b.flash = false;
		b.permaFlash = false;
		b.flashDuration = dura;
	}

}
