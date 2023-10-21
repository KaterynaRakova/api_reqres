package com.test.reqres.stepdef;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.reqres.pojo.*;
import io.cucumber.java.en.*;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.ApiUtils;
import utils.ConfigReader;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ResourceApiStepDef {
    private Response response;
    @Given("Resource endpoint")
    public void get_request_for_resource_endpoint() {
        RestAssured.baseURI=ConfigReader.readProperty("uri");
        RestAssured.basePath= ConfigReader.readProperty("listResource");
    }
    @Then("Get request and validate status code {int} and sum of ids {int} and avg of years {double}")
    public void validate_status_code_and_sum_of_ids_and_avg_of_years(int code, Integer sumIds, Double avgYears) {
        int sum = 0,count=0;
        double avg = 0;
        for(int i = 1; i<=Integer.parseInt(ConfigReader.readProperty("pages")); i++) {
            response = RestAssured.given()
                    .accept(ContentType.JSON)
                    .queryParam("page",i)
                    .when().get()
                    .then().statusCode(code).extract().response();
          for (SingleResource v : response.as(ResourceListPojo.class).getData()) {
               sum += v.getId();
               avg += v.getYear();
               count++;
           }
       }
        Assertions.assertEquals(sumIds,sum);
        Assertions.assertEquals(avgYears,(avg/count));
    }
    
    @Then("Get request for resource and validate code {int} {string},{string},{string},support text")
    public void get_request_for_resource_and_validate_support_text(int code, String id, String year, String color) throws IOException {
      List<Map<String,Object>> expectedList=new ObjectMapper().readValue(new File("/Users/kateryna/Documents/CodeFish project/Api_reqres/src/test/java/com/test/reqres/json/AllResourcesData.json"), new TypeReference<>() {});
          for (int i=0 ;i < expectedList.size();i++){
              response=RestAssured.given()
                      .accept(ContentType.JSON)
                      .when().get(String.valueOf(i+1))
                      .then().statusCode(code).extract().response();
          SingleResourcePojo singleResourcePojo=response.as(SingleResourcePojo.class);
          Assertions.assertEquals(expectedList.get(i).get(id),singleResourcePojo.getData().getId(), ApiUtils.logFailure(id));
          Assertions.assertEquals(expectedList.get(i).get(year),singleResourcePojo.getData().getYear(),ApiUtils.logFailure(year));
          Assertions.assertEquals(expectedList.get(i).get(color),singleResourcePojo.getData().getColor(),ApiUtils.logFailure(color));
         Assertions.assertEquals(ConfigReader.readProperty("text"),singleResourcePojo.getSupport().getText(),ApiUtils.logFailure("text"));
     }
    }
    @Then("validate status code {int} and empty response resource body")
    public void validate_status_code_and_empty_response_resource_body(Integer code) {
        response= RestAssured.given()
                .accept(ContentType.JSON)
                .when().get(ConfigReader.readProperty("singleUserNotFound"))
                .then().statusCode(code)
                .extract().response();
        Assertions.assertTrue(response.as(new TypeRef<Map<String, Object>>() {
        }).isEmpty(),"value not null");
    }


}
