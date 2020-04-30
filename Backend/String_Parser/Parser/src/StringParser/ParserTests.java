package StringParser;

import static org.junit.Assert.*;

import org.junit.Test;

public class ParserTests {

	@Test
	public void test1() {
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
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Sodium=370, Calories=240, Carbohydrates=9, Protein=19, Cholesterol=0, Fat=14}");
	}

	@Test
	public void test2() {
		String test = "Nutrition Facts\n"
				+ "Serving Size 1 Tbsp (21g)\n"
				+ "Servings Per Container 11\n"
				+ "\n"
				+ "Amount Per Serving\n"
				+ "alories 60\n"
				+ "% Daily Value*\n"
				+ "\n"
				+ "Total Fat 0g 0%\n"
				+ "Trans Fat 0g\n"
				+ "\n"
				+ "Sodium Omg 0%\n"
				+ "Total Carbohydrate 179 6%\n"
				+ "Sugars 16g\n"
				+ "rotein Og 0%\n"
				+ "aaa\n"
				+ "\n"
				+ "* Percent Daily Values are based\n"
				+ "on a 2,000 calorie diet.\n"
				+ "\n"
				+ "\n"
				+ "\n"
				+ "8 0z. (2269) Honey\n"
				+ "\n"
				+ "Honey should not be fed to infants\n"
				+ "under one year of age.";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Sodium=0, Calories=60, Carbohydrates=179, Protein=0, Fat=0}");
	}

	@Test
	public void test3() {
		String test = "Nutrition Facts\n"
				+ "\n"
				+ "1 serving per container\n"
				+ "Serving size 1/4 cup (40g)\n"
				+ "\n"
				+ "Amount per serving\n"
				+ "\n"
				+ "Calories 120\n"
				+ "% Daily Value*\n"
				+ "Includes 0 Added Sugars 0%\n"
				+ "Protein 1g\n"
				+ "Vitamin D Omeg 0%\n"
				+ "Calcium 25mg 2%\n"
				+ "Iron 0.7mg 4%\n"
				+ "Potassium 298mg 6%\n"
				+ "a\n"
				+ "* The % Daily Value (DV) tells you how much a nutrient in\n"
				+ "a serving of food contributes to a daily diet. 2,000 calories\n"
				+ "a day is used for general nutrition advice\n"
				+ "Ingredients: California Raisins";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Calories=120, Protein=1}");
	}
	
	@Test
	public void test4() {
		String test = "Nutrition Facts\n"
				+ "--------------------------------------------------\n"
				+ "1 serving per container\n"
				+ "Serving size 1/4 cup (40g)\n"
				+ "---------------------------------------------------\n"
				+ "---------------------------------------------------\n"
				+ "Amount per serving\n"
				+ "Calories 120\n"
				+ "--------------------------------------------------\n"
				+ "% Daily Value*\n"
				+ "Total Fat Og 0%\n"
				+ "---------------------------------------------------\n"
				+ "Saturated Fat 0g\n"
				+ "Trans Fat 0g\n"
				+ "Cholesterol Omg\n"
				+ "0%\n"
				+ "0%\n"
				+ "---------------------------------------------------\n"
				+ "Total Carbohydrate 32g 12%\n"
				+ "Dietary Fiber 2g 7%\n"
				+ "--------------------------------------------------\n"
				+ "Total Sugars 26g\n"
				+ "Includes 0 Added Sugars 0%\n"
				+ "Protein 1g\n"
				+ "---------------------------------------------------\n"
				+ "---------------------------------------------------\n"
				+ "Vitamin D Omeg\n"
				+ "Calcium 25mg\n"
				+ "Iron 0.7mg\n"
				+ "---------------------------------------------------\n"
				+ "Potassium 298mg\n"
				+ "---------------------------------------------------";

		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Calories=120, Carbohydrates=32, Protein=1, Cholesterol=0, Fat=0}");
	}
	
	@Test
	public void testCaloriesMissingLetters() {
		String test = "alories 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Calories=100}");
	}
	@Test
	public void testCaloriesDifferentLetters() {
		String test = "Cal0ries 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Calories=100}");
	}
	@Test
	public void testSodiumMissingLetters() {
		String test = "Soium 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Sodium=100}");
	}
	@Test
	public void testSodiumDifferentLetters() {
		String test = "S0dium 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Sodium=100}");
	}
	@Test
	public void testCarbMissingLetters() {
		String test = "Carbohydrate 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Carbohydrates=100}");
	}
	@Test
	public void testCarbDifferentLetters() {
		String test = "Carbohydr/tes 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Carbohydrates=100}");
	}
	@Test
	public void testProteinMissingLetters() {
		String test = "Proein 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Protein=100}");
	}
	@Test
	public void testProteinDifferentLetters() {
		String test = "Pr0tein 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Protein=100}");
	}
	@Test
	public void testCholesterolMissingLetters() {
		String test = "holesterol 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Cholesterol=100}");
	}
	@Test
	public void testCholesterolDifferentLetters() {
		String test = "/holesterol 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Cholesterol=100}");
	}
	@Test
	public void testFatMissingLetters() {
		//requires 'total' in front of it
		String test = "total at 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Fat=100}");
	}
	@Test
	public void testFatDifferentLetters() {
		//requires 'total' in front of it
		String test = "total sat 100";
		Parser p = new Parser(test);
		String out = p.run();
		assertEquals(out, "{Fat=100}");
	}
}
