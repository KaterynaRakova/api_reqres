@regression
Feature: Users Api
  Scenario: Get List Users Positive
    Given  Users endpoint
    Then Get List Users request and validate status code is 200 number of 'page', 'first_name','last_name','id','avatar' URls, count matches 'total' and store to map,format: key first.lastname and value email

  Scenario: Single User Api Positive
  Given Users endpoint
  Then Get Single user validate status code 200, 'first_name','last_name','id','avatar' URl

  Scenario: Single User Not Found Negative
    Given Users endpoint
    Then validate status code 404 and empty response body

