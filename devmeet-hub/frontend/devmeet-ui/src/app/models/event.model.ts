export interface EventRequest {
  title: string;
  description: string;
  eventDate: string; // ISO formato DateTime string: '2026-12-31T20:00:00'
  cep: string;
}

export interface EventResponse {
  id: number;
  title: string;
  description: string;
  eventDate: string;
  fullAddress: string;
}
