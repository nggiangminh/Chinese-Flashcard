import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { delay } from 'rxjs/operators';
import { Flashcard, CreateFlashcardDto, UpdateFlashcardDto } from '../models/flashcard.model';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FlashcardService {
  private apiUrl = `${environment.apiUrl}/flashcards`;
  
  // Mock data for testing
  private mockFlashcards: Flashcard[] = [
    {
      id: '1',
      userId: '1',
      frontText: '你好',
      backText: 'nǐ hǎo - Hello',
      createdAt: new Date(),
      updatedAt: new Date()
    },
    {
      id: '2',
      userId: '1',
      frontText: '谢谢',
      backText: 'xiè xiè - Thank you',
      createdAt: new Date(),
      updatedAt: new Date()
    },
    {
      id: '3',
      userId: '1',
      frontText: '再见',
      backText: 'zài jiàn - Goodbye',
      createdAt: new Date(),
      updatedAt: new Date()
    }
  ];

  constructor(private http: HttpClient) {}

  getAll(): Observable<Flashcard[]> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.get<Flashcard[]>(this.apiUrl, { withCredentials: true });
    
    // Mock response for testing
    return of(this.mockFlashcards).pipe(delay(300));
  }

  getById(id: string): Observable<Flashcard> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.get<Flashcard>(`${this.apiUrl}/${id}`, { withCredentials: true });
    
    // Mock response for testing
    const flashcard = this.mockFlashcards.find(f => f.id === id);
    return of(flashcard || this.mockFlashcards[0]).pipe(delay(300));
  }

  create(dto: CreateFlashcardDto): Observable<Flashcard> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.post<Flashcard>(this.apiUrl, dto, { withCredentials: true });
    
    // Mock response for testing
    const newFlashcard: Flashcard = {
      id: String(Date.now()),
      userId: '1',
      frontText: dto.frontText,
      backText: dto.backText,
      createdAt: new Date(),
      updatedAt: new Date()
    };
    this.mockFlashcards.push(newFlashcard);
    return of(newFlashcard).pipe(delay(300));
  }

  update(id: string, dto: UpdateFlashcardDto): Observable<Flashcard> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.put<Flashcard>(`${this.apiUrl}/${id}`, dto, { withCredentials: true });
    
    // Mock response for testing
    const index = this.mockFlashcards.findIndex(f => f.id === id);
    if (index !== -1) {
      this.mockFlashcards[index] = {
        ...this.mockFlashcards[index],
        frontText: dto.frontText || this.mockFlashcards[index].frontText,
        backText: dto.backText || this.mockFlashcards[index].backText,
        updatedAt: new Date()
      };
      return of(this.mockFlashcards[index]).pipe(delay(300));
    }
    return of(this.mockFlashcards[0]).pipe(delay(300));
  }

  delete(id: string): Observable<void> {
    // COMMENTED OUT FOR TESTING - Backend API connection
    // return this.http.delete<void>(`${this.apiUrl}/${id}`, { withCredentials: true });
    
    // Mock response for testing
    const index = this.mockFlashcards.findIndex(f => f.id === id);
    if (index !== -1) {
      this.mockFlashcards.splice(index, 1);
    }
    return of(void 0).pipe(delay(300));
  }
}
