package qmul;

import io.appium.java_client.windows.WindowsDriver;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.*;
import java.util.concurrent.TimeUnit;

public class MiCollab {

    public static WindowsDriver driver = null;

    @BeforeMethod
    public void setUp() throws IOException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("app", "C:\\Program Files (x86)\\Mitel\\MiCollab\\MiCollab.exe");
        desiredCapabilities.setCapability("platformName", "Windows");
        desiredCapabilities.setCapability("deviceName", "WindowsPC");
        try {
            driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void cleanUp() throws MalformedURLException, InterruptedException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("app", "Root");
        driver = new WindowsDriver(new URL("http://127.0.0.1:4723"), desiredCapabilities);
        Actions actions = new Actions(driver);
        actions.contextClick(driver.findElement(By.name("MiCollab - 1 running window"))).perform();
        Thread.sleep(1000);
        driver.findElement(By.name("Quit MiCollab")).click();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void outgoingCallTest() throws InterruptedException, IOException {
        String phoneNumber = "+447526537662";
        Thread.sleep(20000);
        driver.findElement(By.name("System")).click();
        Actions action = new Actions(driver);
        action.sendKeys(Keys.ENTER).perform();
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(screenshot, new File(System.getProperty("user.dir") + "\\Screenshot.png"));
        BufferedImage expected = ImageIO.read(new File(System.getProperty("user.dir") + "\\ScreenshotExpected.png"));
        BufferedImage actual = ImageIO.read(screenshot);
        ImageDiffer imageDiffer = new ImageDiffer();
        ImageDiff diff = imageDiffer.makeDiff(expected, actual);
        System.out.println("diff.hasDiff() = " + diff.hasDiff());
        Actions actions = new Actions(driver);
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(Keys.ENTER).perform();
        actions.sendKeys(Keys.TAB).perform();
        actions.sendKeys(phoneNumber).perform();
        actions.sendKeys(Keys.ENTER).perform();
        Thread.sleep(2000);
        String xpath1 = "//Image[@AutomationId='PART_image'][1]";
        driver.findElement(By.xpath(xpath1)).click();
        Thread.sleep(2000);
        actions.keyDown(Keys.CONTROL).sendKeys(Keys.NUMPAD2).perform();
        actions.release().sendKeys(Keys.CONTROL).perform();
    }

    @Test
    public void














    incomingCallTest() throws InterruptedException, IOException {
        int testDurationInMinutes = 2;
        LocalTime startTime = LocalTime.now();
        LocalTime finalTime = startTime.plus(Duration.ofMinutes(testDurationInMinutes));
        Thread.sleep(20000);
        driver.findElement(By.name("System")).click();
        driver.findElement(By.name("Maximize")).click();
        int numberOfCalls = 0;
        while (Duration.between(finalTime, LocalTime.now()).getSeconds() < 0) {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.ENTER).perform();
            Thread.sleep(2000);
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(screenshot, new File(System.getProperty("user.dir") + "\\Screenshot1.png"));
            BufferedImage expected = ImageIO.read(new File("src/test/resources/beforecall.png"));
            BufferedImage actual = ImageIO.read(screenshot);
            if (isSimilar(actual, expected)) {
                boolean flag = true;
                while (flag) {
                    Actions actions = new Actions(driver);
                    actions.sendKeys(Keys.ENTER).perform();
                    actions.keyDown(Keys.CONTROL).sendKeys(Keys.NUMPAD1).perform();
                    actions.release().sendKeys(Keys.CONTROL).perform();
                    Thread.sleep(1000);
                    File screenshot1 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                    FileHandler.copy(screenshot1, new File(System.getProperty("user.dir") + "\\Screenshot2.png"));
                    BufferedImage actual2 = ImageIO.read(screenshot1);
                    actions.sendKeys(Keys.ENTER).perform();
                    actions.keyDown(Keys.CONTROL).sendKeys(Keys.NUMPAD1).perform();
                    actions.release().sendKeys(Keys.CONTROL).perform();
                    flag = isSimilar(actual2, expected);
                    if(isSimilar(actual2,expected)){
                        System.out.println("Waiting for the call...");
                    }
                    else System.out.println("Call answered");
                }
                //call duration
                Thread.sleep(3000);
                File screenshot2 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileHandler.copy(screenshot2, new File(System.getProperty("user.dir") + "\\callInProgress.png"));
                Thread.sleep(3000);
                action.keyDown(Keys.CONTROL).sendKeys(Keys.NUMPAD2).perform();
                action.release().sendKeys(Keys.CONTROL).perform();
                numberOfCalls++;
                File screenshot3 = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileHandler.copy(screenshot3, new File(System.getProperty("user.dir") + "\\afterCall.png"));
                Thread.sleep(1000);
                System.out.println("=============================" +
                        "");
            }
        }
        System.out.println("numberOfCalls = " + numberOfCalls);
        Thread.sleep(1000);
    }

    public boolean isSimilar(BufferedImage actual, BufferedImage expectedImage) {
        double percentage = 1000;
        int w1 = actual.getWidth();
        int w2 = expectedImage.getWidth();
        int h1 = actual.getHeight();
        int h2 = expectedImage.getHeight();
        if ((w1 != w2) || (h1 != h2)) {
            System.out.println("Both images should have same dimensions");
        } else {
            long diff = 0;
            for (int j = 0; j < h1; j++) {
                for (int i = 0; i < w1; i++) {
                    //Getting the RGB values of a pixel
                    int pixel1 = actual.getRGB(i, j);
                    Color color1 = new Color(pixel1, true);
                    int r1 = color1.getRed();
                    int g1 = color1.getGreen();
                    int b1 = color1.getBlue();
                    int pixel2 = expectedImage.getRGB(i, j);
                    Color color2 = new Color(pixel2, true);
                    int r2 = color2.getRed();
                    int g2 = color2.getGreen();
                    int b2 = color2.getBlue();
                    //sum of differences of RGB values of the two images
                    long data = Math.abs(r1 - r2) + Math.abs(g1 - g2) + Math.abs(b1 - b2);
                    diff = diff + data;
                }
            }
            double avg = diff / (w1 * h1 * 3);
            percentage = (avg / 255) * 100;
            //System.out.println("Difference: " + percentage);
        }
        if (percentage > 2) {
            return false;
        } else return true;
    }


}
