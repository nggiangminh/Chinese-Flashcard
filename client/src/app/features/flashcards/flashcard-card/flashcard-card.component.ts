import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Flashcard } from '../../../core/models/flashcard.model';
import { FlashcardService } from '../../../core/services/flashcard.service';

@Component({
  selector: 'app-flashcard-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './flashcard-card.component.html'
})
export class FlashcardCardComponent {
  @Input() flashcard!: Flashcard;
  @Output() edit = new EventEmitter<Flashcard>();
  @Output() delete = new EventEmitter<void>();

  isFlipped = false;
  isDeleting = false;

  constructor(private flashcardService: FlashcardService) {}

  flip(): void {
    this.isFlipped = !this.isFlipped;
  }

  onEdit(): void {
    this.edit.emit(this.flashcard);
  }

  onDelete(): void {
    if (confirm('Are you sure you want to delete this flashcard?')) {
      this.isDeleting = true;
      this.flashcardService.delete(this.flashcard.id).subscribe({
        next: () => {
          this.delete.emit();
        },
        error: () => {
          this.isDeleting = false;
        }
      });
    }
  }
}
