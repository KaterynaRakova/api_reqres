package com.test.reqres.servises;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.ConfigReader;

public class Get {
    Response response;
    public  Response get(int statusCode){
        return  response = RestAssured.given()
                .accept(ContentType.JSON)
                .when().get()
                .then().statusCode(statusCode).extract().response();
    }
    public  Response getPathParam(int statusCode,String pathParam){
        return  response= RestAssured.given()
                .accept(ContentType.JSON)
                .when().get(pathParam)
                .then().statusCode(statusCode)
                .extract().response();
    }
    public Response getQueryParam(int statusCode,String query1,int query2){
        return response = RestAssured.given()
                .accept(ContentType.JSON)
                .queryParam(query1, query2)
                .when().get()
                .then().statusCode(statusCode).extract().response();

    }
}
