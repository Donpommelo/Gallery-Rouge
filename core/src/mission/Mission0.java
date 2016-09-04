package mission;

import java.util.ArrayList;

import com.grouge.Application;

import party.Schmuck;
import states.MapState;

public class Mission0 extends Mission{

	
	public static final int x = 0;
	public static final int y = 0;
	public static final int cc = 3;
	public static final String name = "temp";
	public static ArrayList<Schmuck> enemy;
	
	public Mission0(Application game, MapState ms) {
		super(game, ms, cc, x, y, name,enemy);
	}

	public void initiateMission(){
		
	}
}
