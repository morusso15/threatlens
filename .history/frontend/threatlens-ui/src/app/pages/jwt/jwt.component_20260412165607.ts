import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { JwtService, JwtAnalyzeResponse } from '../../services/jwt.service';

@Component({
  selector: 'app-jwt',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './jwt.component.html',
  styleUrl: './jwt.component.scss'
})
export class JwtComponent {
  private jwtService = inject(JwtService);
  private cdr = inject(ChangeDetectorRef);

  token = '';
  result: JwtAnalyzeResponse | null = null;
  error = '';
  loading = false;

  analyzeToken(): void {
    this.error = '';
    this.result = null;

    if (!this.token.trim()) {
      this.error = 'Please enter a JWT.';
      return;
    }

    this.loading = true;
    this.cdr.detectChanges();

    this.jwtService.analyzeToken(this.token).subscribe({
      next: (response) => {
        this.result = {
          validFormat: response.validFormat,
          header: response.header ?? null,
          payload: response.payload ?? null,
          algorithm: response.algorithm ?? null,
          findings: response.findings ?? []
        };

        this.loading = false;
        this.cdr.detectChanges();
      },
      error: (err) => {
        console.error(err);
        this.error = 'Failed to analyze token.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }

  fillSampleToken(): void {
    this.token =
      'eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VySWQiOjEyMywiZXhwIjo5OTk5OTk5OTk5fQ.c2lnbmF0dXJl';
  }

  clearToken(): void {
    this.token = '';
    this.result = null;
    this.error = '';
  }

  copyJson(value: unknown): void {
    navigator.clipboard.writeText(JSON.stringify(value, null, 2));
  }

  getPayloadCount(result: JwtAnalyzeResponse): number {
    return result.payload ? Object.keys(result.payload).length : 0;
  }
}