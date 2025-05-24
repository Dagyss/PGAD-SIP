# PGAD-SIP

**Plataforma de Generación de Aprendizaje Dinámico** 
Plataforma de aprendizaje personalizada basada en IA que ajusta el contenido a las necesidades y progresos del estudiante en tiempo real.

---

## Requisitos Previos

- Docker y docker-compose instalados.
- Java 17+ y Maven (si se desea ejecutar localmente sin Docker).
- Archivo `.env` en la raíz del proyecto (disponible en el canal de Discord del equipo).

---

## Instalación y Despliegue

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tu-organizacion/PGAD-SIP.git
   cd PGAD-SIP/pga
   ```

2. Copiar el archivo de entorno:
   ```bash
   cp .env.example .env
   # Ajustar variables en .env según tu configuración
   ```

3. Levantar servicios con Docker:
   ```bash
   chmod +x start.sh
   ./start.sh
   ```
   Esto construye las imágenes y arranca los contenedores: base de datos, API y modelo de IA.

4. Acceder a la API en: `http://localhost:6060/api`

---

## Autenticación y Seguridad

- Para endpoints protegidos se utiliza JWT (configuración en `SecurityConfig`).
- Algunos endpoints requieren roles específicos, por ejemplo:
  - `create:order` para creación de órdenes PayPal.
  - `capture:order` para captura de órdenes PayPal.

---

## Endpoints de la API

### Mensajes
| Método | Ruta                          | Descripción                              | Autorización |
|--------|-------------------------------|------------------------------------------|--------------|
| GET    | `/api/messages/public`        | Mensaje público                          | Ninguna      |
| GET    | `/api/messages/protected`     | Mensaje protegido                        | JWT          |
| GET    | `/api/messages/private`       | Mensaje privado                          | JWT + Rol    |

### IA (Llama)
| Método | Ruta            | Descripción                                      | Cuerpo                                          |
|--------|-----------------|--------------------------------------------------|-------------------------------------------------|
| POST   | `/api/llama`    | Envía prompt al modelo Llama y devuelve respuesta| `{ "prompt": "texto" }`                          |

### Usuarios
| Método | Ruta                       | Descripción                          | Parámetros               |
|--------|----------------------------|--------------------------------------|--------------------------|
| GET    | `/api/usuarios`            | Lista todos los usuarios             | —                        |
| GET    | `/api/usuarios/{id}`       | Obtiene usuario por ID               | `id` (String)            |
| POST   | `/api/usuarios`            | Crea un nuevo usuario                | UsuarioDTO en JSON       |
| PUT    | `/api/usuarios/{id}`       | Actualiza usuario                    | `id` y UsuarioDTO        |
| DELETE | `/api/usuarios/{id}`       | Elimina usuario                      | `id`                     |
| GET    | `/api/usuarios/sincronizar`| Sincroniza todos los usuarios        | —                        |

### Cursos y Módulos
- **Cursos** (`/api/cursos`)
  - GET `/api/cursos`: listado de cursos.
  - GET `/api/cursos/{id}`: detalle de curso.
  - POST `/api/cursos`: crea curso.
  - PUT `/api/cursos/{id}`: actualiza curso.
  - DELETE `/api/cursos/{id}`: elimina curso.
- **Módulos** (`/api/modulos`)
  - GET `/api/modulos`: lista módulos (opcional `?cursoId=`).
  - GET `/api/modulos/{id}`: detalle módulo.
  - POST `/api/modulos`: crea módulo.
  - PUT `/api/modulos/{id}`: actualiza módulo.
  - DELETE `/api/modulos/{id}`: elimina módulo.

### Contenidos y Ejercicios
- **Contenidos** (`/api/contenidos`)
  - GET `/api/contenidos`: lista contenidos (opcional `?moduloId=`).
  - GET `/api/contenidos/{id}`: detalle de contenido.
  - POST `/api/contenidos`: crea contenido.
  - PUT `/api/contenidos/{id}`: actualiza contenido.
  - DELETE `/api/contenidos/{id}`: elimina contenido.
- **Ejercicios** (`/api/ejercicios`)
  - GET `/api/ejercicios`: lista ejercicios (opcional `?moduloId=`).
  - GET `/api/ejercicios/{id}`: detalle ejercicio.
  - POST `/api/ejercicios/generate`: genera ejercicio dinámico.
  - PUT `/api/ejercicios/{id}`: actualiza ejercicio.
  - DELETE `/api/ejercicios/{id}`: elimina ejercicio.

