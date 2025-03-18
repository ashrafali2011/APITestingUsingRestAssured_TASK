package com.petroapp.hiring;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import static com.petroapp.hiring.SessionManager.*;

public class APITestingUsingRestAssured {

    //Use a POST request to the Authentication endpoint, passing the
    //username and password. Verify that a valid Session_Id is returned.
    @Test(priority = 1)
    public void VerifyThatAvalidSessionIdIsReturnedAfterPassingValidUsernameAndPasswordRequestToTheAuthenticationEndpoint() {
        Response authResponse =
                given().baseUri("http://hiring.petroapp.com/api.php")
                        .formParams("username", "user1", "password", "password123")
                        .log().all()
                        .when().post("?endpoint=authenticate")
                        .then()
                        .log().all()
                        .statusCode(200)
                        //asserting that session_id has value and no empty
                        .assertThat().body("session_id", notNullValue())
                        //asserting that the response has session_id
                        .assertThat().body("$", hasKey("session_id"))
                        .extract()
                        .response();

        // Extract session_id from response
        String sessionId = authResponse.jsonPath().getString("session_id");
        // passing session_id to be saved to be used in another request
        SessionManager.setSessionId(sessionId);
        System.out.println("Extracted Session ID: " + sessionId);
    }

    //After obtaining the Session_Id, send a GET request to Fetch_cars,
    //validating that the correct cars are retrieved.
    @Test(priority = 2)
    public void VerifyThatAcorrectCarsAreReturnedAfterPassingTheSession_IDtoFetch_carsEndpoint() {
        // retrieving saved session_id value from previous response
        String sessionId = SessionManager.getSessionId();
        given().baseUri("http://hiring.petroapp.com/api.php")
                .header("Session-ID", sessionId)
                .log().all()
                .when().get("?endpoint=fetch_cars")
                .then()
                .log().all()
                .statusCode(200)
                // asserting that cars has value and not empty
                .assertThat().body("cars", notNullValue())
                // asserting that response has cars key and not empty
                .assertThat().body("$", hasKey("cars"));

    }

    //Test for invalid credentials by
    // checking the appropriate error messages returned by the API
    @Test(priority = 3)
    public void VerifyThatErrorMSGIsReturnedAfterPassingInvalidUsernameAndPasswordRequestToTheAuthenticationEndpoint() {
        given().baseUri("http://hiring.petroapp.com/api.php")
                .formParams("username", "userr", "password", "password123")
                .log().all()
                .when().post("?endpoint=authenticate")
                .then()
                .log().all()
                .statusCode(200)
                // asserting that we have error key
                .assertThat().body("status", equalTo("error"))
                // asserting the error msg in case invalid credentials
                // will be "Invalid username or password"
                .assertThat().body("message", equalTo("Invalid username or password"));
    }

    //Test for invalid session IDs by
    // checking the appropriate error messages returned by the API
    @Test(priority = 4)
    public void ErrorMSGIsReturnedAfterPassingInvalidSession_IDtoFetch_carsEndpoint() {
        given().baseUri("http://hiring.petroapp.com/api.php")
                //sending invalid session id that is not retrived from previous request to the api
                .header("Session-ID", "d41d8cd98f00b204e9800998ecf84271")
                .log().all()
                .when().get("?endpoint=fetch_cars")
                .then()
                .log().all()
                .statusCode(200)
                // asserting that we have error key
                .assertThat().body("status", equalTo("error"))
                // asserting the error msg in case sending invalid session id
                // will be "Invalid session ID"
                .assertThat().body("message", equalTo("Invalid session ID"))
        ;

    }

    //Test for missing session IDs by
    // checking the appropriate error messages returned by the API
    @Test(priority = 5)
    public void ErrorMSGIsReturnedAfterNOTPassingSession_IDtoFetch_carsEndpoint() {
        given().baseUri("http://hiring.petroapp.com/api.php")
                // not sending session_id in header
                //.header("Session-ID", "d41d8cd98f00b204e9800998ecf84271")
                .log().all()
                .when().get("?endpoint=fetch_cars")
                .then()
                .log().all()
                .statusCode(200)
                // asserting that we have error key
                .assertThat().body("status", equalTo("error"))
                // asserting the error msg in case not sending session id in header
                // will be "Session-ID header required"
                .assertThat().body("message", equalTo("Session-ID header required"))
        ;

    }

}
