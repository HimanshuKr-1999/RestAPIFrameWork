package specifications;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.ResponseSpecification;

public class responseSpecifications {
	
	public static ResponseSpecification getResponseSpec() {
		
		
		ResponseSpecification respSpec= new ResponseSpecBuilder()
				.expectStatusCode(200)
				.build();
		
		return respSpec;
		
	}
}
