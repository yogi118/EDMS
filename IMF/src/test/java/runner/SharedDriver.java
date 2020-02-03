package runner;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import commonUtils.CUtil;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.AfterStep;
import cucumber.api.java.Before;
import io.restassured.RestAssured;

public class SharedDriver extends EventFiringWebDriver {
	private static WebDriver REAL_DRIVER = null;
	private static boolean takenScreenshot = false;
	

	private static final Thread CLOSE_THREAD = new Thread() {

		@Override
		public void run() {
			REAL_DRIVER.quit();
			try {
				Runtime.getRuntime().exec("taskkill /F /IM chromedriver_Win.exe /T");
				Runtime.getRuntime().exec("taskkill /F /IM geckodriver_Win.exe /T");
				Runtime.getRuntime().exec("taskkill /F /IM Microsoft Edge.exe /T");
			} catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();

			}
		}
	};

	/*
	 * webdriver setup param required browser,os
	 */
	static {
		String browser = System.getProperty("browser");
		if (browser == null) {
			browser = "chrome";
		}
		switch (browser) {
		case "firefox":
		case "FIREFOX":
			System.setProperty("webdriver.gecko.driver", "src/test/resources/Driver/geckodriver_Win.exe");
			ProfilesIni prof = new ProfilesIni();
			FirefoxProfile ffProfile = prof.getProfile("default");
			FirefoxOptions ffoptions = new FirefoxOptions();
			ffoptions.setProfile(ffProfile);
			ffoptions.setAcceptInsecureCerts(true);
			ffoptions.setCapability("marionette", true);
			ffoptions.setHeadless(true);
			ffoptions.setPageLoadStrategy(PageLoadStrategy.NONE);
			REAL_DRIVER = new FirefoxDriver(ffoptions);
			break;
		case "chrome":
		case "CHROME":
		default:
			ChromeOptions chromeoptions = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", "src/test/resource/Driver/chromedriver_Win.exe");
			chromeoptions.addArguments("--start-maximized");
			chromeoptions.setPageLoadStrategy(PageLoadStrategy.NONE);
			chromeoptions.addArguments("--incognito");
			chromeoptions.addArguments("enable-automation");
			chromeoptions.addArguments("--no-sandbox");
			chromeoptions.addArguments("--disable-infobars");
			chromeoptions.addArguments("--disable-dev-shm-usage");
			chromeoptions.addArguments("--disable-browser-side-navigation");
			chromeoptions.addArguments("--disable-gpu");
			chromeoptions.setAcceptInsecureCerts(true);
			REAL_DRIVER = new ChromeDriver(chromeoptions);
			break;
		case "chrome_win_headless":
		case "CHROME_WIN_HEADLESS":
			ChromeOptions chrome_win_headless = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", "src/test/resource/Driver/chromedriver_Win.exe");
			chrome_win_headless.addArguments("--start-maximized");
			chrome_win_headless.setPageLoadStrategy(PageLoadStrategy.NONE);
			chrome_win_headless.addArguments("--incognito");
			chrome_win_headless.addArguments("--headless");
			chrome_win_headless.addArguments("enable-automation");
			chrome_win_headless.addArguments("--window-size=1920,1080");
			chrome_win_headless.addArguments("--no-sandbox");
			chrome_win_headless.addArguments("--disable-infobars");
			chrome_win_headless.addArguments("--disable-dev-shm-usage");
			chrome_win_headless.addArguments("--disable-browser-side-navigation");
			chrome_win_headless.addArguments("--disable-gpu");
			chrome_win_headless.setAcceptInsecureCerts(true);
			REAL_DRIVER = new ChromeDriver(chrome_win_headless);
			break;
		case "chrome_linux_headless":
		case "CHROME_LINUX_HEADLESS":
			ChromeOptions chromeoptionsHeadless = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", "src/test/resource/Driver/chromedriver_linux");
			chromeoptionsHeadless.addArguments("--start-maximized");
			chromeoptionsHeadless.setPageLoadStrategy(PageLoadStrategy.NONE);
			chromeoptionsHeadless.addArguments("--incognito");
			chromeoptionsHeadless.addArguments("--headless");
			chromeoptionsHeadless.addArguments("enable-automation");
			chromeoptionsHeadless.addArguments("--window-size=1920,1080");
			chromeoptionsHeadless.addArguments("--no-sandbox");
			chromeoptionsHeadless.addArguments("--disable-infobars");
			chromeoptionsHeadless.addArguments("--disable-dev-shm-usage");
			chromeoptionsHeadless.addArguments("--disable-browser-side-navigation");
			chromeoptionsHeadless.addArguments("--disable-gpu");
			chromeoptionsHeadless.setAcceptInsecureCerts(true);
			REAL_DRIVER = new ChromeDriver(chromeoptionsHeadless);
			break;
		case "chrome_linux":
		case "CHROME_LINUX":
			ChromeOptions chromeoptionlinux = new ChromeOptions();
			System.setProperty("webdriver.chrome.driver", "src/test/resource/Driver/chromedriver_linux");
			chromeoptionlinux.addArguments("--start-maximized");
			chromeoptionlinux.setPageLoadStrategy(PageLoadStrategy.NONE);
			chromeoptionlinux.addArguments("--incognito");
			chromeoptionlinux.addArguments("enable-automation");
			chromeoptionlinux.addArguments("--no-sandbox");
			chromeoptionlinux.setBinary("opt/google/chrome");
			chromeoptionlinux.addArguments("--disable-infobars");
			chromeoptionlinux.addArguments("--disable-dev-shm-usage");
			chromeoptionlinux.addArguments("--disable-browser-side-navigation");
			chromeoptionlinux.addArguments("--disable-gpu");
			chromeoptionlinux.setAcceptInsecureCerts(true);
			REAL_DRIVER = new ChromeDriver(chromeoptionlinux);
			break;
		case "sauce_labs":
		case "SAUCE_LABS":
			DesiredCapabilities caps = DesiredCapabilities.chrome();
			caps.setCapability("platform", "Windows 10");
			caps.setCapability("platform", "Linux");
			caps.setCapability("platform", "macOS 10.13");
			caps.setCapability("version", "latest");
			caps.setCapability("name", "Test1");
			caps.setCapability("extendedDebugging", "true");
			caps.setCapability("buildNumber", "3.0");
			try {
				REAL_DRIVER = new RemoteWebDriver(new java.net.URL("URL"), caps);
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// implicit wait set up
		REAL_DRIVER.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
	}

	public SharedDriver() {
		super(REAL_DRIVER);
		// setter method call to get webdriver instance
		CUtil.setWebDriver(REAL_DRIVER);
	}

	@Override
	public void close() {
		if (Thread.currentThread() != CLOSE_THREAD) {
			throw new UnsupportedOperationException(
					"You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
		}
		super.close();
	}

	@Before
	/**
	 * Delete all cookies at the start of each scenario to avoid shared state
	 * between tests
	 */
	public void deleteAllCookies() {
		manage().deleteAllCookies();
	}

	@AfterStep
	public void embedScreenshotOnFailuer(Scenario scenario) {
		scenario.write("Current Page URL is " + getCurrentUrl());
		try {
			byte[] screenshot = getScreenshotAs(OutputType.BYTES);
			scenario.embed(screenshot, "image/png");
			takenScreenshot = true;
		} catch (WebDriverException somePlatformsDontSupportScreenshots) {
			System.err.println(somePlatformsDontSupportScreenshots.getMessage());
		}
	}

	@After
	/**
	 * Embed a screenshot in test report if test is marked as failed
	 */
	public void embedScreenshot(Scenario scenario) {
		if (!takenScreenshot) {
			scenario.write("Current Page URL is " + getCurrentUrl());
			try {
				byte[] screenshot = getScreenshotAs(OutputType.BYTES);
				scenario.embed(screenshot, "image/png");
			} catch (WebDriverException somePlatformsDontSupportScreenshots) {
				System.err.println(somePlatformsDontSupportScreenshots.getMessage());
			}
		}
	}

}
