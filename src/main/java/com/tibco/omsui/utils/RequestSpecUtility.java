package com.tibco.omsui.utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestSpecUtility {

    public <T> Response executeApiRequest(String baseUri,String endpoint,String httpMethod,
            String accessToken,T requestBody,int expectedStatusCode) {

        RestAssured.baseURI = baseUri;

        RequestSpecification requestSpecification = RestAssured.given()
                .header("Content-Type", "application/json")
                .header("Authorization", accessToken)
                .body(requestBody);

        Response response = switch (httpMethod.toUpperCase()) {
            case "POST" -> requestSpecification
                    .when().post(endpoint)
                    .then().assertThat().statusCode(expectedStatusCode).extract().response();
            case "GET" -> requestSpecification
                    .when().get(endpoint)
                    .then().assertThat().statusCode(expectedStatusCode).extract().response();
            case "PUT" -> requestSpecification
                    .when().put(endpoint)
                    .then().assertThat().statusCode(expectedStatusCode).extract().response();
            case "DELETE" -> requestSpecification
                    .when().delete(endpoint)
                    .then().assertThat().statusCode(expectedStatusCode).extract().response();
            default -> throw new IllegalArgumentException("Unsupported HTTP method: " + httpMethod);
        };

        return response;
    }
}
