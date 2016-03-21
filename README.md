# PWR-Project

# how to run?
mvn spring-boot:run

# how to browse the database?
http://localhost:8080/console

# working with REST via console
add user:  curl -X POST -H "Content-Type: application/json" -d '{ "username": "test_user", "password": "test_password", "email":"test@gmail.com", "address":{"geoLongitude":1.0,"geoLatitude":1.0}, "dob":"2016-03-21" }' http://localhost:8080/users

retrieve users: curl http://localhost:8080/users

get user: curl http://localhost:8080/users/{id}

delete user: curl -X DELETE http://localhost:8080/users/{id}

update user curl -X PUT -H "Content-Type: application/json" -d '{ "username": "test_user", "password": "test_password", "email":"test@gmail.com", "address":{"geoLongitude":1.0,"geoLatitude":1.0}, "dob":"2016-03-21" }' http://localhost:8080/users/{id}

f.e. 	curl http://localhost:8080/users/1
		curl -X DELETE http://localhost:8080/users/1
	 
Zespołowy Projekt Inżynierski - Politechnika Wrocławska, WIZ
