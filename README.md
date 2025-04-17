
# PGAD-SIP

**Plataforma de Generación de Aprendizaje Dinámico** 
El proyecto consiste en una plataforma de aprendizaje personalizada basada en IA, que ajusta el contenido a las necesidades y progresos del estudiante en tiempo real.

## Implementación con Docker

La aplicación se dockeriza utilizando `Docker` y `docker-compose`, facilitando su despliegue y ejecución. 
El proceso de construcción y levantado del entorno se simplifica mediante el uso de un script bash llamado `start.sh`, que ejecuta:

```bash
mvn clean package
docker-compose up --build 
```

> **Importante:** Es necesario contar con un archivo `.env` en la raíz del proyecto. Dicho archivo está disponible en el canal de Discord del equipo.

## Endpoints disponibles

### GET

- `/api/messages/public` 
  Devuelve un mensaje público.

- `/api/messages/protected` 
  Devuelve un mensaje protegido (requiere autenticación o permisos específicos).

- `/api/messages/private` 
  Devuelve un mensaje privado (requiere autenticación o permisos elevados).

### POST

- `/api/llama` 
  Recibe un prompt en el cuerpo del request y devuelve una respuesta generada por el modelo de IA. 
  **Body esperado:**
  ```json
  {
    "prompt": "tu texto aquí"
  }
  ```

- `/api/v1/mercadopago/preference` 
  Recibe un JSON con los datos de la preferencia de pago. Actualmente ignora los datos y utiliza información hardcodeada. 
  Devuelve el ID de la preferencia generada.

- `/api/v1/mercadopago/notify` 
  Endpoint para recibir notificaciones de MercadoPago. Actualmente solo registra (logea) la información recibida.

- `/api/paypal/orders` 
  Crea una nueva orden de pago en PayPal a partir del JSON recibido y devuelve el `orderID`.

- `/api/paypal/orders/{orderID}/capture` 
  Captura una orden de PayPal previamente creada. `{orderID}` debe ser reemplazado por el ID de la orden a capturar.
