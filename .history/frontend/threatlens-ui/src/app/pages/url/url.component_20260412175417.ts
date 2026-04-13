import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';
import { UrlService, UrlAnalyzeResponse } from '../../services/url.service';

@Component({
  selector: 'app-url',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './url.component.html',
  styleUrl: './url.component.scss'
})
export class UrlComponent {
  private urlService = inject(UrlService);
  private cdr = inject(ChangeDetectorRef);

  url = '';
  result: UrlAnalyzeResponse | null = null;
  loading = false;
  error = '';

  analyze(): void {
    this.error = '';
    this.result = null;

    if (!this.url.trim()) {
      this.error = 'Enter a URL.';
      return;
    }

    this.loading = true;
    this.cdr.detectChanges();

    this.urlService.analyze(this.url).subscribe({
      next: (res) => {
        this.result = res;
        this.loading = false;
        this.cdr.detectChanges();
      },
      error: () => {
        this.error = 'Failed to analyze URL.';
        this.loading = false;
        this.cdr.detectChanges();
      }
    });
  }
}