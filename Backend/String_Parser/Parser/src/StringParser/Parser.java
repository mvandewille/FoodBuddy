package StringParser;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;

public class Parser {
	public static void main(String[] args)
	{
		Dictionary values = new Hashtable();
		String test = "Nutrition Facts\n"
				+ "3 servings per container\n"
				+ "Serving size 4 oz (113g)\n"
				+ "a\n"
				+ "\n"
				+ "Amount per serving\n"
				+ "\n"
				+ "alories 240\n"
				+ "\n"
				+ "% Daily Value*\n"
				+ "Total Fat 14g 18%\n"
				+ "Saturated Fat 8g 40%\n"
				+ "Trans Fat Og\n"
				+ "Cholesterol Omg 0%\n"
				+ "Sodium 370mg 16%\n"
				+ "Total Carbohydrate 9g 3%\n"
				+ "Dietary Fiber 3g 11%\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "Total Sugars <1g\n"
				+ "Includes <1g Added Sugars 1%\n"
				+ "\n"
				+ "\n"
				+ "Protein 19g 31%\n"
				+ "\n"
				+ "ee\n"
				+ "Vitamin D Omcg 0%\n"
				+ "\n"
				+ "Calcium 170mg 15%\n"
				+ "lron 4.2mg 25%\n"
				+ "Potassium 610mg 15%\n"
				+ "Thiamin 2350%\n"
				+ "Riboflavin 15%\n"
				+ "Niacin 50%\n"
				+ "Vitamin Be 20%\n"
				+ "Folate 30%\n"
				+ "Vitamin Biz 130%\n"
				+ "Phosphorus 15%\n"
				+ "Zinc 50%\n"
				+ "\n"
				+ "*The % Daily Value tells you how much a nutrient in a\n"
				+ "serving of food contributes to a daily diet. 2,000 calories a\n"
				+ "day is used for general nutrition advice.";
		
			ArrayList<String> out = new ArrayList<String>();
			split(test, out);
			values = readText(out, values);
			
	}
	
	public static Dictionary readText(ArrayList<String> in, Dictionary values)
	{
		for(int i = 0; i < in.size(); i++){
			checkCalories(in.get(i), values);
			checkSodium(in.get(i), values);
			checkProtein(in.get(i), values);
			checkFat(in.get(i), values);
			checkCholesterol(in.get(i), values);
		}
		return values;
	}
	
	public static void checkCholesterol(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length; i++) {
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
	
	public static void checkFat(String in, Dictionary values) {
		int per = 0;
		int per2 = 0;
		String r[] = in.split(" ");
		for(int i = 1; i < r.length; i++) {
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
	
	public static void checkProtein(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length; i++) {
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
	
	public static void checkSodium(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length; i++) {
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
	
	public static void checkCalories(String in, Dictionary values) {
		int per = 0;
		String r[] = in.split(" ");
		for(int i = 0; i < r.length; i++) {
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