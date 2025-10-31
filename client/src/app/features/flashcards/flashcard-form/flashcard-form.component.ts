import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { FlashcardService } from '../../../core/services/flashcard.service';
import { Flashcard } from '../../../core/models/flashcard.model';

@Component({
  selector: 'app-flashcard-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './flashcard-form.component.html'
})
export class FlashcardFormComponent implements OnInit {
  @Input() flashcard: Flashcard | null = null;
  @Output() saved = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  flashcardForm!: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private flashcardService: FlashcardService
  ) {}

  ngOnInit(): void {
    this.flashcardForm = this.fb.group({
      frontText: [this.flashcard?.frontText || '', [Validators.required]],
      backText: [this.flashcard?.backText || '', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.flashcardForm.invalid) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    const formValue = this.flashcardForm.value;

    if (this.flashcard) {
      this.flashcardService.update(this.flashcard.id, formValue).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.saved.emit();
        },
        error: (error) => {
          this.isSubmitting = false;
          this.errorMessage = 'Failed to update flashcard';
        }
      });
    } else {
      this.flashcardService.create(formValue).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.saved.emit();
        },
        error: (error) => {
          this.isSubmitting = false;
          this.errorMessage = 'Failed to create flashcard';
        }
      });
    }
  }

  onCancel(): void {
    this.cancelled.emit();
  }
}
