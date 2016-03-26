# PWR-Project

# how to run?
mvn spring-boot:run

Important: remember to check if your database drivers dependencies and JPA data source settings are OK.

# how to run unit tests?
mvn test

# how to run both integration and unit tests (e.g. for deployment)?
mvn verify

# working with REST via console
add user:  curl -X POST -H "Content-Type: application/json" -d '{ "username": "test_user", "password": "test_password", "email":"test@gmail.com", "address":{"geoLongitude":1.0,"geoLatitude":1.0}, "dob":"2016-03-21" }' http://localhost:8080/users

retrieve users: curl {URL}/users

get user: curl {URL}/users/{id}

delete user: curl -X DELETE {URL}/users/{id}

update user: curl -X PUT -H "Content-Type: application/json" -d '{ "username": "test_user", "password": "test_password", "email":"test@gmail.com", "address":{"geoLongitude":1.0,"geoLatitude":1.0}, "dob":"2016-03-21" }' {URL}/users/{id}

get user's address: curl {URL}/users/{id}/address

update user's address: curl -X PUT -H "Content-Type: application/json" -d '{"geoLongitude":5.0,"geoLatitude":5.0}' {URL}/users/{id}/address

Zespołowy Projekt Inżynierski - Politechnika Wrocławska, WIZ
