### Guides
* [JavaBrains - JWT from scratch (Youtube)](https://www.youtube.com/watch?v=X80nJ5T7YpE&t=1806s)
* [DZone - JWT Example](https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world)
* [Baeldung - Enabling Method Security](https://www.baeldung.com/spring-security-method-security)

#### Acquiring Token and Logging In
* First you must have a user registered and present in the database
* To create a user, POST http://localhost:8080/auth/register with request body the ApplicationUser object
* Once Sign-Up is successful, you must acquire a token
* Use GET http://localhost:8080/auth/authenticate, using AuthenticationRequest as request body, providing valid user credentials
* Once the jwt token is received, add it to the Authentication Header of the request, preceded by 'Bearer '

#### Confirmation Email Gmail Setup
* https://support.google.com/accounts/answer/185833   

#### Universal REST Response Format
Follow the JSend Specification guide
* https://github.com/omniti-labs/jsend  