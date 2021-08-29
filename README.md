# Washing Machine API

A backend service to control a washing machine. The API is REST based. All the actions, that can be performed on the machine as well as some predefined washing programs are persisted in the SQL database. The following file includes guide to the API.

Technologies that were used to implement the service:
- Java 8
- Spring Boot 2.5.2
- Spring HATEOAS
- Spring Data JPA
- H2 database
- Project Lombok
- MockMVC
- JUnit 5
- AssertJ

In order to start using the service, please download the code of the project and unzip it:
![image](https://user-images.githubusercontent.com/25878042/131256783-54f61aad-b14e-479e-a66a-1440501303bc.png)

Open any terminal application (like Git Bash) and go to the directory with the unpacked code and run the following command:

`mvn spring-boot:run`

After a while you will see some log messages from Spring Boot that it has launched successfully:
![image](https://user-images.githubusercontent.com/25878042/131256996-48fc7905-18e6-4429-94e4-cee823710f6b.png)

Now the service is ready, so you can have a try and fire some of the following requests:

```
GET /api/actions

POST /api/actions/drain

POST /api/actions/unlock

POST /api/actions/run (requires a program in the request body)

GET /api/programs

GET /api/programs/daily

```
Here is the sample output of each request:

`GET /api/actions`

![image](https://user-images.githubusercontent.com/25878042/131257096-47086dd1-f990-4cca-8edd-75a1a016cbe0.png)

Now you can pick an action (like "drain") and fire the following request:

`POST /api/actions/drain`

![image](https://user-images.githubusercontent.com/25878042/131257130-8c1588ed-8a7c-41b8-baee-e751440807c4.png)

In the console you will see a log message that water is drained:

![image](https://user-images.githubusercontent.com/25878042/131257151-5d2d4240-64da-4955-9345-45741ab33358.png)

If you fire another request:

`POST /api/actions/unlock`

The output will be pretty much similar, but the message will be different.

Again, firing `POST /api/actions/run` without a program object will end up with an error:

![image](https://user-images.githubusercontent.com/25878042/131257268-a8647d43-5a18-4180-8718-21883d56d4ba.png)

To run the program you need to pick a program with `GET /api/programs`:

![image](https://user-images.githubusercontent.com/25878042/131257317-53905cce-9a84-4cd6-9071-4660377214de.png)


You can pick a program and check its details with `GET /api/programs/delicate`:

![image](https://user-images.githubusercontent.com/25878042/131257359-9418cf88-0631-4420-a52f-48c51631ca80.png)

And then copy and paste the object to run the program:

![image](https://user-images.githubusercontent.com/25878042/131257390-be5bcb31-65a8-4107-8e29-b8c8c425539f.png)

After sending the request you will see the following output:

![image](https://user-images.githubusercontent.com/25878042/131257426-91f6bfdd-3f20-44fb-a72f-576f4320862a.png)

And in the console you will a short log of washing the clothes:

![image](https://user-images.githubusercontent.com/25878042/131257469-962b9583-3f2a-455f-bd92-7ca08e1c8ea0.png)
