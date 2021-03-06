package alg;

import java.util.function.Supplier;

public class ArrayGrid<T> {
	private T[][] array;
	private int xSize;
	private int ySize;
	
	public ArrayGrid(int xSize, int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		
		array = (T[][]) new Object[ySize][xSize];
	}
	
	public T get(int x, int y) {
		return array[y][x];
	}
	
	public T getSet(int x, int y, Supplier<T> nullValue) {
		T gotten = get(x, y);
		
		if(gotten == null) {
			T value = nullValue.get();
			set(x, y, value);
			return value;
		} else
			return gotten;
	}
	
	public void set(int x, int y, T value) {
		if(!inBounds(x, y)) return;
		
		array[y][x] = value;
	}
	
	public int xSize() {
		return xSize;
	}
	
	public int ySize() {
		return ySize;
	}
	
	public boolean inBounds(int x, int y) {
		return !(0 > x || x >= xSize ||
			   0 > y || y >= ySize);
	}
}
