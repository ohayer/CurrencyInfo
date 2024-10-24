import { Routes } from '@angular/router';
import { PersonSearchTableComponent } from '../person-search-table/person-search-table.component';
import { HomeComponent } from '../home/home.component';
import { SearchFormComponent } from '../search-form/search-form.component';

export const routes: Routes = [
    { path: '', component: HomeComponent },
    { path: 'tableView', component: PersonSearchTableComponent },
    { path: 'searchForm', component: SearchFormComponent },
    { path: '**', redirectTo: '' },
  ];
