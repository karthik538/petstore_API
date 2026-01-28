package stepDefinition;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.testng.Assert;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.ConfigReader;
import utils.TestContext;

public class StepDefinition {
	 private Response response;
	 private TestContext context = new TestContext();
	 
	@Given ("The API base URI is initialized from config")
	public void baseuri()
	{
		String baseUri = ConfigReader.getProperty("base.url");
		RestAssured.baseURI = baseUri;
	}
	
	@Given ("check if petid is present for petid {int}")
	public void validate_petid(int petid)
	{
		create_user(petid);
		Response checkResponse = RestAssured.given()
                .pathParam("petId", petid)
                .get("{petId}");
		
		Assert.assertEquals(checkResponse.getStatusCode(), 200);
		
	}
	public void create_user(int petid) {
	Map<String, Object> body = new HashMap<>();
    body.put("id", petid);
    body.put("name", "doggie");
    body.put("status", "available");
    
    Map<String, Object> category = new HashMap<>();
    category.put("id", petid);
    category.put("name", "Dogs");
    body.put("category", category);

    body.put("photoUrls", Arrays.asList("string"));   
    response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(body)
            .post("");
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as zerobyte in file system")
	public void file_zerobyte(int petid,String metadata)
	{
		create_user(petid);
		
		File fileToUpload = new File(ConfigReader.getProperty("test.data.zerobyte_file"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);

        response = request.post("{petId}/uploadImage");

	}
	
	@Then ("The response status code should be {int} and The response message should contain {string}")
	public void validate_status_code_response_message(int expectedCode,String expectedMsg)
	{
		Assert.assertEquals(response.getStatusCode(), expectedCode);
        
        JsonPath json = response.jsonPath();
        if(json.get("code") != null) {
            Assert.assertEquals(json.getInt("code"), expectedCode);
        }

        if (context.getMetadata() != null) {
        	String actualMsg = json.getString("message");
            Assert.assertTrue(actualMsg.contains(expectedMsg));
            Assert.assertTrue(actualMsg.contains(context.getMetadata()), "Metadata missing in response");
        }
        if (context.getFileName() != null) {
        	String actualMsg = json.getString("message");
            Assert.assertTrue(actualMsg.contains(expectedMsg));
            Assert.assertTrue(actualMsg.contains(context.getFileName()), "Filename missing in response");
        }
        
        context.reset();
	
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as doc file in file system")
	public void doc_file(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.docfile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);

        response = request.post("{petId}/uploadImage");
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as video file in file system")
	public void video_file(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.videofile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);

        response = request.post("{petId}/uploadImage");
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as null and file as null")
	public void null_file(int petid)
	{
		RequestSpecification request = RestAssured.given();
		context.setPetId(petid); 

        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        response = request.post("{petId}/uploadImage");
	}
	
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as image in file system and also add extra key value pair as key as hair value as white")
	public void extra_key_value_pair(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.imagefile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);
        request.multiPart("hair", "white");
        response = request.post("{petId}/uploadImage");
	}
	
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as .exe file in file system")
	public void exe_file(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.exefile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);

        response = request.post("{petId}/uploadImage");
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as image file with 256 chars name in file system")
	public void longname(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.longname"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);

        response = request.post("{petId}/uploadImage");
	}
	@When ("update the pet status as sold {int}")
		public void sold_status(int petid)
	{     
        RequestSpecification request = RestAssured.given();
        context.setPetId(petid); 
		context.setStatus("sold");
      
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("status", "sold");
        response = request.post("{petId}"); 
		
	}
	
	@When ("update parameters petid as {int}, additionalMetadata as {string} and file as image in file system and also upload another image in file system")
	public void images_upload_twice(int petid,String metadata)
	{
		File fileToUpload = new File(ConfigReader.getProperty("test.data.imagefile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);
        File fileToUpload2 = new File(ConfigReader.getProperty("test.data.longname"));
        request.multiPart("file", fileToUpload2);
        context.setFileName(fileToUpload.getName());
        response = request.post("{petId}/uploadImage");
	}
	@Then ("update parameters petid as {int}, additionalMetadata as {string} and file as image in file system")
	public void images(int petid,String metadata){
		File fileToUpload = new File(ConfigReader.getProperty("test.data.imagefile"));
		context.setPetId(petid); 
		context.setMetadata(metadata); 
		context.setFileName(fileToUpload.getName());
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        request.multiPart("additionalMetadata", metadata);
        request.multiPart("file", fileToUpload);
        response = request.post("{petId}/uploadImage");
	}
	
	//Req2
	static int randomNumber;
	public static int random() {
        int min = 10000; 
        int max = 99999; 
        int range = max - min + 1; 
        Random random = new Random();
        randomNumber = Math.abs(min + random.nextInt() % range); 
        return randomNumber;
    }
	
	@Given ("check if petid is present in api for petid random")
	public void check_random_petid_is_present() 
	{
		randomNumber = random();
		
	}
	
	@When ("update form data petid as random, and add remaining data in form data")
	public void update_payload()
	{
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
		//body.put("id", "2207");
        body.put("id", randomNumber);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        //category.put("id", "2207");
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	
	@Then ("The response status code should be {int}")
	public void response_code_check(int responseCode)
	{
		Assert.assertEquals(response.getStatusCode(), responseCode, "Status Code Mismatch!");
        String responseBody = response.getBody().asString();
        JsonPath jsonPath = response.jsonPath();
        if ("delete".equalsIgnoreCase(context.getOperation())) 
        {
        	String message = jsonPath.getString("message");
        	Assert.assertEquals(String.valueOf(context.getPetId()),message);
        	
        }
        else if (responseBody.trim().startsWith("[")) {
            List<Map<String, Object>> pets = jsonPath.getList("$");
            if (context.getStatus() != null && !pets.isEmpty()) {
                for (int i = 0; i < Math.min(pets.size(), 3); i++) {
                    String actualStatus = (String) pets.get(i).get("status");
                    if(context.getStatus().equals("available")) {
                        Assert.assertEquals(actualStatus, "available");
                    }
                }
            }
        }
        else if (responseBody.contains("\"code\"") && responseBody.contains("\"message\"")) {
            Assert.assertNotNull(jsonPath.get("code"), "Response missing 'code' field");     
            String actualMessage = jsonPath.getString("message");
            if (context.getPetId() != 0 && responseCode == 200) {
            }
        }
        else if (responseBody.contains("\"id\"") && responseBody.contains("\"name\"")) {
            int actualId = jsonPath.getInt("id");
            if (context.getPetId() != 0) {
                Assert.assertEquals(actualId, context.getPetId(), "Pet ID mismatch");
            }
            if (context.getName() != null) {
                Assert.assertEquals(jsonPath.getString("name"), context.getName(), "Pet Name mismatch");
            }
            if (context.getStatus() != null) {
                Assert.assertEquals(jsonPath.getString("status"), context.getStatus(), "Pet Status mismatch");
            }
        }

        context.reset();
	}
	
	@When("update form data petid as {int}, and add remaining data in form data")
	public void update_form_data_petid_as_and_add_remaining_data_in_form_data(int petid) {
		
		context.setPetId(petid);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
		
        body.put("id", petid);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", petid);
        //category.put("id", "2207");
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
	}
	
	@Given ("update form data as null")
	public void update_payload_null()
	{
		//context.setPetId(null);
        context.setName(null);
        context.setStatus(null);
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", null);
        body.put("name", null);
        body.put("status", null);
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", null);
        category.put("name", null);
        body.put("category", null);

        body.put("photoUrls", null);
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
		
	}
	
	@When ("update form data petid as random, and add remaining data in form data except name and try to update it")
	public void update_payload_without_name()
	{
		context.setPetId(randomNumber);
        //context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        //body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        //category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	@When ("update form data petid as random, and add remaining data as in form data and change name structure in form data")
	public void update_payload_change_structure()
	{
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
		
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	@When ("update form data petid as random, and add remaining data as in form data and add extra key value pair as key as hair value as white")
	public void update_payload_extra_key_value()
	{
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie");
        body.put("status", "available");
        body.put("hair", "white");
               
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	@Given ("check if petid is present in api for petid kjasdf")
	public void d()
	{
		
	}
	
	@When ("update form data petid as kjasdf, and add remaining data as in form data")
	public void string_new()
	{
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", "kjasdf");
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
	}
	
	@Given ("check if petid is present in api from for petid -1234")
	public void negative()
	{
		
	}
	
	@When ("update form data petid as -1234, and add remaining data as in form data")
	public void g()
	{
		context.setPetId(-1234);
        context.setName("doggie");
        context.setStatus("available");
		
		Map<String, Object> body = new HashMap<>();
        body.put("id", "-1234");
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", "-1234");
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");

	}
	
	@Given ("check if petid is present in api for petid 123456987")
	public void long_number()
	{
		
	}
	
	@When ("update form data petid as 123456987, and add remaining data as in form data")
	public void long_number_id()
	{
		context.setPetId(123456987);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", 123456987);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", 123456987);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
	}
	
	@When ("update form data petid as random, and add remaining data as in form data and in name use emoji or speical charecters")
	public void update_payload_special_char()
	{
        
		randomNumber = random();
		context.setPetId(randomNumber);
        context.setName("doggie*");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie*");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	@When ("update form data petid as random, and add remaining data as in form data but for name field add string of 1000 charecters")
	public void update_payload_long_string()
	{
		randomNumber = random();
		context.setPetId(randomNumber);
        context.setName("qwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnm");
        context.setStatus("available");
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "qwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnmqwertyuiopasdfghjklzxcvbnmqwertyuioppasffghjklzxcvvbnm");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	@When ("update form data petid as random, and add remaining data as in form data and use sql injection in name")
	public void update_payload_sql_injection()
	{
		randomNumber = random();
		context.setPetId(randomNumber);
        context.setName("doggie'");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie'");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
 
	}
	
	@When ("update form data petid as random, and add remaining data as in form data and except category object as null")
	public void update_payload_category_null()
	{
		randomNumber = random();
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie");
        body.put("status", "available");

        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("");
        
        System.out.println(randomNumber);
	}
	
	//Req3
	
	@Given ("update form data petid as {int}, and with valid form data")
	public void update_form(int petid)
	{
		context.setPetId(petid);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", petid);
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, and with null form data")
	public void update_form_null(int petid)
	{
		context.setPetId(petid);
        context.setName(null);
        context.setStatus(null);
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", null);
        body.put("status", null);
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", petid);
        category.put("name", null);
        body.put("category", category);
        
        body.put("photoUrls", null);
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, and with valid form data except name field")
	public void update_form_except_name(int petid)
	{
		context.setPetId(petid);
        context.setName("$doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "$doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, and without form data")
	public void update_form_without_formdata(int petid)
	{
		context.setPetId(petid);
        context.setName("");
        context.setStatus("");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "");
        body.put("status", "");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", "");
        category.put("name", "");
        body.put("category", category);
        
        body.put("photoUrls", "" );
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, and with valid form data and add extra key value pair as key as hair value as white")
	public void update_form_extra_key_value(int petid)
	{
		context.setPetId(petid);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        body.put("hair", "white");
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data with valid form data where petid as qwerty")
	public void update_form_petid_qwerty()
	{
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", "qwerty");
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", "qwerty");
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("check if petid is present in api")
	public void check_petid_is_present()
	{
		
	}
	
	@When ("update form data petid as random, and with valid form data")
	public void update_form_random()
	{
		randomNumber = random();
		context.setPetId(randomNumber);
        context.setName("doggie");
        context.setStatus("available");
		Map<String, Object> body = new HashMap<>();
        body.put("id", randomNumber);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, change only petid as {int}")
	public void update_form_petid(int petid, int newpetid)
	{
		//incorrect test case
		Map<String, Object> body = new HashMap<>();
        body.put("id", newpetid);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", newpetid);
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, change status as flying")
	public void update_form_status(int petid)
	{
		context.setPetId(petid);
        context.setName("doggie");
        context.setStatus("flying");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "doggie");
        body.put("status", "flying");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        
        body.put("photoUrls", Arrays.asList("string"));
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}
	
	@Given ("update form data petid as {int}, without any photoUrls")
	public void update_form_without_photourls(int petid)
	{
		context.setPetId(petid);
        context.setName("doggie");
        context.setStatus("available");
        
		Map<String, Object> body = new HashMap<>();
        body.put("id", petid);
        body.put("name", "doggie");
        body.put("status", "available");
        
        Map<String, Object> category = new HashMap<>();
        category.put("id", randomNumber);
        category.put("name", "Dogs");
        body.put("category", category);
        
        response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(body)
                .put("");
	}	
	
	@Given("check if petid is present in api petid random")
	public void check_if_petid_is_present_in_api_from_for_petid_random() {
		randomNumber=random();
		response = RestAssured.get(""+randomNumber);
	    
	}

	@Given ("find pets by status which are available, sold and pending")
	public void find_pets_by_status_all()
	{
		context.setStatus("available");
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "available");
        request.multiPart("status", "sold");
        request.multiPart("status", "pending");
        response = request.get("findByStatus");
		
	}
	
	@Given ("delete all pets where status is pending")
	public void find_pets_by_status_delete_pending()
	{
		RequestSpecification request = RestAssured.given();
        //request.baseUri(baseUrl);
        request.header("Content-Type", "application/json");
        request.queryParam("status", "pending");
        
        Response searchResponse = request.get("findByStatus");
       
        List<Object> pendingPetIds = searchResponse.jsonPath().getList("id");
        
        System.out.println("Found " + pendingPetIds.size() + " pending pets to delete. but not deleting as it may effect structure of api");
        /*for (Object petId : pendingPetIds) {
            RequestSpecification deleteRequest = RestAssured.given();
            //deleteRequest.baseUri(baseUrl);
            deleteRequest.header("Content-Type", "application/json");
            deleteRequest.pathParam("petId", petId);
            
            deleteRequest.delete("{petId}");
        }*/
	}
	
	@When ("find pets by status by only pending")
	public void find_pets_by_status_pending()
	{
		context.setStatus("available");
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "pending");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status as null")
	public void find_pets_by_status_null()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status which are available and sold")
	public void find_pets_by_status_available_sold()
	{
		context.setStatus("sold");
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "available");
        request.multiPart("status", "sold");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status which are flying")
	public void find_pets_by_status_invalid()
	{
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "flying");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status which are available and give extra key value pair as hair and its value as white")
	public void find_pets_by_status_extra_key_value()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "available");
        request.multiPart("hair", "white");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status which are AVAILABLE")
	public void find_pets_by_status_cap_letters()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "AVAILABLE");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by status which by using sql injection in name")
	public void find_pets_by_status_sql_injectiion()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.multiPart("status", "'available");
        response = request.get("findByStatus");
	}
	
	@Given ("find pets by petid {int}")
	public void find_pets_by_petid(int petid)
	{
		context.setPetId(petid);
		
		update_form(petid);
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        response = request.get("{petId}");
	}
	
	@Given ("find pets by petid qwerty")

	public void find_pets_by_petid_char()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "qwerty");
        response = request.get("{petId}");
	}
	
	@Given ("find pets by petid random")
	public void find_pets_by_petid_random()
	{
		int petid = random();
		context.setPetId(petid);
		update_form(petid);
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        response = request.get("{petId}");
	}
	
	@Given ("find pets by petid as null")
	public void find_pets_by_petid_null()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "");
        response = request.get("{petId}");
	}
	
	@Given ("find pets by petid as #2208")
	public void find_pets_by_petid_special_char()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "#2208");
        response = request.get("{petId}");
	}
	
	@Given ("find pets by petid as 2208 and give extra key value pair as hair and its value as white")
	public void find_pets_by_petid_extra_key_value_pair()
	{
		context.setPetId(2208);
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "2208");
        
        response = request.get("{petId}");
	}
	
	/*@Given ("find pets by petid as 0")
	public void find_pets_by_petid_0()
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "0");
        response = request.get("{petId}");
	}*/
	
	@Given ("find pets by petid as random large int")
	public void find_pets_by_petid_long_int()
	{
		randomNumber=random();
		context.setPetId(randomNumber);
		
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", "randomNumber");
        response = request.get("{petId}");
	}
	
	@Given ("delete by petid using {int}")
	public void delete_by_petid_(int petid)
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", petid);
        
		request.delete("{petId}");
	}
	
	@When ("find pets by petid as {int}")
	public void find_pets_by_petid_specific(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("accept", "application/json");
        request.pathParam("petId", petid);
        response = request.get("{petId}");
	}
	
	@Given ("update form data petid as {int}, with valid status and valid name")
	public void update_form_valid(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "Buddy");
        request.formParam("status", "available");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as {int}, with status as flying and valid name")
	public void update_form_invalid_status(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "Buddy");
        request.formParam("status", "flying");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as {int}, with status and name as @kruse#")
	public void update_form_name_special_charecter(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "@kruse#");
        request.formParam("status", "pending");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as {int}, with status as flying and name as @kruse#")
	public void update_form_inavlid_name_and_status(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "@kruse#");
        request.formParam("status", "flying");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as null, with valid status and valid name")
	public void update_form_petid_null()
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.formParam("name", "kruse");
        request.formParam("status", "pending");

        try {
            request.pathParam("petId", null); 
            
            response = request.post("{petId}");
            
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
        }
	}
	
	@Given ("update form data petid as {int}, with valid status and valid name and give extra key value pair as hair and its value as white")
	public void update_form_extra_key_value_pair(int petid)
	{
		RequestSpecification request = RestAssured.given();
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "@kruse#");
        request.formParam("status", "pending");
        request.formParam("hair", "white");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as {int}, with status and name as null")
	public void update_form_name_null(int petid)
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "");
        request.formParam("status", "pending");
        response = request.post("{petId}");
	}
	
