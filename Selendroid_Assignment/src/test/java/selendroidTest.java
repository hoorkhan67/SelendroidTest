import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;



public class selendroidTest {

    static AppiumDriver<MobileElement> driver;
    static FileInputStream input;
    static XSSFWorkbook workbook;
    static File src;
    static XSSFSheet sheet;
    static XSSFCell cell;



    public selendroidTest() throws IOException {
    }

    public static void main(String[] args) throws Exception {
        openApp();
        displayTextView();
        displayAndFocusOnLayout();
        sayHelloDemo();
        unhandledException();
    }

    public static void openApp() throws Exception{

        DesiredCapabilities cap = new DesiredCapabilities();

        cap.setCapability("udid", "emulator-5554");
        cap.setCapability("platformName","Android");
        cap.setCapability("deviceName", "Nexus_5X_API_26");
        cap.setCapability("platformVersion", "8.0.0");
        cap.setCapability("appPackage", "io.selendroid.testapp");
        cap.setCapability("appActivity", ".HomeScreenActivity");
        cap.setCapability("newCommandTimeout", "3000");

        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        driver = new AppiumDriver<MobileElement>(url, cap);
    }

    public static void displayTextView() throws Exception{

        Thread.sleep(3000);
        driver.findElement(By.id("io.selendroid.testapp:id/visibleButtonTest")).click();
        String textContent = driver.findElement(By.id("io.selendroid.testapp:id/visibleTextView")).getText();
        String expectedContent = "Text is sometimes displayed";
        Assert.assertEquals(expectedContent, textContent);
    }

    public static void displayAndFocusOnLayout() throws Exception{

        Thread.sleep(3000);
        driver.findElement(By.id("io.selendroid.testapp:id/topLevelElementTest")).click();
        Thread.sleep(3000);
        String textContent = driver.findElement(By.id("io.selendroid.testapp:id/focusedText")).getText();
        String expectedContent = "Should only be found once";
        Assert.assertEquals(expectedContent, textContent);

        Thread.sleep(3000);
        driver.findElement(By.id("io.selendroid.testapp:id/topLevelElementTest")).click();
        String isTextFieldFocused = driver.findElement(By.id("io.selendroid.testapp:id/my_text_field")).getAttribute("focused");
        Assert.assertTrue(isTextFieldFocused, true);
    }
    public static void sayHelloDemo() throws Exception{


        src = new File("C:\\Users\\hokhan\\Desktop\\DataDrivenTest.xlsx");
        input = new FileInputStream(src);
        workbook = new XSSFWorkbook(input);

        sheet=workbook.getSheetAt(0);
        cell= sheet.getRow(0).getCell(0);

        Thread.sleep(3000);
        driver.findElement(By.id("io.selendroid.testapp:id/buttonStartWebview")).click();
        Thread.sleep(3000);
        driver.findElement(By.className("android.widget.EditText")).clear();
        driver.findElement(By.className("android.widget.EditText")).sendKeys(cell.getStringCellValue());
        driver.findElement(By.id("io.selendroid.testapp:id/goBack")).click();

    }

    public static void unhandledException() throws Exception{

        Thread.sleep(3000);
        Boolean isAppRunning = driver.getPageSource().contains("io.selendroid.testapp");
        Assert.assertTrue(isAppRunning);

        driver.findElement(By.id("io.selendroid.testapp:id/exceptionTestButton")).click(); //close, crash or send app into background now

        isAppRunning = driver.getPageSource().contains("io.selendroid.testapp");
        Assert.assertFalse(isAppRunning);

    }
}
