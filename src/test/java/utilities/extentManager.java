package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class extentManager {
    private static ExtentReports extent;

	  public static ExtentReports getInstance() {

	        if(extent == null) {

	            ExtentSparkReporter sparkReporter =
	                    new ExtentSparkReporter(
	                            "reports/ExtentReport.html");

	            extent = new ExtentReports();

	            extent.attachReporter(
	                    sparkReporter);

	            extent.setSystemInfo(
	                    "Tester",
	                    "Himanshu");

	            extent.setSystemInfo(
	                    "Framework",
	                    "REST Assured");
	        }

	        return extent;
	    }

}
