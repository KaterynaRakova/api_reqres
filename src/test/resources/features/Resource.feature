@regression
  Feature: Resource Api
    Scenario: List Resource Positive
     Given  Resource endpoint
     Then Get request and validate status code 200 and sum of ids 78 and avg of years 2005.5

    Scenario: Single Resource Positive
      Given  Resource endpoint
      Then Get request for resource and validate code 200 'id','year','color',support text

    Scenario: Single Resource Not Found Negative
      Given  Resource endpoint
      Then validate status code 404 and empty response resource body

