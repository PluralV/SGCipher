package SGCipher;

import java.util.ArrayList;

public class TFList {
	public ArrayList<TaskGroup> list = new ArrayList<>();
	public String toStringLog(){
		String listformat = "";
		for (TaskGroup t : list){
			listformat = listformat + t.name()+","+t.mapcode()+","+t.type()+","+t.callsign()+"\n";
		}
		return listformat;
	}
	public String toString() {
		String listformat = "";
		for (TaskGroup t : list) {
			if (t.type()==6)
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (MERCENARY): callsign "+t.callsign()+"\n";
			else if (t.type()==2)
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (STRIKE GROUP): callsign "+t.callsign()+"\n";
			else if (t.type()==3)
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (CONVOY): callsign "+t.callsign()+"\n";
			else if (t.type()==4)
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (RESERVE SQUADRON): callsign "+t.callsign()+"\n";
			else if (t.type()==5)
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (KZINTI SQUADRON): callsign "+t.callsign()+"\n";
			else
				listformat = listformat + t.name() + " <" +t.mapcode() + ">" + " (NAVAL GROUP): callsign "+t.callsign()+"\n";
		}
		return listformat;
	}
}
