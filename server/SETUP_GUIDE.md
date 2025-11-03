# 🔧 Hướng dẫn cấu hình Chinese Flashcard Server

## 📋 Yêu cầu hệ thống

- Java 17+
- PostgreSQL 12+
- Google Cloud Console Account (để tạo OAuth2 credentials)

## 🗄️ Cấu hình Database

### PostgreSQL Setup

1. Cài đặt PostgreSQL
2. Tạo database:
```sql
-- Development
CREATE DATABASE flashcard_dev;
CREATE USER flashcard_user WITH PASSWORD 'flashcard_password';
GRANT ALL PRIVILEGES ON DATABASE flashcard_dev TO flashcard_user;

-- Production (tùy chọn)
CREATE DATABASE flashcard_prod;
```

## 🔐 Cấu hình Google OAuth2

### 1. Tạo Google Cloud Project

1. Truy cập [Google Cloud Console](https://console.cloud.google.com/)
2. Tạo project mới hoặc chọn project hiện có
3. Kích hoạt Google+ API

### 2. Tạo OAuth2 Credentials

1. Vào **APIs & Services** > **Credentials**
2. Click **Create Credentials** > **OAuth 2.0 Client IDs**
3. Chọn **Web application**
4. Cấu hình:
   - **Name**: Chinese Flashcard App
   - **Authorized JavaScript origins**: 
     - `http://localhost:8080`
     - `http://localhost:3000` (nếu có frontend)
   - **Authorized redirect URIs**:
     - `http://localhost:8080/login/oauth2/code/google`

### 3. Lưu Credentials

Sao chép **Client ID** và **Client Secret** để cấu hình trong application.yml

## ⚙️ Environment Variables

Tạo file `.env` hoặc cấu hình environment variables:

```bash
# Database
DB_USERNAME=flashcard_user
DB_PASSWORD=flashcard_password
DB_HOST=localhost
DB_PORT=5432
DB_NAME=flashcard_dev

# Google OAuth2
GOOGLE_CLIENT_ID=your-google-client-id-here
GOOGLE_CLIENT_SECRET=your-google-client-secret-here

# Server
PORT=8080
```

## 🚀 Chạy ứng dụng

### Development Mode

```bash
# Với Gradle Wrapper
./gradlew bootRun --args='--spring.profiles.active=dev'

# Hoặc với IDE
# Set VM options: -Dspring.profiles.active=dev
```

### Production Mode

```bash
./gradlew bootRun --args='--spring.profiles.active=prod'
```

## 🧪 Test API

### Health Check

```bash
curl http://localhost:8080/api/public/ping
```

Response:
```json
{
  "result": "SUCCESS",
  "message": "Server ping thành công",
  "data": {
    "message": "Pong! Server đang hoạt động",
    "timestamp": "2025-11-03T10:30:00",
    "status": "healthy"
  }
}
```

### OAuth2 Login Flow

1. Truy cập: `http://localhost:8080/oauth2/authorization/google`
2. Đăng nhập với Google account
3. Sau khi thành công, redirect về frontend
4. Test lấy thông tin user: `GET /api/user/me`

## 🔍 API Endpoints

| Method | Endpoint | Mô tả | Auth Required |
|--------|----------|-------|---------------|
| `GET` | `/api/public/ping` | Health check | ❌ |
| `GET` | `/api/public/health` | Health status | ❌ |
| `GET` | `/api/user/me` | Current user info | ✅ |
| `POST` | `/api/user/logout` | Logout user | ✅ |
| `GET` | `/api/user/status` | Auth status | ❌ |

## 🚨 Troubleshooting

### Database Connection Issues

1. Kiểm tra PostgreSQL đang chạy:
```bash
pg_ctl status
```

2. Kiểm tra connection string trong application.yml

3. Kiểm tra user permissions:
```sql
GRANT ALL PRIVILEGES ON DATABASE flashcard_dev TO flashcard_user;
```

### OAuth2 Issues

1. Kiểm tra redirect URI trong Google Console
2. Đảm bảo `GOOGLE_CLIENT_ID` và `GOOGLE_CLIENT_SECRET` đúng
3. Kiểm tra CORS configuration nếu có frontend riêng

### Build Issues

```bash
# Clean và rebuild
./gradlew clean build

# Xem dependency conflicts
./gradlew dependencies
```

## 📝 Logs

Logs sẽ xuất hiện trong console. Để debug OAuth2:

```yaml
logging:
  level:
    org.springframework.security.oauth2: TRACE
```

## 🔐 Security Notes

- Trong production, đặt `server.servlet.session.cookie.secure=true`
- Sử dụng HTTPS trong production
- Không commit sensitive data vào Git
- Sử dụng environment variables hoặc secret management

## 📚 Next Steps

1. Implement Flashcard CRUD operations
2. Implement Note CRUD operations  
3. Add Swagger documentation
4. Add validation và error handling
5. Add unit tests
