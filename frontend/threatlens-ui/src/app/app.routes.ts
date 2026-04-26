import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { JwtComponent } from './pages/jwt/jwt.component';
import { UrlComponent } from './pages/url/url.component';
import { FindingTranslatorComponent } from './pages/finding-translator/finding-translator.component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'jwt', component: JwtComponent },
  { path: 'url', component: UrlComponent },
  { path: 'translator', component: FindingTranslatorComponent },
  { path: '**', redirectTo: '' }
];