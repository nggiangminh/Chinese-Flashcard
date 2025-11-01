import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NoteService } from '../../../core/services/note.service';
import { Note } from '../../../core/models/note.model';
import { HeaderComponent } from '../../../shared/components/header/header.component';
import { FooterComponent } from '../../../shared/components/footer/footer.component';
import { NoteFormComponent } from '../note-form/note-form.component';

@Component({
  selector: 'app-note-list',
  standalone: true,
  imports: [
    CommonModule, 
    HeaderComponent, 
    FooterComponent,
    NoteFormComponent
  ],
  templateUrl: './note-list.component.html'
})
export class NoteListComponent implements OnInit {
  notes: Note[] = [];
  isLoading = true;
  showFormModal = false;
  editingNote: Note | null = null;

  constructor(private noteService: NoteService) {}

  ngOnInit(): void {
    this.loadNotes();
  }

  loadNotes(): void {
    this.isLoading = true;
    this.noteService.getAll().subscribe({
      next: (notes) => {
        this.notes = notes;
        this.isLoading = false;
      },
      error: () => {
        this.isLoading = false;
      }
    });
  }

  openCreateModal(): void {
    this.editingNote = null;
    this.showFormModal = true;
  }

  openEditModal(note: Note): void {
    this.editingNote = note;
    this.showFormModal = true;
  }

  closeModal(): void {
    this.showFormModal = false;
    this.editingNote = null;
  }

  onNoteSaved(): void {
    this.closeModal();
    this.loadNotes();
  }

  onNoteDelete(noteId: string): void {
    if (confirm('Are you sure you want to delete this note?')) {
      this.noteService.delete(noteId).subscribe({
        next: () => {
          this.loadNotes();
        }
      });
    }
  }
}
