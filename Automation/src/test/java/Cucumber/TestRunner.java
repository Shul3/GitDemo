package Cucumber;

import org.junit.runner.RunWith; 	//Run with Junit
import org.testng.annotations.Test;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;	//Run with Junit	
import cucumber.api.testng.AbstractTestNGCucumberTests;

//@RunWith(Cucumber.class)			//Run with Junit
@CucumberOptions(features = "src/test/resources/features/search.feature", glue = "stepDefinitions")

//Run with TestNG
public class TestRunner extends AbstractTestNGCucumberTests {	}	

//Run with Junit
//public class TestRunner {}
