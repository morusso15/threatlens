import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JwtComponent } from './pages/jwt/jwt.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'jwt', component: JwtComponent },
    {
        path: 'url',
        loadComponent: () =>
            import('./pages/placeholder/placeholder.component').then(m => m.PlaceholderComponent)
    },
    {
        path: 'translator',
        loadComponent: () =>
            import('./pages/placeholder/placeholder.component').then(m => m.PlaceholderComponent)
    },
    { pat }
];
