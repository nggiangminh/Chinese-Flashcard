import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterModule } from '@angular/router';
import { AuthService } from '../../core/services/auth.service';
import { FlashcardService } from '../../core/services/flashcard.service';
import { NoteService } from '../../core/services/note.service';
import { User } from '../../core/models/user.model';
import { HeaderComponent } from '../../shared/components/header/header.component';
import { FooterComponent } from '../../shared/components/footer/footer.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, RouterModule, HeaderComponent, FooterComponent],
  templateUrl: './dashboard.component.html'
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  flashcardCount = 0;
  noteCount = 0;
  isLoading = true;

  constructor(
    private authService: AuthService,
    private flashcardService: FlashcardService,
    private noteService: NoteService
  ) {}

  ngOnInit(): void {
    this.authService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });

    this.loadStatistics();
  }

  loadStatistics(): void {
    this.flashcardService.getAll().subscribe({
      next: (flashcards) => {
        this.flashcardCount = flashcards.length;
        this.checkLoadingComplete();
      },
      error: () => {
        this.checkLoadingComplete();
      }
    });

    this.noteService.getAll().subscribe({
      next: (notes) => {
        this.noteCount = notes.length;
        this.checkLoadingComplete();
      },
      error: () => {
        this.checkLoadingComplete();
      }
    });
  }

  private checkLoadingComplete(): void {
    this.isLoading = false;
  }
}
