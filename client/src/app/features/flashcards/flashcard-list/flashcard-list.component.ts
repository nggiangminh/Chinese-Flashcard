import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FlashcardService } from '../../../core/services/flashcard.service';
import { Flashcard } from '../../../core/models/flashcard.model';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { FlashcardCardComponent } from '../flashcard-card/flashcard-card.component';
import { FlashcardFormComponent } from '../flashcard-form/flashcard-form.component';

@Component({
  selector: 'app-flashcard-list',
  standalone: true,
  imports: [
    CommonModule, 
    HeaderComponent, 
    FooterComponent, 
    FlashcardCardComponent,
    FlashcardFormComponent
  ],
  templateUrl: './flashcard-list.component.html'
})
export class FlashcardListComponent implements OnInit {
  flashcards: Flashcard[] = [];
  isLoading = true;
  showFormModal = false;
  editingFlashcard: Flashcard | null = null;

  constructor(private flashcardService: FlashcardService) {}

  ngOnInit(): void {
    this.loadFlashcards();
  }

  loadFlashcards(): void {
    this.isLoading = true;
    this.flashcardService.getAll().subscribe({
      next: (flashcards) => {
        this.flashcards = flashcards;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  openCreateModal(): void {
    this.editingFlashcard = null;
    this.showFormModal = true;
  }

  openEditModal(flashcard: Flashcard): void {
    this.editingFlashcard = flashcard;
    this.showFormModal = true;
  }

  closeModal(): void {
    this.showFormModal = false;
    this.editingFlashcard = null;
  }

  onFlashcardSaved(): void {
    this.closeModal();
    this.loadFlashcards();
  }

  onFlashcardDeleted(): void {
    this.loadFlashcards();
  }
}
