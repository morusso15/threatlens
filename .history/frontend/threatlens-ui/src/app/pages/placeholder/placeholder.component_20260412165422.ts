import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-placeholder',
  standalone: true,
  imports: [CommonModule, RouterLink],
  template: `
    <div class="placeholder-page">
      <div class="card">
        <p class="eyebrow">ThreatLens</p>
        <h1>Tool in progress</h1>
        <p>
          This section is planned next. The JWT Inspector is the first completed tool,
          and the URL Analyzer is the next feature in development.
        </p>
        <a routerLink="/" class="back-link">← Back to dashboard</a>
      </div>
    </div>
  `,
  styles: [`
    .placeholder-page {
      min-height: 100vh;
      display: grid;
      place-items: center;
      background: linear-gradient(180deg, #0b1020 0%, #0f172a 100%);
      padding: 2rem;
      color: #e5e7eb;
    }

    .card {
      max-width: 640px;
      padding: 2rem;
      border-radius: 24px;
      background: rgba(15, 23, 42, 0.9);
      border: 1px solid rgba(148, 163, 184, 0.14);
    }

    .eyebrow {
      color: #93c5fd;
      text-transform: uppercase;
      font-size: 0.8rem;
      letter-spacing: 0.05em;
    }

    h1 {
      margin-top: 0.25rem;
      color: #f8fafc;
    }

    p {
      color: #94a3b8;
      line-height: 1.6;
    }

    .back-link {
      color: #60a5fa;
      text-decoration: none;
      font-weight: 600;
    }
  `]
})
export class PlaceholderComponent {}