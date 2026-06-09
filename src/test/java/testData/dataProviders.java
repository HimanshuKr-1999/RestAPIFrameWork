package testData;

import org.testng.annotations.DataProvider;

public class dataProviders {
	
	@DataProvider(name="productData")
    public Object[][] createProducts() {

        return new Object[][] {

            {"Laptop", 2026, 10000},

            {"Mobile", 2027, 20000},

            {"Tablet", 2028, 30000}
        };
    }
}
