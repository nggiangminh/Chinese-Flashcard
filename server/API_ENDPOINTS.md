# API Endpoints Documentation

Base URL: `http://localhost:8080`

## ⚙️ Prerequisites

**Environment Variables Required:**
- `GOOGLE_CLIENT_ID`: Google OAuth2 Client ID
- `GOOGLE_CLIENT_SECRET`: Google OAuth2 Client Secret

**Setup:**
1. Copy `.env.example` to `.env`
2. Fill in your Google OAuth2 credentials
3. Start server: `./gradlew bootRun`

## 🔐 Authentication Endpoints

### OAuth2 Login
```
GET /oauth2/authorization/google
```
- **Mô tả**: Bắt đầu quá trình OAuth2 login với Google
- **Response**: Redirect đến Google OAuth2, sau đó redirect về `/api/auth/success` hoặc `/api/auth/failure`

### OAuth2 Login Success
```
GET /api/auth/success
```
- **Mô tả**: Endpoint được gọi sau khi OAuth2 login thành công
- **Response**: 
```json
{
  "result": "SUCCESS",
  "message": "Đăng nhập OAuth2 thành công",
  "data": {
    "id": "uuid",
    "name": "User Name", 
    "email": "user@email.com",
    "provider": "google",
    "createdAt": "2025-11-03T10:30:00",
    "updatedAt": "2025-11-03T10:30:00"
  }
}
```

### OAuth2 Login Failure
```
GET /api/auth/failure
```
- **Mô tả**: Endpoint được gọi khi OAuth2 login thất bại
- **Response**: 
```json
{
  "result": "ERROR",
  "message": "OAuth2 login thất bại",
  "data": null
}
```

### Test Session
```
GET /api/auth/test-session
```
- **Mô tả**: Kiểm tra session hiện tại có hợp lệ không
- **Response**: 
```json
{
  "result": "SUCCESS",
  "message": "Session còn hoạt động",
  "data": "AUTHENTICATED"
}
```

### Check Auth Status
```
GET /api/user/status
```
- **Mô tả**: Kiểm tra trạng thái đăng nhập
- **Response**: 
```json
{
  "result": "SUCCESS",
  "message": "Đã đăng nhập",
  "data": "AUTHENTICATED"
}
```

### Get Current User
```
GET /api/user/me
```
- **Mô tả**: Lấy thông tin user hiện tại (yêu cầu authentication)
- **Response**:
```json
{
  "result": "SUCCESS",
  "message": "Lấy thông tin user thành công",
  "data": {
    "id": "uuid",
    "name": "User Name",
    "email": "user@email.com",
    "provider": "google",
    "createdAt": "2025-11-03T10:30:00",
    "updatedAt": "2025-11-03T10:30:00"
  }
}
```

### Logout
```
POST /api/user/logout
```
- **Mô tả**: Đăng xuất khỏi hệ thống
- **Response**:
```json
{
  "result": "SUCCESS",
  "message": "Đăng xuất thành công",
  "data": "LOGGED_OUT"
}
```

---

## 👤 User Management Endpoints

### Create or Get User
```
POST /api/users/auth?name={name}&email={email}&provider={provider}
```
- **Mô tả**: Tạo user mới hoặc lấy user đã tồn tại
- **Parameters**:
  - `name`: Tên người dùng
  - `email`: Email người dùng  
  - `provider`: Nhà cung cấp OAuth (google)

### Get User by ID
```
GET /api/users/{id}
```
- **Mô tả**: Lấy thông tin user theo ID
- **Path Parameters**: `id` (UUID)

### Get User by Email
```
GET /api/users/email/{email}
```
- **Mô tả**: Lấy thông tin user theo email
- **Path Parameters**: `email` (String)

### Get All Users
```
GET /api/users
```
- **Mô tả**: Lấy danh sách tất cả users

### Check Email Exists
```
GET /api/users/exists?email={email}
```
- **Mô tả**: Kiểm tra email đã tồn tại chưa
- **Query Parameters**: `email` (String)

