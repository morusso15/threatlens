import { Routes } from '@angular/router';
import { JwtComponent } from './pages/jwt/jwt.component';

export const routes: Routes = [
    { path: '', redirectTo: 'jwt', pathMatch: 'full' },
    { path: 'jwt', component: JwtComponent }
];
