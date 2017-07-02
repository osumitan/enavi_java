package jp.gr.java_conf.osumitan.enavi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * enaviメイン
 */
public class Main {

	/**
	 * メイン
	 * @param args 引数
	 */
	public static void main(String[] args) {
		try {
			// パラメータ
			String url = args[0];
			// スタート
			new Main().start(url);

		} catch(Throwable e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンストラクタ
	 */
	private Main() {

	}

	/**
	 * スタート
	 * @param url URL
	 * @exception IOException
	 */
	private void start(String url) throws IOException {
		// ドライバ初期化
		RemoteWebDriver driver = initDriver();
		// ページを開く
		driver.get(url);
		// 店リストを出力
		for(WebElement div : driver.findElementsByCssSelector("td.aB3 div")) {
			WebElement a = div.findElement(By.cssSelector("a"));
			String shopUrl = a.getAttribute("href");
			BufferedReader lnr = new BufferedReader(new StringReader(div.getText()));
			String shopName = lnr.readLine();
			String shopArea = lnr.readLine();
			System.out.printf("%s\t%s\t%s\n", shopName, shopArea, shopUrl);
		}
		// クローズ
		driver.close();
	}

	/**
	 * ドライバ初期化
	 * @return ドライバ
	 */
	private RemoteWebDriver initDriver() {
		System.setProperty("webdriver.chrome.driver", "./driver/chromedriver");
		RemoteWebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(800, 600));
		driver.manage().window().setPosition(new Point(520, 120));
		driver.manage().timeouts().pageLoadTimeout(20L, TimeUnit.SECONDS);
		return driver;
	}
}
