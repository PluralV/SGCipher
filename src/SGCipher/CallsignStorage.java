package SGCipher;

import java.util.*;
import java.io.*;
public class CallsignStorage {
	//General
	private static List<String> TYPE_1_CALLSIGNS = new ArrayList<>(
			Arrays.asList(

					"ABAYA","AINU","AKAN","AKSUM","ALDAN","ALNEY","ALPINI","AMGUN","AMUR",
					"ANADYR","ATHOS","ATLANT","AWASA",

					"BALDUR","BENGAL","BERING","BLACKBIRD",

					"CARDINAL","CASTLE","CHANTAL","CHRYSANTH","CORMORANT","CRANE","CYPRIOT",

					"DAGUR","DARIAL","DAWUD","DIMASHQ","DRAGON","DRENGR",

					"EAGLE","ELBASAN","EMISHI","EVERGREEN",

					"FALCON",

					"HANGMAN","HERON","HITTITE","HUANGDI",

					"IBIS","ISFAHAN","IZUMO",

					"JACKAL","JOMON","JURCHEN","JVARI",

					"KALAHARI","KASTRIOT","KETINEN","KHANBALIQ","KHITAN","KORYAK","KUNASHIR","KURIL",

					"LACEWING","LADOGA",

					"MALIQ","MANCHU","MONSOON",

					"NOMAD",

					"OASIS","ONRYO","OROCHI",

					"PALADIN","PASHA","PEREGRINE","PRIVATEER",

					"RENEGADE","RHONE","RONIN",

					"SAGITTAR","SAKHA","SALJUQ","SAXON","SCYTHIAN","SHILKA","SOHEI","SPARROW","SULTAN",

					"TAIRA","TATAR","TIMUR","TORNE","TSUNAMI","TSUSHIMA","TYPHOON",

					"URAGAN","URAL",

					"VIIPURI","VULTURE",

					"WARSAW","WYVERN",

					"YUGE","YAKUT","YABAN",

					"ZHAYEDAN","ZULU"

					 ));
	//Strike group
	private static List<String> TYPE_2_CALLSIGNS = new ArrayList<>(
			Arrays.asList(
					"ALEUT","ALTAY",

					"CARNATION",

					"GOLYAT",

					"KAVKAZ","KHAGAN",

					"MEDJAY",

					"OCEAN",

					"PRAETOR",

					"SHIMANTO","SHIVA","SINAI","SOLAR",

					"VARYAG","VITYAZ",

					"YENISEI","YANYCHAR"

			));
	//Zin
	private static List<String> TYPE_3_CALLSIGNS = new ArrayList<>(
			Arrays.asList(
					"AELFRED","AETHELRED","ALLOY",

					"BEOW","BRITON",

					"CALVIN","CHANNEL","CHARLES",

					"DOVER",

					"EGRET","ELY",

					"FERRIS",

					"GIANT","GLOWECESTRE",

					"HADRIAN","HALFDAN","HASTINGS","HENRY","HOOD",

					"OCTAVIAN","OTHELLO",

					"PALANQUIN",

					"ROMAN",

					"SEALION","SEAX","STRAIT",

					"THRONE","TIDEWALL","TRAFALGAR",

					"WHITECLIFF","WINCESTRE"

					));
	


	public CallsignStorage(String interiorpath){
		String normal_file = interiorpath+"callsigns_normal.txt";
		String strike_file = interiorpath+"callsigns_sg.txt";
		String zin_file = interiorpath+"callsigns_zin.txt";
		TYPE_1_CALLSIGNS = readCallsignsFile(normal_file);
		TYPE_2_CALLSIGNS = readCallsignsFile(strike_file);
		TYPE_3_CALLSIGNS = readCallsignsFile(zin_file);
	}

	public CallsignStorage(String filetype1, String filetype2, String filetype3){
		LinkedList<String> callsigns_1 = readCallsignsFile(filetype1);
		TYPE_1_CALLSIGNS = callsigns_1;
		LinkedList<String> callsigns_2 = readCallsignsFile(filetype2);
		TYPE_1_CALLSIGNS = callsigns_2;
		LinkedList<String> callsigns_3 = readCallsignsFile(filetype3);
		TYPE_1_CALLSIGNS = callsigns_3;

	}

	private void importCallsignFile(int type, String file){
		LinkedList<String> non_native_callsigns = readCallsignsFile(file);
		if (type == 1) TYPE_1_CALLSIGNS = non_native_callsigns;
		else if (type == 2) TYPE_2_CALLSIGNS = non_native_callsigns;
		else if (type == 3) TYPE_3_CALLSIGNS = non_native_callsigns;
	}

	private LinkedList<String> readCallsignsFile(String filename){
		File callsignfile = new File(filename);
		StringBuilder csv = new StringBuilder();
		try{
			Scanner sc = new Scanner(callsignfile);
			while (sc.hasNextLine()) csv.append(sc.nextLine());
		}catch(Exception e) {
			System.out.println("Invalid file entered.");
		}
		String pre_list = trim(csv.toString());
		return new LinkedList<>(Arrays.asList(pre_list.split(",")));
	}

	private String trim (String s){
		s = removeChar(s," ");
		s = removeChar(s,"\n");
		return removeChar(s,"\t");
	}

	private String removeChar(String s,String remove){
		while (s.contains(remove)){
			int k = s.indexOf(remove);
			s = s.substring(0,k)+s.substring(k+1);
		}
		return s;
	}
	
	public void AssignAllCallSigns(TFList tflist) {
		for (TaskGroup tf : tflist.list) {
			if (tf.type()==2)
				tf.setCallsign(TYPE_2_CALLSIGNS.remove((int)(Math.random()* TYPE_2_CALLSIGNS.size())));
			else if (tf.type()==5)
				tf.setCallsign(TYPE_3_CALLSIGNS.remove((int)(Math.random()* TYPE_3_CALLSIGNS.size())));
			else 
				tf.setCallsign(TYPE_1_CALLSIGNS.remove((int)(Math.random()* TYPE_1_CALLSIGNS.size())));
			
		}
	}
}
