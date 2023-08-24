package SGCipher;

public class SGCipherTestDriver {
	public static final String path = "C:\\Users\\bensc\\OneDrive\\Desktop\\The Great Fleet\\DITV Part 2\\ADMIRAL'S TABLE\\";
	public static final String interiorpath = "C:\\Users\\bensc\\IdeaProjects\\StrikeGroupCipher\\src\\savedata\\";
	public static void main (String[] s) {
			NotesDocReader.readSGTextFile("callsign_log",System.getProperty("user.dir").toString()+"\\src\\savedata\\");
			try {
				NotesDocReader.editMoveOrderDoc("WSC5_MOVE_ORDERS_11",true,path,interiorpath);
				System.out.println(NotesDocReader.getTFList());
			}catch(Exception e){
				System.out.println(e+" in main");
			}
	}
	
	
}
