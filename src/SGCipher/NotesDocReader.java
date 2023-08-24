package SGCipher;

import javax.swing.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NotesDocReader {
	private static TFList tflistholder;
	private static final String extension = ".txt";

	public static void readSGTextFile(String filename, String interiorpath){
		
		File file = new File(interiorpath+filename+extension);
		Scanner sc;
		try {
			sc = new Scanner(file);
			TFList tflist = new TFList();
			while (sc.hasNextLine()) {
				String taskGroupLine = sc.nextLine();
				if (!taskGroupLine.contentEquals("")) {
					int index1 = taskGroupLine.indexOf(",");
					String nameLine = taskGroupLine.substring(0, index1);
					index1++;
					int index2 = index1 + taskGroupLine.substring(index1).indexOf(",");
					String mapCodeLine = taskGroupLine.substring(index1, index2);
					int type = Integer.parseInt(taskGroupLine.substring(index2 + 1));
					tflist.list.add(new TaskGroup(nameLine, mapCodeLine, type));
				}
			}
			tflistholder = tflist;
		}catch (FileNotFoundException ex){
			JOptionPane.showMessageDialog(new JFrame(),"Invalid file entered!");
		}


	}
	
	public static void editMoveOrderDoc(String filename, boolean firstImpulse, String path, String interiorpath) throws Exception{
			filename = path+filename;
			if (firstImpulse){
				new CallsignStorage(System.getProperty("user.dir")+"\\src\\media\\").AssignAllCallSigns(tflistholder);
				writeSGTextFile(tflistholder.toStringLog(),interiorpath);
			}
			else{
				try{
					readLog(interiorpath);
				}
				catch(Exception e){
					return;
				}
			}
			replaceNamesWithCallsigns(filename);
	}

	public static void writeSGTextFile(String text, String interiorpath){
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(interiorpath+"log"+extension);
			fos.write(text.getBytes(StandardCharsets.UTF_8));
		}catch(Exception e) {
			close(fos);
		}
	}

	public static void readLog(String interiorpath){
		File file = new File(interiorpath+"log"+extension);
		Scanner sc;
		try{
			sc = new Scanner(file);
			TFList tflist = new TFList();
			while (sc.hasNextLine()) {
				String taskGroupLine = sc.nextLine();
				int index1 = taskGroupLine.indexOf(",");
				String nameLine = taskGroupLine.substring(0,index1);
				index1++;
				int index2 = index1+taskGroupLine.substring(index1).indexOf(",");
				String mapCodeLine = taskGroupLine.substring(index1,index2);
				int type = Integer.parseInt(taskGroupLine.substring(index2+1,index2+2));
				String callsign = taskGroupLine.substring(index2+3);
				tflist.list.add(new TaskGroup(nameLine,mapCodeLine,type,callsign));
			}
			tflistholder = tflist;
		}catch(Exception ex){
			JOptionPane.showMessageDialog(new JFrame(),"You don't have old codenames to use!\nCreate a set of new codenames" +
					" first.");
		}
	}
	
	public static void replaceNamesWithCallsigns(String filename) throws Exception {
		String sgtextorderfile = readMoveOrderFile(filename+extension);
		for (TaskGroup t : tflistholder.list) {
			int indexOfName = sgtextorderfile.indexOf(t.name());
			if (indexOfName>=0){
				String before = sgtextorderfile.substring(0,indexOfName);
				String remainder = sgtextorderfile.substring(indexOfName+t.name().length());
				sgtextorderfile = before + t.callsign() + remainder;
			}
		}
		try{
			writeMoveOrderFile(filename,sgtextorderfile);
		}catch(Exception e) {
			
		}
	}
	
	public static String readMoveOrderFile(String filename) throws Exception {
		File orderFile = new File(filename);
		int length = (int)(orderFile.length());
		byte[] bytes = new byte[length];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(orderFile);
			fis.read(bytes);
		}catch(Exception e) {
			close(fis);
			throw e;
		}
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	public static void writeMoveOrderFile(String filename,String text) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(filename+"_real"+extension);
			fos.write(text.getBytes(StandardCharsets.UTF_8));
		}catch(Exception e) {
			close(fos);
		}
	}
	
	public static void close(Closeable closeable) {
		try {
			closeable.close();
		}catch(Exception e) {
			
		}
	}
	
	public static TFList getTFList() {
		return tflistholder;
	}
}
