import { Routes } from '@angular/router';
import { JwtComponent } from './pages/jwt/jwt.component';

export const routes: Routes = [
    { path: '', redirectTo: 'jwt' }
    { path: 'jwt', component: JwtComponent }
];
