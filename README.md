# OrdersBackend
Simple CRUD with Spring 4, Hibernate 4, maven 3, MySQL, Java 1.8 and Restful Webservices.

The backend system was implemented using a layered approach where there are 3 different layers:

Controller(uy.com.orders.controller)
	This layer is in charge of receiving the requests and call the appropiate service to process it.

Service(uy.com.orders.service)
	This layer is the mediator between controllers and the model (data). It dispatches to the proper dao to process the request.

Model (uy.com.orders.model, uy.com.orders.dao)
	This layer is the one responsible to communicate with database. CRUD operations are done here. It exposes its methods through DAOs interfaces and then communicates with the database through model classes defined with JPA annotations and processed by hibernate.


* It would be good to add JUnit tests.
* Exception handling needs refactoring. 
The implementation was to return specific http error codes for these controls(stock and credit 	limit). 

