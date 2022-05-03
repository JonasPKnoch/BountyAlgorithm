package alg;

import java.util.HashMap;

public class TestClient {
	static HashMap<Character, Integer> toMap;
	static HashMap<Integer, Character> fromMap;
	
	static String testGrid =
			  "   #x#   " + "\n"
			+ "   #x#   " + "\n"
			+ "####x####" + "\n"
			+ "xxxxxxxxx" + "\n"
			+ "#########";
	static String testPattern =
			  "#x#";

	public static void main(String[] args) {
		generateMaps(" x#");
		
		int[][] grid = buildArray(testGrid);
		Bounty[][] starters = Bounty.getStarters(buildArray(testPattern), 0, toMap.size()) ;
		
		BountyAlgorithm alg = new BountyAlgorithm(starters, grid, toMap.size());
	}
	
	public static void displayArr(int[][] arr) {
		StringBuilder sb = new StringBuilder();
		
		System.out.println();
		for(int i = 0; i < arr[0].length; i++) {
			sb.setLength(0);
			for(int j = 0; j < arr.length; j++) {
				sb.append(fromMap.get(arr[j][i]));
			}
			System.out.println(sb.toString());
		}
	}
	
	public static void generateMaps(String allChars) {
		toMap = new HashMap<>();
		fromMap = new HashMap<>();
		
		for(int i = 0; i < allChars.length(); i++) {
			char c = allChars.charAt(i);
			if(c != '\n') {
				toMap.put(c, toMap.size());
				fromMap.put(fromMap.size(), c);
			}
		}
	}
	
	public static int[][] buildArray(String input) {
		String[] rows = input.split("\n");
		int[][] resultArray = new int[rows[0].length()][rows.length];
		
		for(int i = 0; i < rows[0].length(); i++) {
			for(int j = 0; j < rows.length; j++) {			
				
				resultArray[i][j] = toMap.get(rows[j].charAt(i));
			}
		}
		
		return resultArray;
	}
}
