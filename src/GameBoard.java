import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GameBoard {
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(board);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GameBoard other = (GameBoard) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}

	private int [][] board = new int[4][4];
	private Direction lastMoveDirection;
	
	public GameBoard() {
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				board[x][y] = 0;
			}
		}
	}
	
	public void setValue(int x, int y, int value) {
		board[x][y] = value;
	}
	
	public int getValue(int x, int y) {
		return board[x][y];
	}
	
	
	public List<GameBoard> getNextMoves() {
		return null;
	}
	
	public List<GameBoard> getNextMoves(Direction dir) {
		return null;
	}
	
	public GameBoard getBoardDirectlyAfterMove(Direction dir) {
		GameBoard newBoard = getCopy();
		
		boolean justMerged = false;
		int x = dir.getxStart(); int y = dir.getyStart();
		while(isLegal(x) && isLegal(y)) {

				if (newBoard.board[x][y] != 0) {
					Point openMove = getFirstUnoccupiedSpace(dir, newBoard, x, y);
					if (x != openMove.x || y != openMove.y) {
						newBoard.board[openMove.x][openMove.y] = newBoard.board[x][y];
						newBoard.board[x][y] = 0;
					}
					
					int nextX = openMove.x + dir.getX();
					int nextY = openMove.y + dir.getY();
					if (!justMerged 
							&& isLegal(nextX, nextY)
							&& newBoard.board[nextX][nextY] 
									==  newBoard.board[openMove.x][openMove.y]) {
						newBoard.board[openMove.x][openMove.y] = 0;
						newBoard.board[nextX][nextY] *= 2;
						justMerged = true;
					} else {
						justMerged = false;
					}
				}
				
				// Iterate in the order of dir
				// This is kind of ghetto, but we need to make sure we iterate in the direction of dir entirely
				// Before switching rows/columns
				if (dir.getX() == 0) {
					y += dir.getyInc();
					if (!isLegal(y)) {
						y = dir.getyStart();
						x += dir.getxInc();
					}
				} else {
					x += dir.getxInc();
					if (!isLegal(x)) {
						x = dir.getxStart();
						y += dir.getyInc();
					}
				}
		}
		
		return newBoard;
	}
	
	// If no spaces in direction are occupied, returns (x, y)
	private Point getFirstUnoccupiedSpace(Direction dir, GameBoard b, int x, int y) {
		while(isLegal(x + dir.getX(), y + dir.getY()) 
				&& b.board[x + dir.getX()][y + dir.getY()] == 0) {
			y += dir.getY(); x += dir.getX();
		}
		
		return new Point(x, y);
	}
	
	private boolean isLegal(int x, int y) {
		return (isLegal(x) && isLegal(y));
	}
	
	private boolean isLegal(int x) {
		return (x >= 0 && x <= 3);
	}
	
	public static List<GameBoard> getPossiblePlacementsFromBoard(GameBoard gameBoard) {
		List<GameBoard> possiblePlacements = new ArrayList<>();
		
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				if (gameBoard.board[x][y] == 0) {
					GameBoard copy2 = gameBoard.getCopy();
					GameBoard copy4 = gameBoard.getCopy();
					
					copy2.board[x][y] = 2;
					copy4.board[x][y] = 4;
					
					possiblePlacements.add(copy2);
					possiblePlacements.add(copy4);
				}
			}
		}
		
		return possiblePlacements;
	}
	
	public GameBoard getCopy() {
		GameBoard b = new GameBoard();
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				b.setValue(x, y, board[x][y]);
			}
		}
		return b;
	}
	
	public String toString() {
		StringBuilder b = new StringBuilder();
		for (int y = 0; y < 4; y++) {
			for (int x = 0; x < 4; x++) {
				b.append("\t" + board[x][y]);
			}
			b.append("\n");
		}
		return b.toString();
	}

	public Direction getLastMoveDirection() {
		return lastMoveDirection;
	}

	public void setLastMoveDirection(Direction lastMoveDirection) {
		this.lastMoveDirection = lastMoveDirection;
	}
	
}
