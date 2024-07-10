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
brew install --cask adoptopenjdk/openjdk/adoptopenjdk21
```
and then:
```bash
./gradlew bootRun
```

## Tests

This app includes both unit and integration tests located in the `src/test/` folder.
### Local tests

After the Springboot app is running, you can `POST` the `http://localhost:8080/api/user/{USER-UUID}/send-email`
with the following payload:

```json
{
   "subject": "Example Subject",
   "body": "Example Body"
}
```
