package initialtest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Read_and_Create {
	
	static ArrayList<String> filePaths = new ArrayList<String>();
	static ArrayList<String> input = new ArrayList<String>();
	static ArrayList<String> lastTwo = new ArrayList<String>();
	static File[] files = new File("C:\\Automation\\CGSTestData\\").listFiles();
	 
	public static void main(String[] args) throws IOException{
		
		    showFiles(files);
		    //readAndCreate(filePaths);
	}
		
	/*
	 * Drill down to specific files and create arrayList to store file paths
	 */
	public static void showFiles(File[] files) throws IOException {
	    for (File singleFile : files) {
	        if (singleFile.isDirectory() && !(singleFile.getName().contains("CVU"))) {
	            showFiles(singleFile.listFiles()); // Calls same method again.
	        } 
	        else {
	        	String fileName = singleFile.getAbsolutePath();
	        	if(singleFile.getName().contains("CVU")){
	        		break;
	        	}
	        	else{
	        	String extension = fileName.substring(fileName.lastIndexOf("."), fileName.length());
	        		if((extension.equals(".in") || extension.equals(".out") || extension.equals(".txt")) && !(fileName.toLowerCase().contains("edit") || 
	        			fileName.toLowerCase().contains("report") || fileName.toLowerCase().contains("error") || extension.equals("rule") || extension.equals("dic"))) {
	        		filePaths.add(singleFile.getAbsolutePath());
	        		}
	        	}
	        }
	    }
	    readAndCreate(filePaths);
	}
	
	/*
	 * Read file and get last two lines
	 */
	public static void readAndCreate(ArrayList<String> in) throws IOException{
		for(String file: filePaths){
			BufferedReader br = new BufferedReader(new FileReader(file));
			String filePath = file.substring(0, file.lastIndexOf("\\"));
			String fileNameOnly = file.substring((file.lastIndexOf("\\"))+1, file.length());
			String inLine = "";
			while((inLine = br.readLine()) != null){
				input.add(inLine);
			}
			br.close();
			//get last two lines
			if(input.size() == 1){
				lastTwo.add(input.get(input.size()-1));
			}
			else {
				lastTwo.add(input.get(input.size()-2));
				lastTwo.add(input.get(input.size()-1));
			}	
			input.clear();
			//call writeToFile to print
			writeToFile(file, filePath, fileNameOnly);
		}
		filePaths.clear();
		
	}	
	
	/*
	 * Write created arrayList 'lastTwo' to file
	 */
	public static void writeToFile(String fileName, String filePath, String fileNameOnly) throws FileNotFoundException{
		String outFilePathAndName = filePath + "\\CVU\\" +fileNameOnly;
		String outFilePathDir = filePath + "\\CVU\\";
		File file = new File(outFilePathAndName);
		File theDir = new File(outFilePathDir);
		theDir.mkdir();
		PrintWriter pw = new PrintWriter(file);
		for(String s: lastTwo){
			pw.println(s);
		}
		lastTwo.clear();
		pw.close();
	}
}
