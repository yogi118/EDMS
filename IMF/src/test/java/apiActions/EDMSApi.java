package apiActions;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import commonUtils.APIUtil;
import io.cucumber.datatable.DataTable;

public class EDMSApi {

	public void doPost(Map<String, String> map) {
		APIUtil.post(map);
	}

	public void generateEdmsScroe(String fileName, String trans_Id, String controType) {
		Map<String, String> headers = new HashMap<String,String>();
		headers.put("trans_id", trans_Id);
		headers.put("file_name", fileName);
		String filePath = String.join(File.separator, System.getProperty("user.dir"), "src", "test", "resource",
				"PDF_Files",fileName);
		System.out.println(filePath);
		File pdfFile = new File(filePath);
		APIUtil.post(headers, controType, pdfFile);
	}
	
	public void verifyResponse() {
		APIUtil.validResponse();
	}

	public void verifyBodyContent(DataTable dataTable) {
	
		List<List<String>> test = dataTable.asLists();
		for (List<String> list : test) {
			String attribute = list.get(0);
			String expectedValue = list.get(1);
			assertEquals("Response body is not as expected", APIUtil.response.extract().path(attribute).toString(), expectedValue);
		}
	}
	
	public void doGet(String url) {
		APIUtil.get(url);
	}
}
