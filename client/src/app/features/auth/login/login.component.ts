import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
    selector: 'app-login',
    standalone: true,
    imports: [CommonModule],
    templateUrl: './login.component.html'
})
export class LoginComponent {
    constructor(private authService: AuthService, private router: Router) { }

    loginWithGoogle(): void {
        // this.authService.loginWithGoogle();
        this.router.navigate(['/dashboard']);
    }
}
