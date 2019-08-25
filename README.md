# REQUIREMENTS:

-JAVA 1.8

-MYSQL

-MAVEN INSTALLED

# HOW TO RUN:
-Have a database running on a default port ```3306```
Run these scripts in mysql console.

```\src\main\resources```

```create database notesdb;```

```create user 'kecik'@'localhost' identified by 'kecik';```

```grant all priveleges on notesdb.* TO 'kecik'@'localhost';```

all tables will be created automatically thanks to Hibernate

-Clone this repo (or download zip)

```git clone https://github.com/keciauke/notes.git```

-Open console, and run a app using

```mvn spring-boot:run```

# EXAMPLE REQUESTS

To get all notes (only latest versions)

```curl -X GET http://localhost:8080/notes/all```

To get a single note

```curl -X GET http://localhost:8080/notes/1```

To post a new note

```curl -X POST http://localhost:8080/notes -H "Content-Type: application/json" -d '{"title":"example title 1 ", "content": "example content 1"}'```

To update a existing note

```curl -X PUT http://localhost:8080/notes/1 -H "Content-Type: application/json" -d '{"title":"example title 2 ", "content": "example content 2"}'```

To mark a note deleted

```curl -X DELETE http://localhost:8080/notes/1```

To get a history of a single note

```curl -X GET http://localhost:8080/notes/1/history```
