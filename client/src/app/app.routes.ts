import { Routes } from '@angular/router';
import { authGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  {
    path: '',
    redirectTo: '/dashboard',
    pathMatch: 'full'
  },
  {
    path: 'login',
    loadComponent: () => import('./features/auth/login/login.component').then(m => m.LoginComponent)
  },
  {
    path: 'auth/callback',
    loadComponent: () => import('./features/auth/callback/callback.component').then(m => m.CallbackComponent)
  },
  {
    path: 'dashboard',
    loadComponent: () => import('./features/dashboard/dashboard.component').then(m => m.DashboardComponent),
    canActivate: [authGuard]
  },
  {
    path: 'flashcards',
    loadComponent: () => import('./features/flashcards/flashcard-list/flashcard-list.component').then(m => m.FlashcardListComponent),
    canActivate: [authGuard]
  },
  {
    path: 'flashcards/:id',
    loadComponent: () => import('./features/flashcards/flashcard-detail/flashcard-detail.component').then(m => m.FlashcardDetailComponent),
    canActivate: [authGuard]
  },
  {
    path: 'notes',
    loadComponent: () => import('./features/notes/note-list/note-list.component').then(m => m.NoteListComponent),
    canActivate: [authGuard]
  },
  {
    path: '**',
    redirectTo: '/dashboard'
  }
];
