# Synergy — Smart Street Light Management System

A JavaFX desktop application that allows city administrators to report, track, and manage street light maintenance issues through a city-scoped dashboard.

## Features

- **Login & Sign Up** — Admins authenticate with a username/password. New accounts require a city-specific special key (product key).
- **Home** — Overview of the system and its purpose.
- **Maintenance Tab** — View and manage street light data from a MySQL database, filtered by the admin's assigned city. Supports:
  - Selecting tables from a dropdown
  - Refreshing data
  - Performing status updates on streets
  - Adding new streets and street lights
- **Profile Tab** — View city, name, username, and email. Edit profile details or log out.
- **Contact Us** — Support contact information.

## Tech Stack

| Layer | Technology |
|---|---|
| UI | JavaFX 22 (controls, FXML, web, swing) |
| Database | MySQL (via JDBC) |
| Build | Maven |
| Java | Java 22 |

**Additional libraries:** ControlsFX, FormsFX, Ikonli, BootstrapFX, TilesFX

## Prerequisites

- Java 22+
- Maven
- MySQL server running locally on port `3306`

## Database Setup

1. Create a database named `synergy`.
2. Create an `admin` table with the following columns:
   ```sql
   CREATE TABLE admin (
     city VARCHAR(50),
     special_code VARCHAR(20),
     name VARCHAR(100),
     userName VARCHAR(100),
     password VARCHAR(100),
     email_id VARCHAR(100)
   );
   ```
3. Seed cities using `csvfiles/admin.csv` (includes entries for Bangalore, Chennai, Delhi, Kolkata, Mumbai).
4. Update `src/main/java/jdbc/DatabaseConnection.java` with your MySQL credentials:
   ```java
   private static final String URL = "jdbc:mysql://localhost:3306/synergy";
   private static final String USER = "your_username";
   private static final String PASSWORD = "your_password";
   ```

## Running the App

```bash
./mvnw clean javafx:run
```

On Windows:
```cmd
mvnw.cmd clean javafx:run
```

## Project Structure

```
Synergy-main/
├── src/main/java/
│   ├── Login/
│   │   └── LoginPage.java       # Login & sign-up UI (entry point)
│   ├── HomePage/
│   │   └── HomePage.java        # Main dashboard with tabs
│   ├── jdbc/
│   │   ├── AdminService.java    # Auth logic (login, signup, key validation)
│   │   ├── DatabaseConnection.java  # MySQL connection config
│   │   └── update_tables.java   # DB update operations (streets, lights)
│   └── module-info.java
├── csvfiles/
│   └── admin.csv                # Seed data for cities and special codes
└── pom.xml
```

## Sign-Up Flow

New admins register using a **special key** tied to their city (found in the `admin` table). On first sign-up, a city-specific table is created in the database to store street light records.
