
public enum Direction {
	Up(0, -1, 3, -1, 0, 1),
	Down(0, 1, 3, -1, 3, -1),
	Left(-1, 0, 0, 1, 0, 1),
	Right(1, 0, 3, -1, 0, 1);
	
	Direction(int x, int y, int xStart, int xInc, int yStart, int yInc) {
		this.x = x;
		this.y = y;
		this.xInc = xInc;
		this.yInc = yInc;
		this.xStart = xStart;
		this.yStart = yStart;
	}
	
	private int x;
	private int y;
	private int xInc;
	private int yInc;
	private int xStart;
	private int yStart;
	
	public int getxInc() {
		return xInc;
	}
	public int getyInc() {
		return yInc;
	}
	public int getxStart() {
		return xStart;
	}
	public int getyStart() {
		return yStart;
	}
	public int getX() { return x; }
	public int getY() { return y; }
}
