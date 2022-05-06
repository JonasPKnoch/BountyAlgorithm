package alg;

import java.awt.Point;
import java.util.ArrayList;

public class Bounty {
	private int value;
	private int index;
	private int patternID;
	private Bounty next;
	private int x;
	private int y;
	
	private Bounty(int value, int index, int patternID) {
		this.value = value;
		this.index = index;
		this.patternID = patternID;
	}
	
	public int value() {
		return value;
	}
	
	public int index() {
		return index;
	}
	
	
	public int patternID() {
		return patternID;
	}
	
	public Bounty getNext() {
		return next;
	}
	
	public boolean hasNext() {
		return next != null;
	}
	
	public int offsetX() {
		return x;
	}
	
	public int offsetY() {
		return y;
	}
	
	public static Bounty getStarter(ArrayGrid<Integer> pattern, int empty, int patternID) {
		Bounty starter = null;
		Point lastTile = null;
		Bounty last = null;
		int index = 0;
		
		for(int y = 0; y < pattern.ySize(); y++) {
			for(int x = 0; x < pattern.xSize(); x++) {
				int current = pattern.get(x, y);
				
				if(current != empty) {
					if(last == null) {
						last = new Bounty(current, index++, patternID);
						starter = last;
						lastTile = new Point(x, y);
						continue;
					}
					
					Bounty next = new Bounty(current, index++, patternID);
					last.next = next;
					last.x = x - lastTile.x;
					last.y=  y - lastTile.y;
					lastTile = new Point(x, y);
					last = next;
				}
			}
		}
		
		return starter;
	}
	
	public static BountyLookup getStarters(Iterable<ArrayGrid<Integer>> patterns, int empty, int tiles) {
		BountyLookup starters = new BountyLookup(tiles);
		
		int id = 1;
		for(ArrayGrid<Integer> pattern : patterns) {
			Bounty bounty = getStarter(pattern, empty, id++);
			starters.addBounty(bounty);
		}
		
		return starters;
	}
	
	public static BountyLookup getStarters(ArrayGrid<Integer> pattern, int empty, int tiles) {
		ArrayList<ArrayGrid<Integer>> arr = new ArrayList<>();
		arr.add(pattern);
		return getStarters(arr, empty, tiles);
	}
}
