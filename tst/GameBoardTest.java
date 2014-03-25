import static org.junit.Assert.*;

import org.junit.Test;


public class GameBoardTest {

	
	/*
	0	0	0	0
	0	0	0	0
	8	0	0	0
	8	0	0	0
==>
	16	0	0	0
	0	0	0	0
	0	0	0	0
	0	0	0	0
	 */
	@Test
	public void testUp() {
		GameBoard start = new GameBoard();
		start.setValue(0, 2, 8);
		start.setValue(0, 3, 8);
		
		GameBoard expected = new GameBoard();
		expected.setValue(0, 0, 16);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Up));
		
	}
	
	/*
	8	0	0	0
	8	0	0	0
	8	0	0	0
	8	0	0	0
==>
	16	0	0	0
	16	0	0	0
	0	0	0	0
	0	0	0	0
	 */
	@Test
	public void testUpDoubleMerge() {
		GameBoard start = new GameBoard();
		start.setValue(0, 0, 8);
		start.setValue(0, 1, 8);
		start.setValue(0, 2, 8);
		start.setValue(0, 3, 8);
		
		GameBoard expected = new GameBoard();
		expected.setValue(0, 0, 16);
		expected.setValue(0, 1, 16);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("down did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Up));
	}
	
	@Test
	public void testDown() {
		GameBoard start = new GameBoard();
		start.setValue(0, 2, 8);
		start.setValue(0, 3, 8);
		
		GameBoard expected = new GameBoard();
		expected.setValue(0, 3, 16);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Down));
	}
	
	@Test
	public void testRight() {
		GameBoard start = new GameBoard();
		start.setValue(0, 3, 16);
		start.setValue(2, 3, 16);
		
		GameBoard expected = new GameBoard();
		expected.setValue(3, 3, 32);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Right));
	}
	
	@Test
	public void testLeft() {
		GameBoard start = new GameBoard();
		start.setValue(0, 1, 8);
		start.setValue(3, 1, 8);
		
		GameBoard expected = new GameBoard();
		expected.setValue(0, 1, 16);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Left));
	}
	
	@Test
	public void bigTest() {
		GameBoard start = new GameBoard();
		start.setValue(0, 1, 8);
		start.setValue(1, 1, 8);
		start.setValue(2, 1, 4);
		start.setValue(3, 1, 16);
		start.setValue(0, 2, 8);
		start.setValue(1, 2, 4);
		start.setValue(2, 2, 4);
		start.setValue(3, 2, 16);
		
		GameBoard expected = new GameBoard();
		expected.setValue(1, 1, 16);
		expected.setValue(2, 1, 4);
		expected.setValue(3, 1, 16);
		expected.setValue(1, 2, 8);
		expected.setValue(2, 2, 8);
		expected.setValue(3, 2, 16);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Right));
	}
	
	@Test
	public void bigTest2() {
		GameBoard start = new GameBoard();
		start.setValue(0, 1, 8);
		start.setValue(1, 1, 8);
		start.setValue(2, 1, 4);
		start.setValue(3, 1, 16);
		start.setValue(0, 2, 8);
		start.setValue(1, 2, 4);
		start.setValue(2, 2, 4);
		start.setValue(3, 2, 16);
		start.setValue(3, 3, 32);
		
		GameBoard expected = new GameBoard();
		expected.setValue(0, 0, 16);
		expected.setValue(1, 0, 8);
		expected.setValue(1, 1, 4);
		expected.setValue(2, 0, 8);
		expected.setValue(3, 0, 32);
		expected.setValue(3, 1, 32);
		
		System.out.println(start);
		System.out.println(expected);
		
		assertEquals("up did not merge correctly", expected, start.getBoardDirectlyAfterMove(Direction.Up));
	}
	
}
