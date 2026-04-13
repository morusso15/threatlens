import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JwtComponent } from './pages/jwt/jwt.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'jwt', component: JwtComponent },
  {
    path: 'url', com},
  {
    path: 'translator',
    loadComponent: () =>
      import('./pages/placeholder/placeholder').then(m => m.Placeholder)
  },
  { path: '**', redirectTo: '' }
];