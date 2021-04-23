Feature: Purchase Flight

  Scenario: Purchase flight on London
    When I navigate to "https://blazedemo.com/"
    And I set destination to 'London'
    And I click on button 'Find Flights'
    And I click on button 'Choose This Flight'
    And I type 'Leandro' on 'Name'
    And I type 'Rua de Cima' on 'Address'
    And I type 'Vale de Cambra' on 'City'
    And I click on button 'Purchase Flight'
    Then I should be redirected to 'https://blazedemo.com/confirmation.php'
    And I should be shown results including 'Thank you'