package com.tibco.omsui.Action;

import com.tibco.omsui.Model.CreateTokenRequest;
import com.tibco.omsui.Model.CreateTokenResponse;
import com.tibco.omsui.config.ProjectPropertiesLoader;
import io.restassured.RestAssured;

import java.util.HashMap;
import java.util.Map;

public class Authorization {
    private String accessToken;
    public String getAuthToken() {
        RestAssured.baseURI = ProjectPropertiesLoader.projectProperties().getProperty("AUTH_URL");
        CreateTokenRequest createToken = new CreateTokenRequest();
        createToken.setUsername("admin");
        createToken.setPassword("admin");
        createToken.setGrantType("password");
        createToken.setTenantId("TIBCO");
        createToken.setRefreshToken("");
        Map<String,String> queryParam = new HashMap<>();
        queryParam.put("grant_type","password");
        queryParam.put("scope","read");
        queryParam.put("username","admin");
        queryParam.put("password","admin");
        queryParam.put("tenantId","TIBCO");
        CreateTokenResponse tokenResponse = RestAssured
                .given()
                .header("accept", "application/json")
                .header("Authorization","Basic MTox")
                .queryParams(queryParam)
                .log().all()
                .when().post("/oauth/token")
                .then().log().all().assertThat().statusCode(200).extract().response().as(CreateTokenResponse.class);
        accessToken = tokenResponse.getAccess_token();
        System.out.println("Access token received: " + accessToken);
        return accessToken;
    }

    public String fetchAuthToken(){
        if(accessToken != null){
            return accessToken;
        }
        accessToken = getAuthToken();
        return accessToken;
    }
}
