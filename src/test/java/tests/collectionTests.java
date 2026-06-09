package tests;
import static io.restassured.RestAssured.*;

import java.util.HashMap;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.baseTest;
import endpoints.routes;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import utilities.configReader;

import payloads.objectPayload;
import specifications.requestSpecifications;
import specifications.responseSpecifications;
import testData.dataProviders;

public class collectionTests extends baseTest {
	String objectId;
	@Test
	public void basicTest() {
		Response res=
		given()
			.spec(requestSpecifications.getRequestSpec())
			.pathParam("collectionName", configReader.getProperty("collectionName"))
		.when()
			.get(routes.GET_OBJECTS_IN_COLLECTION);
		
		res
		.then()
			.spec(responseSpecifications.getResponseSpec());
		System.out.println("statuscode="+ res.statusCode());
		res.prettyPrint();
		
	}
	@Test
	public void getCollections() {
		given()
			.spec(requestSpecifications.getRequestSpec())
			.get(routes.GET_COLLECTIONS)
			.then().statusCode(200).log().all();
		
	}
	
	@Test
	public void getCollectionsUsingResponse() {

	    Response response =

	            given()

	                .header(
	                        "x-api-key",
	                        configReader.getProperty("apiKey"))

	            .when()

	                .get(routes.GET_COLLECTIONS);
	    System.out.println(response.asPrettyString());

	    System.out.println(
	            "Status Code : "
	            + response.getStatusCode());

	    System.out.println(
	            "Response Body : "
	            + response.getBody().asString());
	}
	
	
	@Test
	public void getObjectsFromCollections() {
		
		
		
		Response response=
				given()
					.header("x-api-key", configReader.getProperty("apiKey"))
					.pathParam("collectionName", configReader.getProperty("collectionName")) //Users //products
				.when()
					.get(routes.GET_OBJECTS_IN_COLLECTION);
		JsonPath jp= response.jsonPath();
		
		System.out.println(response.asPrettyString());
		
//		objectId= jp.getString("id");
//		System.out.println(objectId);
		
		/////print json response
		
		System.out.println(jp.getString("[0].name"));
		System.out.println(jp.getString("[0].data.price"));
		///////print header - content type
		System.out.println("header contenttype-"+response.getHeader("content-type"));
//		
		/////response time
		
		long time= response.time();
		System.out.println("response time : "+time);
		
		///////////validations
//		Assert.assertEquals(jp.getString("[0].name"), "Apple Himanshu 1");
//		
//		Assert.assertEquals(jp.getInt("[0].data.year"), 2019);
//		Assert.assertTrue(jp.getFloat("[1].data.age")>18);
//		Assert.assertTrue(
//				response.getHeader("content-Type")
//					.contains("application/json"));
//		
//		Assert.assertTrue(response.time()<5000);
		

		
		
	}
	
	@Test
	public void createObjectInCollection() {
		
		String requestBody = """
	            {
	              "name": "HK Laptop",
	              "data": {
	                "year": 2026,
	                "price": 50000,
	                "CPU model": "Intel i7",
	                "Hard disk size": "1 TB"
	              }
	            }
	            """;

	    Response response =

	            given()

	                .header(
	                        "x-api-key",
	                        configReader.getProperty("apiKey"))

	                .header(
	                        "Content-Type",
	                        "application/json")

	                .pathParam(
	                        "collectionName",
	                        configReader.getProperty("collectionName"))

	                .body(requestBody)

	            .when()

	                .post(
	                        routes.CREATE_OBJECT_IN_COLLECTION);

	    System.out.println(
	            response.asPrettyString());
	}
	@Test
	public void createObjectInCollectionUsingPOJO() {
		
		objectPayload payload= createPayload();
		
		Response response =
				given()
					.spec(requestSpecifications.getRequestSpec())
					.pathParam("collectionName",configReader.getProperty("collectionName"))
					.body(payload)
				.when()
					.post(routes.CREATE_OBJECT_IN_COLLECTION);
		response.prettyPrint();
		JsonPath jp= response.jsonPath();
		objectId= jp.getString("id");
		
	}
	private objectPayload createPayload() {

        objectPayload payload =
                new objectPayload();

        payload.setName("new object name 1");

        HashMap<String, Object> data =
                new HashMap<>();

        data.put("year", "2005");
        data.put("price", "200000");

        payload.setData(data);

        return payload;
    }
	private objectPayload createPayload(String name,int year,int price) {

        objectPayload payload =
                new objectPayload();

        payload.setName(name);

        HashMap<String, Object> data =
                new HashMap<>();

        data.put("year", year);
        data.put("price", price);

        payload.setData(data);

        return payload;
    }
	private objectPayload updatePayload() {
		objectPayload payload = new objectPayload();
		
		payload.setName("HK Laptop 0606 new-3");
		
		 HashMap<String, Object> data =
		            new HashMap<>();
		 data.put("year", 2027);
		 data.put("price", 25000);
		payload.setData(data);
		return payload;
	}
	
