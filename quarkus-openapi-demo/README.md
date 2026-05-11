# quarkus-openapi-demo

Demo mínima: **Quarkus REST** + **SmallRye OpenAPI** + **Swagger UI** + **Jackson** para JSON.

| Recurso | URL |
| --- | --- |
| Listar frutas | `GET /fruits` |
| Fruta por id | `GET /fruits/{id}` |
| OpenAPI (JSON) | <http://localhost:8080/q/openapi> |
| OpenAPI (YAML) | <http://localhost:8080/q/openapi.yaml> |
| Swagger UI | <http://localhost:8080/q/swagger-ui> |

Código: `com.example.FruitResource`, metadatos globales en `FruitApplication`.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/quarkus-openapi-demo-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- [REST](https://quarkus.io/guides/rest)
- [OpenAPI and Swagger UI](https://quarkus.io/guides/openapi-swaggerui)

Documentación Quarkus: <https://quarkus.io/>.
