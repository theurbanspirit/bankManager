# bankManager

1. Create an admin using the 'createFirstAdmin' request from the postman collection.
Please use this request only to create the first admin in the database as this doesn't need any authorization and is just for testing purpose. 
The default password is 'providedUsername.123'

2.login to the application using 'localhost:9090/login'. Acquire the token from responseHeaders.
 
3.Create other employees using the 'createEmployee' request. This request requires 'ADMIN' rights so make sure you're signed in as ADMIN. Since you're logged in as ADMIN, you've the right to configure the employees as 'ADMIN' or 'EMPLOYEE'

4.Create customers using the 'createCustomer' request. This requires 'ADMIN' or 'EMPLOYEE' rights.

5.Create customer account using the 'createAccount' request. This requires 'ADMIN' or 'EMPLOYEE' rights.