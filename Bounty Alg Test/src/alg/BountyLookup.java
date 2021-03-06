package alg;

import java.util.ArrayList;

public class BountyLookup {
	private static Iterable<Bounty> empty = new ArrayList<Bounty>();
	
	private ArrayList<Bounty>[] lookup;
	private BountyLookup starters;
	
	public BountyLookup(int tileCount) {
		this(tileCount, null);
	}
	
	public BountyLookup(int tileCount, BountyLookup starters) {
		lookup = new ArrayList[tileCount];
		this.starters = starters;
	}
	
	public void addBounty(Bounty bounty) {
		ArrayList<Bounty> target = lookup[bounty.value()];
		
		if(target == null) {
			target = new ArrayList<Bounty>();
			lookup[bounty.value()] = target;
		}
		
		target.add(bounty);
	}
	
	public Iterable<Bounty> matches(int value) {
		Iterable<Bounty> source = lookup[value];
		if(source != null) return source;
		
		if(starters != null) return starters.matches(value);
		
		return empty;
	}
}
