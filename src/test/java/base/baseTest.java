package base;

import org.testng.annotations.BeforeTest;

import io.restassured.RestAssured;
import utilities.configReader;

public class baseTest {
	
	@BeforeTest
	public void setup() {
		RestAssured.baseURI=configReader.getProperty("baseUrl");
		
	}

}
