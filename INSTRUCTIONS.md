# Run instructions

## Prerequisites
- Install gradle
- Check the test suite

## How to run
This application where migrated to gradle cause is cleaner than maven.
The test suite where built on spock framework https://spockframework.org/

- Run the application
  `gradle bootRun`
- Test the application
  `gradle test`

- To login
  `curl -i -X POST \
  -H "Content-Type:application/json" \
  -d \
  '{
  "user" : "pinky",
  "password": "pwd"
  }' \
  'http://localhost:8080/login'`
  
