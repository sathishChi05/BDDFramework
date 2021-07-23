import cucumber.api.CucumberOptions;
import cucumber.api.testng.AbstractTestNGCucumberTests;

@CucumberOptions(
        strict = true,
        features = {"C:/Automation/BDDFramework/src/test/resources/com/Practice/Features/InProgress/MyStoreProductSearch.feature:40"},
        plugin = {"json:C:/Automation/BDDFramework/target/cucumber-parallel/1.json", "html:C:/Automation/BDDFramework/target/cucumber-parallel/1"},
        monochrome = true,
        glue = {"com.UIQ.stepsdefinitions"})
public class Parallel01IT extends AbstractTestNGCucumberTests {
}
