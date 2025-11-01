import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Note, CreateNoteDto, UpdateNoteDto } from '../models/note.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class NoteService {
  private apiUrl = `${environment.apiUrl}/notes`;
  
  // Mock data for testing
  private mockNotes: Note[] = [
    {
      id: '1',
      userId: '1',
      title: 'Chinese Tones',
      content: 'There are four main tones in Mandarin Chinese: 1st tone (flat), 2nd tone (rising), 3rd tone (dipping), 4th tone (falling).',
      createdAt: new Date(),
      updatedAt: new Date()
    },
    {
      id: '2',
      userId: '1',
      title: 'Common Greetings',
      content: 'Basic greetings to practice: 你好 (hello), 早上好 (good morning), 晚上好 (good evening)',
      createdAt: new Date(),
      updatedAt: new Date()
    }
  ];

  constructor(private http: HttpClient) {}

  getAll(): Observable<Note[]> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.get<Note[]>(this.apiUrl, { withCredentials: true });
    
    // Mock response for testing
    return of(this.mockNotes).pipe(delay(300));
  }

  create(dto: CreateNoteDto): Observable<Note> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.post<Note>(this.apiUrl, dto, { withCredentials: true });
    
    // Mock response for testing
    const newNote: Note = {
      id: String(Date.now()),
      userId: '1',
      title: dto.title,
      content: dto.content,
      createdAt: new Date(),
      updatedAt: new Date()
    };
    this.mockNotes.push(newNote);
    return of(newNote).pipe(delay(300));
  }

  update(id: string, dto: UpdateNoteDto): Observable<Note> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.put<Note>(`${this.apiUrl}/${id}`, dto, { withCredentials: true });
    
    // Mock response for testing
    const index = this.mockNotes.findIndex(n => n.id === id);
    if (index !== -1) {
      this.mockNotes[index] = {
        ...this.mockNotes[index],
        title: dto.title || this.mockNotes[index].title,
        content: dto.content || this.mockNotes[index].content,
        updatedAt: new Date()
      };
      return of(this.mockNotes[index]).pipe(delay(300));
    }
    return of(this.mockNotes[0]).pipe(delay(300));
  }

  delete(id: string): Observable<void> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
    
    // Mock response for testing
    const index = this.mockNotes.findIndex(n => n.id === id);
    if (index !== -1) {
      this.mockNotes.splice(index, 1);
    }
    return of(void 0).pipe(delay(300));
  }
}
