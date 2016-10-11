package status;

import party.Schmuck;

public class Purification extends Status{
	
	final static int id = 0;
	final static String name = "Purification";
	final static boolean perm = true;
	final static boolean vis= true;
	final static boolean end = true;
	final static boolean dec = true;

	public Purification(int duration, Schmuck p,Schmuck v) {
		super(id, duration, name, perm, vis, end, dec, p, v);
	}
	
	//Purification is a status that is checked for in the status manager.
	//Purified targets will not have their other statuses procced.
	//Purified has no other effect and is never "activated"
	
	public String inflictText(Schmuck s){
		return s.getName()+" was Purified!";
	}

	public String cureText(Schmuck s){
		return s.getName()+" is no longer Purified!";
	}
}
