package com.test.reqres.servises;

import io.restassured.RestAssured;
import utils.ConfigReader;

public class SetEndpoint {

    public static void setEndpoint(String baseUrl,String basePath){
    RestAssured.baseURI= ConfigReader.readProperty(baseUrl);
    RestAssured.basePath= ConfigReader.readProperty(basePath);}
}
