package specifications;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import utilities.configReader;

public class requestSpecifications {
	
	public static RequestSpecification getRequestSpec() {
		
		RequestSpecification reqSpec= new RequestSpecBuilder()
				.addHeader(
                        "x-api-key",
                        configReader.getProperty("apiKey"))

                .setContentType(ContentType.JSON)
                .build();
		
		return reqSpec;
		
		
	}

}
