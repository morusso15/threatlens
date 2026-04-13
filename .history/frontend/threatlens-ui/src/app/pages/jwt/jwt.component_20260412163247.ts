import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
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
        this.result = response;
        this.loading = false;
      },
      error: () => {
        this.error = 'Failed to analyze token.';
        this.loading = false;
      }
    });
  }
}