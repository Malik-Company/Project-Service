// MISSING DEPENDENCY: io.cucumber:cucumber-java
// MISSING DEPENDENCY: io.cucumber:cucumber-junit
package com.pmotracker.msproject;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

public class MissingStepDefinitions {

    @Given("a sample step")
    public void a_sample_step() {
        System.out.println("Executing: a sample step");
    }

    @When("another sample step is executed")
    public void another_sample_step_is_executed() {
        System.out.println("Executing: another sample step is executed");
    }

    @Then("a sample assertion should pass")
    public void a_sample_assertion_should_pass() {
        System.out.println("Executing: a sample assertion should pass");
    }
}
