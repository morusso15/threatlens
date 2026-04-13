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

  <div class="results" *ngIf="result">
  <h2>Analysis Result</h2>

  <p><strong>Valid Format:</strong> {{ result?.validFormat }}</p>
  <p><strong>Algorithm:</strong> {{ result?.algorithm || 'N/A' }}</p>

  <div *ngIf="result?.header">
    <h3>Header</h3>
    <pre>{{ result?.header | json }}</pre>
  </div>

  <div *ngIf="result?.payload">
    <h3>Payload</h3>
    <pre>{{ result?.payload | json }}</pre>
  </div>

  <div *ngIf="result?.findings?.length">
    <h3>Findings</h3>
    <ul>
      <li *ngFor="let finding of result?.findings ?? []">{{ finding }}</li>
    </ul>
  </div>
</div>