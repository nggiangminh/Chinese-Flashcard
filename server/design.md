# 🧠 Flashcard Web App — Thiết kế tối giản (Next.js + Spring Boot + PostgreSQL)

## 🎯 Mục tiêu

Xây dựng ứng dụng web học tiếng Trung với 3 tính năng cốt lõi:
1. **Flashcard** — tạo, xem, sửa, xoá thẻ từ vựng.
2. **Note** — ghi chú học tập nhanh.
3. **Google OAuth2 Login** — quản lý người dùng an toàn.

---

## ⚙️ Kiến trúc tổng thể

| Thành phần | Công nghệ | Mô tả |
|-------------|------------|-------|
| **Frontend** | Next.js 15 | Giao diện người dùng, gọi API backend |
| **Backend** | Spring Boot 3.x | Cung cấp REST API, xác thực OAuth2 |
| **Database** | PostgreSQL | Lưu người dùng, flashcard, note |
| **Auth** | Google OAuth2 | Đăng nhập/đăng xuất |
| **Docs** | Swagger / OpenAPI 3 | Tài liệu API backend |

---

## 🧩 Cấu trúc thư mục Backend

```
com.example.flashcard
├── config/              # Security, CORS, Swagger
├── controller/          # REST API controllers
├── entity/              # Entities: User, Flashcard, Note
├── repository/          # JPA repositories
├── service/             # Business logic
└── dto/                 # DTO
```

---

## 🧱 Database Schema

### 🧑 User

| Trường | Kiểu | Ghi chú |
|--------|------|---------|
| id | UUID | Khóa chính |
| name | VARCHAR | Tên hiển thị |
| email | VARCHAR | Email đăng nhập |
| provider | VARCHAR | "google" |
| created_at | TIMESTAMP | Thời gian tạo |

---

### 🪧 Flashcard

| Trường | Kiểu | Ghi chú |
|--------|------|---------|
| id | UUID | Khóa chính |
| user_id | UUID | Liên kết đến User |
| front_text | TEXT | Mặt trước (từ tiếng Trung) |
| back_text | TEXT | Mặt sau (nghĩa, pinyin...) |
| created_at | TIMESTAMP | Thời gian tạo |
| updated_at | TIMESTAMP | Thời gian cập nhật |

---

### 📝 Note

| Trường | Kiểu | Ghi chú |
|--------|------|---------|
| id | UUID | Khóa chính |
| user_id | UUID | Liên kết đến User |
| title | VARCHAR | Tiêu đề ghi chú |
| content | TEXT | Nội dung ghi chú |
| created_at | TIMESTAMP | Thời gian tạo |

---

## 🔐 Xác thực — Google OAuth2

- Đăng ký ứng dụng tại [Google Cloud Console](https://console.cloud.google.com/).
- Cấu hình trong `application.yml`:

```yaml
spring:
  security:
    oauth2:
      client:
        registration:
          google:
            client-id: YOUR_GOOGLE_CLIENT_ID
            client-secret: YOUR_GOOGLE_CLIENT_SECRET
            redirect-uri: "{baseUrl}/login/oauth2/code/{registrationId}"
            scope: email, profile
```

> Khi user đăng nhập lần đầu, backend lưu thông tin `User` vào DB.

---

## 🌐 API Endpoints

### 👤 Auth API

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| `GET` | `/api/user/me` | Lấy thông tin người dùng hiện tại | ✅ |
| `POST` | `/logout` | Đăng xuất người dùng | ✅ |

---

### 🪧 Flashcard API

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| `GET` | `/api/flashcards` | Lấy danh sách flashcard của user | ✅ |
| `GET` | `/api/flashcards/{id}` | Lấy chi tiết 1 flashcard | ✅ |
| `POST` | `/api/flashcards` | Tạo flashcard mới | ✅ |
| `PUT` | `/api/flashcards/{id}` | Cập nhật flashcard | ✅ |
| `DELETE` | `/api/flashcards/{id}` | Xóa flashcard | ✅ |

---

### 📝 Note API

| Method | Endpoint | Mô tả | Auth |
|--------|----------|-------|------|
| `GET` | `/api/notes` | Lấy danh sách ghi chú của user | ✅ |
| `POST` | `/api/notes` | Tạo ghi chú mới | ✅ |
| `PUT` | `/api/notes/{id}` | Cập nhật ghi chú | ✅ |
| `DELETE` | `/api/notes/{id}` | Xóa ghi chú | ✅ |

---

### 🌍 Public API

| Method | Endpoint | Mô tả |
|--------|----------|-------|
| `GET` | `/api/public/ping` | Kiểm tra kết nối backend |
| `GET` | `/swagger-ui/index.html` | Trang Swagger |

---

## 📗 Swagger

* Swagger UI: `http://localhost:8080/swagger-ui/index.html`
* API Docs: `http://localhost:8080/v3/api-docs`

---

## 🚀 Tóm tắt điểm chính

| Thành phần | Có trong dự án | Ghi chú |
|------------|----------------|---------|
| OAuth2 Google | ✅ | Dùng Spring Security |
| Flashcard | ✅ | CRUD đơn giản |
| Note | ✅ | CRUD đơn giản |
| Swagger | ✅ | Dễ test API |
| Docker Compose | ✅ | PostgreSQL + Backend |

---