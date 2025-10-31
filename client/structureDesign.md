# 🏗️ Flashcard Frontend — Cấu trúc & Architecture (Angular + Bootstrap)

## 🎯 Tổng quan

- **Framework**: Angular 17+ (Standalone Components)
- **UI Framework**: Bootstrap 5.3+
- **Style**: Neo-Brutalism Design
- **Auth**: Google OAuth2
- **State Management**: RxJS BehaviorSubject
- **HTTP Client**: Angular HttpClient với Interceptors

---

## 📁 Cấu trúc thư mục

```
flashcard-app/
├── src/
│   ├── app/
│   │   ├── core/                     # Singleton services, guards, interceptors
│   │   │   ├── guards/
│   │   │   │   └── auth.guard.ts
│   │   │   ├── interceptors/
│   │   │   │   └── auth.interceptor.ts
│   │   │   ├── services/
│   │   │   │   ├── auth.service.ts
│   │   │   │   ├── flashcard.service.ts
│   │   │   │   └── note.service.ts
│   │   │   └── models/
│   │   │       ├── user.model.ts
│   │   │       ├── flashcard.model.ts
│   │   │       └── note.model.ts
│   │   │
│   │   ├── shared/                   # Shared components
│   │   │   ├── components/
│   │   │   │   ├── header/
│   │   │   │   └── footer/
│   │   │   └── shared.module.ts
│   │   │
│   │   ├── features/                 # Feature modules
│   │   │   ├── auth/
│   │   │   │   ├── login/
│   │   │   │   └── callback/
│   │   │   ├── dashboard/
│   │   │   ├── flashcards/
│   │   │   │   ├── flashcard-list/
│   │   │   │   ├── flashcard-card/
│   │   │   │   ├── flashcard-form/
│   │   │   │   └── flashcard-detail/
│   │   │   └── notes/
│   │   │       ├── note-list/
│   │   │       └── note-form/
│   │   │
│   │   ├── app.component.ts
│   │   ├── app.routes.ts
│   │   └── app.config.ts
│   │
│   ├── styles/
│   │   ├── _variables.scss
│   │   ├── _brutalism.scss
│   │   └── styles.scss
│   │
│   └── environments/
│       ├── environment.ts
│       └── environment.prod.ts
```

---

## 🧩 Core Layer

### Models (DTOs)

**User Model**
- `id`, `name`, `email`, `provider`, `createdAt`

**Flashcard Model**
- `id`, `userId`, `frontText`, `backText`, `createdAt`, `updatedAt`
- DTOs: `CreateFlashcardDto`, `UpdateFlashcardDto`

**Note Model**
- `id`, `userId`, `title`, `content`, `createdAt`
- DTOs: `CreateNoteDto`, `UpdateNoteDto`

### Services

**AuthService**
- `currentUser$: Observable<User>` - BehaviorSubject để track user state
- `loginWithGoogle()` - Redirect đến OAuth2
- `logout()` - Đăng xuất
- `isAuthenticated()` - Kiểm tra authentication
- `loadCurrentUser()` - Load user từ session

**FlashcardService**
- `getAll()` - Lấy danh sách flashcards
- `getById(id)` - Lấy chi tiết 1 flashcard
- `create(dto)` - Tạo mới
- `update(id, dto)` - Cập nhật
- `delete(id)` - Xóa

**NoteService**
- `getAll()` - Lấy danh sách notes
- `create(dto)` - Tạo mới
- `update(id, dto)` - Cập nhật
- `delete(id)` - Xóa

### Guards & Interceptors

**AuthGuard**
- Bảo vệ routes yêu cầu authentication
- Redirect về `/login` nếu chưa đăng nhập

**AuthInterceptor**
- Tự động thêm `withCredentials: true` cho mọi HTTP request
- Đảm bảo cookies được gửi kèm

---

## 🎨 Shared Components

### Header Component
- Navigation bar với logo
- Menu: Flashcards, Notes
- User badge hiển thị tên
- Logout button
- Responsive: collapse menu trên mobile

### Footer Component (Optional)
- Copyright info
- Links

---

## 📱 Feature Modules

### 1. Auth Module

**Login Component**
- Welcome screen với branding
- Google login button
- Animation cho emojis

**Callback Component**
- Xử lý OAuth2 callback
- Redirect về dashboard sau khi login thành công

### 2. Dashboard Module

