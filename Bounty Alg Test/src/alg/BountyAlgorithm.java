package alg;

import java.util.ArrayList;

public class BountyAlgorithm {
	private Bounty[][] startingBounties;
	private int[][] inputGrid;
	private int tileCount;
	private int width;
	private int height;
	
	private ArrayList<Bounty>[][][] bountyGrid;
	
	public BountyAlgorithm(Bounty[][] startingBounties, int[][] inputGrid, int tileCount) {
		run(startingBounties, inputGrid, tileCount);
	}
	
	private void addNextBounty(Bounty bounty, int x, int y) {
		Bounty nextBounty = bounty.next;
		
		if(nextBounty == null) {
			System.out.println("Matched at " + x + ", " + y);
			return;
		}
		
		int indexX = x + bounty.offset.x;
		int indexY = y + bounty.offset.y;
		
		if(indexX < 0 || indexX >= bountyGrid.length ||
			indexY < 0 || indexY >= bountyGrid[0].length)
			return;
		
		ArrayList<Bounty>[] bountyArr = bountyGrid[indexX][indexY];
		
		if(bountyArr == null) {
			bountyArr = new ArrayList[tileCount];
			bountyGrid[indexX][indexY] = bountyArr;
		}
		
		ArrayList<Bounty> targetBounties = bountyArr[nextBounty.value];
		
		if(targetBounties == null) {
			targetBounties = new ArrayList<Bounty>();
			bountyArr[nextBounty.value] = targetBounties;
		}
		
		targetBounties.add(nextBounty);
	}
	
	private boolean bountyMatch(int currentCell, int x, int y) {
		
		ArrayList<Bounty>[] targetBounties = bountyGrid[x][y];
		
		if(targetBounties != null && targetBounties[currentCell] != null)
			return true;
		
		if(startingBounties[currentCell] != null)
			return true;
		
		return false;
	}
	
	private void addNextBountiesAll(int matchCell, int x, int y) {
		ArrayList<Bounty>[] sourceBounties = bountyGrid[x][y];
		
		if(sourceBounties != null && sourceBounties[matchCell] != null) {
			for(Bounty el : sourceBounties[matchCell]) {
				addNextBounty(el, x, y);
			}
		}
		
		Bounty[] sourceStarters = startingBounties[matchCell];
		
		if(sourceStarters != null) {
			for(Bounty el : sourceStarters) {
				addNextBounty(el, x, y);
			}
		}
	}
	
	public void run(Bounty[][] startingBounties, int[][] inputGrid, int tileCount) {
		this.startingBounties = startingBounties;
		this.inputGrid = inputGrid;
		this.tileCount = tileCount;
		width = inputGrid.length;
		height = inputGrid[0].length;
		
		bountyGrid = new ArrayList[inputGrid.length][inputGrid[0].length][];
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int currentCell = inputGrid[j][i];
				if(bountyMatch(currentCell, j, i))
					addNextBountiesAll(currentCell, j, i);
			}
		}
	}
}
