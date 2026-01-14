# Tasca S4.02 Spring Boot - Nivel 2
## Gestión de Frutas con MySQL

---

## 📌 Descripción - Enunciado del ejercicio

Este proyecto amplía la funcionalidad del **Nivel 1**, integrando la gestión de **proveedores de fruta** y utilizando **MySQL** como sistema de base de datos persistente.

El objetivo principal es implementar una relación **@ManyToOne** entre las entidades `Fruit` y `Provider`.

El sistema permite registrar el origen de cada producto, garantizando la integridad de los datos mediante **validaciones** y una **arquitectura por capas** (`Controller`, `Service`, `Repository`).

---

## 👤 Historias de Usuario Implementadas

### Gestión de Proveedores
- Registro de proveedores con **nombre y país**
- No se permiten **duplicados ni nombres vacíos**

### Relación Fruit–Provider
- Cada fruta debe estar asociada a un **ID de proveedor válido**

### Filtrado Avanzado
- Consulta de todas las frutas suministradas por un proveedor específico

### CRUD Completo
- Actualización y eliminación de proveedores
- Restricción: **no se puede eliminar un proveedor con frutas asociadas**

### Manejo de Errores
- Respuestas HTTP estandarizadas:
    - `201 Created`
    - `204 No Content`
    - `400 Bad Request`
    - `404 Not Found`

---

## 🛠️ Tecnologías Utilizadas

- **Java 21**
- **Spring Boot 3.5.x**
- **Spring Data JPA (Hibernate)**
- **MySQL Connector/J**
- **Spring Boot Validation (Jakarta Validation)**
- **Lombok**
- **JUnit 5 & Mockito** (Unit Testing)
- **MockMvc** (Integration Testing)
- **Docker & Docker Compose**

---

## 📁 Estructura del Proyecto

```
plaintext
cat.itacademy.s04.t02.n02.JavaSprint4_2CRUDLevel2Mysql/
├── controllers/          # Controladores REST con mapeo de rutas
├── services/             # Lógica de negocio e interfaces
│   └── impl/             # Implementación de los servicios
├── repository/           # Interfaces que extienden JpaRepository
├── entities/             # Entidades JPA (Fruit, Provider)
├── DTO/                  # Data Transfer Objects (Request / Response)
├── exceptions/           # Excepciones personalizadas y GlobalExceptionHandler
└── JavaSprint42Application.java  # Clase principal
```
## ⚙️ Configuración y Requisitos
Requisitos Previos
Java JDK 21
Maven
Docker & Docker Compose (opcional, para la base de datos)

🗄️ Configuración de la Base de Datos
```
application.properties
properties
Copiar código
spring.datasource.url=jdbc:mysql://localhost:3306/mytest?createDatabaseIfNotExist=true&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=888888

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```
🐳 Ejecución con Docker
El proyecto incluye un Dockerfile y un docker-compose.yaml para facilitar el despliegue.

Construir la imagen de la aplicación
```
bash
Copiar código
mvn clean package -DskipTests
docker-compose build
```
Levantar la infraestructura (App + MySQL)
```
bash
Copiar código
docker-compose up -d
```
🔌 Endpoints Disponibles
```
Proveedores (Providers)
Método	Endpoint	Descripción
POST	/providers	Crea un nuevo proveedor
GET	/providers	Lista todos los proveedores
PUT	/providers/{id}	Actualiza un proveedor existente
DELETE	/providers/{id}	Elimina un proveedor (si no tiene frutas)
```
```
Frutas (Fruits)
Método	Endpoint	Descripción
POST	/fruits	Crea una fruta asociada a un proveedor
GET	/fruits	Lista todas las frutas
GET	/fruits/by-provider?name=...	Busca frutas por nombre de proveedor
```
🧪 Pruebas Automatizadas
Se han implementado pruebas exhaustivas para asegurar la calidad del código:

Tests Unitarios (Mockito)
Validación de la lógica de negocio

Creación, eliminación y manejo de excepciones

Tests de Integración (MockMvc)
Simulación de peticiones HTTP

Validación de controladores y manejo de excepciones globales

Ejecutar los tests
bash
```
Copiar código
mvn test
```
📝 Notas de Implementación
DTOs
Se utilizan para no exponer directamente las entidades de base de datos, permitiendo una evolución independiente de la API y el modelo.

Validaciones
Uso de @NotBlank, @Positive y @NotNull para asegurar que los datos entrantes sean correctos antes de llegar a la capa de servicio.

Arquitectura
Separación clara de responsabilidades para facilitar el mantenimiento y la escalabilidad del proyecto.

## 🤝 Contribuciones
¡Contribuciones bienvenidas!

Haz un Fork del proyecto

Crea una rama para tu funcionalidad
```
bash
Copiar código
git checkout -b feature/nueva-funcionalidad
```
Haz commit de tus cambios
```
bash
Copiar código
git commit -m "Add: nueva funcionalidad"
```
Haz push a la rama
```
bash
Copiar código
git push origin feature/nueva-funcionalidad
```
Abre un Pull Request