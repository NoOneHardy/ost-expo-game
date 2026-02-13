export interface Race {
  id: string;
  startTime: Date;
  status: 'pending' | 'active' | 'completed';
  participants: string[];
  createdAt: Date;
}

export interface CreateRaceRequest {
  participants?: string[];
}

export interface RaceResponse {
  id: string;
  startTime: string;
  status: string;
  participants: string[];
  createdAt: string;
}
