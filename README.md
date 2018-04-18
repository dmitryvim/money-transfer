# money-transfer

Design and implement a RESTful API (including data model and the backing implementation) for money transfers between accounts. Explicit requirements: 

1. keep it simple and to the point (e.g. no need to implement any authentication, assume the APi is invoked by another internal system/service) 
2. use whatever frameworks/libraries you like (except Spring, sorry!) but don't forget about the requirement #1 
3. the datastore should run in-memory for the sake of this test 
4. the final result should be executable as a standalone program (should not require a pre-installed container/server) 
5. demonstrate with tests that the API works as expected 

## Implicit requirements: 

1. the code produced by you is expected to be of high quality. 
2. there are no detailed requirements, use common sense.

# Build and run

## Configure

To keep app simple, we haven't implemented any configuration file.
So, to configure server port use `com.mikhaylovich.MoneyTransferApplication` and change line 
```java
accountsController.startServer(8081);
```

## Build

Build jar with gradle or use bash command
```bash
./gradlew build
``` 

This way you'll run all tests and get jar at `build/libs/money-transfer.jar`

## Run

Run app with java
```bash
java -jar build/libs/money-transfer.jar
```

And test it with curl
```bash
curl -X POST http://localhost:8081/accounts
curl -X GET http://localhost:8081/accounts/0
```

