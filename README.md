# spintransaction
Proyecto Spin By Oxxo proceseo de selección 

1.- Stack Técnologico utilizado:
* Maven proyect
* Lenguaje 17.0.12 2024-07-16 LTS 
* Spring Boot 4.1.1
* PostgreSQL 17 
* Packaging  tipo Jar
* Properties tipo Yaml

1.1 - Dependencias iniciales: 
* Spring Boot DevTools
* Spring Web
* Loombook 
* Spring Data JPA 
* PostgreSQL Driver

1.2- Dependencias extras relevantes
* org.mapstruct 1.5.5-Final : Reduccion de BoilerPlate.
* Junit-jupiter 6.0.3: Fase de testing automatizado.
* MockMvc: Fase de testing simulacion de servidor. 
* org.springdoc (Swagger - OpenApi): Documentacion ordenada estandar OpenAPI.
* slf4j: Logs dentro del servicio - Medida de observabilidad. 
* org.jacoco 0.8.12: Calcular covertura de pruebas - reporteria.  

1.3- Herramientas utilizadas para el desarrollo. 
* IntelliJ IDEA: Ide de desarrollo. 
* PgAdmin 4: Entorno Sql Local. 
* Postman: Pruebas unitarias. 
* GitHub Desktop: Herramientas de control de versiones. 
* Docker Desktop for Windows: Simulador de docker para uso local. 


COBERTURA DE IMPEMENTACION:
Implementacion de spring initializr: Generación base del proyecto. 
Implementacion de Mock por medio de servicios: Se uso Un controller separado como Mock para la respuesta de proveedor. 
Implementacion de Jacoco: Documentación de la covertura de pruebas. 
Implementacion de Swagger - OpenAPI Standar: Documentacion de contratos y testing. 
Implementacion de pruebas con Junit y Mocks: Test automatizadaos. 
Implementacion de pruebas unitarias con Postman: Test unitarios. Se compartira coleccion. 
Implementacion de docker con DockerFile y Docker-Compose: Se genera imgane base para dockerizar manualmente y por compose. 
Implementacion de Mapstuct: Reducción de BoilerPlate.
Implementacion de Logs con slf4j.



El proyecto está construido sobre un stack moderno basado en Java 17 LTS y Spring Boot, lo que garantiza estabilidad, 
rendimiento y soporte a largo plazo. El uso de Maven como gestor de dependencias permite mantener un control ordenado 
del ciclo de vida del proyecto, mientras que PostgreSQL 17 aporta una base de datos robusta y confiable para entornos 
productivos.
La elección de Spring Boot como framework principal facilita el desarrollo de servicios REST escalables y bien estructurados, 
reduciendo la complejidad de configuración y acelerando la entrega de funcionalidades. Complementado con Spring Data JPA, 
se simplifica el acceso a datos, permitiendo una integración eficiente con la base de datos. Además, herramientas como Lombok 
optimizan la productividad al reducir código repetitivo, y DevTools mejora la experiencia de desarrollo con recarga automática.
El uso de YAML para la configuración aporta mayor claridad y organización en comparación con formatos tradicionales.
En conjunto, este stack tecnológico ofrece un equilibrio sólido entre productividad, mantenibilidad y escalabilidad, 
lo que lo convierte en una base confiable para construir servicios REST competitivos y preparados para entornos empresariales 
reales.


2-. Instrucciones de compilación del proyecto:
Tras descargar el proyecto y abrirlo en el IDE de su eleccion. 

Seguir los siguientes pasos para una compilación exitosa.

Requisitos minimos: 
* Tener instalado y configurado Maven.
* Tener instalado y configurado version de 17.0.12 2024-07-16 LTS. 
* Tener una instancia de SQL Server 17 o Superior. 

1.- Colocarse en la ruta del proyecto. (Corresponder a su local)
"PS C:\Users\e_dohernandez\Downloads\PRACTICAS DE GIT\spintransaction>"

2.- Ejecutar los siguientes comandos en el siguiente orden. 
* mvn clean
* mvn compile
* mvn install "En caso de tener problemas ejecutar el siguiente sin TEST"
* mvn install -DskipTests
* mvn package -DskipTests

