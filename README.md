# Master Martini — backend del equipo

Spring Boot 4.2, Java 21, Spring Data JPA, Spring Security y JWT. Integra usuarios, intereses, favoritos y catálogo del integrante 1 con los CRUD de cursos, recetas y videos del integrante 2.

## Arquitectura

`Controller → Service → Repository → MySQL`. Los DTOs separan el JSON de las entidades. `Contenido` concentra los campos comunes y usa herencia JPA `JOINED`: Curso, Receta y Video mantienen su entidad y tabla específica, pero sus registros también aparecen en `/api/contenidos` y pueden ser favoritos.

Base `master_martini`: usuarios, intereses, usuario_intereses, contenidos, favoritos, cursos, recetas y videos. SQL sin datos: [estructura-master-martini.sql](database/estructura-master-martini.sql). Importarlo solo sobre una base nueva; no reemplaza copias de seguridad ni migraciones de datos existentes.

## Ejecutar

Requiere JDK 21 o superior y Maven Wrapper. Variables de entorno: `DB_URL`, `DB_USERNAME`, `DB_PASSWORD`, `JWT_SECRET` (32 bytes aleatorios codificados en Base64), `JWT_EXPIRATION` (3600 segundos por defecto), `SERVER_ADDRESS` y `PORT`.

```powershell
./mvnw.cmd verify
./mvnw.cmd package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

El repositorio Android incluye scripts para generar credenciales privadas de desarrollo, preparar MySQL separado y levantar este backend. Clonar ambos proyectos como carpetas hermanas `app_2` y `backend_app`. El Dockerfile permite preparar el servidor para un alojamiento con HTTPS; sus secretos deben configurarse en el entorno, nunca en Git.

`application.properties` usa `DB_DDL=update` para el desarrollo. El alojamiento remoto debe revisar su esquema y usar migraciones/validate cuando corresponda. La configuración local no demuestra por sí sola un despliegue remoto.

## Autenticación

- `POST /api/usuarios/registro`: crea una cuenta; devuelve los datos públicos, sin contraseña ni hash.
- `POST /api/usuarios/login`: devuelve `token`, `tipo: Bearer` y `usuario`.
- Rutas protegidas: enviar `Authorization: Bearer TOKEN`.
- `GET /api/intereses` permite consultar las opciones iniciales sin token.
- Los favoritos verifican que el usuario de la ruta corresponda a la identidad del JWT.

El acceso de administración por roles no se implementa en esta entrega: los usuarios autenticados pueden gestionar contenidos del catálogo académico. Mantener el backend en un entorno de prueba o incorporar las políticas de roles necesarias antes de un uso público real.

## Módulos del integrante 2

`/api/cursos`, `/api/recetas` y `/api/videos` tienen GET listado, GET por ID, POST, PUT por ID y DELETE por ID. Se conservan Entity, DTO, Repository, Service y Controller por tipo. Los errores de validación incluyen nombres de campo; los ID inexistentes responden 404. Al eliminar contenido se eliminan antes sus relaciones de favoritos, para mantener la integridad referencial.

Documentación: [API_CONTENIDOS.md](docs/API_CONTENIDOS.md). La app usa además TheMealDB como servicio externo independiente.

## Pruebas

`./mvnw.cmd verify` ejecuta pruebas con H2: CRUD de los tres módulos, validaciones, registro/login con JWT real, correo duplicado, cuenta inactiva, intereses, catálogo general, favoritos, integridad referencial y control de acceso entre usuarios. JaCoCo exige al menos **80 % de cobertura global de líneas**; informe: `target/site/jacoco/index.html`.

Se probó adicionalmente el servidor con MySQL 8.4 real y el cliente Retrofit de Android. Los secretos del perfil de pruebas son exclusivos de la suite y no son la configuración del servidor en ejecución.
