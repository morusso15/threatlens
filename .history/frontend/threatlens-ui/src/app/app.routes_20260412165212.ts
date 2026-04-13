import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JwtComponent } from './pages/jwt/jwt.component';

export const routes: Routes = [
    { path: '', redirectTo: 'jwt', pathMatch: 'full' },
    { path: 'jwt', component: JwtComponent },
    {
        
    }
];
