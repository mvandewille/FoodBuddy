package StringParser;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Parser {
	
	public String arg;
	
	//Little constructor for the parser
	public Parser(String in)
	{
		arg = in;
	}
	
	//Start the program running the input
	public String run()
	{
		Dictionary values = new Hashtable();
		
		
		ArrayList<String> out = new ArrayList<String>();
		split(arg, out);
		values = readText(out, values);
		
		printString(values);
		
		return values.toString();
	}
	
	//Print for debugging
	public static void printString(Dictionary values)
	{
		System.out.println(values.toString());
	}
	
	//Reads the text and puts it into a dictionary
	public static Dictionary readText(ArrayList<String> in, Dictionary values)
	{
		for(int i = 0; i < in.size(); i++){
			checkCalories(in.get(i), values);
			checkSodium(in.get(i), values);
			checkProtein(in.get(i), values);
			checkFat(in.get(i), values);
			checkCholesterol(in.get(i), values);
			checkCarb(in.get(i), values);
		}
		return values;
	}
	
	//Checks the string for Carbs
	public static void checkCarb(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length-1; i++) {
			per = 0;
			if(r[i].contains("c") || r[i].contains("C")) {
				per += 1;
			}
			if(r[i].contains("a")|| r[i].contains("A")) {
				per += 1;
			}
			if(r[i].contains("r")|| r[i].contains("R")) {
				per += 1;
			}
			if(r[i].contains("b")|| r[i].contains("B")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("h")|| r[i].contains("H")) {
				per += 1;
			}
			if(r[i].contains("y")|| r[i].contains("Y")) {
				per += 1;
			}
			if(r[i].contains("d")|| r[i].contains("D")) {
				per += 1;
			}
			if(r[i].contains("r")|| r[i].contains("R")) {
				per += 1;
			}
			if(r[i].contains("a")|| r[i].contains("A")) {
				per += 1;
			}
			if(r[i].contains("t")|| r[i].contains("T")) {
				per += 1;
			}
			if(r[i].contains("e")|| r[i].contains("E")) {
				per += 1;
			}
			if(r[i].contains("s")|| r[i].contains("S")) {
				per += 1;
			}
			if(r[i].length() <= 13) {
				if((per >= 11 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) || r[i].toLowerCase() == "carbs") {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Carbohydrates", 0);
					}
					else {
						values.put("Carbohydrates", r[i+1].replaceAll("[^0-9]", ""));
					}
					return;
				}
			}
		}
	}
	
	//Checks the string for Cholesterol
	public static void checkCholesterol(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length-1; i++) {
			per = 0;
			if(r[i].contains("c") || r[i].contains("C")) {
				per += 1;
			}
			if(r[i].contains("h")|| r[i].contains("H")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("l")|| r[i].contains("L")) {
				per += 1;
			}
			if(r[i].contains("e")|| r[i].contains("E")) {
				per += 1;
			}
			if(r[i].contains("s")|| r[i].contains("S")) {
				per += 1;
			}
			if(r[i].contains("t")|| r[i].contains("T")) {
				per += 1;
			}
			if(r[i].contains("e")|| r[i].contains("E")) {
				per += 1;
			}
			if(r[i].contains("r")|| r[i].contains("R")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("l")|| r[i].contains("L")) {
				per += 1;
			}
			if(r[i].length() <= 11) {
				if(per >= 10 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Cholesterol", 0);
					}
					else {
						values.put("Cholesterol", r[i+1].replaceAll("[^0-9]", ""));
					}
					return;
				}
			}
		}
	}
	
	//Check string for fat
	public static void checkFat(String in, Dictionary values) {
		int per = 0;
		int per2 = 0;
		String r[] = in.split(" ");
		for(int i = 1; i < r.length-1; i++) {
			per = 0;
			per2 = 0;
			if(r[i].contains("f") || r[i].contains("F")) {
				per += 1;
			}
			if(r[i].contains("a")|| r[i].contains("A")) {
				per += 1;
			}
			if(r[i].contains("t")|| r[i].contains("T")) {
				per += 1;
			}
			
			
			if(r[i-1].contains("t") || r[i].contains("T")) {
				per2 += 1;
			}
			if(r[i-1].contains("o")|| r[i].contains("O")) {
				per2 += 1;
			}
			if(r[i-1].contains("t")|| r[i].contains("T")) {
				per2 += 1;
			}
			if(r[i-1].contains("a") || r[i].contains("A")) {
				per2 += 1;
			}
			if(r[i-1].contains("l")|| r[i].contains("L")) {
				per2 += 1;
			}
			if(r[i].length() <= 3)
			{
				if(per >= 2 && per2 >= 4 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Fat", 0);
					}
					else {
						values.put("Fat", r[i+1].replaceAll("[^0-9]", ""));
					}
					break;
				}
			}
		}
	}
	
	//Check String for protein
	public static void checkProtein(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length-1; i++) {
			per = 0;
			if(r[i].contains("p") || r[i].contains("P")) {
				per += 1;
			}
			if(r[i].contains("r")|| r[i].contains("R")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("t")|| r[i].contains("T")) {
				per += 1;
			}
			if(r[i].contains("e")|| r[i].contains("E")) {
				per += 1;
			}
			if(r[i].contains("i")|| r[i].contains("I")) {
				per += 1;
			}
			if(r[i].contains("n")|| r[i].contains("N")) {
				per += 1;
			}
			if(r[i].length() <= 7) {
				if(per >= 6 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Protein", 0);
					}
					else {
						values.put("Protein", r[i+1].replaceAll("[^0-9]", ""));
					}
					break;
				}
			}
		}
	}
	
	//Check the string for sodium
	public static void checkSodium(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length-1; i++) {
			per = 0;
			if(r[i].contains("s") || r[i].contains("S")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("d")|| r[i].contains("D")) {
				per += 1;
			}
			if(r[i].contains("i")|| r[i].contains("I")) {
				per += 1;
			}
			if(r[i].contains("u")|| r[i].contains("U")) {
				per += 1;
			}
			if(r[i].contains("m")|| r[i].contains("M")) {
				per += 1;
			}
			if(r[i].length() <= 6) {
				if(per >= 5 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Sodium", 0);
					}
					else {
						values.put("Sodium", r[i+1].replaceAll("[^0-9]", ""));
					}
					break;
				}
			}
		}
	}
	
	//Check string for calories
	public static void checkCalories(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length - 1; i++) {
			per = 0;
			if(r[i].contains("c") || r[i].contains("C")) {
				per += 1;
			}
			if(r[i].contains("a")|| r[i].contains("A")) {
				per += 1;
			}
			if(r[i].contains("l")|| r[i].contains("L")) {
				per += 1;
			}
			if(r[i].contains("o")|| r[i].contains("O")) {
				per += 1;
			}
			if(r[i].contains("r")|| r[i].contains("R")) {
				per += 1;
			}
			if(r[i].contains("i")|| r[i].contains("I")) {
				per += 1;
			}
			if(r[i].contains("e")|| r[i].contains("E")) {
				per += 1;
			}
			if(r[i].contains("s")|| r[i].contains("S")) {
				per += 1;
			}
			if(r[i].length() <= 8) {
				if(per >= 7 && (r[i+1].matches(".*\\d.*") || r[i+1].toLowerCase().charAt(0) == 'o')) {
					if(r[i+1].charAt(0) == 'o' || r[i+1].charAt(0) == 'O') {
						values.put("Calories", 0);
					}
					else {
						values.put("Calories", r[i+1].replaceAll("[^0-9]", ""));
					}
					return;
				}
			}
		}
	}
	
	//Split the string into an array
	public static String[] split(String in, ArrayList<String> modify)
	{
		String[] r = in.split("\n");
		for(int i = 0; i < r.length; i++){
			if(!r[i].equals("")){
				modify.add(r[i]);
			}
		}
		return r;
	}
}