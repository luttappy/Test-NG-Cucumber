@RunMe
Feature: Flight Reservation Login

Scenario: Login to flight reservation 
       Given the user opened "chrome" browser
       When the user navigated to "http://newtours.demoaut.com/" page
       And the user enters username "tcoedemo" in  input field
      And the user enters password "password" in  input field
      Then the user clicks on "submit" button
