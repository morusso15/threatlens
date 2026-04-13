import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  tools = [
    {
      title: 'JWT Inspector',
      description: 'Decode and analyze JSON Web Tokens, inspect claims, and surface security findings.',
      route: '/jwt',
      status: 'Available'
    },
    {
      title: 'URL Phishing Analyzer',
      description: 'Check suspicious URLs for phishing indicators, risky patterns, and deceptive structure.',
      route: '/url',
      status: 'Available'
    },
    {
      title: 'Finding Translator',
      description: 'Rewrite security findings for executives, technicians, and other audiences.',
      route: '/translator',
      status: 'Planned'
    }
  ];
}