import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ThemeService {
  private readonly THEME_KEY = 'chinese-flashcard-theme';
  private isDarkModeSubject = new BehaviorSubject<boolean>(this.getStoredTheme());
  
  isDarkMode$: Observable<boolean> = this.isDarkModeSubject.asObservable();

  constructor() {
    // Apply initial theme
    this.applyTheme(this.isDarkModeSubject.value);
  }

  toggleTheme(): void {
    const newTheme = !this.isDarkModeSubject.value;
    this.isDarkModeSubject.next(newTheme);
    this.applyTheme(newTheme);
    this.saveTheme(newTheme);
  }

  private applyTheme(isDark: boolean): void {
    if (isDark) {
      document.body.classList.add('dark-mode');
    } else {
      document.body.classList.remove('dark-mode');
    }
  }

  private getStoredTheme(): boolean {
    const stored = localStorage.getItem(this.THEME_KEY);
    return stored === 'dark';
  }

  private saveTheme(isDark: boolean): void {
    localStorage.setItem(this.THEME_KEY, isDark ? 'dark' : 'light');
  }
}
