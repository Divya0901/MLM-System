1.Project Structure
The project is organized as follows:

src/main/java/com.dg.MLMSystem: Contains the main application class and the application logic.
src/main/resources: Contains application properties and SQL scripts.

2.Setup and Database Configuration:
Create a MariaDB database and configure the connection details in application.properties.
Build and run the project using Maven.

3. Endpoints:
   I. User Management
     To register a user:
       Endpoint: Post -> "/api/users/register"
       Request Body:
                    {
                          "username": "John Doe",
                          "email": "john.doe@example.com",
                          "password": "password123",
                           "roles" : "ROLE_USER, ROLE_ADMIN"      
                     }
  Note: After 1 -2 users registration provide the roles as "ROLE_USER".
 
   II. Referral System
     To record a Referral
     Endpoint: Post -> "/api/referrals/record"
     Request Body: Provide referrerId and referredId in the params.
   Important Note: Need to authenticate this user using JWT token before calling this endpoint, so perform the following steps to get the token.
       Endpoint: Post -> "/authenticate"
       Request Body:
                     {
                         "username": "John Doe",
                         "password": "password123"
                      }

     Response:
               {
                  "accessToken": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJBbmtpdCIsImlhdCI6MTcwMjQ5MzM2NiwiZXhwIjoxNzAyNDkzNDg2fQ.shYrYiW1fnbLeIQrpS674uyV0rCOuB1dptEFncVTMEA",
                  "token": "07a1e0d0-cf26-43b5-a7de-20e18077c63e"
              }

   Take this access token and add in the baearer token section before hitting the api/referral/record.

   III. MLM Structure
     a.To view user details
     Edpoint: Get -> "/api/users/{userId}"
     Response:
                 {
                     "id": 2,
                     "name": "John Doe",",
                      "email": "john.doe@example.com",
                      "password": "$2a$10$MMDzw/V.UlIas9Ie0YQEGexOKsBYz8AX6Mez.Cabl7kuxlKq/cbBq",
                     "roles": "ROLE_ADMIN, ROLE_USER",
                    "referrer": null,
                    "referrals": []
                }

   Important Note: Need to authenticate this user using JWT token before calling this endpoint, so perform the following steps to get the token.
       Endpoint: Post -> "/authenticate"

   b. To calculate commission by ADMIN only. (Authorization)
     Endpoint: Get -> "/api/commissions/calculate"

     Important Note: Remember as this endpoint is accessible only to ADMIN use admin crdentails before adding the JWT bearer token.

  c. To view commission by Users:
      Endpoint: Get -> "/api/users{userId}/commissions"
      Important Note: Need to authenticate this user using JWT token before calling this endpoint, so perform the following steps to get the token.
       Endpoint: Post -> "/authenticate"


4. Security
  All endpoints are secured. Users need to authenticate to access them.
  Admins have access to additional endpoints.

    
      
      
