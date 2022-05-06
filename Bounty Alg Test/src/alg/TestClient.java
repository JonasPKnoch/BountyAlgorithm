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
	
	static String testPatternT =
			  "xxx" + "\n"
			+ " x ";
	
	static String testPatternL =
			  "  x" + "\n"
			+ "xxx";
	
	static String testPatternCross =
			  " x " + "\n"
			+ "xxx" + "\n"
			+ " x ";

	public static void main(String[] args) {
		generateMaps(" x#");
		
		ArrayList<ArrayGrid<Integer>> patterns = new ArrayList<>();
		for(int i = 0; i < 99; i += 3) {
			patterns.add(buildGrid(testPatternT));
			patterns.add(buildGrid(testPatternL));
			patterns.add(buildGrid(testPatternCross));
		}
		System.out.println("Matching " + patterns.size() + " patterns");
		
		ArrayGrid<Integer> grid = generateGrid(1000, 1500, 0);
		populateGrid(grid, 1, 300000);
		populateGrid(grid, 2, 10000);
		
		BountyAlgorithm alg = new BountyAlgorithm(patterns, toMap.size());
		
		long startTime = System.nanoTime();
		alg.run(grid, new PrintListner());
		long endTime = System.nanoTime();
		
		System.out.println("Finished in " + (endTime-startTime)/1000000000.0d + "s");
	}
	
	public static void displayGrid(ArrayGrid<Integer> grid) {
		StringBuilder sb = new StringBuilder();
		
		System.out.println();
		for(int y = 0; y < grid.ySize(); y++) {
			for(int x = 0; x < grid.xSize(); x++) {
				sb.append(fromMap.get(grid.get(x, y)));
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
	
	public static ArrayGrid<Integer> generateGrid(int xSize, int ySize, int fill) {
		ArrayGrid<Integer> grid = new ArrayGrid<>(xSize, ySize);
		
		for(int x = 0; x < xSize; x++) {
			for(int y = 0; y < ySize; y++) {
				grid.set(x, y, fill);
			}
		}
		
		return grid;
	}
	
	public static void populateGrid(ArrayGrid<Integer> grid, int value, int quantity) {
		for(int i = 0; i < quantity; i++) {
			int x = (int) Math.round(Math.random()*grid.xSize());
			int y = (int) Math.round(Math.random()*grid.ySize());
			grid.set(x, y, value);
		}
	}
	
	public static class PrintListner implements BountyListner {
		public HashMap<Integer, Integer> matches = new HashMap<>();
		
		@Override
		public void foundPattern(int pattern, int x, int y) {
			Integer current = matches.get(pattern);
			
			if(current == null) {
				matches.put(pattern, 1);
			} else {
				matches.put(pattern, current+1);
			}
		}
	
	}
}
