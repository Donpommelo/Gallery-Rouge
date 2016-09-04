package mission;

import java.util.ArrayList;

import com.grouge.Application;

import states.MapState;

public class MissionManager {

	public Application game;
	public MapState ms;
	
	public Mission[] missions;
	
	public MissionManager(Application game, MapState ms){
		this.game = game;
		this.ms = ms;
		initMissions();
	}
	
	public ArrayList<Mission> initMissions(){
		
		ArrayList<Mission> visMiss = new ArrayList<Mission>();
		
		missions = new Mission[1];
		
		missions[0] = new Mission0(game, ms);
		
		
		for(int i=0; i < missions.length; i++){
			visMiss.add(missions[i]);
		}
		
		return visMiss;
	}
		
}