### Evaluaciones y Calificaciones
- **Evaluaciones** (`/api/evaluaciones`)
  - GET `/api/evaluaciones`: lista evaluaciones (opcional `?cursoId=`).
  - GET `/api/evaluaciones/{id}`: detalle evaluación.
  - POST `/api/evaluaciones`: crea evaluación.
  - PUT `/api/evaluaciones/{id}`: actualiza evaluación.
  - DELETE `/api/evaluaciones/{id}`: elimina evaluación.
- **Calificaciones** (`/api/calificaciones`)
  - POST `/api/calificaciones`: crea calificación.
  - GET `/api/calificaciones/usuario/{idUsuario}`: calificaciones de un usuario.
  - GET `/api/calificaciones/curso/{idCurso}`: calificaciones de un curso.
  - DELETE `/api/calificaciones/{idCurso}/{idUsuario}`: elimina calificación.

### Resoluciones y Progresos
- **Resoluciones** (`/api/resoluciones`)
  - POST `/api/resoluciones`: crea resolución.
  - GET `/api/resoluciones/usuario/{idUsuario}`: resoluciones por usuario.
  - GET `/api/resoluciones/ejercicio/{idEjercicio}`: resoluciones por ejercicio.
  - DELETE `/api/resoluciones/{idResolucion}`: elimina resolución.
- **Progresos** (`/api/progresos`)
  - GET `/api/progresos`: lista progresos (opcional `?idUsuario=&idCurso=`).
  - GET `/api/progresos/{id}`: detalle progreso.
  - POST `/api/progresos`: crea progreso.
  - PUT `/api/progresos/{id}`: actualiza progreso.
  - DELETE `/api/progresos/{id}`: elimina progreso.

### Categorías y Certificaciones
- **Categorías** (`/api/categorias`)
  - GET `/api/categorias`: lista categorías.
  - GET `/api/categorias/{id}`: detalle categoría.
  - POST `/api/categorias`: crea categoría.
  - PUT `/api/categorias/{id}`: actualiza categoría.
  - DELETE `/api/categorias/{id}`: elimina categoría.
- **Certificaciones** (`/api/certificaciones`)
  - GET `/api/certificaciones`: lista certificaciones (opcional `?idUsuario=`).
  - GET `/api/certificaciones/{id}`: detalle certificación.
  - POST `/api/certificaciones`: crea certificación.
  - PUT `/api/certificaciones/{id}`: actualiza certificación.
  - DELETE `/api/certificaciones/{id}`: elimina certificación.

### Suscripciones y Pagos
- **Tipos de Suscripción** (`/api/tiposSuscripcion`)
  - GET `/api/tiposSuscripcion`: lista tipos de suscripción.
- **Suscripciones** (`/api/suscripciones`)
  - GET `/api/suscripciones`: lista suscripciones (opcional `?idUsuario=`).
  - GET `/api/suscripciones/{id}`: detalle suscripción.
  - POST `/api/suscripciones`: crea suscripción.
  - PUT `/api/suscripciones/{id}`: actualiza suscripción.
  - DELETE `/api/suscripciones/{id}`: elimina suscripción.
- **Pagos** (`/api/pagos`)
  - GET `/api/pagos`: lista pagos (opcional `?idUsuario=`).
  - GET `/api/pagos/{paymentId}`: detalle pago.
  - POST `/api/pagos`: crea pago (incluir `idUsuario` en el body).
  - PUT `/api/pagos/{paymentId}`: actualiza pago.
  - DELETE `/api/pagos/{paymentId}`: elimina pago.
- **MercadoPago** (`/api/v1/mercadopago`)
  - POST `/preference`: crea preferencia de pago.
  - POST `/notify`: recibe notificaciones de pago.
- **PayPal** (`/api/paypal`)
  - POST `/orders` (create:order): crea orden PayPal.
  - POST `/orders/{orderID}/capture` (capture:order): captura orden.

---

## Ejemplos de Uso

### Crear Usuario
```bash
curl -X POST http://localhost:6060/api/usuarios \
  -H "Content-Type: application/json" \
  -d '{"nombre": "Carlos Núñez", "correo": "carlos.nunez@example.com", "nivelConocimiento": "intermedio","tipoUsuario": "free","estadoCuenta": false}'
```

### Generar Ejercicio
```bash
curl -X POST http://localhost:6060/api/ejercicios/generate \
  -H "Content-Type: application/json" \
  -d '{"moduloId": 1, "dificultad": 3, "categoriaIds": [2, 3]}'
```

