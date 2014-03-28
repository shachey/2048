
public class BasicHeuristic implements GameBoardHeuristic {

	@Override
	public int getValue(GameBoard b) {
		int numFreeSpaces = 0;
		int totalSum = 0;
		int max = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (b.getValue(i, j) == 0) {
					numFreeSpaces++;
				}
				
				totalSum += b.getValue(i, j);
				
				if (b.getValue(i, j)>max) {
					max = b.getValue(i, j);
				}
			}
		}
		
		int maxTopRight = b.getValue(3, 0) == max ? 1 : 0;
		
		int adjacentTiles = 0;
		int futureMerge = 0;
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (b.getValue(i, j) != 0) {
					if (GameBoard.isLegal(i + 1, j)) {
						if (b.getValue(i, j) == b.getValue(i + 1, j)) {
							adjacentTiles += b.getValue(i, j);
						}
						else if (2 * b.getValue(i, j) == b.getValue(i + 1, j) 
								|| b.getValue(i, j) == 2 * b.getValue(i + 1, j)) {
							futureMerge += b.getValue(i, j);
						}
					}
					
					if (GameBoard.isLegal(i, j + 1)) {
						if (b.getValue(i, j) == b.getValue(i, j + 1)) {
							adjacentTiles += b.getValue(i, j);
						}
						else if (2 * b.getValue(i, j) == b.getValue(i, j + 1) 
								|| b.getValue(i, j) == 2 * b.getValue(i, j + 1)) {
							futureMerge += b.getValue(i, j);
						}
					}
				}
			}
		}
		
		int numMaxOnEdge = 0;
		// largest on edge
		for (int i = 0; i < 4; i++) {
			int middleMax = Math.max(b.getValue(i, 1), b.getValue(i, 2));
			if (b.getValue(i, 0) >= middleMax || b.getValue(i, 3) > middleMax) {
				numMaxOnEdge++;
			}
			
			middleMax = Math.max(b.getValue(1, i), b.getValue(2, i));
			if (b.getValue(0, i) >= middleMax || b.getValue(3, i) > middleMax) {
				numMaxOnEdge++;
			}
		}
		
		if (numFreeSpaces == 0) {
			return Integer.MIN_VALUE;
		}
		
		return numFreeSpaces * 100 + adjacentTiles * 1 + numMaxOnEdge * 10 + totalSum*0 + maxTopRight * 0 + max * 1;
		//return numFreeSpaces * 100 + adjacentTiles * 1 + numMaxOnEdge * 10 + totalSum*0 + maxTopRight * 100 + max * 1;
	}

}
