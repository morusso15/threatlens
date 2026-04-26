import { CommonModule } from '@angular/common';
import { ChangeDetectorRef, Component, NgZone, inject } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { RouterLink } from '@angular/router';

import {
  TranslatorService,
  FindingTranslateResponse,
  TranslatorAudience
} from '../../services/translator.service';

@Component({
  selector: 'app-finding-translator',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterLink],
  templateUrl: './finding-translator.component.html',
  styleUrl: './finding-translator.component.scss'
})
export class FindingTranslatorComponent {
  private translatorService = inject(TranslatorService);
  private cdr = inject(ChangeDetectorRef);
  private zone = inject(NgZone);

  finding = '';
  audience: TranslatorAudience = 'analyst';

  result: FindingTranslateResponse | null = null;
  loading = false;
  error = '';

  analyze(): void {
    this.error = '';
    this.result = null;

    if (!this.finding.trim()) {
      this.error = 'Paste a security finding first.';
      this.cdr.detectChanges();
      return;
    }

    this.loading = true;
    this.cdr.detectChanges();

    this.translatorService.analyze(this.finding, this.audience).subscribe({
      next: (response) => {
        this.zone.run(() => {
          this.result = response;
          this.loading = false;
          this.cdr.detectChanges();
        });
      },
      error: (err) => {
        console.error(err);
        this.zone.run(() => {
          this.error = 'Unable to translate finding. Make sure the backend is running.';
          this.loading = false;
          this.cdr.detectChanges();
        });
      }
    });
  }

  clear(): void {
    this.finding = '';
    this.result = null;
    this.error = '';
    this.loading = false;
    this.cdr.detectChanges();
  }
}