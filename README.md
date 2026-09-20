# SpinTransaction 🚀
> **Proyecto para proceso de selección Spin By Oxxo**

---

## 🛠️ 1. Stack Tecnológico

* **Proyecto Base:** Maven
* **Lenguaje:** Java `17.0.12 LTS` (2024-07-16)
* **Framework:** Spring Boot `4.1.1`
* **Base de Datos:** PostgreSQL `17`
* **Empaquetado:** JAR
* **Configuración:** YAML (`application-dev.yaml`)

### 1.1 Dependencias Iniciales
* `Spring Boot DevTools`
* `Spring Web`
* `Lombok`
* `Spring Data JPA`
* `PostgreSQL Driver`

### 1.2 Dependencias Extras Relevantes
* **MapStruct (`org.mapstruct 1.5.5-Final`):** Reducción de código repetitivo (Boilerplate).
* **JUnit Jupiter (`6.0.3`):** Fase de testing automatizado.
* **MockMvc:** Simulación de servidor para testing.
* **SpringDoc (`Swagger / OpenAPI`):** Documentación ordenada bajo estándares OpenAPI.
* **SLF4J:** Registros y logs del servicio (Observabilidad).
* **JaCoCo (`0.8.12`):** Generación de reportes y métricas de cobertura de pruebas.

### 1.3 Herramientas de Desarrollo
* **IDE:** IntelliJ IDEA
* **SQL Client Local:** PgAdmin 4
* **Testing API:** Postman
* **Control de Versiones:** GitHub Desktop
* **Contenedores:** Docker Desktop for Windows

---

## 🛠️ 2. Instrucciones de Compilación

Tras clonar o descargar el proyecto y abrirlo en tu IDE de preferencia, sigue estos pasos para una compilación exitosa.

### Requisitos Mínimos
* Maven instalado y configurado en el sistema.
* Java JDK `17.0.12 LTS` instalado y configurado.
* Instancia de PostgreSQL (o SQL Server compatible) versión 17 o superior.
* Base de datos creada con el nombre: **`localcredit`**.

### Pasos para Compilar
1. **Configurar Base de Datos:**  
   Modifica el archivo `application-dev.yaml` ajustando la URL según el puerto de tu base de datos:
   ```yaml
   url: jdbc:postgresql://localhost:5432/localcredit
   ```
2. **Ubicarse en el directorio raíz del proyecto:**
   ```bash
   cd "C:\ruta\a_tu_proyecto\spintransaction"
   ```
3. **Ejecutar los siguientes comandos en orden:**
   ```bash
   mvn clean
   mvn compile
   mvn install
   ```
   > 💡 *Nota:* En caso de tener problemas con las pruebas durante la instalación, ejecuta sin tests:
   ```bash
   mvn install -DskipTests
   mvn package -DskipTests
   ```

---

## 💻 3. Ejecución en Entorno Local

### Requisitos Mínimos
* Java JDK `17.0.12 LTS`.
* Base de datos creada con el nombre: localcredit.

### Pasos de Ejecución
1. Configurar la cadena de conexión en `application-dev.yaml`.
2. Abrir una terminal en la carpeta `LOCAL` que viene dentro del proyecto.
3. Ejecutar el artefacto mediante el comando:
   ```bash
   java -jar app.jar --spring.profiles.active=dev
   ```

---

## 🐳 4. Ejecución en Entorno Docker

Ubicarse en la ruta `DOCKER` dentro del proyecto y ejecutar los siguientes comandos desde un entorno dockerizado:

```bash
# Ejemplo de ruta local: C:\spintransaction\DOCKER>
```

1. **Construir la imagen:**
   ```bash
   docker build -t app .
   ```
2. **Comprobar la imagen generada:**
   ```bash
   docker images app:latest
   ```
3. **Crear y desplegar el contenedor:**
   ```bash
   docker run -p 8085:8085 -e SPRING_PROFILES_ACTIVE=prod app
   ```

