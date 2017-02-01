## Using MySQL in Spring Boot via Spring Data JPA and Hibernate

### Build and run

#### Configurations

Open the `application.properties` file and set your own configurations.

#### Prerequisites

- Java 8
- Maven > 3.0

#### From terminal

Go on the project's root folder, then type:

    $ mvn spring-boot:run

#### From Eclipse (Spring Tool Suite)

Import as *Existing Maven Project* and run it as *Spring Boot App*.


### Usage

- Run the application and go on `http://localhost:8080/`
- Use the following urls to invoke controllers methods and see the interactions
  with the database:
  	* `/student/create?name=[student-name]&teamName=[team-name]`: create a new Student with an auto-generated id and name as passed values. 		If teamName query param is passed, the team is referenced if the team exists.
  	* `/student/get`: Shows all Student info.
  	* `/student/get/{id}`: Shows the Student info with team of the provided id.
  	* `/student/get-by-name?name=[student-name]`: Shows all Students info from the name provided.
  	* `/student/update?id=[student-id]&name=[student-name]&teamName=[team-name]`: update Student details and update team reference by team-			name of the provided student-id.
  	* `/student/delete?id=[student-id]`: delete student by provided student-id.
  	* `/team/create?name=[team-name]`: create a new Team with an auto-generated id and name as passed values.
  	* `/team/get`: Shows all Teams info.
  	* `/team/get/{id}`: Shows the Teams info with students of the provided team id.
  	* `/team/get-by-name?name=[team-name]`: Shows the team info from the provided team name.
  	* `/team/update?id=[team-id]&name=[team-name]`: update Team details like team-name.
  	* `/team/delete?id=[team-id]`: delete team by provided team-id.
    * `/user/create?email=[email]&name=[name]`: create a new user with an auto-generated id and email and name as passed values.
    * `/user/delete?id=[id]`: delete the user with the passed id.
    * `/user/get-by-email?email=[email]`: retrieve the id for the user with the passed email address.
    * `/user/update?id=[id]&email=[email]&name=[name]`: update the email and the name for the user indentified by the passed id.
    
