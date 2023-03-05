package main;

import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.touch.offset.PointOption;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@DisplayName("Проведем тест калькулятора на: ")
public class CalculatorAutoTest {

    static AndroidDriver driver;
    static DesiredCapabilities desiredCapabilities;
    static URL remoteUrl;

    @BeforeAll
    public static void setUp() throws MalformedURLException {
        remoteUrl = new URL("http://localhost:4723/wd/hub");

        desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("appium:deviceName", "Pixel 2");
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("appium:app", "E:\\A\\APK\\Calculator.apk");
        desiredCapabilities.setCapability("appium:noReset", true);
        desiredCapabilities.setCapability("appium:ensureWebviewsHavePages", true);
        desiredCapabilities.setCapability("appium:nativeWebScreenshot", true);
        desiredCapabilities.setCapability("appium:newCommandTimeout", 3600);
        desiredCapabilities.setCapability("appium:connectHardwareKeyboard", true);
        desiredCapabilities.setCapability("appium:setWebContentsDebuggingEnabled", true);

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
    }
    public void cleanWindow(){
        WebElement cleanWindow = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"clear\"]"));
        cleanWindow.click();
    }

    public void moreOptions() {
        WebElement buttonMoreOptions = driver.findElement(By.xpath("//android.widget.ImageView[@content-desc=\"More options\"]"));
        buttonMoreOptions.click();

        Set<String> openMoreOptions = driver.getContextHandles();
        for (String contextName : openMoreOptions) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName);
            }
        }
    }

    @DisplayName("проверку умножения.")
    @Test
    public void shoulSuccessfullyMultiplyTest(){
        WebElement buttonThree = driver.findElement(By.id("com.google.android.calculator:id/digit_3"));
        buttonThree.click();

        WebElement buttonMultiply = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"multiply\"]"));
        buttonMultiply.click();

        WebElement buttonFive = driver.findElement(By.id("com.google.android.calculator:id/digit_5"));
        buttonFive.click();

        WebElement buttonEquals = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"equals\"]"));
        buttonEquals.click();

        String multiplicationResult = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();
        cleanWindow();
        Assertions.assertEquals("15", multiplicationResult);
    }

    @DisplayName("вычесление корня.")
    @Test
    public void shouldSuccessfullyRootCalculationTest(){
        WebElement buttonRoot = driver.findElement(By.id("com.google.android.calculator:id/op_sqrt"));
        buttonRoot.click();

        WebElement buttonThree = driver.findElement(By.id("com.google.android.calculator:id/digit_3"));
        buttonThree.click();

        WebElement buttonTwo = driver.findElement(By.id("com.google.android.calculator:id/digit_2"));
        buttonTwo.click();

        WebElement buttonFour = driver.findElement(By.id("com.google.android.calculator:id/digit_4"));
        buttonFour.click();

        WebElement buttonEquals= driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"equals\"]"));
        buttonEquals.click();

        String rootCalculationResult = driver.findElement(By.id("com.google.android.calculator:id/result_final")).getText();
        cleanWindow();
        Assertions.assertEquals("18", rootCalculationResult);
    }

    @DisplayName("деление на 0.")
    @Test
    public void shouldDivisionByZeroTest() {
        WebElement buttonFive = driver.findElement(By.id("com.google.android.calculator:id/digit_5"));
        buttonFive.click();

        WebElement buttonDivide = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"divide\"]"));
        buttonDivide.click();

        WebElement buttonZero = driver.findElement(By.id("com.google.android.calculator:id/digit_0"));
        buttonZero.click();

        WebElement buttonEquals = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"equals\"]"));
        buttonEquals.click();

        String divisionByZeroResult = driver.findElement(By.id("com.google.android.calculator:id/result_preview")).getText();
        cleanWindow();
        Assertions.assertEquals("Can't divide by 0", divisionByZeroResult);
    }

    @DisplayName("вычисление cos.")
    @Test
    public void shouldCosineСalculationTest() {
        WebElement buttonExpand = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Expand\"]"));
        buttonExpand.click();

        WebElement buttonCosine = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"cosine\"]"));
        buttonCosine.click();

        WebElement buttonNine = driver.findElement(By.id("com.google.android.calculator:id/digit_9"));
        buttonNine.click();

        WebElement buttonEight = driver.findElement(By.id("com.google.android.calculator:id/digit_8"));
        buttonEight.click();

        WebElement buttonEquals = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"equals\"]"));
        buttonEquals.click();

        String cosineСalculationResult = driver.findElement(By.id("com.google.android.calculator:id/symbolic")).getText();
        cleanWindow();
        Assertions.assertEquals("-sin(8)", cosineСalculationResult);

        WebElement buttonCollapse = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Collapse\"]"));
        buttonCollapse.click();
    }

    @DisplayName("просмотр истории.")
    @Test
    public void shouldOpenHistoryTabTest() {
        moreOptions();

        Set<String> HistoryTab = driver.getContextHandles();
        for (String contextName : HistoryTab) {
            if (contextName.contains("NATIVE_APP")){
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[1]/android.widget.LinearLayout")).click();
            }
        }

        String openHistoryTabResult = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.view.ViewGroup/android.widget.TextView")).getText();

        TouchAction swipeUp1 = new TouchAction(driver);
        PointOption poStartUp1 = new PointOption();
        poStartUp1.withCoordinates(700, 2600);
        PointOption poEndUp1 = new PointOption();
        poEndUp1.withCoordinates((700), (800));
        swipeUp1.longPress(poStartUp1).moveTo(poEndUp1).release().perform();


        TouchAction swipeUp2 = new TouchAction(driver);
        PointOption poStartUp2 = new PointOption();
        poStartUp2.withCoordinates(700, 1350);
        PointOption poEndUp2 = new PointOption();
        poEndUp2.withCoordinates((700), (200));
        swipeUp2.longPress(poStartUp2).moveTo(poEndUp2).release().perform();

        Assertions.assertEquals("History", openHistoryTabResult);
    }

    @DisplayName("смену темы.")
    @Test
    public void  shouldChooseThemeTest() {
        moreOptions();

        Set<String> themeTab = driver.getContextHandles();
        for (String contextName : themeTab) {
            if (contextName.contains("NATIVE_APP")){
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout")).click();
            }
        }

        Set<String> darkThemeTab = driver.getContextHandles();
        for (String contextName : darkThemeTab) {
            if (contextName.contains("NATIVE_APP")){
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]")).click();
            }
        }

        Set<String> buttonOK = driver.getContextHandles();
        for (String contextName : buttonOK) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[2]")).click();
            }
        }

        moreOptions();

        Set<String> themeCheck = driver.getContextHandles();
        for (String contextName : themeCheck) {
            if (contextName.contains("NATIVE_APP")){
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout")).click();
            }
        }

        WebElement chooseThemeResult = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.FrameLayout/android.widget.ListView/android.widget.CheckedTextView[2]"));
        boolean isSelected = chooseThemeResult.isSelected();
        if(isSelected == true){}

        Set<String> buttonOK2 = driver.getContextHandles();
        for (String contextName : buttonOK2) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[2]")).click();
            }
        }
    }

    @DisplayName("отправку отзыва.")
    @Test
    public void shouldSendFeedbackTest() {
        moreOptions();

        Set<String> feedbackTab = driver.getContextHandles();
        for (String contextName : feedbackTab) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[3]/android.widget.LinearLayout")).click();
            }
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement editText = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout[2]/android.widget.FrameLayout/android.widget.ScrollView/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.EditText"));
        editText.sendKeys("Text for feedback");

        WebElement checkBoxLogs = driver.findElement(By.id("com.google.android.gms:id/gf_include_logs"));
        checkBoxLogs.click();

        WebElement sendFeedback = driver.findElement(By.id("com.google.android.gms:id/common_send"));
        sendFeedback.click();

        String sendFeedbackResult = driver.findElement(By.xpath("/hierarchy/android.widget.Toast")).getText();
        Assertions.assertEquals("Thank you for the feedback", sendFeedbackResult);
    }

    @DisplayName("просмотр статьи.")
    @Test
    public void shouldOpenArticleTest() {
        moreOptions();

        Set<String> helpTab = driver.getContextHandles();
        for (String contextName : helpTab) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout[4]/android.widget.LinearLayout")).click();
            }
        }

        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        WebElement openArticle = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.LinearLayout[1]/android.widget.LinearLayout/android.widget.TextView"));
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        openArticle.click();

        String openArticleResult = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.FrameLayout/android.widget.LinearLayout/android.webkit.WebView/android.webkit.WebView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View[1]")).getText();

        WebElement closeArticle = driver.findElement(By.xpath("//android.widget.ImageButton[@content-desc=\"Close\"]"));
        closeArticle.click();

        Assertions.assertEquals("Use your Calculator app", openArticleResult);
    }

    @DisplayName("очищение истории.")
    @Test
    public void shouldCleanHistoryTest() throws InterruptedException {

        TouchAction swipeDown = new TouchAction(driver);
        PointOption poStartDown = new PointOption();
        poStartDown.withCoordinates(700, 800);
        PointOption poEndDown = new PointOption();
        poEndDown.withCoordinates(700, 2700);
        swipeDown.longPress(poStartDown).moveTo(poEndDown).release().perform();

        WebElement buttonMoreOptions = driver.findElement(By.xpath("(//android.widget.ImageView[@content-desc=\"More options\"])[1]"));
        buttonMoreOptions.click();

        Set<String> openMoreOptions = driver.getContextHandles();
        for (String contextName : openMoreOptions) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName);
            }
        }

        Set<String> buttonClean = driver.getContextHandles();
        for (String contextName : buttonClean) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.ListView/android.widget.LinearLayout/android.widget.LinearLayout")).click();
            }
        }

        Set<String> confirmationClean = driver.getContextHandles();
        for (String contextName : confirmationClean) {
            if (contextName.contains("NATIVE_APP")) {
                driver.context(contextName).findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.support.v7.widget.LinearLayoutCompat/android.widget.ScrollView/android.widget.LinearLayout/android.widget.Button[2]")).click();
            }
        }

        Thread.sleep(1500);

        swipeDown.longPress(poStartDown).moveTo(poEndDown).release().perform();

        String cleanHistoryResult = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.view.ViewGroup/android.widget.LinearLayout[1]/android.support.v7.widget.RecyclerView/android.widget.FrameLayout/android.widget.TextView")).getText();
        Assertions.assertEquals("No History", cleanHistoryResult);

        TouchAction swipeUp1 = new TouchAction(driver);
        PointOption poStartUp1 = new PointOption();
        poStartUp1.withCoordinates(700, 2600);
        PointOption poEndUp1 = new PointOption();
        poEndUp1.withCoordinates((700), (800));
        swipeUp1.longPress(poStartUp1).moveTo(poEndUp1).release().perform();

        TouchAction swipeUp2 = new TouchAction(driver);
        PointOption poStartUp2 = new PointOption();
        poStartUp2.withCoordinates(700, 1350);
        PointOption poEndUp2 = new PointOption();
        poEndUp2.withCoordinates((700), (200));
        swipeUp2.longPress(poStartUp2).moveTo(poEndUp2).release().perform();

    }
}
