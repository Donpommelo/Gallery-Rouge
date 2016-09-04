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
	
	public ArrayList<Schmuck> all,party, enemy, battlers;
	public ArrayList<BattleButton> team1,team2,toq, actionq, actionAllies, actionEnemy,ko;
	public BattleButton actor, opposing;
	
	public BattleQueue(BattleState bs, Application game, ArrayList<Schmuck> party, ArrayList<Schmuck> enemy){
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

		for(Schmuck s : party){
			battlers.add(s);
			all.add(s);
			initSchmuck(s,0,true);
		}
		
		for(Schmuck s : enemy){
			battlers.add(s);
			all.add(s);
			initSchmuck(s,0,false);
		}

		sortButtons(toq);
		adjustButtons();
	}
	
	public void getDelegates(){
		
		for(int i = 0; i < Math.min(bs.ActionGroupSize, toq.size()); i++){
			actionq.add(toq.get(0));
			toq.remove(0);
		}
		
		for(BattleButton b: actionq){
			if(team1.contains(b)){
				actionAllies.add(b);
			}
			else{
				actionEnemy.add(b);
			}
		}
		bs.phase = 1;
		
		if(actionAllies.isEmpty()){
			bs.bt.addScene("Enemies dominated Action Group!");
			bs.partyReady = true;
		}
		if(actionEnemy.isEmpty()){
			bs.bt.addScene("Allies dominated Action Group!");
			bs.enemyReady = true;
		}
		else{
			int rand = (int) (Math.random()*actionEnemy.size());
			opposing = actionEnemy.get(rand);
			bs.actions.add(opposing.getSchmuck().getAction(bs));
		}
		
		adjustButtons();
		
		manageFlash(actionAllies);
	}
	
	public void endofRound(){
		actionAllies.clear();
		actionEnemy.clear();
		actor = null;
		opposing = null;
		
		sortButtons(actionq);
		toq.addAll(actionq);
		actionq.clear();
		
		adjustButtons();
		
		bs.roundNum++;
		bs.phase = 0;
		bs.nextRound.setText("Round: "+bs.roundNum+" Start?");
		bs.nextRound.addAction(moveBy(0,-500,.5f,Interpolation.pow5Out));

		
		bs.em.endofTurn(bs);
	}
	
	public void adjustButtons(){
		
		for(int i = 0; i<toq.size(); i++){
			if (i < 10){
				toq.get(i).addAction(Actions.moveTo(152+64*i, bs.stage.getHeight()-256,.5f,Interpolation.pow5Out));
			}
			if (9 < i && i < 20){
				toq.get(i).addAction(Actions.moveTo(728-64*(i-10), bs.stage.getHeight()-176,.5f,Interpolation.pow5Out));
			}
			if(i>19){
				toq.get(i).addAction(Actions.moveTo(152+64*(i-20), bs.stage.getHeight()-96,.5f,Interpolation.pow5Out));	
			}
		}
		for(int i = 0; i<actionAllies.size(); i++){
			if(actionAllies.get(i) != actor){
				actionAllies.get(i).addAction(Actions.moveTo(10, 100+75*i,.5f,Interpolation.pow5Out));
			}
			else{
				actionAllies.get(i).addAction(Actions.moveTo(120, 250,.5f,Interpolation.pow5Out));
			}
		}
		
		for(int i = 0; i<actionEnemy.size(); i++){
			if(actionEnemy.get(i) != opposing){
				actionEnemy.get(i).addAction(Actions.moveTo(bs.stage.getWidth()-110, 100+75*i,.5f,Interpolation.pow5Out));
			}
			else{
				actionEnemy.get(i).addAction(Actions.moveTo(bs.stage.getWidth()-220, 250,.5f,Interpolation.pow5Out));
			}
		}
		
		if(bs.dedShowing){
			for(int i = 0; i<ko.size(); i++){
				ko.get(i).addAction(Actions.moveTo(bs.stage.getWidth()-ko.get(i).getWidth(), bs.stage.getHeight()-100*i,.5f,Interpolation.pow5Out));
			}
		}
		else{
			for(int i = 0; i<ko.size(); i++){
				ko.get(i).addAction(Actions.moveTo(bs.stage.getWidth(), bs.stage.getHeight(),.5f,Interpolation.pow5Out));
			}
		}
		
	}
	
	public void sortButtons(ArrayList<BattleButton> buttons){
		int j;
		boolean flag = true;
		BattleButton temp;
		while (flag){
			flag=false;
			for(j=0; j<buttons.size()-1; j++){
				if(buttons.get(j) != null && buttons.get(j+1) != null){
					if(buttons.get(j).getSchmuck().getBuffedStat(3, bs) < buttons.get(j+1).getSchmuck().getBuffedStat(3, bs)){
						temp = buttons.get(j);
						buttons.set(j,buttons.get(j+1));
						buttons.set(j+1,temp);
						flag = true;
					}
				}
			}
		}
		
//		return buttons;
	}
	
	public ArrayList<BattleButton> getAllyTeam(Schmuck s){
		if(team1.contains(s.getButton())){
			return team1;
		}
		else{
			return team2;
		}
	}
	
	public ArrayList<BattleButton> getEnemyTeam(Schmuck s){
		if(team1.contains(s.getButton())){
			return team2;
		}
		else{
			return team1;
		}
	}
	
	public BattleButton getOpposingActor(Schmuck s){
		if(team1.contains(s.getButton())){
			return opposing;
		}
		else{
			return actor;
		}
	}
	
	public void initSchmuck(Schmuck s, int pos, Boolean ally){
		
		s.addIntrinsics();
		
		s.setButton(new BattleButton(s,game, bs,ally));
		
		s.getButton().initButton();
		bs.stage.addActor(s.getButton());

		if(ally){
			team1.add(s.getButton());
		}
		else{
			team2.add(s.getButton());
		}
//		all.add(s);
		toq.add(pos,s.getButton());
	}
	
	public void removeSchmuck(Schmuck s){
		s.getButton().remove();
		
		team1.remove(s.getButton());
		team2.remove(s.getButton());
		
		toq.remove(s.getButton());
		actionq.remove(s.getButton());
		ko.remove(s.getButton());
	}
	
	public void manageFlash(ArrayList<BattleButton> bb){
		for(BattleButton b : team1){
			if(bb.contains(b)){
				b.setFlashing(true);
			}
			else{
				b.setFlashing(false);
			}
			b.flashInterval = 0;
			b.flash = false;
		}
		for(BattleButton b : team2){
			if(bb.contains(b)){
				b.setFlashing(true);
			}
			else{
				b.setFlashing(false);
			}
			b.flashInterval = 0;
			b.flash = false;
		}
	}

}