	@Test
	public void getObjectsFromCollectionWithLimit() {               //Example of querry param
		Response response=
		given()
			.header("x-api-key",
                    configReader.getProperty("apiKey"))
			.pathParam("collectionName", configReader.getProperty("collectionName"))
			.queryParam("limit","2")
		.when()
			.get(routes.GET_OBJECTS_IN_COLLECTION);
		
		response.prettyPrint();
	}
	@Test(dependsOnMethods="createObjectInCollectionUsingPOJO")
	public void getCreatedObject() {    
		
		
		Response response =
				given()
					.header("x-api-key",
							configReader.getProperty("apiKey"))
					.pathParam("collectionName", configReader.getProperty("collectionName"))
					.pathParam("id", objectId)
				.when()
					.get(routes.GET_OBJECT_IN_COLLECTION_BY_ID);
		response.prettyPrint();
		System.out.println("Createdid="+objectId);
		JsonPath jp= response.jsonPath();
		System.out.println("Responseid="+jp.getString("id"));
		Assert.assertEquals(jp.getString("id"),objectId );
		
		
	}
	
	@Test(dependsOnMethods="getCreatedObject")
	public void updateCreatedObject() {
		
		objectPayload payload = updatePayload();
		
		Response response=
		given()
			.header("x-api-key",
                    configReader.getProperty("apiKey"))
			.header("Content-Type",
                    "application/json")
			.pathParam("collectionName", configReader.getProperty("collectionName"))
			.pathParam("id", objectId)
			.body(payload)
		.when()
			.put(routes.UPDATE_OBJECT_IN_COLLECTION);
		
		JsonPath jp= response.jsonPath();
		jp.prettyPrint();
		
		Assert.assertEquals(jp.getString("id"), objectId,"wrong ObjectID");
		Assert.assertEquals(jp.getString("name"), payload.getName(),"worng name recieved in put call");
		
		
	}
	
	@Test(dependsOnMethods="updateCreatedObject")
	
	public void verifyCreatedObject() {
		
		Response response =
				given()
					.header(
		                    "x-api-key",
		                    configReader.getProperty("apiKey"))
					.pathParam("collectionName", configReader.getProperty("collectionName"))
					.pathParam("id", objectId)
				.when()
					.get(routes.GET_OBJECT_IN_COLLECTION_BY_ID);
		JsonPath jp= response.jsonPath();
		
		jp.prettyPrint();
		
		Assert.assertEquals(jp.getString("name"), "HK Laptop 0606 new-3");
		
		
		
		
	}
	
	
	@Test(dependsOnMethods="verifyCreatedObject")
	public void deleteObjectById() {
		
		Response response =
				given()
					.header("x-api-key",configReader.getProperty("x-api-key"))
					.pathParam("id",objectId)
					.pathParam("collectionName",configReader.getProperty("collectionName"))
				.when()
					.delete(routes.DELETE_OBJECT_BY_ID);
		response.prettyPrint();
		
		Assert.assertEquals(response.getStatusCode(),200);
		
	}
	
	@Test(dependsOnMethods="verifyCreatedObject")
	public void verifyObjectDeleted() {
		
		Response response =
				given()
					.header(
	                    "x-api-key",
	                    configReader.getProperty("apiKey"))

	                .pathParam(
	                    "collectionName",
	                    configReader.getProperty("collectionName"))

	                .pathParam(
	                    "id",
	                    objectId)
	              .when()
	              	.get(routes.GET_OBJECT_IN_COLLECTION_BY_ID);
		
		System.out.println("status code:"+response.getStatusCode());
	                
	                
	}
	@Test(dataProvider="productData",
		    dataProviderClass=dataProviders.class)
	public void createMultipleObjects(String name,int year,int price) {
		
		objectPayload payload= createPayload(name, year, price);
		
		
		Response response =
				given()
					.spec(requestSpecifications.getRequestSpec())
					.pathParam("collectionName", configReader.getProperty("collectionName"))
					.body(payload)
				.when()
					.post(routes.CREATE_OBJECT_IN_COLLECTION);
		response.prettyPrint();
	}
	
}