> 📌 **Notas Importantes sobre Docker:**
> * Perfiles disponibles: `prod`, `test`, `dev`. Se recomienda usar **`prod`**.
> * La cadena de conexión a PostgreSQL para conectar el contenedor con el host local debe usar el puente Docker-Host:
>   ```properties
>   spring.datasource.url=jdbc:postgresql://host.docker.internal:5433/localcredit
>   ```

---

## 🏗️ 5. Estructura y Arquitectura del Proyecto

El proyecto está estructurado bajo una **Arquitectura en Capas (*Layered Architecture*)** utilizando Spring Boot. Mantiene una separación clara de responsabilidades que garantiza un diseño modular, escalable y mantenible.

* **Enfoque RESTful:** La comunicación externa se realiza mediante DTOs, desacoplando el modelo de persistencia (`Entities`) de los contratos expuestos en la API.
* **Consultas Dinámicas:** Uso de `Specifications` para filtrados y búsquedas complejas.
* **Mapeo Centralizado:** Integración de `Mappers` (MapStruct) para la conversión limpia de objetos.

### 📂 Estructura de Directorios

```text
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
```

---

## 📊 6. Cobertura de Implementación

- [x] **Spring Initializr:** Estructuración base del proyecto.
- [x] **Mocks de Servicios:** Controller independiente simulando respuestas del proveedor externo.
- [x] **JaCoCo:** Generación y documentación de métricas de cobertura de código.
- [x] **Swagger / OpenAPI Standard:** Documentación interactiva de contratos de API.
- [x] **Testing Automatizado:** Pruebas de integración e unitarias con JUnit y Mocks.
- [x] **Colección de Postman:** Pruebas unitarias/funcionales (colección compartida en el repositorio).
- [x] **Dockerización:** Configuración con `Dockerfile` y `docker-compose`.
- [x] **MapStruct:** Mapeo eficiente para reducción de *boilerplate*.
- [x] **Observabilidad:** Trazado de logs con SLF4J.

---

## 📝 7. Conclusiones Finales

El proyecto está construido sobre un stack moderno basado en **Java 17 LTS** y **Spring Boot**, lo que garantiza estabilidad, rendimiento y soporte a largo plazo. El uso de Maven como gestor de dependencias permite mantener un control ordenado del ciclo de vida del proyecto, mientras que **PostgreSQL 17** aporta una base de datos robusta y confiable para entornos productivos.

La elección de Spring Boot facilita el desarrollo de servicios REST escalables y bien estructurados, reduciendo la complejidad de configuración. Complementado con **Spring Data JPA**, se simplifica el acceso a datos. Además, herramientas como Lombok y DevTools optimizan la productividad y la experiencia de desarrollo. El uso de YAML para la configuración aporta claridad en comparación con formatos tradicionales.

En conjunto, este stack ofrece un equilibrio sólido entre productividad, mantenibilidad y escalabilidad, convirtiéndolo en una base confiable para servicios REST de nivel empresarial.

---
## 📝 8. UPDATE
- Desacoplamiento del dominio con la entidad: Service orquesta la implementación de la logica.
- Cambio de Servlete: Quitamos removi TOMCAT y implemente JETTY
- Implementación de CircuitBreaker: Patron de reciliencia a fallos. 
- Pool de conexiones: Mejora en la configuración de Hikari. Concurrencia. 
- Implementación de Idempotencia: Evitar duplicidad de registro en la transaccion. 
---

> 🤝 *Si llegaste hasta aquí, agradezco mucho tu atención y tiempo dedicado a la revisión. ¡Espero que este proyecto sea de tu agrado!*

### 💡 Nota sobre el uso de Inteligencia Artificial
No se utilizó IA generativa para la escritura directa del proyecto. Se empleó **ChatGPT (Modelo GPT-5.6-mini)** como **consultor técnico** para resolver dudas puntuales de implementación. Cada parte del código fue desarrollada paso a paso, *commit* por *commit*.

**Apoyos significativos de la IA:**
* Diseño de consulta `GET` con paginación dinámica utilizando `Specifications`.
* Configuración de pruebas unitarias y de integración con `@SpringBootTest` y JUnit.
* Estructuración y manejo centralizado de excepciones agrupadas.
