# 🎨 Flashcard Frontend — Neo-Brutalism Style Guide

## 🎯 Design Philosophy

Neo-Brutalism là phong cách thiết kế đặc trưng bởi:
- **Bold borders** (viền đậm, dày)
- **Hard shadows** (đổ bóng cứng, không blur)
- **High contrast** (màu sắc tương phản mạnh)
- **Geometric shapes** (hình khối rõ ràng)
- **No gradients** (không dùng gradient)
- **Flat colors** (màu phẳng, solid)
- **Strong typography** (chữ đậm, dễ đọc)

---

## 🎨 Color Palette

### Primary Colors
```scss
$primary: #FF6B35;      // Bright Orange - CTA buttons, links
$secondary: #4ECDC4;    // Turquoise - Secondary actions
$success: #95E1D3;      // Mint Green - Success states
$warning: #FFE66D;      // Yellow - Warnings, highlights
$danger: #FF3B30;       // Red - Delete, errors
$info: #A8E6CF;         // Light Green - Info messages
```

### Neutral Colors
```scss
$white: #FFFFFF;        // Background, cards
$black: #000000;        // Text, borders, shadows
$gray-100: #F7F7F7;     // Page background
$gray-900: #333333;     // Secondary text
```

### Usage
- **Backgrounds**: White cho cards, Gray-100 cho page
- **Text**: Black cho heading, Gray-900 cho body
- **Borders**: Black 4px cho mọi elements
- **Shadows**: Black solid shadow (không blur)

---

## 🔤 Typography

### Font Families
```scss
$font-family-base: 'Space Grotesk', sans-serif;     // Body text
$font-family-monospace: 'Space Mono', monospace;    // Code, numbers
```

### Font Sizes
```scss
$h1: 3rem (48px)        // Page titles
$h2: 2.5rem (40px)      // Section titles
$h3: 2rem (32px)        // Card titles
$h4: 1.5rem (24px)      // Subsection titles
$h5: 1.25rem (20px)     // Small headings
Body: 1rem (16px)       // Default text
Small: 0.875rem (14px)  // Captions, meta info
```

### Font Weights
```scss
Normal: 400       // Body text
Bold: 700         // Buttons, labels, emphasis
Black: 900        // Headings, strong emphasis
```

### Typography Rules
- **Headings**: Font weight 900, uppercase cho buttons/badges
- **Body text**: Font weight 400, line-height 1.6
- **Letter spacing**: 0.5px cho uppercase text
- **Text alignment**: Left-align mặc định, center cho cards/modals

---

## 📐 Spacing System

### Bootstrap Spacing Scale
```scss
$spacer: 1rem (16px)

0: 0
1: 0.25rem (4px)
2: 0.5rem (8px)
3: 1rem (16px)
4: 1.5rem (24px)
5: 3rem (48px)
```

### Usage Guidelines
- **Component padding**: p-3 hoặc p-4 (16-24px)
- **Section spacing**: mb-4 hoặc mb-5 (24-48px)
- **Grid gaps**: g-3 hoặc g-4 (16-24px)
- **Button padding**: px-3 py-2 (12px 8px)

---

## 🔲 Borders & Shadows

### Border Style
```scss
$border-width: 4px;
$border-color: $black;
$border-radius: 0;      // No rounded corners
```

### Shadow Variations
```scss
// Small shadow - Buttons
box-shadow: 4px 4px 0 black;

// Medium shadow - Cards
box-shadow: 6px 6px 0 black;

// Large shadow - Modals, important cards
box-shadow: 8px 8px 0 black;
```

### Border & Shadow Rules
- **Mọi components** đều có border 4px màu đen
- **Không blur** trong shadow (offset-x offset-y 0 color)
- **Shadow direction**: Right-bottom (4px 4px)
- **Hover effect**: Shadow nhỏ đi, element di chuyển

---

## 🎭 Component Styles

### Buttons

**Base Style**
- Border: 4px solid black
- Shadow: 4px 4px 0 black
- Padding: 8px 24px (small), 12px 32px (medium), 16px 48px (large)
- Font: Bold (700), uppercase
- Transition: transform 150ms, shadow 150ms

**States**
- **Hover**: Transform translate(2px, 2px), shadow 2px 2px
- **Active**: Transform translate(4px, 4px), shadow none
- **Disabled**: Opacity 0.5, no hover effect

**Variants**
- **Primary**: Orange background, white text
- **Secondary**: Turquoise background, white text
- **Danger**: Red background, white text
- **Outline**: White background, colored border

### Cards

**Base Style**
- Border: 4px solid black
- Shadow: 6px 6px 0 black
- Background: White
- Padding: 24px
- Transition: transform 250ms, shadow 250ms

**Hover Effect** (optional)
- Transform: translate(-2px, -2px)
- Shadow: 8px 8px 0 black

**Variants**
- **Default**: White background
- **Colored**: Yellow/Turquoise/Mint background cho highlights

### Forms

**Input & Textarea**
- Border: 4px solid black
- Padding: 12px 16px
- Font size: 16px
- Focus: Border color = primary, box-shadow ring

**Labels**
- Font weight: 700 (bold)
- Margin bottom: 8px
- Font size: 14px

**Validation**
- Error state: Red border, red text below
- Success state: Green border

### Modals

