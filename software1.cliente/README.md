# Plataforma de Clientes - Empresa de Gas

Este proyecto es una aplicación web full-stack construida con **Spring Boot** (Java) que proporciona un portal de autogestión para los clientes de una Empresa de Gas.

## Estructura del Proyecto

El proyecto está estructurado bajo el estándar oficial de una aplicación **Spring Boot**, que permite integrar tanto el backend como el frontend en un único repositorio de forma organizada.

### 1. El Backend (Lógica y Base de Datos)
Toda la lógica de servidor y conexión a la base de datos se encuentra en la ruta:
📁 `src/main/java/proye/soft1/cliente/`

*   **Entidades/Modelos** (ej. `Cliente.java`, `Contrato.java`, `Propiedad.java`): Representan las tablas de la base de datos y la estructura de los objetos de negocio.
*   **Repositorios** (ej. `ClienteRepository.java`): Interfaces que extienden de JPA/Spring Data para realizar operaciones CRUD y consultas a la base de datos de manera automática.
*   **Controladores** (ej. `ApiController.java`): Encargados de crear los *endpoints* de la API REST (`/api/...`). Reciben las peticiones HTTP (GET, POST) desde el frontend, interactúan con los repositorios y devuelven respuestas en formato JSON.

### 2. El Frontend (Las Vistas e Interfaz)
Toda la interfaz gráfica del usuario (arquitectura multipágina con diseño Glassmorphism) vive dentro de la ruta de recursos estáticos:
📁 `src/main/resources/static/`

*   **Archivos HTML** (`index.html`, `auth.html`, `propiedades.html`, `contratos.html`, `procesos.html`, `solicitudes.html`, `notificaciones.html`): Definen la estructura y el contenido de las diferentes pantallas.
*   **Diseño (`styles.css`)**: Contiene todos los estilos visuales, animaciones, utilidades responsive y la implementación del tema Glassmorphism.
*   **Lógica de Interfaz (`app.js`)**: Maneja el estado de la sesión utilizando `localStorage`, maneja los eventos del DOM (formularios, modales) y realiza las peticiones asíncronas (`fetch`) a la API del backend.

*(Al ejecutar la aplicación Spring Boot, el framework expone automáticamente el contenido de esta carpeta estática como páginas web accesibles desde el navegador en `http://localhost:8080`)*.

### 3. Carpetas de Construcción y Ejecución
*   📁 **`build/`**: Es una carpeta auto-generada. Cada vez que se compila el proyecto (ej. usando `./gradlew build`), Gradle toma el código fuente, lo compila y lo empaqueta aquí. **No se deben editar archivos directamente en esta carpeta**, ya que cualquier cambio se sobrescribirá en la siguiente compilación. Las ediciones deben hacerse siempre dentro de `src/`.
*   📁 **`gradle/`** y archivos **`build.gradle` / `gradlew`**: Son las herramientas de configuración y construcción del proyecto. Gestionan las dependencias (como la base de datos H2/PostgreSQL, Spring Web, librerías de prueba), ejecutan los tests y construyen el ejecutable (`.jar`).

### 4. Pruebas (Testing)
Toda la suite de pruebas automatizadas reside en:
📁 `src/test/java/proye/soft1/cliente/`

*   **`ApiControllerTest.java`**: Contiene las pruebas unitarias y de integración utilizando **JUnit 5** y **MockMvc**. Estas pruebas validan los flujos de la API (registro exitoso, validación de credenciales, protección de acceso a datos falsos) simulando el comportamiento de la capa de acceso a datos (`@MockBean`) para asegurar la confiabilidad del sistema antes de su ejecución.

---

## Cómo ejecutar el proyecto

1.  Asegúrate de tener Java (JDK 17 o superior) instalado.
2.  Desde la terminal, en la raíz del proyecto, ejecuta el siguiente comando:

    ```bash
    # En Windows:
    .\gradlew bootRun

    # En Linux / Mac:
    ./gradlew bootRun
    ```

3.  Una vez el servidor inicie, abre tu navegador y visita `http://localhost:8080`.
