package alg;

import java.util.ArrayList;
import java.util.HashMap;

public class TestClient {
	static HashMap<Character, Integer> toMap;
	static HashMap<Integer, Character> fromMap;
	
	static String blankGrid =
			  "         " + "\n"
			+ "         " + "\n"
			+ "         " + "\n"
			+ "         " + "\n"
			+ "         ";
	static String testGrid =
			  "   xxx   " + "\n"
			+ "    x    " + "\n"
			+ "         " + "\n"
			+ "    xxx  " + "\n"
			+ "     x   ";
	
	static String testPattern =
			  "xxx" + "\n"
			+ " x ";

	public static void main(String[] args) {
		generateMaps(" x#");
		
		/*
		ArrayGrid<Integer> grid = buildGrid(blankGrid);
		Bounty starter = Bounty.getStarter(buildGrid(testPattern), 0);
		
		imprintPattern(starter, grid, 2, 2);
		displayGrid(grid);
		*/
		
		ArrayGrid<Integer> grid = buildGrid(testGrid);
		ArrayList<ArrayGrid<Integer>> patterns = new ArrayList<>();
		patterns.add(buildGrid(testPattern));
		
		BountyAlgorithm alg = new BountyAlgorithm(patterns, toMap.size());
		
		alg.run(grid);
	}
	
	public static void displayGrid(ArrayGrid<Integer> grid) {
		StringBuilder sb = new StringBuilder();
		
		System.out.println();
		for(int y = 0; y < grid.ySize(); y++) {
			for(int x = 0; x < grid.xSize(); x++) {
				sb.append(grid.get(x, y));
			}
			System.out.println(sb.toString());
			sb.setLength(0);
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
	
	public static ArrayGrid<Integer> buildGrid(String input) {
		String[] rows = input.split("\n");
		ArrayGrid<Integer> resultGrid = new ArrayGrid<Integer>(rows[0].length(), rows.length);
		
		for(int x = 0; x < rows[0].length(); x++) {
			for(int y = 0; y < rows.length; y++) {			
				
				resultGrid.set(x, y, toMap.get(rows[y].charAt(x)));
			}
		}
		
		return resultGrid;
	}
	
	public static void imprintPattern(Bounty starter, ArrayGrid<Integer> grid, int x, int y) {
		int cX = x;
		int cY = y;
		Bounty current = starter;
		
		while(true) {
			grid.set(cX, cY, current.value());
			
			if(current.getNext() != null) {
				cX += current.offsetX();
				cY += current.offsetY();
				current = current.getNext();
			} else {
				break;
			}
		}
	}
}
