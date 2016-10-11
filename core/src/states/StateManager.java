package states;

import java.util.HashMap;

import com.grouge.Application;

public class StateManager {
 
   public final Application game;
   private HashMap<STATE, State> gameScreens;
   
   public enum STATE {
	   TITLE,
	   LOADING,
	   CUTSCENE,
	   BATTLE,
	   MAP,
   }
   
   public StateManager(final Application game) {
	   this.game = game;
	   
	   initGameScreens();
	   setScreen(STATE.LOADING);
   }
   
   public void initGameScreens() {
	   this.gameScreens = new HashMap<STATE,State>();
	   this.gameScreens.put(STATE.TITLE, new TitleState(game));
	   this.gameScreens.put(STATE.LOADING, new LoadingState(game));
	   this.gameScreens.put(STATE.CUTSCENE, new CutsceneState(game));
	   this.gameScreens.put(STATE.BATTLE, new BattleState(game));
	   this.gameScreens.put(STATE.MAP, new MapState(game));
   }
   
   public void setScreen(STATE nextScreen) {
	   game.setScreen(gameScreens.get(nextScreen));
   }
   
   public void dispose() {
	   for (State s : gameScreens.values()) {
		   if (s != null) {
			   s.dispose();
		   }
	   }
   }
   
   public BattleState getBattleState() {
	   return (BattleState) gameScreens.get(STATE.BATTLE);
   }

   public CutsceneState getCutsceneScreen() {
		return (CutsceneState) gameScreens.get(STATE.CUTSCENE);
   }
   
}