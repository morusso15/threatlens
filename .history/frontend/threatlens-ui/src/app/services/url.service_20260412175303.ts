import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface UrlAnalyzeResponse {
  originalUrl: string;
  normalizedUrl: string | null;
  hostname: string | null;
  usesHttps: boolean;
  isIpAddress: boolean;
  suspiciousKeywords: string[];
  riskScore: number;
  findings: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UrlService {
  private http = inject(HttpClient);
  private baseUrl = 'http://localhost:8080/api/url';

  analyze(url: string): Observable<UrlAnalyzeResponse> {
    return this.http.post<UrlAnalyzeResponse>(`${this.baseUrl}/analyze`, { url });
  }
}