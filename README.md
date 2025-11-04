# ☁️ MiniCloud — Cloud Storage cifrado y concurrente en Java

**MiniCloud** es un sistema de almacenamiento de archivos tipo “Google Drive local”, desarrollado con **Java 17 y Spring Boot 3**, que permite a varios usuarios **subir, descargar y eliminar archivos** de forma **segura y cifrada (AES)**.

El proyecto integra todos los conocimientos del ciclo de **Desarrollo de Aplicaciones Multiplataforma**, incluyendo concurrencia, seguridad, cifrado, persistencia y comunicación cliente-servidor.

---

## 🚀 Características principales

- 🔐 **Autenticación JWT** (usuarios y roles)
- 📁 **Subida, descarga y eliminación de archivos**
- 🧩 **Cifrado AES** automático antes de guardar los archivos
- 🧮 **Persistencia con MySQL y Hibernate (JPA)**
- 📤 **Notificación por correo electrónico** al subir archivos
- ⚙️ **Procesamiento concurrente de clientes**
- 🌐 **API REST completa** para conectar un frontend (React o Angular)

---

## 🧱 Tecnologías utilizadas

| Categoría | Tecnologías |
|------------|-------------|
| Backend | Java 21, Spring Boot 3 |
| Seguridad | Spring Security, JWT |
| Base de datos | MySQL, Hibernate / JPA |
| Cifrado | AES (javax.crypto) |
| Concurrencia | Threads / ExecutorService |
| Comunicación | REST API (HTTP) |
| Otros | Spring Mail, Lombok (opcional), Maven |

---

## 🧩 Estructura del proyecto
MiniCloud/
├── src/main/java/com/minicloud/
│   ├── MiniCloudApplication.java
│   ├── controller/        → Controladores REST
│   ├── service/           → Lógica de negocio
│   ├── repository/        → Interfaces JPA
│   ├── model/             → Entidades JPA (User, FileMeta)
│   └── security/          → Configuración JWT y cifrado
└── src/main/resources/
├── application.properties
└── static/

---

## ⚙️ Configuración inicial

### 1️⃣ Requisitos
- **Java 17+**
- **Maven 3.8+**
- **MySQL 8+**

### 2️⃣ Crear base de datos
```sql
CREATE DATABASE minicloud CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
CREATE USER 'minicloud_user'@'localhost' IDENTIFIED BY 'tu_password';
GRANT ALL PRIVILEGES ON minicloud.* TO 'minicloud_user'@'localhost';
FLUSH PRIVILEGES;

spring.datasource.url=jdbc:mysql://localhost:3306/minicloud?useSSL=false&serverTimezone=UTC
spring.datasource.username=minicloud_user
spring.datasource.password=tu_password

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

---

## ⚙️ Como ejecutar tu proyecto

# Clonar el repositorio
git clone https://github.com/tuusuario/minicloud.git
cd minicloud

# Compilar y ejecutar
mvn spring-boot:run

---

📧 Créditos

Desarrollado por [robertodfj / Roberto Frutos Jiménez]
Proyecto académico (Fase 3) — Desarrollo de Aplicaciones Multiplataforma
📅 Noviembre 2025

⸻

🌟 Licencia

Este proyecto se distribuye bajo la licencia MIT.
Eres libre de usarlo, modificarlo y adaptarlo con fines educativos o personales.