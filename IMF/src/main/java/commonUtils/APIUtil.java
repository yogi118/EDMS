package commonUtils;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.Map;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class APIUtil {

	public static ValidatableResponse response;

	public static void post(Map<String, String> body) {
		response = given().contentType(ContentType.JSON).body(body).when().post().then().log().all();
	}
	
	public static void post(Map<String, String> headers, String contentType, File file) {
		response = given().multiPart(contentType, file).headers(headers).when().post().then().log().all();
	}
	
	public static void get(String url) {
		response = given().when().get(url).then().log().all();
	}

	public static ValidatableResponse validResponse() {
		return response.statusCode(200);
	}
}
