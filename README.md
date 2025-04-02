# product-search-availability-system

## Description
The Product Search and Availability System is a web application that allows users to search for products and check their availability in different markets. The system provides an admin panel for managing products and markets, and a market dashboard for market users to update product quantities.

## Setup Instructions
1. Clone the repository:
   ```
   git clone https://github.com/itsmeneil23/product-search-availability-system.git
   ```
2. Navigate to the project directory:
   ```
   cd product-search-availability-system
   ```
3. Build the project using Maven:
   ```
   mvn clean install
   ```
4. Run the application:
   ```
   mvn spring-boot:run
   ```

## Usage Instructions
1. Access the application in your web browser at `http://localhost:8080`.
2. Use the search functionality on the home page to search for products and check their availability.
3. Admin users can log in to the admin panel to manage products and markets.
4. Market users can log in to the market dashboard to update product quantities.

## Dependencies
- Spring Boot
- Thymeleaf
- Spring Security
- Spring Data JPA
- MySQL Connector
- Lombok
- JUnit

## Admin Credentials
- Username: admin
- Password: admin

## Prerequisites
- MySQL (with root, root in our case; 3306 the port)
- Maven

## In-a-go Command
To clean and run the application, use the following command:
```
mvn clean spring-boot:run
```
