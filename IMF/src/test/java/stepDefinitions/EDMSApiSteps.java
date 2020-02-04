package stepDefinitions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import apiActions.EDMSApi;
import commonUtils.APIUtil;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.cucumber.datatable.DataTable;
import io.restassured.RestAssured;

public class EDMSApiSteps {

	private EDMSApi edmsAPI;

	public EDMSApiSteps() {
		edmsAPI = new EDMSApi();
	}

	@Given("I have {string} and {string} for EDMS")
	public void i_have_and_for_fari_model(String baseURL, String basePath) {
		RestAssured.baseURI = baseURL;
		RestAssured.basePath = basePath;
	}

	@Then("I should get valid response")
	public void i_should_get_valid_response() {
		edmsAPI.verifyResponse();
	}

	@Then("Response body should contain expected values")
	public void response_body_should_contain_expected_values(DataTable dataTable) {
		edmsAPI.verifyBodyContent(dataTable);
	}

	@When("I want hit {string} of the mulesoft GET api request")
	public void i_want_hit_of_the_mulesoft_GET_api_request(String url) {
		edmsAPI.doGet(url);
	}
	
	@Then("Mulesoft should API shoul up and body should repsond with {string} message")
	public void mulesoft_should_API_shoul_up_and_body_should_repsond_with_message(String message) {
		assertEquals("Mulesoft application is not up", message, APIUtil.response.extract().htmlPath().getString("html.body"));
	}
	
	
	@When("I want to get EDMS score for {string} file and {string} transcation along with {string} control type")
	public void i_want_to_get_EDMS_score_for_file_and_transcation_along_with_control_type(String fileName, String trans_Id, String controType) {
		edmsAPI.generateEdmsScroe(fileName, trans_Id, controType);
	}
	
	@When("I want to upload {string} file for {string} transcation along with {string} control type")
	public void i_want_to_upload_file_for_transcation_along_with_control_type(String fileName, String trans_Id, String controType) {
	    edmsAPI.uploadFile(fileName, trans_Id, controType);
	}
	
	@Then("File should successfully uploaded in the EDMS system")
	public void file_should_successfully_uploaded_in_the_EDMS_system() {
	    String fileUploadId = edmsAPI.getFileUploadId();
	    assertTrue("File is not uploaded successfully", !fileUploadId.isEmpty());
	}
}
