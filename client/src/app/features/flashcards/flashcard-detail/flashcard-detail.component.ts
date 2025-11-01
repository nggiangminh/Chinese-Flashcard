import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { FlashcardService } from '../../../core/services/flashcard.service';
import { Flashcard } from '../../../core/models/flashcard.model';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';

@Component({
  selector: 'app-flashcard-detail',
  standalone: true,
  imports: [CommonModule, HeaderComponent, FooterComponent],
  templateUrl: './flashcard-detail.component.html'
})
export class FlashcardDetailComponent implements OnInit {
  flashcard: Flashcard | null = null;
  isLoading = true;
  isFlipped = false;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private flashcardService: FlashcardService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.loadFlashcard(id);
    }
  }

  loadFlashcard(id: string): void {
    this.flashcardService.getById(id).subscribe({
      next: (flashcard) => {
        this.flashcard = flashcard;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
        this.router.navigate(['/flashcards']);
      }
    });
  }

  flip(): void {
    this.isFlipped = !this.isFlipped;
  }

  goBack(): void {
    this.router.navigate(['/flashcards']);
  }

  onDelete(): void {
    if (this.flashcard && confirm('Are you sure you want to delete this flashcard?')) {
      this.flashcardService.delete(this.flashcard.id).subscribe({
        next: () => {
          this.router.navigate(['/flashcards']);
        }
      });
    }
  }
}
