import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';

@Component({
  selector: 'app-callback',
  standalone: true,
  imports: [],
  template: `
    <div class="container vh-100 d-flex align-items-center justify-content-center">
      <div class="text-center">
        <div class="spinner-border" role="status">
          <span class="visually-hidden">Loading...</span>
        </div>
        <p class="mt-3">Processing login...</p>
      </div>
    </div>
  `
})
export class CallbackComponent implements OnInit {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.authService.loadCurrentUser().subscribe({
      next: () => {
        this.router.navigate(['/dashboard']);
      },
      error: () => {
        this.router.navigate(['/login']);
      }
    });
  }
}
