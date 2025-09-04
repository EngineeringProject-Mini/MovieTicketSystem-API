# MovieTicketSystem-API

This is a simple ATM backend application built using Spring Boot.

It provides basic REST APIs for ATM operations like deposit, withdrawal, and balance check.

---

## 🚀 Getting Started

### 1. Generate Project with Spring Initializr

1. Go to [Spring Initializr](https://start.spring.io/).
2. Fill in the details:
    - Project: Maven
    - Language: Java
    - Spring Boot Version: (latest stable, e.g., 3.x.x)
    - Group: movieticketbookinsystem
    - Artifact:
    - Name: movieticketbookinsystem
    - Packaging: Jar
    - Java: 17 (or your installed version)
3. Add dependencies:
    - Spring Web
    - Lombook
4. Click Generate, and extract the downloaded project.

---

### 2. Open in IntelliJ IDEA

1. Open IntelliJ IDEA.
2. Import the project by selecting the extracted folder.
3. Wait until Maven builds the project and dependencies are downloaded.

---

### 3. Run the Application

1. Locate the main class:
src/main/java/movieticketbookinsystem.java
2. Right-click and select Run 'Application'.
3. The application will start on [http://localhost:8080](http://localhost:8080/) by default.

---

### 🛠️ Tech Stack

---

- Java 17+
- Spring Boot
- Maven
- REST API

---

# 🎬 Movie Ticket Booking System – API Documentation

Base URL: `http://localhost:8080/api/movies`

---

## 🏙️ Add City

**POST** `/cities`
Adds a new city to the system.

**Parameters (Query):**

* `id` (string) – Unique city ID
* `name` (string) – City name

**Example:**

```
POST /api/movies/cities?id=C001&name=Hyderabad
```

**Response:**

```json
{
  "id": "C001",
  "name": "Hyderabad"
}
```
<img width="1366" height="768" alt="Screenshot (424)" src="https://github.com/user-attachments/assets/c2919b0b-2114-43a6-8da4-17061c571eeb" />

---

## 🎞️ Add Movie

**POST** `/movies`
Adds a movie to the system.

**Parameters (Query):**

* `id` (string) – Movie ID
* `title` (string) – Movie title
* `duration` (int) – Duration in minutes

**Example:**

```
POST /api/movies/movies?id=M001&title=Inception&duration=148
```

**Response:**

```
Movie added: Inception
```
<img width="1366" height="768" alt="Screenshot (425)" src="https://github.com/user-attachments/assets/d951e906-da4f-4162-b0a6-68b19efa5a5d" />

---

## 🍿 Add Cinema with Screens & Seats

**POST** `/cinemas`
Adds a cinema with one screen and automatically generates seats.

**Parameters (Query):**

* `id` (string) – Cinema ID
* `name` (string) – Cinema name
* `cityId` (string) – City ID
* `rows` (int) – Number of seat rows
* `cols` (int) – Number of seat columns

**Example:**

```
POST /api/movies/cinemas?id=CN001&name=PVR&cityId=C001&rows=5&cols=10
```

**Response:**

```
Cinema CN001 added successfully with 1 screen and 50 seats
```
<img width="1366" height="768" alt="Screenshot (426)" src="https://github.com/user-attachments/assets/3fd9ac2e-8c8a-4001-8a80-11372e23e638" />

---

## ⏰ Add Show

**POST** `/shows`
Adds a movie show for a given cinema screen.

**Parameters (Query):**

* `id` (string) – Show ID
* `movieId` (string) – Movie ID
* `screenId` (string) – Screen ID
* `cinemaId` (string) – Cinema ID
* `hoursFromNow` (int) – Scheduled time (e.g., 2 = 2 hours later)

**Example:**

```
POST /api/movies/shows?id=SH001&movieId=M001&screenId=S-CN001&cinemaId=CN001&hoursFromNow=2
```

**Response:**

```json
{
  "id": "SH001",
  "movie": { "id": "M001", "title": "Inception" },
  "screen": { "id": "S-CN001" },
  "showTime": "2025-08-28T18:00:00"
}
```
<img width="1366" height="768" alt="Screenshot (428)" src="https://github.com/user-attachments/assets/fba3967f-6cc7-44df-ba36-c3112596112a" />

---

## 👤 Add User

**POST** `/users`
Registers a new user.

**Parameters (Query):**

* `name` (string) – User name
* `email` (string) – User email

**Example:**

```
POST /api/movies/users?name=John&email=john@example.com
```

**Response:**

```json
{
  "id": "U001",
  "name": "John",
  "email": "john@example.com"
}
```
<img width="1366" height="768" alt="Screenshot (427)" src="https://github.com/user-attachments/assets/96c6d934-8dfc-4c65-940a-16733d287d0c" />

---

## 🔍 Search Shows

**GET** `/shows/search`
Find available shows in a city for a given movie.

**Parameters (Query):**

* `movieTitle` (string) – Movie title
* `cityName` (string) – City name

**Example:**

```
GET /api/movies/shows/search?movieTitle=Inception&cityName=Hyderabad
```

**Response:**

```json
[
  {
    "id": "SH001",
    "movie": { "title": "Inception" },
    "screen": { "id": "S-CN001" },
    "showTime": "2025-08-28T18:00:00"
  }
]
```

---

## 🎟️ Book Tickets

**POST** `/book`
Books seats for a user in a given show.

**Parameters (Query):**

* `userId` (string) – User ID
* `showId` (string) – Show ID
* `seatIds` (list) – Selected seat IDs (comma separated)
* `cardNumber` (string) – Credit card number
* `cvv` (string) – CVV

**Example:**

```
POST /api/movies/book?userId=U001&showId=SH001&seatIds=A1,A2,A3&cardNumber=1234567890123456&cvv=123
```

**Response (Success):**

```
✅ Booking Successful!
Booking ID: B001
Seats: [A1, A2, A3]
Total: $450.00
```

**Response (Failure):**

```
❌ Booking failed. Seats may be locked or unavailable.
```

---

## 🛑 Shutdown System

**POST** `/shutdown`
Shuts down the movie booking system.

**Example:**

```
POST /api/movies/shutdown
```

**Response:**

```
System shut down.
```

---


