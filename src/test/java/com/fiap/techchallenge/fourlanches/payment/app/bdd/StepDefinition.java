package com.fiap.techchallenge.fourlanches.payment.app.bdd;

import com.fiap.techchallenge.fourlanches.payment.app.domain.entity.Payment;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.http.HttpStatus;
import utils.PaymentIntentHelper;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class StepDefinition {

    private Response response;

    private Payment paymentResponse;

    private final String ENDPOINT_API_PAYMENT = "http://localhost:8080/payments";


    @When("creating payment")
    public Payment creating_payment() {
        var paymentRequest = PaymentIntentHelper.generatePaymentIntent();
        response = given()
                .contentType("application/json")
                .body(paymentRequest)
                .when()
                .post(ENDPOINT_API_PAYMENT);

        return response.then().extract().as(Payment.class);
    }
    @Then("should store in database")
    public void should_store_in_database() {
        response.then()
                .statusCode(HttpStatus.OK.value());
    }
    @Then("retrieve as a response")
    public void retrieve_as_a_response() {
        response.then()
                .statusCode(HttpStatus.OK.value())
                .body(matchesJsonSchemaInClasspath("schema/Payment.schema.json"));
    }

    @Given("a payment already exists")
    public void a_payment_already_exists() {
        paymentResponse = creating_payment();
    }
    @When("searching for a payment by Id")
    public void searching_for_a_payment_by_id() {
        response = when()
                        .get(ENDPOINT_API_PAYMENT + "/{id}", paymentResponse.getId());
    }
    @Then("return the payment object")
    public void return_the_payment_object() {
        response.then()
                .body(matchesJsonSchemaInClasspath("schema/Payment.schema.json"));
    }
}
