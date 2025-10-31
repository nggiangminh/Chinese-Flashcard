export interface Flashcard {
  id: string;
  userId: string;
  frontText: string;
  backText: string;
  createdAt: Date;
  updatedAt: Date;
}

export interface CreateFlashcardDto {
  frontText: string;
  backText: string;
}

export interface UpdateFlashcardDto {
  frontText?: string;
  backText?: string;
}
