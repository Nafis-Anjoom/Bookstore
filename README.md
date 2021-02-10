# Bookstore-account Manager

## Project Summary

This project is a simple bookstore-account management software. The application will help users manage
their book purchases, rentals, and wishlist. The application will be beneficial to any bookstore owner
or even library manager. This project is of interest to me because it greatly challenges my understanding
of object-oriented programming. I employed concepts such as polymorphism and inheritance to make the project
immensely scalable and adaptable.

##User Stories

- As a user, I want to be able to purchase textbooks and novels.
- As a user, I want to be able to add books to my wishlist and remove them later.
- As a user, I want to be able to rent and return novels.
- As a user, I want to be able to add funds to my account.
- As a user, When I select the quit option from the main menu,  want to be reminded 
to save inventory details and account details to a file and have the option to do so or not.
- As a user, when I start the application, I want to be given the option to load saved account details
and inventory details.

##Phase 4: Task 2
Made the Account class robust using custom Exceptions. Specifically, rentBook(), purchaseBook(),
and addFund() takes advantage of the UnsuccessfulTransactionException.

##Phase 4: Task 3
After drawing the UML diagram, I realized that my code is not as cohesive as I wanted it to be. For example,
AccountUI, Listpane, and Account class have very high level of coupling. The ListPane object only requires
a few fields from the Account class. These fields could have been passed through the AccountUI class easily.
If I had more time, I would have decreased coupling in my GUI classes by optimizing access to my model classes.