	@Given ("update form data petid as {int}, with status as null and name")
	public void update_form_status_null(int petid)
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "kruse");
        request.formParam("status", "");
        response = request.post("{petId}");
		
	}
	
	@Given ("update form data petid as {int}, with status and name as encoded data")
	public void update_form_encoded_name(int petid)
	{

		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/x-www-form-urlencoded");
        request.header("accept", "application/json");
        request.pathParam("petId", petid);

        request.formParam("name", "hello%20world");
        request.formParam("status", "pending");
        response = request.post("{petId}");
	}
	
	@Given ("delete pet by petid {int}")
	public void delete_by_petid(int petid)
	{
		context.setPetId(petid);
		context.setOperation("delete");
		update_form(petid);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", petid);
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}
	
	@Given ("delete pet by petid qwerty")
	public void delete_by_petid_char()
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", "qwerty");
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}
	
	@Given ("delete pet by petid random")
	public void delete_by_petid_random()
	{
		randomNumber=random();
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", randomNumber);
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}
	
	@Given ("delete pet by petid as null")
	public void delete_by_petid_null()
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", "");
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}
	
	@Given ("delete pet by petid as #2208")
	public void delete_by_petid_special_char()
	{
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", "#2208");
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}
	
	@Given ("delete pet by petid as {int} and give extra key value pair as hair and its value as white")
	public void delete_by_petid_extra_key_value_pair(int petid)
	{
		context.setPetId(petid);
		context.setOperation("delete");
		update_form(petid);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", petid);
		request.header("api_key","special-key");
		request.header("hair","white");
		response = request.delete("{petId}");
	}
	
	@Given ("find pet by petid as 2207 and without api_key header")
	public void delete_by_petid_witout_api()
	{	
		update_form(2207);
		context.setPetId(2207);
		context.setOperation("delete");
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", "2207");
		response = request.delete("{petId}");
	}
	
	@Given ("find pet by petid as random and without api_key header")
	public void delete_by_petid_random_without_api()
	{
		context.setPetId(randomNumber);
		context.setOperation("delete");
		update_form(randomNumber);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", randomNumber);
		response = request.delete("{petId}");
	}
	
	@Given ("find pet by petid as {int} and delete again with 2207")
	public void delete_by_petid_delete_twice(int petid)
	{

		update_form(petid);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.pathParam("petId", petid);
		request.header("api_key","special-key");
		response = request.delete("{petId}");
	}

}
