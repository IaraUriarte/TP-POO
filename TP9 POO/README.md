# TP9 – GRASP y SOLID aplicados · SmartTicket

Sistema de venta de entradas y control de acceso. El backend está desarrollado en Java 17 con Spring Boot, Spring Data JPA y H2 en memoria. La API permite administrar lugares, sectores, eventos y clientes, cotizar y pagar entradas, y validar el ingreso mediante QR.

## Estructura

| Carpeta o archivo | Contenido |
| --- | --- |
| [`ticketera/`](./ticketera/) | Proyecto backend Spring Boot y configuración Maven. |
| `ticketera/src/main/java/com/ticketera/controller/` | Controladores REST; reciben solicitudes HTTP y delegan en servicios. |
| `ticketera/src/main/java/com/ticketera/service/` | Casos de uso y lógica de negocio de cotización, pagos, eventos, sectores, clientes y accesos. |
| `ticketera/src/main/java/com/ticketera/repository/` | Repositorios Spring Data JPA para persistir y consultar entidades. |
| `ticketera/src/main/java/com/ticketera/model/` | Entidades del dominio: `Lugar`, `Sector`, `Evento`, `PrecioSector`, `Entrada`, `Cliente` y `Venta`, además de sus estados. |
| `ticketera/src/main/java/com/ticketera/dto/` | Objetos de entrada y respuesta de la API. |
| `ticketera/src/main/java/com/ticketera/pasarela/` | Interfaz y pasarela de pago simulada, desacoplada de `PagoService`. |
| `ticketera/src/main/java/com/ticketera/exception/` | Manejo global de errores HTTP. |
| `ticketera/src/main/resources/application.properties` | Configuración de Spring Boot, H2, JPA y Swagger. |
| [`diagrama-clases.md`](./diagrama-clases.md) | Diagrama Mermaid de clases del sistema. |
| [`secuencia-hu1.md`](./secuencia-hu1.md), [`secuencia-hu2.md`](./secuencia-hu2.md), [`secuencia-hu3.md`](./secuencia-hu3.md) | Diagramas Mermaid de secuencia para cada historia de usuario. |

## Cómo ejecutarlo

Se necesita Java 17 y Maven instalado.

Desde PowerShell, ejecutar:

```powershell
cd ticketera
mvn spring-boot:run
```

La aplicación inicia en `http://localhost:8080`.

### Swagger / OpenAPI

- [Swagger UI](http://localhost:8080/swagger-ui.html)
- [Especificación OpenAPI JSON](http://localhost:8080/v3/api-docs)

### Base de datos H2

La aplicación usa una base H2 en memoria (`jdbc:h2:mem:ticketera`) y crea/elimina el esquema al iniciar y detenerse. Los datos no persisten al reiniciar la aplicación.

- [Consola H2](http://localhost:8080/h2-console)
- JDBC URL: `jdbc:h2:mem:ticketera`
- Usuario: `sa`
- Contraseña: *(vacía)*

## Historias de usuario y endpoints

| Historia | Endpoint | Comportamiento principal |
| --- | --- | --- |
| HU1 – Selección y cotización | `POST /api/ventas/cotizar` | Valida que la cantidad sea positiva y haya entradas disponibles; devuelve precio y total. |
| HU2 – Pago y emisión | `POST /api/pagos/iniciar-pago` | Verifica cliente, precio y disponibilidad; procesa el pago y, si se aprueba, crea la venta y emite las entradas. |
| HU3 – Control de acceso | `POST /api/accesos/escanear-acceso` | Busca el QR, valida que la entrada esté emitida y registra la hora de ingreso al cambiarla a utilizada. |

La API también incluye operaciones para crear, listar y buscar por ID lugares, sectores, clientes y eventos:

- `/api/lugares`
- `/api/sectores`
- `/api/clientes`
- `/api/eventos`

El endpoint `POST /api/pagos/webhook` está declarado como pendiente y actualmente responde `501 Not Implemented`.

## Escenarios cubiertos

- **HU1:** cotización exitosa; falta de disponibilidad; cantidad nula, cero o negativa.
- **HU2:** pago aprobado con venta pagada y entradas emitidas; pago rechazado con error `402`; falta de disponibilidad con error `409` antes de llamar a la pasarela.
- **HU3:** acceso permitido con registro de hora; entrada ya utilizada con error `409` sin reemplazar la hora original; QR inexistente con error `404`; entrada no emitida con error `400`.

### Decisión sobre el mensaje de cantidad inválida

La consigna presenta dos redacciones para el rechazo de una cantidad cero o negativa: en la sección 3, “La cantidad de entradas solicitadas debe ser mayor a cero”; en la sección 6, “Cantidad inválida”. El proyecto adopta **“Cantidad inválida”**, siguiendo la sección 6, y ese es el mensaje que devuelve la implementación de HU1.

La pasarela es simulada: rechaza los números de tarjeta que terminan en `0000` y aprueba los demás. El webhook todavía no procesa notificaciones.

## Pruebas

Para ejecutar las pruebas configuradas en Maven:

```powershell
cd ticketera
mvn test
```
