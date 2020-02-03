package commonUtils;

import java.io.IOException;

public class DriverKill {

	public static void main(String[] args) {
		try {
			Runtime.getRuntime().exec("taskkill /F /IM chromedriver_Win.exe /T");
			Runtime.getRuntime().exec("taskkill /F /IM geckodriver_Win.exe /T");
			Runtime.getRuntime().exec("taskkill /F /IM Microsoft Edge.exe /T");
		} catch (IOException e) { // TODO Auto-generated catch block e.printStackTrace();

		}
	}
}
