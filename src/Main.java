import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


public class Main {

	public static void main(String[] args) throws Exception {
		System.setProperty("webdriver.chrome.driver", "./lib/chromedriver");
		WebDriver driver = new ChromeDriver();

        driver.get("http://gabrielecirulli.github.io/2048/");
        driver.findElement(By.className("restart-button")).click();
        allowKeys(driver);
        setupBoardRead(driver);
        
        GameBoardSolver solver = new MinimaxGameBoardSolver();
        solver.setHeuristic(new BasicHeuristic());

        Thread.sleep(1000);
        pressKey(driver, 38);
        GameBoard b = getBoardState(driver);
//        GameBoard b = new GameBoard().getPossibleNextState().getPossibleNextState();
        
        while (true) {
        	Direction dir = solver.getBestNextMove(b);
        	if (dir == null)
        		break;
        	
        	pressKey(driver, dir.getKeyValue());
        	
//        	GameBoard next = b.getBoardDirectlyAfterMove(dir);
//        	b = next.getPossibleNextState();
        	b = getBoardState(driver);
        }
        
        
        Thread.sleep(100000);
 
        driver.quit();
	}
	public static void allowKeys(WebDriver driver) {
		String allowJS = "KeyboardInputManager.prototype.targetIsInput = function (event) { return 0; };";
		
		if (driver instanceof JavascriptExecutor) {
    		((JavascriptExecutor) driver)
    			.executeScript(allowJS);
    	}
	}
	public static void pressKey(WebDriver driver, int key) {
		String js = "var keyEvent = document.createEvent(\"Events\"); " +
		        "var keyCode = " + key +  "; " +
		        "keyEvent.initEvent(\"keydown\", true, true); " +
		        "keyEvent.keyCode = keyCode; " +
		        "keyEvent.which = keyCode; " +
		        "document.dispatchEvent(keyEvent);";
		
        if (driver instanceof JavascriptExecutor) {
    		((JavascriptExecutor) driver)
    			.executeScript(js);
    	}
	}
	
	public static GameBoard getBoardState(WebDriver driver) {
		
		Object o = ((JavascriptExecutor) driver)
		.executeScript("return window.grid;");
		
		GameBoard b = new GameBoard();
		int rowNum = 0;
		for (ArrayList<Long> row : (ArrayList<ArrayList<Long>>) o) {
			int col = 0;
			for (Long l : row) {
				long convert = l;
				b.setValue(col, rowNum, (int)convert);
				col++;
			}
			rowNum++;
		}
		
		return b;
	}
	
	public static void setupBoardRead(WebDriver driver) {
		String js = "(function() { \n" + 
				"			 \n" + 
				"			GameManager.prototype.export = function() {\n" + 
				"			   window.grid = Array(this.grid.size);\n" + 
				"			   window.score = this.score;\n" + 
				"			   for (var i = 0; i < this.grid.size; i++) {\n" + 
				"			    window.grid[i] = new Array(this.grid.size);\n" + 
				"			   }\n" + 
				"			   this.grid.eachCell(function(x,y,z) {        \n" + 
				"				window.grid[y][x] = z==null||z==undefined?0:z.value;  \n" + 
				"			   });\n" + 
				"			};\n" + 
				"			GameManager.prototype.oldSetup = GameManager.prototype.setup;\n" + 
				"			GameManager.prototype.setup = function() {\n" + 
				"			    this.oldSetup();\n" + 
				"			    this.export();    \n" + 
				"			};\n" + 
				"			GameManager.prototype.origActuate = GameManager.prototype.actuate;\n" + 
				"			GameManager.prototype.actuate = function() {\n" + 
				"			   this.export();\n" + 
				"			   this.origActuate();\n" + 
				"			}\n" + 
				"			 \n" + 
				"			})();";
		
		
		if (driver instanceof JavascriptExecutor) {
    		((JavascriptExecutor) driver)
    			.executeScript(js);
    	}
	}
}
