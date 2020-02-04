#Author: yogendra.sahu@lntinfotech.com
@edms @api
Feature: Verify the EDMS API response

  @mulesoft @healthcheck @api
  Scenario Outline: As an end user, I wants to verify mulesoft appliction is up and running
    When I want hit "<url>" of the mulesoft GET api request
    Then I should get valid response
    And Mulesoft should API shoul up and body should repsond with "Process completed successfully" message

    Examples: 
      | url                                                         |
      | http://dedmsapi.centralus.cloudapp.azure.com:8083/heartbeat |

  @article4 @api
  Scenario Outline: As an end user, I want to generate edms sore  for pdf file type
    Given I have "<base_uri>" and "<base_path>" for EDMS
    When I want to get EDMS score for "<fileName>" file and "<transaction_ID>" transcation along with "<control_type>" control type
    Then I should get valid response
    And Response body should contain expected values
      | class    | article_4 |
      | score    |     0.327 |
      | trans_id |       123 |

    Examples: 
      | base_uri                   | base_path               | fileName                   | transaction_ID | control_type |
      | http://40.77.103.9:8089/ml | classify_documents_form | article_4_1CANEA201900.pdf |            123 | article      |

  @fileupload @api
  Scenario Outline: As an end user, I want to upload a pdf file in EDMS system
    Given I have "<base_uri>" and "<base_path>" for EDMS
    When I want to upload "<fileName>" file for "<transaction_ID>" transcation along with "<control_type>" control type
    Then I should get valid response
    And File should successfully uploaded in the EDMS system
    Examples: 
      | base_uri                   												| base_path | fileName             | transaction_ID | control_type |
      | http://dedmsapi.centralus.cloudapp.azure.com:8083 | lti-imf   | article_8_052606.pdf |  210           | article      |
