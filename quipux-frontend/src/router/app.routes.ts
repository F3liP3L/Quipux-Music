import { Routes } from '@angular/router';

export const routes: Routes = [
    {path: '', redirectTo: 'home', pathMatch: 'full'},
    {path: 'login', loadComponent: () => import('../components/login/login').then(m => m.Login)},
    {path: 'home', loadComponent: () => import('../components/home/home').then(m => m.Home)},
];