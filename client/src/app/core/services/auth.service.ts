import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, of } from 'rxjs';
import { tap, delay } from 'rxjs/operators';
import { User } from '../models/user.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private currentUserSubject = new BehaviorSubject<User | null>(null);
  public currentUser$: Observable<User | null> = this.currentUserSubject.asObservable();

  constructor(private http: HttpClient) {}

  loginWithGoogle(): void {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // window.location.href = `${environment.apiUrl}/auth/google`;
    
    // Mock login for testing
    const mockUser: User = {
      id: '1',
      name: 'Test User',
      email: 'test@example.com',
      provider: 'google',
      createdAt: new Date()
    };
    this.currentUserSubject.next(mockUser);
  }

  logout(): Observable<any> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.post(`${environment.apiUrl}/auth/logout`, {}, { withCredentials: true })
    //   .pipe(
    //     tap(() => this.currentUserSubject.next(null))
    //   );
    
    // Mock logout for testing
    this.currentUserSubject.next(null);
    return of(null).pipe(delay(300));
  }

  loadCurrentUser(): Observable<User> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.get<User>(`${environment.apiUrl}/auth/me`, { withCredentials: true })
    //   .pipe(
    //     tap(user => this.currentUserSubject.next(user))
    //   );
    
    // Mock user for testing
    const mockUser: User = {
      id: '1',
      name: 'Test User',
      email: 'test@example.com',
      provider: 'google',
      createdAt: new Date()
    };
    this.currentUserSubject.next(mockUser);
    return of(mockUser).pipe(delay(300));
  }

  isAuthenticated(): boolean {
    return this.currentUserSubject.value !== null;
  }

  getCurrentUser(): User | null {
    return this.currentUserSubject.value;
  }
}
