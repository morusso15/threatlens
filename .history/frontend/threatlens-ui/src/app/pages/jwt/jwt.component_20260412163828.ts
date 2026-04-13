import { CommonModule } from '@angular/common';
import { Component, ChangeDetectorRef, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { JwtService, JwtAnalyzeResponse } from '../../services/jwt.service';

@Component({
  selector: 'app-jwt',
  standalone: true,
  imports: [CommonModule, FormsModule],
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

    this.jwtService.analyzeToken(this.token).subscribe({
      next: (response) => {
        console.log('JWT response:', response);
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
        console.error('JWT error:', err);
        this.error = 'Failed to analyze token.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}