package com.test.reqres.stepdef;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.reqres.pojo.SingleUser;
import com.test.reqres.pojo.SingleUserPojo;
import com.test.reqres.pojo.UsersListPojo;
import com.test.reqres.servises.Get;
import com.test.reqres.servises.SetEndpoint;
import io.cucumber.java.en.*;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import utils.ApiUtils;
import utils.ConfigReader;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UsersApiStepDef {
   private Response response;
    private List<Map<String,Object>> expectedList;
    private Get get=new Get();

    @Given("Users endpoint")
    public void a_list_users_endpoint() {
        SetEndpoint.setEndpoint("uri","listUsers");
    }

    @Then("Get List Users request and validate status code is {int} number of {string}, {string},{string},{string},{string} URls, count matches {string} and store to map,format: key first.lastname and value email")
    public void get_list_users_request_pages_and_validate_status_code_is_u_rls_count_matches_and_store_to_map_format_key_first_lastname_and_value_email(int code,String page,  String firstName, String lastName, String id, String avatar, String total) throws IOException {
        Map<String,Object> data=new HashMap<>();
        for(int i=1; i<=Integer.parseInt(ConfigReader.readProperty("pages"));i++) {
            response= get.getQueryParam(code,"page",i);
          Assertions.assertEquals(i,Integer.parseInt(response.jsonPath().getString(page)));
          expectedList= new ObjectMapper().readValue(new File("/Users/kateryna/Documents/CodeFish project/Api_reqres/src/test/java/com/test/reqres/json/UsersPage"+i+".json"), new TypeReference <>() {});
            List<SingleUser> actualList = response.as(UsersListPojo.class).getData();
          actualList.forEach(n->data.put(n.getFirst_name().concat(".")+n.getLast_name(),n.getEmail()));
          for(int j = 0; j< actualList.size()-1; j++){
              Assertions.assertEquals(expectedList.get(j).get(id), actualList.get(j).getId(),ApiUtils.logFailure(id));
              Assertions.assertEquals(expectedList.get(j).get(firstName), actualList.get(j).getFirst_name(),ApiUtils.logFailure(firstName));
              Assertions.assertEquals(expectedList.get(j).get(lastName), actualList.get(j).getLast_name(),ApiUtils.logFailure(lastName));
              Assertions.assertEquals(expectedList.get(j).get(avatar), actualList.get(j).getAvatar(),ApiUtils.logFailure(avatar));
          }
      }
        Assertions.assertEquals(Integer.valueOf(response.jsonPath().getString(total)),data.size());

    }

    @Given("Get Single user validate status code {int}, {string},{string},{string},{string} URl")
    public void get_single_user_validate_status_code_u_rl(int code,String name, String lastName, String id, String avatar) throws IOException {
        expectedList=new ObjectMapper().readValue(new File("/Users/kateryna/Documents/CodeFish project/Api_reqres/src/test/java/com/test/reqres/json/AllUserData.json"), new TypeReference<>() {
        });
        for (int i=0 ;i < expectedList.size();i++){
            response= get.getPathParam(code,String.valueOf(i+1));
            SingleUserPojo userPojo=response.as(SingleUserPojo.class);
            Assertions.assertEquals(expectedList.get(i).get(id),userPojo.getData().getId(),ApiUtils.logFailure(id));
            Assertions.assertEquals(expectedList.get(i).get(name),userPojo.getData().getFirst_name(),ApiUtils.logFailure(name));
            Assertions.assertEquals(expectedList.get(i).get(lastName),userPojo.getData().getLast_name(),ApiUtils.logFailure(lastName));
            Assertions.assertEquals(expectedList.get(i).get(avatar),userPojo.getData().getAvatar(),ApiUtils.logFailure(avatar));
            Assertions.assertEquals(ConfigReader.readProperty("supportUrl"),userPojo.getSupport().getUrl(),ApiUtils.logFailure("url"));
        }
    }

    @Then("validate status code {int} and empty response body")
    public void validate_status_code_and_empty_response_body(Integer code) {
        response= get.getPathParam(code,ConfigReader.readProperty("singleUserNotFound"));
          Assertions.assertTrue(response.as(new TypeRef<Map<String, Object>>() {
          }).isEmpty(),"value not null");
    }
}
