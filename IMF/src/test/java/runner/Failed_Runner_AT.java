package runner;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
@CucumberOptions(dryRun = false, features = "@rerun/failed_scenarios.txt", glue = { "classpath:/" }, plugin = {
		"html:target/cucumber-report/ReRun", "json:target/cucumber-report/ReRun.json" })
public class Failed_Runner_AT {

}
