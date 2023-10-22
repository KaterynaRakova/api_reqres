package com.test.reqres.runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "/Users/kateryna/Documents/CodeFish project/Api_reqres/src/test/resources/features",
        glue = "com/test/reqres/stepdef",
        dryRun = false,
        tags="@regression",
        plugin= {"pretty", "json:target/report.json","html:target/uiReport.html"}
)
public class Runner {
}
