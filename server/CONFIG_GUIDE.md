# Chinese Flashcard Server - Configuration Guide

## 📁 Cấu hình YAML

Project sử dụng **YAML** thay vì properties để cấu hình dễ đọc hơn.

### Các file cấu hình:

1. **application.yml** - Cấu hình mặc định (default profile)
2. **application-dev.yml** - Cấu hình development
3. **application-prod.yml** - Cấu hình production

---

## 🚀 Cách chạy ứng dụng

### Development mode:
```bash
.\gradlew.bat bootRun --args='--spring.profiles.active=dev'
```

Hoặc:
```bash
java -jar build/libs/server-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

### Production mode:
```bash
.\gradlew.bat bootRun --args='--spring.profiles.active=prod'
```

Hoặc:
```bash
java -jar build/libs/server-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### Default mode (không chỉ định profile):
```bash
.\gradlew.bat bootRun
```

---

## 🗄️ Database Configuration

### PostgreSQL Setup

1. **Cài đặt PostgreSQL** (nếu chưa có)

2. **Tạo database**:
```sql
CREATE DATABASE chinese_flashcard;
CREATE DATABASE chinese_flashcard_dev;
CREATE DATABASE chinese_flashcard_prod;
```

3. **Cập nhật thông tin kết nối** trong `application.yml`:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/chinese_flashcard
    username: your_username
    password: your_password
```

---

## 📝 Chi tiết cấu hình

### application.yml (Default)
- **Database**: chinese_flashcard
- **Port**: 8080
- **DDL Auto**: update (tự động update schema)
- **Show SQL**: true
- **Log level**: DEBUG

### application-dev.yml (Development)
- **Database**: chinese_flashcard_dev
- **DDL Auto**: create-drop (tạo mới mỗi lần restart)
- **DevTools**: enabled
- **LiveReload**: enabled
- **Show SQL**: true

### application-prod.yml (Production)
- **Database**: chinese_flashcard_prod (từ environment variables)
- **DDL Auto**: validate (không thay đổi schema)
- **Show SQL**: false
- **Connection Pool**: HikariCP optimized
- **Logging**: file-based

---

## 🔐 Environment Variables (Production)

Trong production, sử dụng environment variables:

```bash
export DATABASE_URL=jdbc:postgresql://your-prod-server:5432/chinese_flashcard_prod
export DATABASE_USERNAME=your_prod_username
export DATABASE_PASSWORD=your_prod_password
export PORT=8080
```

Windows:
```cmd
set DATABASE_URL=jdbc:postgresql://your-prod-server:5432/chinese_flashcard_prod
set DATABASE_USERNAME=your_prod_username
set DATABASE_PASSWORD=your_prod_password
set PORT=8080
```

---

## 📊 API Endpoints

### User APIs (`/api/users`)
- `POST /api/users/auth` - Login/Register
- `GET /api/users/{id}` - Get user by ID
- `GET /api/users/email/{email}` - Get user by email
- `GET /api/users` - Get all users
- `GET /api/users/exists?email={email}` - Check email exists
- `DELETE /api/users/{id}` - Delete user

### Flashcard APIs (`/api/flashcards`)
- `POST /api/flashcards?userId={userId}` - Create flashcard
- `GET /api/flashcards?userId={userId}` - Get all flashcards
- `GET /api/flashcards/{id}?userId={userId}` - Get flashcard by ID
- `PUT /api/flashcards/{id}?userId={userId}` - Update flashcard
- `DELETE /api/flashcards/{id}?userId={userId}` - Delete flashcard
- `GET /api/flashcards/count?userId={userId}` - Count flashcards
- `DELETE /api/flashcards/all?userId={userId}` - Delete all flashcards

### Note APIs (`/api/notes`)
- `POST /api/notes?userId={userId}` - Create note
- `GET /api/notes?userId={userId}` - Get all notes
- `GET /api/notes/{id}?userId={userId}` - Get note by ID
- `PUT /api/notes/{id}?userId={userId}` - Update note
- `DELETE /api/notes/{id}?userId={userId}` - Delete note
- `GET /api/notes/search?userId={userId}&title={title}` - Search notes
- `GET /api/notes/count?userId={userId}` - Count notes
- `DELETE /api/notes/all?userId={userId}` - Delete all notes

---

## 🧪 Testing

Test API với Postman hoặc curl:

```bash
# Health check
curl http://localhost:8080/actuator/health

# Create user
curl -X POST "http://localhost:8080/api/users/auth?name=Test&email=test@example.com&provider=google"

# Get all users
curl http://localhost:8080/api/users
```

---

## 📦 Build & Deploy

### Build JAR file:
```bash
.\gradlew.bat clean build
```

### Run JAR:
```bash
java -jar build/libs/server-0.0.1-SNAPSHOT.jar
```

### Build với profile cụ thể:
```bash
.\gradlew.bat clean build -Pprofile=prod
```

---

## 🛠️ Tech Stack

- **Framework**: Spring Boot 3.5.7
- **Java**: JDK 17
- **Database**: PostgreSQL
- **ORM**: Spring Data JPA / Hibernate
- **Build Tool**: Gradle
- **Dependencies**: Lombok, Spring Web, Spring Data JPA, PostgreSQL Driver

---

## 📞 Support

Nếu gặp vấn đề, kiểm tra:
1. PostgreSQL đã chạy chưa
2. Database đã được tạo chưa
3. Thông tin kết nối trong YAML đúng chưa
4. Port 8080 có bị chiếm dụng không

---

**Author**: Chinese Flashcard Team  
**Version**: 0.0.1-SNAPSHOT

