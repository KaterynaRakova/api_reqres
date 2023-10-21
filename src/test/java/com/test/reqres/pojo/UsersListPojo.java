package com.test.reqres.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.test.reqres.pojo.SingleUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UsersListPojo {
    private List <SingleUser> data;
}
