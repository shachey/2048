import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;


public class Main {

	public static void main(String[] args) throws Exception {
		WebDriver driver = new SafariDriver();

        // And now use this to visit Google
        driver.get("http://gabrielecirulli.github.io/2048/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");
        driver.findElement(By.className("restart-button")).click();
        
        
        for (int i = 0; i < 5; i++) {
        	pressKey(driver, 37);
        	for (int j = 0; j < 10; j++){ 
	            Thread.sleep(50);
		        pressKey(driver, 38);
		        Thread.sleep(50);
		        pressKey(driver, 39);
        	}
        }
        
        Thread.sleep(1000);
        System.out.println(getBoardState(driver));
        
        Thread.sleep(10000);
        

        // Should see: "cheese! - Google Search"
        System.out.println("Page title is: " + driver.getTitle());
      //Close the browser
        driver.quit();
	}
	
	public static void pressKey(WebDriver driver, int key) {
		String js = "var keyEvent = document.createEvent(\"Events\");" +
		        "var keyCode = " + key +  ";" +
		        "keyEvent.initEvent(\"keydown\", true, true); " +
		        "keyEvent.keyCode = keyCode;" +
		        "keyEvent.which = keyCode;" +
		        "document.dispatchEvent(keyEvent);";

        if (driver instanceof JavascriptExecutor) {
    		((JavascriptExecutor) driver)
    			.executeScript(js);
    	}
	}
	
	public static GameBoard getBoardState(WebDriver driver) {
		GameBoard b = new GameBoard();
        for (int x = 1; x <= 4; x++) {
        	for (int y = 1; y <= 4; y++) {
        		try {
        			WebElement element = driver.findElement(By.className("tile-position-" + x + "-" + y));
        			String c = element.getAttribute("class");
        			Pattern p = Pattern.compile("tile-([1-9]+)");
        			Matcher m = p.matcher(c);
        			m.find();
        			String value = m.group(1);
        			b.setValue(x - 1, y - 1, Integer.parseInt(value));
        		}
        		catch (NoSuchElementException e) {
        			
        		}
        		
        	}
        }
        
        return b;
	}
}
