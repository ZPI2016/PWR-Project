# PWR-Project

# how to run?
mvn spring-boot:run

# how to browse the database?
http://localhost:8080/console

# working with REST via console
add user:  curl -X POST -H "Content-Type: application/json" -d '{ "username": "test_user", "password": "test_password", "email":"test@gmail.com" }' http://localhost:8080/user

retrieve users: curl http://localhost:8080/user


Zespołowy Projekt Inżynierski - Politechnika Wrocławska, WIZ
