import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.common.collect.Maps;


public class MinimaxGameBoardSolver implements GameBoardSolver {

	GameBoardHeuristic heuristic;
	ExecutorService threadPool = Executors.newCachedThreadPool();
	List<Direction> allDirections = null;
	
	@Override
	public Direction getBestNextMove(GameBoard board) {
		int bestScore = Integer.MIN_VALUE;
		Direction bestMove = null;
		Map<Direction, Future<Integer>> moveMap = Maps.newHashMap();
		
		for (Direction dir : Direction.values()) {
			GameBoard newBoard = board.getBoardDirectlyAfterMove(dir);
			
			if (newBoard.equals(board)) {
				continue;
			}
			Future<Integer> future = threadPool.submit(
					() -> minimax(newBoard, false, Integer.MIN_VALUE, Integer.MAX_VALUE, 13));
			moveMap.put(dir, future);
		}

		for(Entry<Direction, Future<Integer>> movePair : moveMap.entrySet()) {
			int score;
			
			try {
				score = movePair.getValue().get();
			} catch (InterruptedException | ExecutionException e) {
				return null;
			}
			
			Direction dir = movePair.getKey();
			if (score >= bestScore) {
				bestMove = dir;
				bestScore = score;
			}
		}
		
		return bestMove;
	}
	
	public int minimax(GameBoard board, boolean maximize, int a, int b, int depth) {
		if (depth == 0) {
			return heuristic.getValue(board);
		}
		
		if (maximize) {
			for (Direction dir : Direction.values()) {
				GameBoard newBoard = board.getBoardDirectlyAfterMove(dir);
				
				if (newBoard.equals(board)) {
					continue;
				}
				
				a = Math.max(a, minimax(newBoard, false, a, b, depth - 1));
				if (b <= a) {
					break;
				}
			}
			
			return a;
		}
		else {
			for (GameBoard newBoard : GameBoard.getPossiblePlacementsFromBoard(board)) {
				b = Math.min(b, minimax(newBoard, true, a, b, depth - 1));
				if (a <= b) {
					break;
				}
			}
			
			return b;
		}
	}

	public void setHeuristic(GameBoardHeuristic heuristic) {
		this.heuristic = heuristic;
	}

}
