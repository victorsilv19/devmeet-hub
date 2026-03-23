import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { EventResponse, EventRequest } from '../../models/event.model';

@Component({
  selector: 'app-event-home',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './event-home.component.html',
  styleUrls: ['./event-home.component.css']
})
export class EventHomeComponent implements OnInit {

  events: EventResponse[] = [];
  
  newEvent: EventRequest = {
    title: '',
    description: '',
    eventDate: '',
    cep: ''
  };

  isLoading = false;
  errorMessage = '';

  constructor(private eventService: EventService) {}

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.getAllEvents().subscribe({
      next: (data) => this.events = data,
      error: (err) => console.error('Erro ao buscar eventos', err)
    });
  }

  onSubmit(): void {
    if(!this.newEvent.title || !this.newEvent.cep || !this.newEvent.eventDate) {
      this.errorMessage = "Preencha os campos obrigatórios!";
      return;
    }

    this.isLoading = true;
    this.errorMessage = '';

    this.eventService.createEvent(this.newEvent).subscribe({
      next: () => {
        this.loadEvents(); // Recarrega a lista
        this.resetForm();
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = err.error?.error || 'Erro ao criar evento. Verifique o CEP.';
        this.isLoading = false;
      }
    });
  }

  deleteEvent(id: number): void {
    this.eventService.deleteEvent(id).subscribe({
      next: () => this.loadEvents(),
      error: (err) => console.error('Erro ao deletar', err)
    });
  }

  resetForm(): void {
    this.newEvent = { title: '', description: '', eventDate: '', cep: '' };
  }
}
