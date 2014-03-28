
public interface GameBoardSolver {
	public Direction getBestNextMove(GameBoard b);
	public void setHeuristic(GameBoardHeuristic heuristic);
}
