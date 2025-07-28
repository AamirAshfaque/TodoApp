package todo;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;


public class TodoUITest {
    static WebDriver driver;

    @BeforeClass
    public static void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:3000"); // React app
    }

    @AfterClass
    public static void teardown() {
        driver.quit();
    }

    @Test
    public void invalidLoginShowsAlert() {
        driver.findElement(By.id("username")).sendKeys("x");
        driver.findElement(By.id("password")).sendKeys("y");
        driver.findElement(By.id("login-btn")).click();
        Alert alert = driver.switchTo().alert();
        Assert.assertEquals("Invalid", alert.getText());
        alert.accept();
    }

    @Test
    public void validLoginAndCrudTodo() {
        // valid login
        driver.findElement(By.id("username")).clear();
        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("username")).sendKeys("user");
        driver.findElement(By.id("password")).sendKeys("pass");
        driver.findElement(By.id("login-btn")).click();
        new WebDriverWait(driver, 5)
            .until(d -> d.findElement(By.id("todo-app")));

        // create
        WebElement input = driver.findElement(By.id("new-todo"));
        input.sendKeys("Task 1");
        driver.findElement(By.id("add-btn")).click();
        Assert.assertEquals(1,
          driver.findElements(By.cssSelector("#todo-list li")).size());

        // edit (inline or via prompt)
        WebElement li = driver.findElement(By.cssSelector("#todo-list li"));
        li.findElement(By.cssSelector(".edit-btn")).click();
        Alert prompt = driver.switchTo().alert(); // assume prompt
        prompt.sendKeys("Edited Task");
        prompt.accept();
        Assert.assertTrue(li.getText().contains("Edited Task"));

        // delete
        li.findElement(By.cssSelector(".del-btn")).click();
        Assert.assertEquals(0,
          driver.findElements(By.cssSelector("#todo-list li")).size());
    }
}
