import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { EventRequest, EventResponse } from '../models/event.model';

@Injectable({
  providedIn: 'root'
})
export class EventService {
  
  // Endpoint local do Backend Java Spring Boot
  private apiUrl = 'http://localhost:8080/api/events';

  constructor(private http: HttpClient) { }

  getAllEvents(): Observable<EventResponse[]> {
    return this.http.get<EventResponse[]>(this.apiUrl);
  }

  createEvent(event: EventRequest): Observable<EventResponse> {
    return this.http.post<EventResponse>(this.apiUrl, event);
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
