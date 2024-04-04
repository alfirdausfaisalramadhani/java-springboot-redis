[![SonarQube Status](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:main-be-template-name&metric=alert_status)](https://sonarqube-new.app.mylab.local/dashboard?id=id.co.cimbniaga.octomobile:main-be-template-name)
[![SonarQube Coverage](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:main-be-template-name&metric=coverage)](https://sonarqube-new.app.mylab.local/component_measures/metric/coverage/list?id=id.co.cimbniaga.octomobile:main-be-template-name)
[![SonarQube Bugs](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:main-be-template-name&metric=bugs)](https://sonarqube-new.app.mylab.local/component_measures/metric/reliability_rating/list?id=id.co.cimbniaga.octomobile:main-be-template-name)
[![SonarQube Vulnerabilities](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:main-be-template-name&metric=vulnerabilities)](https://sonarqube-new.app.mylab.local/component_measures/metric/security_rating/list?id=id.co.cimbniaga.octomobile:main-be-template-name)

# MICROSERVICES TEMPLATE PROJECT

This repository consist of base template to create new project also have example codes that implement Spring Boot 2.3.6 for Microservice.  Developers can follow pattern in this template project to develop new OMO microservice.
### When creating new code repository for new microservice developer should :
1. Create and assign ticket to service engineer on JIRA with title `Create repository bitbucket for <project-name>`
2. After bitbucket repository created by service engineer, next step is initiating the main branch. To do it follow these steps:
    - create empty folder in your local device, and go to the folder path in terminal
    - push empty folder to main branch `development`,`rc`, `master` with these commands (assumed you are already set your git username, email, password)
   ```shell
       git init
       git remote add origin <bitbucket repository>
       git remote add .
       git commit --allow-empty -m "Initial Commit"
       git push origin master
       git checkout -b development && git push origin development
       git checkout -b rc && git push origin rc
       git checkout -b <your working branch>
   ```
3. Next you can start working on JIRA task on working branch branch `<scope>/<jira_ticket_number>
    - You can initiate new code on your working branch by copying the content of this template project into the working project that you just created, and next its up to you
4. Person who get assigned the ticket to create new microservice should write `README.md` of the microservice's project
    - Template for README is provided in `README-template.md` in this project
    - The template consist of mandatory information that should be written in the `README.md`
    - The README.md should be covering these :
        1. SonarQube badges of project status  [(tutorial)](#how-to-put-sonar-badges-in-your-readmemd)
        2. Application / Microservice Name & Its objectives (**notes:** you can sum the technologies used in the microservice too it is nice to have but not mandatory)
        3. Important rules for developers (**notes:**  you can just copy this from template )
        4. Requirements (**notes:**  you can just copy this from template and add new requirements if needed)
        5. How to : Run Application, Instal JAR & Check Code Quality (**notes:**  you can just copy this from template and add new command if needed)
        6. References & external links for documentation (if needed)
5. Every project files should have `settings.xml` attached (use settings.xml attached in this project)
6. Project files should following project folder structures of this template project

### How to put sonar badges in your `README.md`<a name="how-to-put-sonar-badges-in-your-readmemd"></a>
1. Write the code of your project
2. Build, And Install your Project in local following [this](#how-to-install)
3. Push the test result to sonar following [this](#how-to-sonar)
4. Check whether the code have uploaded to CIMB Niaga SonarQube or not by finding your application name (**notes:** the application name uploaded is the `<name>` tag in your `pom.xml`)
5. If the code already pushed, you can just replace the placeholder `${application-name}` with the application name into your `README.md` that you get from this `README-template.md`
```$xslt
 [![SonarQube Status](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:${application-name}&metric=alert_status)](https://sonarqube-new.app.mylab.local/dashboard?id=id.co.cimbniaga.octomobile:${application-name}) 
 [![SonarQube Coverage](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:${application-name}&metric=coverage)](https://sonarqube-new.app.mylab.local/component_measures/metric/coverage/list?id=id.co.cimbniaga.octomobile:${application-name})
 [![SonarQube Bugs](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:${application-name}&metric=bugs)](https://sonarqube-new.app.mylab.local/component_measures/metric/reliability_rating/list?id=id.co.cimbniaga.octomobile:${application-name})
 [![SonarQube Vulnerabilities](https://sonarqube-new.app.mylab.local/api/project_badges/measure?project=id.co.cimbniaga.octomobile:${application-name}&metric=vulnerabilities)](https://sonarqube-new.app.mylab.local/component_measures/metric/security_rating/list?id=id.co.cimbniaga.octomobile:${application-name})
```

## Endpoint Naming Convention

- All endpoint should using method `POST`
- URL path should be in lowercase

Acceptable pattern for endpoint are below :

| Pattern  | Example |Description|
|-----------|-------------|-------------|
|/`${object}`/`${operation}` | /cat/create | save/create object cat |
|/`${objectgroup}`/`${objectspecific}`/`${operation}` | /cat/owner/create | save/create object owner that can be grouped in controller cat |

Below are example endpoint convention standard for operation CRUD described by example object `cat`:

| Endpoint  | Operation |Result|
|-----------|-------------|-------------|
|/cat/|get all cat |List of object cat|
|/cat/create|insert cat  |Status of create object cat|
|/cat/update|update cat |Status of update object cat|
|/cat/delete|delete cat |Status of delete object cat|
|/cat/detail|get detail cat| Detail of object cat|

For operation besides CRUD, can follow the acceptable pattern
**example** : if there are endpoints related to transfer other bank skn, the endpoint can be:
- `/otherbank/skn/inquiry` for submit inquiry transfer otherbank (if controller handle all transfer to other bank)
- `/skn/inquiry` for submit inquiry transfer otherbank (if controller handle transfer other bank skn only)
- etc.

## API Versioning Convention

see: [**Reference**](https://javahotfix.blogspot.com/2019/03/versioning-in-rest-api.html)

## Auto Generated Swagger

To automatically generate swagger open api 3 documentation with swagger ui :

1. Setting up dependecy  
   remove these dependencies:

```xml

<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
</dependency>
<dependency>
<groupId>io.springfox</groupId>
<artifactId>springfox-swagger-ui</artifactId>
</dependency>
```

add this dependency:

```xml

<dependency>
    <!-- the version is not managed in parent pom, so you have to define the dependency like this-->
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>${springfox-swagger2.version}</version>
</dependency>
```

2. Add a swagger configuration class that have annotation `@EnableOpenApi`, take a look at this example
   class [SwaggerConfiguration](https://bitbucket.cimbniaga.co.id/projects/OMO/repos/main-be-template-name/browse/src/main/java/id/co/cimbniaga/octomobile/servicename/config/SwaggerConfiguration.java?at=refs%2Fheads%2Fdevelopment)
3. Create your classes of controller and dto class that is using swagger annotation.
4. To see the swagger ui, you can open this url on browser when Application is
   running `http://host:port/servlet-context/swagger-ui/`,
   example: [http://localhost:8081/api/template-name/swagger-ui/](http://http://localhost:8081/api/template-name/swagger-ui/)
5. To see the automatically generated OpenAPI3 Specification, you can open this url on browser when Application is
   running `http://host:port/servlet-context/v3/api-docs`,
   example: [http://localhost:8081/api/template-name/v3/api-docs](http://localhost:8081/api/template-name/v3/api-docs)
6. For service that is secured with authentication, you have to permit swagger resources path in configuration class, [example](https://springfox.github.io/springfox/docs/current/):
```java
@Configuration
  public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
      http
        .csrf().disable()
        .exceptionHandling()
        .authenticationEntryPoint(unauthorizedHandler)
        .accessDeniedHandler(accessDeniedHandler)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(
          HttpMethod.GET,
          "/v3/api-docs",
          "/swagger-resources/**", 
          "/swagger-ui/**",
          "/swagger-ui.html**",
          "/webjars/**",
          "favicon.ico"
        ).permitAll()
        .anyRequest().authenticated();

      http
        .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class)
        .headers()
        .cacheControl();
    }
  }
```
## Implement Actuator

Add this dependency to implement actuator on service

```xml

<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```
To check service health, you can go to this url when Application is running: `http://host:port/servlet-context/actuator/health`, example : [http://localhost:8081/api/template-name/actuator/health](http://localhost:8081/api/template-name/actuator/health)

## Important Rules For Developer

- Allowed to use `SNAPSHOT` version only for **development**
- Code in environment SIT, UAT, PRODUCTION should be using `RELEASE` version
- Any addition of a new library must be clear where this source come from and put as a remark for the download URL
- Choose the latest / stable version if add a new library
- In this repository, attached `settings.xml` that should be use when running build/run/deploy application.

## Project Structure Description
 ```
src
├── main
│   └── java
│   │  └── id.co.ist.mobile.servicename
│   │      ├── component                                               # Package containing class that can be used as parent class for service class
│   │      ├── config                                                  # Package containing class with annotation @Config or any class for configuration 
│   │      ├── constant                                                # Package containing constant class
│   │      ├── controller                                              # Package containing controller class
│   │      ├── domain                                                  
│   │      │   ├── dao                                                 # Package containing dao/entity class class
│   │      │   └── dto
│   │      │       ├── common                                          # Package containing pojo / dto class that commonly used within this project (can be created if needed)
│   │      │       ├── external                                        # Package containing dto class that is used for messaging to/from surrounding & other microservices
│   │      │       └── internal                                        # Package containing dto class that is used for messaging to/from front end
│   │      ├── exception                                               # Package containing exception class & exception handler
│   │      ├── repository                                              # Package containing jpa interface & class
│   │      ├── service                                                 # Package containing class to handle business logic
│   │      └── util                                                    # Package containing custom utility & helper class with static method
│   └── resources
└── test
    └── java
    │  └── id.co.ist.mobile.servicename
    │      ├── component                                               # Package containing unit test class for component class
    │      ├── controller                                              # Package containing integration test & unit test for controller class
    │      ├── domain                                                  # Package containing dto & pojo class for testing purpose
    │      ├── repository                                              # Package containing unit test class for jpa interface & class
    │      ├── service                                                 # Package containing unit test class for class that handle business logic
    │      └── util                                                    # Package containing unit test class for utility & helper class
    └── resources

```

## Requirements

For building and running the application you need:

- [JDK 11](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html)
- [Maven 3](https://maven.apache.org)

## Running the application locally

There are several ways to run a Spring Boot application on your local machine. One way is to execute the `main` method in the `id.co.cimbniaga.octomobile.Application` class from your IDE.

Alternatively you can use the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/reference/html/build-tool-plugins-maven-plugin.html) like so:

```shell
mvn spring-boot:run
```
If you have any issue to download dependency libraries while running this application, you can run with command below:
```shell
mvn spring-boot:run --settings=settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true 
```

## Installing jar of application locally <a name="how-to-install"></a>

```shell
mvn clean install
```
If you have any issue to download dependency libraries while running this application, you can run with command below:
```shell
mvn clean install --settings=settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true 
```

## Check the code quality <a name="how-to-sonar"></a>
If you need to check the code quality of this project, you can run with command below:
```shell
mvn sonar:sonar
```
or
```shell
mvn sonar:sonar --settings=settings.xml -Dmaven.wagon.http.ssl.insecure=true -Dmaven.wagon.http.ssl.allowall=true -Dmaven.wagon.http.ssl.ignore.validity.dates=true 
```
**Notes** :
- Make sure the jar is already compiled & installed (by runing command mvn install) in local project folder
- Sonar will look up to the jar, test reports & compiled class in the build target folder

## References
- [Versioning in REST API](https://javahotfix.blogspot.com/2019/03/versioning-in-rest-api.html)
- [Octo Mobile Backend Parent POM](https://bitbucket.cimbniaga.co.id/projects/OMO/repos/main-be-module-parent/browse/readme.md)
- [Git Tutorials](https://www.atlassian.com/git/tutorials)
- [Spring Tutorials](https://spring.io/guides#tutorials)
