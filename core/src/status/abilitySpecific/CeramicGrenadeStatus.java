package status.abilitySpecific;

import party.Schmuck;
import status.Status;

public class CeramicGrenadeStatus extends Status{
	
	final static int id = 2;
	final static String name = "Ceramic Grenade Planted";
	final static int dura = 1;
	final static boolean perm = false;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = false;

	int index;
	
	public CeramicGrenadeStatus(int index,Schmuck p,Schmuck v) {
		super(id, dura, name, perm, vis, end, dec, p, v);
		this.index = index;
	}

	public int getExtraVar(){
		return index;
	}
	
	public String inflictText(Schmuck s){
		return "A Ceramic Grenade was planted in the battlefield!";
	}

	public String cureText(Schmuck s){
		return "A Ceramic Grenade was removed from the battlefield!";
	}
}
