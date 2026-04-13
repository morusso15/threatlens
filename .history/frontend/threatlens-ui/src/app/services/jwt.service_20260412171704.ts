import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface JwtAnalyzeResponse {
  validFormat: boolean;
  header: Record<string, unknown> | null;
  payload: Record<string, unknown> | null;
  algorithm: string | null;
  signature: string | null;
  expired: boolean | null;
  expiresAt: number | null;
  issuedAt: number | null;
  issuer: string | null;
  audience: unknown;
  signatureVerified: boolean | null;
  findings: string[];
}

@Injectable({
  providedIn: 'root'
})
export class JwtService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/jwt';

  analyzeToken(token: string, secret?: string): Observable<JwtAnalyzeResponse> {
    return this.http.post<JwtAnalyzeResponse>(`${this.baseUrl}/analyze`, {
      token,
      secret: secret?.trim() || null
    });
  }
}