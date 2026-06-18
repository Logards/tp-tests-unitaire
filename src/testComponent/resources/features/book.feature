Feature: Store and get books

  Scenario: the user creates 2 books and retrieves both
    Given the user creates the first book "Eragon" with author "Christopher Paolini"
    And the user creates the second book "OUI-OUI à l'école des sorciers" with author "Timy"
    When the user get all books
    Then the books lists should contains
      | Eragon , Christopher Paolini           |
      | OUI-OUI à l'école des sorciers, "Timy" |
