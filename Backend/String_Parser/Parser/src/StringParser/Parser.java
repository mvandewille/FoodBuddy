package StringParser;

import java.util.ArrayList;

public class Parser {
	public static void main(String[] args)
	{
		String test = "Nutrition Facts\n"
				+ "3 servings per container\n"
				+ "Serving size 4 oz (113g)\n"
				+ "a\n"
				+ "\n"
				+ "Amount per serving\n"
				+ "\n"
				+ "Calories 240\n"
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