**Backdrop**
- Background: rgba(0,0,0,0.5)
- Blur: none

**Modal Content**
- Border: 4px solid black
- Shadow: 8px 8px 0 black
- Background: White
- Max width: 500px (small), 800px (large)

**Modal Header**
- Border bottom: 4px solid black
- Padding: 24px
- Font weight: 900

### Badges

**Style**
- Border: 2px solid black
- Padding: 4px 12px
- Font: Bold (700), uppercase, 12px
- Letter spacing: 0.5px

### Alerts

**Style**
- Border: 4px solid black
- Shadow: 4px 4px 0 black
- Padding: 16px
- Font weight: 600

**Variants**
- Success: Mint green background
- Warning: Yellow background
- Danger: Red background
- Info: Light green background

---

## 🎬 Animations

### Button Interaction
```scss
// Hover
transform: translate(2px, 2px);
box-shadow: 2px 2px 0 black;

// Active/Click
transform: translate(4px, 4px);
box-shadow: none;
```

### Card Hover
```scss
transform: translate(-2px, -2px);
box-shadow: 8px 8px 0 black;
```

### Flashcard Flip
```scss
// 3D flip animation
transform: rotateY(180deg);
transition: transform 0.6s;
```

### Loading Bounce
```scss
@keyframes bounce-subtle {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}
animation: bounce-subtle 2s infinite;
```

### Transition Timing
```scss
Fast: 150ms      // Buttons, small interactions
Base: 250ms      // Cards, modals
Slow: 600ms      // Flip animations
```

---

## 📱 Responsive Design

### Breakpoints (Bootstrap Default)
```scss
xs: <576px       // Mobile portrait
sm: ≥576px       // Mobile landscape
md: ≥768px       // Tablet
lg: ≥992px       // Desktop
xl: ≥1200px      // Large desktop
xxl: ≥1400px     // Extra large
```

### Layout Guidelines

**Mobile (xs, sm)**
- Single column layout
- Full width cards
- Stacked navigation
- Larger touch targets (min 44px)

**Tablet (md)**
- 2-column grid for cards
- Collapsible navigation
- Moderate spacing

**Desktop (lg, xl, xxl)**
- 3-4 column grid for cards
- Horizontal navigation
- Generous spacing
- Max container width: 1200px

---

## 🎨 Page-Specific Styles

### Login Page
- Background: Turquoise ($secondary)
- Card: Centered, max-width 500px, large shadow
- Animation: Bouncing emojis

### Dashboard
- Background: Light gray ($gray-100)
- Welcome card: Yellow background, large padding
- Stats cards: White background, icons + numbers
- Quick action cards: Hoverable, cursor pointer

### Flashcard List
- Background: Light gray
- Grid: 3 columns (desktop), 2 (tablet), 1 (mobile)
- Cards: Flip animation, white front, yellow back

### Note List
- Background: Light gray
- Grid: 3 columns (desktop), 2 (tablet), 1 (mobile)
- Cards: Text truncate (3 lines), white background

---

## 🎯 Utility Classes

### Custom Classes
```scss
// Shadows
.shadow-brutal-sm { box-shadow: 4px 4px 0 black; }
.shadow-brutal { box-shadow: 6px 6px 0 black; }
.shadow-brutal-lg { box-shadow: 8px 8px 0 black; }

// Borders
.border-brutal { border: 4px solid black !important; }

// Hover effects
.card-hover {
  transition: transform 0.25s, box-shadow 0.25s;
  cursor: pointer;
}

// Text
.text-bold { font-weight: 700; }
.text-black { font-weight: 900; }

// Animation
.animate-bounce { animation: bounce-subtle 2s infinite; }
```

---

## 📋 Component Checklist

Mỗi component cần đảm bảo:

✅ **Border**: 4px solid black
✅ **Shadow**: Phù hợp với kích thước (4px/6px/8px)
✅ **No border-radius**: Góc vuông 90°
✅ **Typography**: Bold headings, readable body
✅ **Colors**: Chỉ dùng màu trong palette
✅ **Spacing**: Tuân theo spacing scale
✅ **Hover states**: Transform + shadow change
✅ **Responsive**: Mobile-friendly
✅ **Accessibility**: Contrast ratio ≥4.5:1
✅ **Performance**: Smooth animations (60fps)

---

## 🎨 SCSS File Structure

### _variables.scss
- Override Bootstrap variables
- Custom color palette
- Typography settings
- Spacing scale
- Border & shadow values

### _brutalism.scss
- Custom utility classes
- Component overrides
- Animation keyframes
- Hover effects
- Shadow utilities

### styles.scss
- Import variables
- Import Bootstrap
- Import brutalism
- Global styles
- Font imports (Google Fonts)

---

## 🚀 Implementation Tips

1. **Consistency**: Dùng các class Bootstrap kết hợp custom classes
2. **Override**: Override Bootstrap variables trong _variables.scss
3. **Reusability**: Tạo utility classes cho effects thường dùng
4. **Performance**: Limit animations, optimize shadows
5. **Accessibility**: Đảm bảo contrast, focus states rõ ràng
6. **Testing**: Test trên nhiều devices và browsers

---

## 📚 References

- Google Fonts: Space Grotesk, Space Mono
- Bootstrap 5.3 Documentation
- Neo-Brutalism Design Trend
- WCAG 2.1 Accessibility Guidelines