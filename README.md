# droneDispatcher

This application was generated using JHipster 6.10.5, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.10.5](https://www.jhipster.tech/documentation-archive/v6.10.5).

This is a "microservice" application intended to be part of a microservice architecture, please refer to the [Doing microservices with JHipster][] page of the documentation for more information.

This application is configured for Service Discovery and Configuration with . On launch, it will refuse to start if it is not able to connect to .

## Development

To start your application in the dev profile, run:

```
./mvnw
```

For further instructions on how to develop with JHipster, have a look at [Using JHipster in development][].

## Building for production

### Packaging as jar

To build the final jar and optimize the droneDispatcher application for production, run:

```

./mvnw -Pprod clean verify


```

To ensure everything worked, run:

```

java -jar target/*.jar


```

Refer to [Using JHipster in production][] for more details.

### Packaging as war

To package your application as a war in order to deploy it to an application server, run:

```

./mvnw -Pprod,war clean verify


```

## Testing

To launch your application's tests, run:

```
./mvnw verify
```

For more information, refer to the [Running tests page][].

### Code quality

Sonar is used to analyse code quality. You can start a local Sonar server (accessible on http://localhost:9001) with:

```
docker-compose -f src/main/docker/sonar.yml up -d
```

You can run a Sonar analysis with using the [sonar-scanner](https://docs.sonarqube.org/display/SCAN/Analyzing+with+SonarQube+Scanner) or by using the maven plugin.

Then, run a Sonar analysis:

```
./mvnw -Pprod clean verify sonar:sonar
```

If you need to re-run the Sonar phase, please be sure to specify at least the `initialize` phase since Sonar properties are loaded from the sonar-project.properties file.

```
./mvnw initialize sonar:sonar
```

For more information, refer to the [Code quality page][].

## Using Docker to simplify development (optional)

You can use Docker to improve your JHipster development experience. A number of docker-compose configuration are available in the [src/main/docker](src/main/docker) folder to launch required third party services.

For example, to start a mysql database in a docker container, run:

```
docker-compose -f src/main/docker/mysql.yml up -d
```

To stop it and remove the container, run:

```
docker-compose -f src/main/docker/mysql.yml down
```

You can also fully dockerize your application and all the services that it depends on.
To achieve this, first build a docker image of your app by running:

```
./mvnw -Pprod verify jib:dockerBuild
```

Then run:

```
docker-compose -f src/main/docker/app.yml up -d
```

For more information refer to [Using Docker and Docker-Compose][], this page also contains information on the docker-compose sub-generator (`jhipster docker-compose`), which is able to generate docker configurations for one or several JHipster applications.

## Continuous Integration (optional)

To configure CI for your project, run the ci-cd sub-generator (`jhipster ci-cd`), this will let you generate configuration files for a number of Continuous Integration systems. Consult the [Setting up Continuous Integration][] page for more information.

[jhipster homepage and latest documentation]: https://www.jhipster.tech
[jhipster 6.10.5 archive]: https://www.jhipster.tech/documentation-archive/v6.10.5
[doing microservices with jhipster]: https://www.jhipster.tech/documentation-archive/v6.10.5/microservices-architecture/
[using jhipster in development]: https://www.jhipster.tech/documentation-archive/v6.10.5/development/
[using docker and docker-compose]: https://www.jhipster.tech/documentation-archive/v6.10.5/docker-compose
[using jhipster in production]: https://www.jhipster.tech/documentation-archive/v6.10.5/production/
[running tests page]: https://www.jhipster.tech/documentation-archive/v6.10.5/running-tests/
[code quality page]: https://www.jhipster.tech/documentation-archive/v6.10.5/code-quality/
[setting up continuous integration]: https://www.jhipster.tech/documentation-archive/v6.10.5/setting-up-ci/

## Test plan for running this project

In this project, I have added some drone and medication entries (look under `main/resources/config/liquibase/data-data`). You can see data on database on http://localhost:8080/h2-console. If you want to remove database content, you have to remove `target/h2db` directory (I use h2 disk based database).

### Create drone (POST)

```
http://localhost:8080/api/drones
{
    "serialNumber": "JND-06-HYG",
    "model": "LIGHTWEIGHT",
    "weightLimit": 500,
    "batteryCapacity": 100
}
```

### Create medication (POST)

```
http://localhost:8080/api/medications
{
    "name": "UN,MBMF",
    "weight": 100,
    "code:": "8Z7R",
    image: null
}
```

image attribute is a base64 image (see load medications section)

### Load medications into drone (POST)

```
http://localhost:8080/api/drone-to-medications
{
  "droneId" : 1,
  "medications" : [{
  "id" : 2,
  "name" : "UN,MBMF",
  "weight" : 100.0,
  "code" : "8Z7R",
  "image" : "iVBORw0KGgoAAAANSUhEUgAAAMAAAADACAMAAABlApw1AAAC/VBMVEUAAADLqqNLVmysmYO6p4u1mIWwjn6wkH61lYSwj36zkoKxkYCxkYCxkH+8mYa5loSxkH6wkIC1k4K3loWujXywj36wj36xkH6si320k4KxkH+wj37GoY2ykYCxkH+zkoJ5cnfJo4/TrJfCnYqxkH+ykoGti3rYsJrOp5OxkIC/m4iriXeqh3XAopPMsaLRtqjWu625morHqpvlzcDv2Mzz3dH24tf45Njw2s7cwrW8no7gx7ru1sr14NT449b24dX039Py3NDKrZ/r1Mf03tHjyr3Os6Xo0MTYv7HEp5j65dmohHL039T649Xx3tT54tTUzNCVq8uBoMmjsszm19LPytCKpMrr2c5BicYAgMUgg8UbgsUmhMUuhcYAfMJ7nspNjMZHisZKi8YCgsXKxs8RgcRAiMaut8ysv9i1yeGyus1vmci9v8785dX99/T////+/f3/8eX78ev89O/ny74JgcX66N7++/jrz8Pu08b5+vv57OXs0MPf4OGSkpNsb3fV1dfw8vSqqqt9gYa/vsDsz8Lrz8L63M712MrHxccAAADo6OlFRUWYm5zu0sXsz8I5OTm1trjLzM3rz8KjpKTrz8KHjZA8iMZilMepp6ZKTVmdnp5TWGbrz8Ht1cfrz8OTl5nrz8LqzsLsz8Lq0MNVj8fm1MycrMvKv7o4hMFBjMtBicY5frjSuausschFkdE5gr9BicZBicbBopO9n44/Y4n24dTy3dD24dTezsW9trHVyMCOlJayr62cnZ2Xm5xGTF1IT19LVWpHTmBHTmDX19oxPFNIT2BJUGFJUGFJUGFIT2BJUGBJT2FJUGFJUGE+RlpYXm1NVWdKUmRJUGFJT2FIUWNJUGFJUGEkMkxIT2FIUGETJ0ZJUGFIT2EAGkBGV3FAYIVAapUIWY19mr7b5O4ib6hQgbE/d6wzc6ozc6o2ebMAY6MAesFAgrtHYYJxlLtsnc85b6HG0+QBcbAzcqozdKs2eLEyc6pBiMYzcqoycqpBicZBiMZAicac2SAaAAAA/3RSTlMAAwMCBRhCcIaPVpvg////5zP////7/aglZL/////xegv////Ltv///9b//////////////////////////////////////////////////////////////////////////////////////////////////2n///9ILv//x//////////+vn////////////r//////6//////////oBHW/+fR4Iz/////b//7KNrZ/0jrhC5EFd2fpv//////rOA3VX2m1OH73bKRdUYnaL7///////mJYYP8/3HJ//Cb//////////////7///////////////KDn7fQ1Fy1395vg4/qAAAZP0lEQVR4AdSX0XayOhCFgwAWNRMbZ2LrQCtQJfL+D3gWszhdFOH/z80Bum9Y5OrbzN4TVf+zgk3vJYxC9ZvUAcfJ9iVN05dd3J78KvyWfrc/aBCZ4/5VqSD8TR8/2R8snBCtJSJngc5vSkXrBA4iURDKi3z89wvDiQlNlllm1o5y+Pi8rnAIYdSv6kb4XtODMdoR2qKsGFnkyMJXsrYhBEHrIU522+0uiYUtfr8RWEcOsSpLc+dvOTL8uSYHUtXrLv06sjXG6uPXOU0vRws5EWvMi9rnqLknyuH2tp4YBUpdtw8LxgqltgagfSHHzJj7AX8r5+CYqHAdDiKldg8wjpxzLHCOiIS+47eoeSg66a14X371qPh8Mo74hxAsyrOoS/PMLzE6vYj7xeOTHIBpgIh5lmP7rOq6QX4WIhPDOVLR4vHZakPPfE0m/KeyLnAMH6DNWnNb1IHw70HTCGFR3bsAWRzB95kcE1wWdRCp6AzsRvitbwHRlHWFT/jGlxXKsaNFZxCp+ALk+Fn3zCPLALzGp3bU/nTnTtScpUgL8X8B8YhQl22C0JbDBiNCWWfcO3SQKhUuxP8Y52dsJPlYDQagUWd12WD/zDmzVWG4Kn5GX7T87AcNQOtrPyy10/ZdRSvKj3TXSIUHK0jio5EHIkvJ7A4CFV2m+BkL311i2Q/+ahifbwfHt1kdSOnOQHqC35aNYPraYK++WS2DGRHB4zqvg436BMcTulelRvHh+Qe/xH/CwU2pzZwFSFhPGkCf3SXx/QpjVhetrUkH6YyraKOuD0OT/LZNiiBb7PFniDwpp08zrqJAvQDxlND4XAx4P8E/Jpe72VbRRr19WDdtoCmEPy8lSTzYR1Mic4hVNNMG2gPxHwxU+H0ZiBD+zi+/TOcpcqBeKXd/MtAgs743XkorVv4DP+u5ihyodHoAwgsoy7To+HVZIPLf5bTdqWgG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",
  "imageContentType" : "image/png",
  "droneToMedicationsId" : null
},
{
  "id" : 3,
  "name" : "|XDHVC",
  "weight" : 150.0,
  "code" : "FT",
  "image" : null,
  "imageContentType" : "image/png",
  "droneToMedicationsId" : null
}]
}
```

PS: you can add drone or medication directly in h2 database console.
