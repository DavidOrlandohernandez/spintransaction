# spintransaction
Proyecto Spin By Oxxo proceso de selección 

1.- Stack Tecnológico utilizado:
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
* org.mapstruct 1.5.5-Final : Reducción de BoilerPlate.
* Junit-jupiter 6.0.3: Fase de testing automatizado.
* MockMvc: Fase de testing simulación de servidor. 
* org.springdoc (Swagger - OpenApi): Documentación ordenada estándar OpenAPI.
* slf4j: Logs dentro del servicio - Medida de observabilidad. 
* org.jacoco 0.8.12: Calcular cobertura de pruebas - repostería.  

1.3- Herramientas utilizadas para el desarrollo. 
* IntelliJ IDEA: Ide de desarrollo. 
* PgAdmin 4: Entorno Sql Local. 
* Postman: Pruebas unitarias. 
* GitHub Desktop: Herramientas de control de versiones. 
* Docker Desktop for Windows: Simulador de docker para uso local. 



2-. Instrucciones de compilación del proyecto:
Tras descargar el proyecto y abrirlo en el IDE de su elección. 

Seguir los siguientes pasos para una compilación exitosa.

Requisitos mínimos: 
* Tener instalado y configurado Maven.
* Tener instalado y configurado versión de Java 17.0.12 2024-07-16 LTS. 
* Tener una instancia de SQL Server 17 o Superior. 
* Necesario Crear una base de datos con el nombre: "localcredit".

2.1- Modificar el application-dev.yaml Según el puerto de exposición de la base de datos:
Ejemplo:  url: jdbc:postgresql://localhost:5432/localcredit
2.2- Colocarse en la ruta del proyecto. (Corresponder a su local)
"PS C:\Users\e_dohernandez\Downloads\PRACTICAS DE GIT\spintransaction>"
2.3- Ejecutar los siguientes comandos en el siguiente orden. 
* mvn clean
* mvn compile
* mvn install "En caso de tener problemas ejecutar el siguiente sin TEST"
* mvn install -DskipTests
* mvn package -DskipTests

NOTA: Configurar su IDE de acuerdo a la tecnologias aquí descritas. 




3.- Instrucciones para ejecutar el proyecto entorno LOCAL:
Requisitos mínimos: 
* Tener instalado y configurado versión de Java 17.0.12 2024-07-16 LTS. 
* Tener una instancia de SQL Server 17 o Superior.
* Necesario Crear una base de datos con el nombre: "localcredit".

3.1- Modificar el application-dev.yaml Según el puerto de exposición de la base de datos:
Ejemplo:  url: jdbc:postgresql://localhost:5432/localcredit
3.2- Abrir una línea de comando en la carpeta LOCAL que viene en el proyecto. 
3.3- Ejecutar el siguiente comando:
java -jar app.jar --spring.profiles.active=prod



4.- Instrucciones para ejecutar DockerFile en entorno DOCKER. 
Colocarse en la ruta DOCKER del proyecto y en un entorno dockerizado realizar 
los siguientes comandos: 
Ejemplo root: PS C:\Users\DAVID\Desktop\DOCUMENTACION\REPOSITORIOS\spintransaction\DOCKER> 

4.1- Crear la imagen      : docker build -t app .
4.2- Comprobación de la imagen : docker images / app:latest  Ejemplo: 432408856830        629MB          226MB    U
4.3- Crear el contenedor  : docker run -p 8085:8085 -e SPRING_PROFILES_ACTIVE=prod app
Nota: Uso de perfiles prod, test, dev. Usar PROD.

NOTAS: 
* Recordar que la ruta del POSTGRES DEBE DE IR DE LA SIG FORMA: PUENTE DOCKER LOCAL
spring.datasource.url=jdbc:postgresql://host.docker.internal:5433/localcredit
Esto ya está aplicado para tener cuidado con el puerto. 





4- Decisión de la estructura y arquitectura del proyecto:
El proyecto está estructurado bajo una arquitectura en capas (Layered Architecture) 
utilizando Spring Boot, con una separación clara de responsabilidades que permite mantener un 
diseño modular, escalable y fácil de mantener. Se implementa un enfoque RESTful donde la 
comunicación se realiza mediante DTOs, desacoplando el modelo de persistencia (Entities) 
del modelo expuesto a la API. Además, se incorpora el uso de Specifications para 
consultas dinámicas y un sistema de mapeo centralizado mediante Mappers.

4.1- Representación de (Layered Architecture):

com.spin.transaction
├── SpinTransactionApplication.java
├── client
├── config
├── controller
├── dto
├── entity
├── enums
├── exception
├── mapper
├── repository
├── service
├── specification
└── wrapper


5.- COBERTURA DE IMPLEMENTACIÓN:
* Implementacion de spring initializr: Generación base del proyecto. 
* Implementación de Mock por medio de servicios: Se usó Un controller separado como Mock para la respuesta de proveedor. 
* Implementación de Jacoco: Documentación de la cobertura de pruebas. 
* Implementación de Swagger - OpenAPI Standar: Documentación de contratos y testing. 
* Implementación de pruebas con Junit y Mocks: Test automatizados. 
* Implementación de pruebas unitarias con Postman: Test unitarios. Se compartirá la colección. 
* Implementación de docker con DockerFile y Docker-Compose: Se genera imagen base para dockerizar manualmente y por compose. 
* Implementación de Mapstuct: Reducción de BoilerPlate.
* Implementación de Logs con slf4j.



6.- CONCLUSIONES FINALES
El proyecto está construido sobre un stack moderno basado en Java 17 LTS y Spring Boot, lo que garantiza estabilidad, 
rendimiento y soporte a largo plazo. El uso de Maven como gestor de dependencias permite mantener un control ordenado 
del ciclo de vida del proyecto, mientras que PostgreSQL 17 aporta una base de datos robusta y confiable para entornos 
productivos.
La elección de Spring Boot como framework principal facilita el desarrollo de servicios REST escalables y bien estructurados, 
reduciendo la complejidad de configuración y acelerando la entrega de funcionalidades. Complementado con Spring Data JPA, 
Se simplifica el acceso a datos, permitiendo una integración eficiente con la base de datos. Además, herramientas como Lombok 
optimizan la productividad al reducir código repetitivo, y DevTools mejora la experiencia de desarrollo con recarga automática.
El uso de YAML para la configuración aporta mayor claridad y organización en comparación con formatos tradicionales.
En conjunto, este stack tecnológico ofrece un equilibrio sólido entre productividad, mantenibilidad y escalabilidad, 
lo que lo convierte en una base confiable para construir servicios REST competitivos y preparados para entornos empresariales 
reales.


:::Si llegaste hasta aca te agradezco mucho tu atención espero sea de su agrado este pequeño proyecto.:::

No se utilizó IA generativa para este proyecto. Más bien si use IA pero como 
consultor de dudas e implementación de algunas partes del proyecto. 
cada parte del código se realizó paso a paso.  Comit por comit. 
IA utilizada: ChatGPT basada en el modelo GPT-5.6-mini.
Apoyos significativos de la IA:

* En la generación del GET para la paginación. Interesante uso specification
* Pruebas con SpringBootTest y Junit.
* Algunas dudas sobre el manejo de excepciones agrupadas. 