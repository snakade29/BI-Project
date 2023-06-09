
package biassignment;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class App {

    public static ChromeDriver createDriver() {
        // TODO Create a new chrome Driver and Return the same
        System.setProperty("webdriver.chrome.driver", "C:\\Drivers\\chromedriver_win32\\chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        ChromeDriver driver = new ChromeDriver(options);
        return driver;
    }

    public static void printQKartLoadingtime(ChromeDriver driver) throws InterruptedException {
        // TODO Navigate to home page of QKart and verify the time taken for the page to
        // load
        long start = System.currentTimeMillis();
        driver.get("https://crio-qkart-frontend-qa.vercel.app/");
        long end = System.currentTimeMillis();
        System.out.println("Elapsed Time in milli seconds: " + (end - start));

    }

    public static void captureFullPageScreenshot(ChromeDriver driver) {
        // TODO: Capture the full page screenshot
        // Save the file with a unique name
        // Print the path of the file

        try {
            // String directoryPath = "screenshots";
            // File theDir = new File(directoryPath);
            // if (!theDir.exists()) {
            // theDir.mkdirs();
            // }
            // String timestamp = String.valueOf(java.time.LocalDateTime.now());
            // String fileName = String.format("screenshot_%s.png", timestamp);
            // String path = directoryPath + File.separator + fileName;
            // TakesScreenshot scrShot = ((TakesScreenshot) driver);
            // File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            // File DestFile = new File(path);
            // FileUtils.copyFile(SrcFile, DestFile);
            // String directoryPath = "\\screenshots";
            // File theDir = new File(directoryPath);
            // if (!theDir.exists()) {
            // theDir.mkdirs();
            // }
            // String timestamp = String.valueOf(java.time.LocalDateTime.now());
            // String fileName = String.format("screenshot_%s.png", timestamp);
            // String path = directoryPath + File.separator + fileName;
            // TakesScreenshot scrShot = ((TakesScreenshot) driver);
            // File SrcFile = scrShot.getScreenshotAs(OutputType.FILE);
            // File DestFile = new File(path);
            // FileUtils.copyFile(SrcFile, DestFile);

            String path = System.getProperty("user.dir");
            System.out.println(path);
            String homepath = path + "\\screenshots";
            System.out.println(homepath);
            File theDir = new File(homepath);
            if (!theDir.exists()) {
                theDir.mkdirs();
            }
            String timestamp = String.valueOf(java.time.LocalDateTime.now()).replace(":", "-");
            String fileName = String.format("screenshotTime%s", timestamp);
            String picPath = homepath + "\\fullPage" + fileName + ".png";
            new AShot()
                    .shootingStrategy(ShootingStrategies.viewportPasting(100))
                    .takeScreenshot(driver);
            Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000))
                    .takeScreenshot(driver);
            ImageIO.write(screenshot.getImage(), "PNG", new File(picPath));
            Path pathUrl = Paths.get(picPath);
            System.out.println("Full Page screenshot captured and saved at:");
            System.out.printf("%-25s : %s%n", "path", pathUrl);
            System.out.printf("%-25s : %s%n", "path.toAbsolutePath()", pathUrl.toAbsolutePath());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static void GetProductImageandURL(ChromeDriver driver, String productName) throws InterruptedException {
        // TODO: Given the product name, print the price of the project and the url of
        // the image
        try {

            WebDriverWait wait = new WebDriverWait(driver, 10);
            WebElement parent = driver.findElement(By.xpath(
                    " //div[contains(@class,'card css-s18byi')]"));
            wait.until(ExpectedConditions.visibilityOf(parent));

            WebElement productname = parent.findElement(By.xpath("//*[text()='" + productName + "']"));
            System.out.println("product :" + productname.getText());

            WebElement price = parent
                    .findElement(By.xpath("//*[text()='" + productName + "']/following-sibling::*[1]"));
            System.out.println("price of the product is  :" + price.getText());

            WebElement src = parent.findElement(
                    By.xpath("(//*[text()='" + productName + "'])//ancestor::div[2]/img"));
            System.out.println("URL of the product image is :" + src.getAttribute("src"));
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getStackTrace());
        }

    }

    public static void main(String[] args) {

        ChromeDriver driver = createDriver();

        String input = String.join(" ", args);
        try {

            printQKartLoadingtime(driver);

            captureFullPageScreenshot(driver);

            GetProductImageandURL(driver, input);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            driver.quit();
        }
    }

    public Object getGreeting() {
        return null;
    }
}