import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"com.tibco.omsui.StepDefinitions"},
        plugin = {
                "pretty",
                "json:target/jsonReports/cucumber-reports.json"
        }
)
public class TestRunner {
}
