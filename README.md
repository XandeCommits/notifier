# Notifier

## How to run

### Docker
```bash
docker build -t notifier . && docker run -p 8080:8080 notifier
```

### Local

#### Dependencies

- Java 21

MacOS:
```bash
brew install openjdk@21
```
and then:
```bash
./gradlew bootRun
```

## Tests

This app includes both unit and integration tests located in the `src/test/` folder. In every new `docker build` the tests will run.


### Local tests

After the Springboot app is running, you can `POST` the `http://localhost:8080/api/user/{USER-UUID}/send-email`
with the following payload:

```json
{
   "emailType": "status",
   "emailContent": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
}
```

- cURL:

```bash
curl --request POST \
  --url http://localhost:8080/api/user/849bb309-ca60-409c-9a30-7ce8f2cf4a3f/send-email \
  --header 'Content-Type: application/json' \
  --data '{
        "emailType": "news",
	"emailContent": "Lorem ipsum dolor sit amet, consectetur adipiscing elit"
}
'
```

For the example above I used the UUID `849bb309-ca60-409c-9a30-7ce8f2cf4a3f` as a path parameter, but any UUID is valid 
and accepted. 

This code has no objective to validate user e-mail address, so for the sake of testing, any UUID will be mapped to: `{UUID}@email.com`.

The `emailType` accepted are: `news`, `status` and `marketing`, any other different type will result in a `400 Bad Request`.

For the sake of testing you can add the `mocked-time` Header to actually mock the current time that this request is being made.

Eg:

```bash
curl --request POST \
  --url http://localhost:8080/api/user/1eac3c3c-7cd1-4804-99c5-123/send-email \
  --header 'Content-Type: application/json' \
  --header 'mocked-time: 2023-07-09T12:45:00.000Z' \
  --data '{
  "emailType": "news",
        "emailContent": "Lorem Ipsum"
}
'
```

This `mocked-time` Header is meant **ONLY FOR LOCAL TEST SIMULATION**. Specially for types of emails that requires a longer
cooldown (`news` and `marketing`). All integration tests uses [**Mockito**](https://site.mockito.org/).

Every e-mail sent returns a `200 Success`, and if the current e-mail is considered a spam the server returns `429 Too many request`

## Datastore

This code stores all sent e-mail in-memory database called [H2](https://www.h2database.com/html/main.html).

All data is erased when the app is stopped.

## Architecture

The architecture chosen was [The Ports and Adapters Architecture](https://en.wikipedia.org/wiki/Hexagonal_architecture_(software)).

The main packages are:

- **adapters**: Here we have all external communication logic, (http endpoints, smtp, database).
- **domain**: This package contains all the business rules.
- **ports**: Defines the communication interface to adapters.
- **application**: Receives data from the adapters and send it to ports.

In short: The main logic is implemented under the **domain** package, and the **application** package interact with the **domain** 
and send the results through ports.

## Logic

Under the **domain** package there is an `Email` Interface, that defines the methods to get the rules for each type of e-mail.

Each different type of e-mail implements this Interface with it own rules.

There is an `EmailFactory` class that can create the correct `Email` based on the `EmailType` received.

The `EmailService` class receives the current `Email` (with the correct implementation), and a list of all previous sent 
e-mails for that user with the same type. With this information we can check if the current `Email` is an spam or not.

## Add new e-mail types

As described above, to add a new e-mail type you need to:

- Create a new class for the new type, and implement the `Email` interface.
- Add this new type to the `EmailTypeEnum`
- Add integration tests.