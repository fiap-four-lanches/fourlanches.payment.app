Feature: Payment

  Scenario: Create Payment 
    When creating payment
    Then should store in database 
    And retrieve as a response
  
  Scenario: Get Payment
    Given a payment already exists
    When searching for a payment by Id
    Then return the payment object
