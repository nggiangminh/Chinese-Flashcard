import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { NoteService } from '../../../core/services/note.service';
import { Note } from '../../../core/models/note.model';

@Component({
  selector: 'app-note-form',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './note-form.component.html'
})
export class NoteFormComponent implements OnInit {
  @Input() note: Note | null = null;
  @Output() saved = new EventEmitter<void>();
  @Output() cancelled = new EventEmitter<void>();

  noteForm!: FormGroup;
  isSubmitting = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private noteService: NoteService
  ) {}

  ngOnInit(): void {
    this.noteForm = this.fb.group({
      title: [this.note?.title || '', [Validators.required]],
      content: [this.note?.content || '', [Validators.required]]
    });
  }

  onSubmit(): void {
    if (this.noteForm.invalid) {
      return;
    }

    this.isSubmitting = true;
    this.errorMessage = '';

    const formValue = this.noteForm.value;

    if (this.note) {
      this.noteService.update(this.note.id, formValue).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.saved.emit();
        },
        error: (error) => {
          this.isSubmitting = false;
          this.errorMessage = 'Failed to update note';
        }
      });
    } else {
      this.noteService.create(formValue).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.saved.emit();
        },
        error: (error) => {
          this.isSubmitting = false;
          this.errorMessage = 'Failed to create note';
        }
      });
    }
  }

  onCancel(): void {
    this.cancelled.emit();
  }
}
