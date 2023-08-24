package SGCipher;

public class TaskGroup {
	private String name;
	private String mapcode;
	private int type; //1: GENERAL 2: STRIKE 3: TRADE 4: RESERVE 5: KZINTI 6: MERCENARY
	private String callsign;
	
	public TaskGroup(String name, String mapcode, int type) {
		this.name = name;
		this.mapcode = mapcode;
		this.type = type;
		if (this.type<1||this.type>6)
			this.type = 1;
	}
	public TaskGroup(String name, String mapcode) {
		this.name = name;
		this.mapcode = mapcode;
		this.type = 1;
	}

	public TaskGroup(String name, String mapcode, int type, String callsign){
		this.name = name;
		this.mapcode = mapcode;
		this.type = type;
		if (this.type<1||this.type>6)
			this.type = 1;
		this.callsign = callsign;
	}
	
	public String name() {
		return name;
	}
	
	public int type() {
		return type;
	}
	
	public String mapcode() {
		return mapcode;
	}
	
	public String callsign() {
		return callsign;
	}
	
	public void setCallsign(String s) {
		callsign = s;
	}
	
}
