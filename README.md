# Federal Holidays RESTful API

This project implements a RESTful API for managing federal holidays for countries, initially supporting Canada and USA. It allows adding, updating, and listing holidays for these countries.

## Introduction

This project implements a RESTful API using Spring Boot to manage federal holidays for Canada and USA. It includes functionalities to add, update, and list holidays, and is designed to be easily extensible for supporting additional countries.

## Tools and Technologies:

- Java 11
- Maven
- H2 Database

## Running Locally
Using In-Memory Database (H2)
For quick setup and testing, you can run the application with an in-memory H2 database:

mvn spring-boot:run
The application will start at http://localhost:8080.

## Testing
The project has extensive unit and integration tests, achieving over 70% test coverage(JUnit, Mockito).

## API Documentation
Swagger
API documentation is available via Swagger UI:
Open http://localhost:8080/swagger-ui.html in your browser after starting the application.

## Postman Collection
A Postman collection is provided for easy testing:
Postman Collection for Holiday and Country APIs (Local)
Environment Variables:
Set up an environment variable for your local server URL:

base_url: http://localhost:8080

    ## APIs for Holidays
Get all holidays
Request Type: GET
URL: {{base_url}}/api/holidays
Description: Retrieves all holidays.

Get Holiday by ID
Request Type: GET
URL: {{base_url}}/api/holidays/{id}
Description: Retrieves a holiday by its ID.

List Holidays by Country
Request Type: GET
URL: {{base_url}}/api/holidays/{countryCode}
Description: Retrieves holidays for a specific country.

Search Holidays by Country and Month
Request Type: GET
URL: {{base_url}}/api/holidays/search?countryCode={countryCode}&month={month}
Description: Searches holidays by country code and month.

Add List of Holidays
Request Type: POST
URL: {{base_url}}/api/holidays/addAll
Body:
json
[
{
"name": "New Year",
"date": "2024-01-01",
"countryCode": "US"
},
{
"name": "Christmas",
"date": "2024-12-25",
"countryCode": "US"
}
]
Description: Adds multiple holidays.

Add Holiday
Request Type: POST
URL: {{base_url}}/api/holidays/add
Body:
json
{
"name": "Independence Day",
"date": "2024-07-04",
"countryCode": "US"
}
Description: Adds a single holiday.

Update Holiday
Request Type: PUT
URL: {{base_url}}/api/holidays/{id}
Body:
json
{
"name": "Updated Holiday Name",
"date": "2024-12-25",
"countryCode": "US"
}
Description: Updates an existing holiday.

Delete Holiday
Request Type: DELETE
URL: {{base_url}}/api/holidays/{id}
Description: Deletes a holiday by its ID.


    ## APIs for Countries
Get all countries
Request Type: GET
URL: {{base_url}}/api/countries
Description: Retrieves all countries.

Get Country by Code
Request Type: GET
URL: {{base_url}}/api/countries/code/{code}
Description: Retrieves a country by its code.

Get Country by ID
Request Type: GET
URL: {{base_url}}/api/countries/id/{id}
Description: Retrieves a country by its ID.

Add Country
Request Type: POST
URL: {{base_url}}/api/countries
Body:
json
{
"name": "Canada",
"code": "CA"
}
Description: Adds a new country.

Update Country
Request Type: PUT
URL: {{base_url}}/api/countries/{id}
Body:
json
{
"name": "Updated Country Name",
"code": "CA"
}
Description: Updates an existing country.

Delete Country
Request Type: DELETE
URL: {{base_url}}/api/countries/{id}
Description: Deletes a country by its ID.

## Enhancements
•	Implement authentication and authorization mechanisms(Spring Security).
•	Implement role-based access control for API endpoints(Using JWT).
•	Add support for more countries dynamically.
•	Integrate caching mechanisms for improved performance(Redis Cache).
•	Implement logging and monitoring(SLF4j, Log4j, Actuators).
•   Implement Factory/visitor design pattern in validators to perform validation based on dto type 



