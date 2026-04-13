import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JwtComponent } from './pages/jwt/jwt.component';
import { PlaceholderComponent } from './pages/placeholder/placeholder.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'jwt', component: JwtComponent },
    {
        path: 'url',
        loadComponent: () =>
            import(PlaceholderComponent).then(m => m.PlaceholderComponent)
    },
    {
        path: 'translator',
        loadComponent: () =>
            import(PlaceholderComponent).then(m => m.PlaceholderComponent)
    },
    { path: '**', redirectTo: '' }
];
