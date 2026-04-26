import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

export type TranslatorAudience = 'executive' | 'developer' | 'analyst';

export interface FindingTranslateResponse {
  audience: string;
  title: string;
  summary: string;
  riskLevel: string;
  impact: string;
  recommendedAction: string;
  explanation: string;
  keyTerms: string[];
}

@Injectable({
  providedIn: 'root'
})
export class TranslatorService {
  private http = inject(HttpClient);
  private baseUrl = `${environment.apiUrl}/api/translator`;

  analyze(finding: string, audience: TranslatorAudience): Observable<FindingTranslateResponse> {
    return this.http.post<FindingTranslateResponse>(`${this.baseUrl}/analyze`, {
      finding,
      audience
    });
  }
}