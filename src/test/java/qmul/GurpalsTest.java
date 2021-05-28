package qmul;

import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class GurpalsTest {

    private static WindowsDriver micollabSession = null;

    public static String getDate(){
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    @BeforeClass
    public static void setUp() throws IOException {


        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Program Files (x86)\\Mitel\\MiCollab\\MiCollab.exe");
            capabilities.setCapability("platformName","Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            micollabSession = new WindowsDriver(new URL("http://127.0.0.1:4723"), capabilities);
            micollabSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterClass
    public static void cleanApp(){
        //micollabSession.quit();
        //setUp();
    }

	/*
	@After
	 public void tearDown(){
	 notepadSession.quit();
	 }
	*/

    @Test
    public void clickHome() throws InterruptedException {

        String xpath1 = "//Image[@AutomationId='PART_image'][1]";
        String xpath2 = "//Image[@AutomationId='PART_image'][1]";
        String xpath3 = "//Image[@AutomationId='PART_image'][2]";
        String xpath4 = "//Image[@AutomationId='PART_image'][3]";
        String xpath5 = "//Image[@AutomationId='PART_image'][4]";

        micollabSession.findElementByXPath(xpath1).click();
        Thread.sleep(2000);
        micollabSession.findElementByXPath(xpath2).click();
        Thread.sleep(2000);
//		micollabSession.findElementByXPath(xpath3).click();
        Thread.sleep(2000);
        micollabSession.findElementByXPath(xpath4).click();
        Thread.sleep(2000);
        micollabSession.findElementByXPath(xpath5).click();
        Thread.sleep(2000);

        //micollabSession.findElementByXPath("//CheckBox[@Name='checkBox2'][2]").click();

        Thread.sleep(5000);
        micollabSession.findElementByName("Minimise").click();
        //micollabSession.findElementByAccessibilityId("PART_image").click();
    }

    @Test
    public void sendTestText(){
        //micollabSession.findElementByName("Help").click();
    }

    @Test()
    public void pressTimeAndDateButton(){
        //micollabSession.findElementByName("Help").click();
    }

}
