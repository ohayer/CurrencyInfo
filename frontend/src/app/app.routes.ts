import { Routes } from '@angular/router';
import { PersonSearchTableComponent } from '../person-search-table/person-search-table.component';
import { HomeComponent } from '../home/home.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'tableView', component: PersonSearchTableComponent },
    { path: '**', redirectTo: '' },
  ];
