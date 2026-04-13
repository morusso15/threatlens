import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface JwtAnalyzeRequest {
  token: string;
}

export interface JwtAnalyzeResponse {
  validFormat: boolean;
  header: Record<string, unknown> | null;
  payload: Record<string, unknown> | null;
  algorithm: string | null;
  findings: string[];
}

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/jwt';

  analyzeToken(token: string): Observable<JwtAnalyzeResponse> {
    return this.http.post<JwtAnalyzeResponse>(`${this.baseUrl}/analyze`, { token });
  }
}