**Dashboard Component**
- Welcome message với tên user
- Statistics cards:
  - Tổng số flashcards
  - Tổng số notes
- Quick action cards:
  - Browse Flashcards
  - Create Flashcard
  - My Notes
- Study tips section

### 3. Flashcards Module

**Flashcard List Component**
- Header với title và create button
- Loading state với spinner
- Empty state khi chưa có flashcards
- Grid layout hiển thị flashcards
- Modal để create/edit flashcard

**Flashcard Card Component**
- Flip card animation (front/back)
- Front: Chinese text
- Back: Translation/Pinyin
- Action buttons: View Detail, Delete

**Flashcard Form Component**
- Reactive form với validation
- 2 textarea: Front Text, Back Text
- Submit và Cancel buttons
- Loading state khi đang save
- Error handling

**Flashcard Detail Component** (Optional)
- Xem chi tiết flashcard
- Edit và Delete actions
- Navigation back

### 4. Notes Module

**Note List Component**
- Header với title và create button
- Loading state
- Empty state
- Card grid hiển thị notes
- Modal để create/edit note

**Note Form Component**
- Reactive form với validation
- Input: Title
- Textarea: Content
- Submit và Cancel buttons
- Loading state và error handling

---

## 🛣️ Routing

```
/ → redirect to /dashboard
/login → Login page (public)
/dashboard → Dashboard (protected)
/flashcards → Flashcard list (protected)
/flashcards/:id → Flashcard detail (protected)
/notes → Note list (protected)
** → redirect to /dashboard
```

**Route Guards**
- Dashboard, Flashcards, Notes: Require authentication

**Lazy Loading**
- Mỗi feature module được lazy load để tối ưu performance

---

## ⚙️ Configuration

### App Config
- Router setup
- HTTP Client với interceptors
- Bootstrap và NgBootstrap providers

### Environment
- `apiUrl`: Backend API URL
- `production`: Flag cho production build

---

## 📦 Dependencies

```json
{
  "dependencies": {
    "@angular/core": "^17.0.0",
    "@angular/common": "^17.0.0",
    "@angular/forms": "^17.0.0",
    "@angular/router": "^17.0.0",
    "@ng-bootstrap/ng-bootstrap": "^16.0.0",
    "bootstrap": "^5.3.3",
    "rxjs": "~7.8.0"
  }
}
```

---

## 🔄 Data Flow

1. **Authentication Flow**
   - User click "Login with Google"
   - Redirect đến backend OAuth2 endpoint
   - Backend xử lý OAuth2, tạo session
   - Redirect về frontend callback
   - Frontend load user info, lưu vào BehaviorSubject
   - Navigate đến dashboard

2. **CRUD Flow**
   - Component gọi Service
   - Service gọi HTTP Client
   - Interceptor tự động thêm credentials
   - Backend xử lý và trả về response
   - Service emit data qua Observable
   - Component subscribe và update UI

3. **State Management**
   - User state: BehaviorSubject trong AuthService
   - Component data: Component-level state với RxJS
   - Form state: Reactive Forms

---

## 🎯 Key Features

✅ **Authentication**: Google OAuth2 với session-based auth
✅ **CRUD Operations**: Đầy đủ Create, Read, Update, Delete
✅ **Responsive Design**: Mobile-first với Bootstrap Grid
✅ **Form Validation**: Reactive Forms với validators
✅ **Loading States**: Spinner khi đang load data
✅ **Empty States**: Thông báo khi chưa có data
✅ **Error Handling**: Hiển thị lỗi khi API fail
✅ **Modal Dialogs**: Create/Edit forms trong modal
✅ **Animations**: Card flip, hover effects
✅ **Type Safety**: TypeScript strict mode
✅ **Lazy Loading**: Optimize performance

---

## 🚀 Development Workflow

1. **Setup**: `npm install`
2. **Development**: `npm start` (http://localhost:4200)
3. **Build**: `npm run build`
4. **Production**: Serve từ thư mục `dist/`

---

## 📝 Coding Standards

- **Components**: Single responsibility principle
- **Services**: Injectable với `providedIn: 'root'`
- **Models**: Interface cho type safety
- **Naming**: PascalCase cho classes, camelCase cho methods
- **File structure**: Feature-based organization
- **Error handling**: Try-catch và RxJS error operators
- **Observables**: Unsubscribe để tránh memory leaks