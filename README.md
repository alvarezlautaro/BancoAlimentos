# BancoAlimentos
 
API REST desarrollada con Spring Boot para la gestión integral de un banco de alimentos: donantes, donaciones, productos, remitos, instituciones beneficiarias y facturación. Proyecto académico en equipo (UTN).
 
## Tabla de contenidos
 
- [Características](#características)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Endpoints principales](#endpoints-principales)

## Características
 
- Gestión de **donantes**, con historial de donaciones por donante.
- Registro de **donaciones** y sus ítems (`ItemDonacion`), con soporte de filtrado por producto, donación o categoría.
- Catálogo de **productos**, con categorías y filtrado.
- Generación y gestión de **remitos** y **detalles de remito**, incluyendo control de stock.
- Gestión de **instituciones** beneficiarias, con historial y filtros por nombre, tipo o estado de pago.
- **Facturación**, incluyendo generación automática de factura a partir de una donación.
- Reglas de negocio validadas en capa de servicio: control de deudas, ítems duplicados o vencidos, ajuste de stock, con manejo global de excepciones (`GlobalExceptionHandler`) y respuestas de error estandarizadas (HTTP 422 vía `ReglaNegocioException`).
- **Autenticación y autorización** basada en JWT, con roles y permisos configurables.
- Paginación de resultados (`Pageable`) en los listados principales.
- Documentación interactiva de la API vía Swagger/OpenAPI.
- Auditoría de cambios con Hibernate Envers.
## Tecnologías
 
- **Java 21**
- **Spring Boot** (Web MVC, Data JPA, Validation, Security)
- **MySQL** como base de datos principal (H2 disponible para pruebas)
- **JWT** (jjwt) para autenticación
- **ModelMapper** para mapeo entre entidades y DTOs
- **Hibernate Envers** para auditoría
- **Springdoc OpenAPI** para documentación de la API
- **Lombok**
- **Docker / Docker Compose**
- **Maven**
## Arquitectura
 
El proyecto sigue una organización por *features* (vertical slicing), donde cada módulo de negocio agrupa su propio controlador, servicio, repositorio, modelo, DTOs y mapper:
 
```
src/main/java/com/group6/BancoAlimentos/
├── Common/            # Excepciones y mappers compartidos
├── Config/            # Configuración general de la aplicación
├── Security/          # Autenticación JWT, roles, permisos
└── Features/
    ├── Donantes/
    ├── Donacion/
    ├── ItemDonacion/
    ├── Producto/
    ├── Remito/
    ├── DetalleRemito/
    ├── Institucion/
    └── Factura/
```
 
Cada feature expone su propio `@RequestMapping` bajo `/api/...` y encapsula su lógica de negocio en la capa de servicio.

## Endpoints principales
 
| Recurso | Base path | Operaciones destacadas |
|---|---|---|
| Donantes | `/api/donantes` | CRUD + historial de donaciones (`/{id}/historial`) |
| Donaciones | `/api/donaciones` | CRUD + confirmación (`/{id}/confirmar`) |
| Ítems de donación | `/api/itemdonacion` | CRUD + filtros por producto, donación y categoría |
| Productos | `/api/productos` | CRUD + filtrado por categoría |
| Remitos | `/api/remitos` | CRUD |
| Detalles de remito | `/api/detalles-remito` | CRUD + búsqueda por remito e ítem de donación |
| Instituciones | `/api/instituciones` | CRUD + historial y filtros por nombre, tipo y estado de pago |
| Facturas | `/api/facturas` | CRUD + generación automática (`/generar/{idDonacion}`) |
| Autenticación | `/api/auth` | Login (`/login`) |

## Autores
 
Proyecto desarrollado en equipo como trabajo académico para la Tecnicatura Universitaria en Programación (UTN).