### Delete User
```
DELETE /api/users/{id}
```
- **Mô tả**: Xóa user theo ID
- **Path Parameters**: `id` (UUID)

---

## 📚 Flashcard Endpoints

### Create Flashcard
```
POST /api/flashcards?userId={userId}
Content-Type: application/json

{
  "chineseText": "你好",
  "pinyin": "nǐ hǎo", 
  "translation": "Hello",
  "difficulty": "BEGINNER"
}
```
- **Mô tả**: Tạo flashcard mới
- **Query Parameters**: `userId` (UUID)

### Get All Flashcards
```
GET /api/flashcards?userId={userId}
```
- **Mô tả**: Lấy tất cả flashcards của user
- **Query Parameters**: `userId` (UUID)

### Get Flashcard by ID
```
GET /api/flashcards/{id}?userId={userId}
```
- **Mô tả**: Lấy flashcard theo ID
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Update Flashcard
```
PUT /api/flashcards/{id}?userId={userId}
Content-Type: application/json

{
  "chineseText": "你好世界",
  "pinyin": "nǐ hǎo shì jiè",
  "translation": "Hello World", 
  "difficulty": "INTERMEDIATE"
}
```
- **Mô tả**: Cập nhật flashcard
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Delete Flashcard
```
DELETE /api/flashcards/{id}?userId={userId}
```
- **Mô tả**: Xóa flashcard
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Count Flashcards
```
GET /api/flashcards/count?userId={userId}
```
- **Mô tả**: Đếm số lượng flashcards của user
- **Query Parameters**: `userId` (UUID)

### Delete All Flashcards
```
DELETE /api/flashcards/all?userId={userId}
```
- **Mô tả**: Xóa tất cả flashcards của user
- **Query Parameters**: `userId` (UUID)

---

## 📝 Note Endpoints

### Create Note
```
POST /api/notes?userId={userId}
Content-Type: application/json

{
  "title": "Note Title",
  "content": "Note content here"
}
```
- **Mô tả**: Tạo note mới
- **Query Parameters**: `userId` (UUID)

### Get All Notes
```
GET /api/notes?userId={userId}
```
- **Mô tả**: Lấy tất cả notes của user
- **Query Parameters**: `userId` (UUID)

### Get Note by ID
```
GET /api/notes/{id}?userId={userId}
```
- **Mô tả**: Lấy note theo ID
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Update Note
```
PUT /api/notes/{id}?userId={userId}
Content-Type: application/json

{
  "title": "Updated Title",
  "content": "Updated content"
}
```
- **Mô tả**: Cập nhật note
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Delete Note
```
DELETE /api/notes/{id}?userId={userId}
```
- **Mô tả**: Xóa note
- **Path Parameters**: `id` (UUID)
- **Query Parameters**: `userId` (UUID)

### Search Notes
```
GET /api/notes/search?userId={userId}&title={title}
```
- **Mô tả**: Tìm kiếm notes theo title
- **Query Parameters**: 
  - `userId` (UUID)
  - `title` (String)

### Count Notes
```
GET /api/notes/count?userId={userId}
```
- **Mô tả**: Đếm số lượng notes của user
- **Query Parameters**: `userId` (UUID)

### Delete All Notes
```
DELETE /api/notes/all?userId={userId}
```
- **Mô tả**: Xóa tất cả notes của user
- **Query Parameters**: `userId` (UUID)

---

## 🌐 Public Endpoints

### Ping
```
GET /api/public/ping
```
- **Mô tả**: Kiểm tra server còn hoạt động
- **Response**:
```json
{
  "result": "SUCCESS",
  "message": "Server ping thành công",
  "data": {
    "message": "Pong! Server đang hoạt động",
    "timestamp": "2025-11-03T13:48:30.123",
    "status": "healthy"
  }
}
```

