package cmgplus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/*
 * This utility reads in the Clinical Research delivered test data and converts it into usable CGS format.
 * They provide us with an testing document that shows how their data was created and formatted.
 * cmgconfig.java takes in every possible value from the data they've provided and then writes the necessary values
 * out to an output file.
 * 
 * Update the file names and 'typeOfFileToConvert' parameter to convert either the input file or expected.
 * Also, update the grouperVersion variable, this is hard-coded given that Clinical Research most likely assumes this value and
 * it's not provided to us.
 */
public class cmg {
	
	static Date date = new Date();
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	
	//converted input file
	public static File outFile = new File("C:\\Users\\a6610zz\\Desktop\\CATesting\\CMG\\Converted\\CMG+_CACS_2017_CIHI_Supplied_Test_File_V01_00_" +dateFormat.format(date) +".txt");
	//input file to convert
	public static File inFile = new File("C:\\Users\\a6610zz\\Desktop\\CATesting\\CMG\\Original\\CMG+_CACS_2017_CIHI_Supplied_Test_File_V01_00.txt");
	//converted expected
	public static File expectedOutFile = new File("C:\\Users\\a6610zz\\Desktop\\CATesting\\CMG\\Converted\\CMG+_CACS_2017_CIHI_Supplied_Test_File_v01_00_" +dateFormat.format(date) +".out.txt");
	//expected file to convert
	public static File expectedInFile = new File("C:\\Users\\a6610zz\\Desktop\\CATesting\\CMG\\Original\\CMG+_CACS_2017_CIHI_Supplied_Test_File_v01_00.out.txt");
	//declare what you want to convert, input or expected CMG+_CACS_2017_CIHI_Supplied_Test_File v01_00.out
	public static String typeOfFileToConvert = "expected";
	
	public static String grouperVersion = "39100";
	public static ArrayList<String> output;
	public static ArrayList<String> outputExp;
	
	
	public static void main(String args[]) throws IOException{
        long startTime = System.currentTimeMillis();

		if(typeOfFileToConvert.equals("input")){
			cmg.readInput();
		}
		if(typeOfFileToConvert.equals("expected")){
			cmg.readExpected();
		}
		long stopTime = System.currentTimeMillis();
		long elapsedTime = stopTime - startTime;
	    System.out.println(elapsedTime);
	}
		
		/*
		 * Master method that calls helper functions for input conversion
		 */
		public static void readInput() throws IOException {
			FileReader input = new FileReader(inFile);
			BufferedReader bufferedReader = new BufferedReader(input);
			String line ;
			while((line = bufferedReader.readLine()) != null) {
				cmgconfig.parseLine(line);
				//add the parsed variables to arrayList input
				cmgconfig.organizeForTemplate();
				//put into desired format - template approved
				seperateLines();
			}
			bufferedReader.close();
		}
		
		/*
		 * Master method that calls helper functions for expected conversion
		 */
		public static void readExpected() throws IOException {
			FileReader input = new FileReader(expectedInFile);
			BufferedReader bufferedReader = new BufferedReader(input);
			String line ;
			while((line = bufferedReader.readLine()) != null) {
				cmgconfig.parseLine(line);
				//add the parsed variables to arrayList input
				cmgconfig.organizeExpForTemplate();
				//put into desired format - template approved
				seperateLinesExpected();
			}
			bufferedReader.close();
		}
	
		/*
		 * Write out each line to input converted file
		 */
		public static void seperateLines() throws IOException {
			//check to see if file is already there//
			
			FileWriter fw = new FileWriter(outFile, true);
			
			for(String s: output){
				fw.write(s);
			}
			fw.write('\n');
			output.clear();
			fw.close();
		}
		
		/*
		 * Write out each line to expected converted file
		 */
		public static void seperateLinesExpected() throws IOException {
			//check to see if file is already there//
			
			FileWriter fw = new FileWriter(expectedOutFile, true);
			
			for(String s: outputExp){
				fw.write(s);
			}
			fw.write('\n');
			outputExp.clear();
			fw.close();
		}
}
