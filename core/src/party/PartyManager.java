package party;

import java.util.ArrayList;

import com.grouge.Application;

public class PartyManager {

	public Application game; 
	public ArrayList<Schmuck> party;
	
	public PartyManager(Application game){
		this.game = game;
		this.party = new ArrayList<Schmuck>();
	}

	public void addSchmuck(Schmuck s){
		party.add(s);
	}
	
	public ArrayList<Schmuck> getParty() {
		return party;
	}
	
	
}
