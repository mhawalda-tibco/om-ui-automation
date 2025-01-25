package com.tibco.omsui.runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/main/resources/features",
        glue = {"com.tibco.omsui.StepDefinitions"},
        plugin = {
                "pretty",
                "json:target/jsonReports/cucumber-reports.json"
        },
        tags = "@SmokeTest"
)
public class TestRunner {
}
