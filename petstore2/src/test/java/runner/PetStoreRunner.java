package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
	    features = "src/test/resources/feature",
	    glue = {"stepDefinition"},
	    plugin = {"pretty", "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:", "html:target/cucumber-reports.html"},
	    tags = "@Req7",
	    monochrome = true
	)

public class PetStoreRunner extends AbstractTestNGCucumberTests{

}
