package alg;

import java.util.function.Supplier;

public class BountyAlgorithm {
	private Supplier<BountyLookup> newLookup;
	private BountyLookup startingBounties;
	
	private ArrayGrid<BountyLookup> bountyGrid;
	private BountyListner listner;
	
	public BountyAlgorithm(Iterable<ArrayGrid<Integer>> patterns, int tileCount) {		
		startingBounties = Bounty.getStarters(patterns, 0, tileCount);
		
		newLookup = () -> new BountyLookup(tileCount, startingBounties);
	}
	
	private void addNextBounty(Bounty bounty, int x, int y) {		
		if(!bounty.hasNext()) {
			listner.foundPattern(bounty.patternID(), x, y);
			return;
		}
		
		Bounty nextBounty = bounty.getNext();
		
		int indexX = x + bounty.offsetX();
		int indexY = y + bounty.offsetY();
		
		if(!bountyGrid.inBounds(indexX, indexY))
			return;
		
		bountyGrid.getSet(indexX, indexY, newLookup).addBounty(nextBounty);;
	}
	
	private Iterable<Bounty> bountyMatch(int currentCell, int x, int y) {
		return bountyGrid.getSet(x,  y, newLookup).matches(currentCell);
	}
	
	public void run(ArrayGrid<Integer> inputGrid, BountyListner listner) {
		bountyGrid = new ArrayGrid<>(inputGrid.xSize(), inputGrid.ySize());
		this.listner = listner;

		for(int y = 0; y < inputGrid.ySize(); y++) {
			for(int x = 0; x < inputGrid.xSize(); x++) {

				for(Bounty el : bountyMatch(inputGrid.get(x, y), x, y))
					addNextBounty(el, x, y);
				
			}
		}
	}
}
