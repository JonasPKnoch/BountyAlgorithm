package alg;

import java.awt.Point;
import java.util.ArrayList;

public class Bounty {
	public int value;
	public int index;
	public Bounty next;
	public Point offset;
	
	public Bounty(int value, int index) {
		this.value = value;
		this.index = index;
	}
	
	public static Bounty getStarter(int[][] pattern, int empty) {
		Bounty starter = null;
		Point lastTile = null;
		Bounty last = null;
		int index = 0;
		
		for(int i = 0; i < pattern[i].length; i++) {
			for(int j = 0; j < pattern.length; j++) {
				int current = pattern[j][i];
				
				if(current != empty) {
					if(last == null) {
						last = new Bounty(current, index++);
						starter = last;
						lastTile = new Point(j, i);
						continue;
					}
					
					Bounty next = new Bounty(current, index++);
					last.next = next;
					last.offset = new Point(j - lastTile.x, i - lastTile.y);
					last = next;
				}
			}
		}
		
		return starter;
	}
	
	public static Bounty[][] getStarters(int[][][] patterns, int empty, int tiles) {
		ArrayList<Bounty>[] starters = new ArrayList[tiles];
		
		for(int i = 0; i < starters.length; i++) {
			starters[i] = new ArrayList<Bounty>();
		}
		
		for(int[][] pattern : patterns) {
			Bounty bounty = getStarter(pattern, empty);
			starters[bounty.value].add(bounty);
		}
		
		Bounty[][] arr = new Bounty[tiles][];
		
		for(int i = 0; i < starters.length; i++) {
			ArrayList<Bounty> el = starters[i];
			arr[i] = new Bounty[el.size()];
			
			for(int j = 0; j < el.size(); j ++) {
				arr[i][j] = el.get(j);
			}
		}
		
		return arr;
	}
	
	public static Bounty[][] getStarters(int[][] pattern, int empty, int tiles) {
		return getStarters(new int[][][] {pattern}, empty, tiles);
	}
}
