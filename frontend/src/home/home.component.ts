import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent {
  constructor(private router: Router) { }

  searchByPerson() {
    console.log('Wyszukaj osobę');
  }

  showSearchList() {
    console.log('Wyświetl listę wyszukań');
    this.router.navigate(['/tableView']);
  }
}
