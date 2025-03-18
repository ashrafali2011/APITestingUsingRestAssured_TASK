NOTE:
to run the API test cases , you can run it from any of the below files :
1-
src/test/resources/TestRunner.xml
2-
src/test/java/com/petroapp/hiring/APITestingUsingRestAssured.java

Part 3: API Testing Using RestAssured Write a RestAssured test script in Java to verify the API's functionality. You are expected to:

Authenticate the user: Use a POST request to the Authentication endpoint, passing the username and password. Verify that a valid Session Id is returned.
Fetch associated cars: After obtaining the Session Id, send a GET request to Fetch_cars, validating that the correct cars are retrieved.
Handle error scenarios: Test for invalid credentials and missing/invalid session IDs by checking the appropriate error messages returned by the API. You can find the API endpoint details and sample credentials below:  Authenticate: http://hiring.petroapp.com/api.php?endpoint=authenticate  Fetch Cars: http://hiring.petroapp.com/api.php?endpoint=fetch_cars Endpoint 1: Authenticate User Method: POST Parameters:
username: The username of the user.
password: The password of the user. Endpoint 2: Fetch Cars Method: GET Headers: Session-Id: The Session Id obtained from the authenticate endpoint. User Credentials: User 1: User 2: username: user1 Password: password123 username: user2 Password: password456 Admin: username: admin Password: adminpass
