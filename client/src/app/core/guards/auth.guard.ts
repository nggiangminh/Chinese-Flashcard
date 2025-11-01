import { inject } from '@angular/core';
import { Router, CanActivateFn } from '@angular/router';
import { AuthService } from '../services/auth.service';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // COMMENTED OUT FOR TESTING - Auto-authenticate
  // if (authService.isAuthenticated()) {
  //   return true;
  // }

  // return authService.loadCurrentUser().pipe(
  //   map(() => true),
  //   catchError(() => {
  //     router.navigate(['/login']);
  //     return of(false);
  //   })
  // );
  
  // Mock authentication for testing - always allow access
  if (!authService.isAuthenticated()) {
    authService.loadCurrentUser().subscribe();
  }
  return true;
};