### Health Check
```
GET /api/public/health
```
- **Mô tả**: Kiểm tra sức khỏe hệ thống
- **Response**:
```json
{
  "result": "SUCCESS", 
  "message": "Health check thành công",
  "data": {
    "status": "UP",
    "application": "Chinese Flashcard API",
    "version": "1.0.0"
  }
}
```

---

## 📋 Response Format

Tất cả API đều trả về response theo format sau:

```json
{
  "result": "SUCCESS|ERROR",
  "message": "Thông báo mô tả",
  "data": "Dữ liệu trả về (có thể null nếu lỗi)"
}
```

### HTTP Status Codes
- `200 OK`: Thành công
- `201 Created`: Tạo thành công
- `400 Bad Request`: Lỗi request không hợp lệ
- `401 Unauthorized`: Chưa đăng nhập
- `404 Not Found`: Không tìm thấy resource
- `500 Internal Server Error`: Lỗi server

---

## 🔧 Testing với cURL/Postman

### Example: Login Flow (API Only - No Frontend)

1. **Bắt đầu OAuth2 login trong browser**:
   ```
   GET http://localhost:8080/oauth2/authorization/google
   ```
   - Điều này sẽ redirect đến Google login
   - Sau khi login thành công, sẽ redirect về `http://localhost:8080/api/auth/success`
   - Server sẽ trả về JSON response thay vì redirect frontend

2. **Kiểm tra auth status**:
   ```bash
   curl -X GET "http://localhost:8080/api/user/status" \
        -H "Cookie: JSESSIONID=your-session-id" \
        -c cookies.txt -b cookies.txt
   ```

3. **Lấy thông tin user hiện tại**:
   ```bash
   curl -X GET "http://localhost:8080/api/user/me" \
        -H "Cookie: JSESSIONID=your-session-id" \
        -c cookies.txt -b cookies.txt
   ```

### Important Notes for API Testing:
- Sau khi OAuth2 login, server sẽ tạo session và trả về JSESSIONID cookie
- Lưu cookie này để sử dụng cho các request tiếp theo
- Tất cả response đều là JSON format, không có HTML redirect

### Complete Testing Flow:

1. **Test server health**:
   ```bash
   curl -X GET "http://localhost:8080/api/public/health"
   ```

2. **Check initial auth status**:
   ```bash
   curl -X GET "http://localhost:8080/api/user/status" -c cookies.txt
   ```

3. **Login via browser**: 
   - Mở browser và truy cập: `http://localhost:8080/oauth2/authorization/google`
   - Login với Google account
   - Browser sẽ redirect về `/api/auth/success` và hiển thị JSON response

4. **Extract session from browser và test với cURL**:
   ```bash
   # Inspect browser cookies to get JSESSIONID value
   curl -X GET "http://localhost:8080/api/auth/test-session" \
        -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE"
   ```

5. **Get user info**:
   ```bash
   curl -X GET "http://localhost:8080/api/user/me" \
        -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE"
   ```

6. **Test protected endpoints (cần userId từ step 5)**:
   ```bash
   # Get all flashcards
   curl -X GET "http://localhost:8080/api/flashcards?userId=YOUR_USER_ID" \
        -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE"
   ```

7. **Logout**:
   ```bash
   curl -X POST "http://localhost:8080/api/user/logout" \
        -H "Cookie: JSESSIONID=YOUR_SESSION_ID_HERE"
   ```

### Example: Create Flashcard
```bash
curl -X POST "http://localhost:8080/api/flashcards?userId=your-user-id" \
     -H "Content-Type: application/json" \
     -H "Cookie: JSESSIONID=your-session-id" \
     -d '{
       "chineseText": "你好",
       "pinyin": "nǐ hǎo",
       "translation": "Hello",
       "difficulty": "BEGINNER"
     }'
```

### Example: Get All Flashcards
```bash
curl -X GET "http://localhost:8080/api/flashcards?userId=your-user-id" \
     -H "Cookie: JSESSIONID=your-session-id"
```
