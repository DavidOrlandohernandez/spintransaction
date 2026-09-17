package com.spin.transaction.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "TRANSACTION API BY SPIN",
                description = "API REST que GESTIONA la ejecución de transacciones financieras\n" +
                        "(crédito y débito).",
                termsOfService = "www.spintransation.com/terminos_y_condiciones",
                version = "0.0.1-SNAPSHOT",
                contact = @Contact(
                        name = "David Hernandez Bastidas",
                        url = "https://github.com/DavidOrlandohernandez/spintransaction",
                        email = "david.hernandez@gmail.com"

                ),
                license = @License(
                        name = "Standar Sofware use License for SpinTransaction",
                        url = "https://github.com/DavidOrlandohernandez"
                )
        ),
        servers = {
                @Server(
                        description = "DEV SERVER",
                        url = "http://localhost:8085"
                ),
                @Server(
                        description = "TEST SERVER",
                        url = "http://localhost:8085"
                )
        }/*,
        security = @SecurityRequirement(
                name = "Security Token"
        )*/
)
/*@SecurityScheme(
        name = "Security Token",
        description = "Access Token For My API",
        type = SecuritySchemeType.HTTP,
        paramName = HttpHeaders.AUTHORIZATION,
        in = SecuritySchemeIn.HEADER,
        scheme = "bearer",
        bearerFormat = "JWT"
)*/
public class SwaggerConfig {
}
