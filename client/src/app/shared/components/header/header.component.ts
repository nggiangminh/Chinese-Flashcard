import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { ThemeService } from '../../../core/services/theme.service';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './header.component.html'
})
export class HeaderComponent implements OnInit {
  currentUser: User | null = null;
  isDarkMode = false;

  constructor(
    private authService: AuthService,
    private themeService: ThemeService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });

    this.themeService.isDarkMode$.subscribe(isDark => {
      this.isDarkMode = isDark;
    });
  }

  toggleTheme(): void {
    this.themeService.toggleTheme();
  }

  logout(): void {
    this.authService.logout().subscribe(() => {
      window.location.href = '/login';
    });
  }
}
