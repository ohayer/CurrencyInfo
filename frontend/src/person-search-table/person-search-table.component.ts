import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { AfterViewInit } from '@angular/core';

// Angular Material Modules
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
import { MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';

export interface PersonSearchInfo {
  id: number;
  name: string;
  date: string;
  responseCode: number;
}

@Component({
  selector: 'app-person-search-table',
  templateUrl: './person-search-table.component.html',
  styleUrls: ['./person-search-table.component.css'],
  standalone: true,
  imports: [
    HttpClientModule,
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatSortModule,
    MatFormFieldModule,
    MatInputModule,
  ],
})
export class PersonSearchTableComponent implements OnInit, AfterViewInit {
  displayedColumns: string[] = ['id', 'name', 'date', 'responseCode'];
  dataSource = new MatTableDataSource<PersonSearchInfo>();
  errorMessage: string = '';

  @ViewChild(MatSort) sort!: MatSort;
  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private http: HttpClient) { }

  ngAfterViewInit() {
    this.dataSource.paginator = this.paginator;
  }
  ngOnInit(): void {
    this.dataSource.filterPredicate = (data: PersonSearchInfo, filter: string) => {
      return data.name.toLowerCase().includes(filter);
    };
    this.fetchData();
  }
  isLoading: boolean = true;
  fetchData() {
    this.isLoading = true;
    this.http.get<PersonSearchInfo[]>('http://localhost:8080/currencies/requests')
      .pipe(
        catchError(error => {
          this.errorMessage = 'Nie można załadować danych z serwera';
          return of([]);
        })
      )
      .subscribe(data => {
        this.isLoading = false;
        if (data && data.length > 0) {
          this.dataSource.data = data;
          this.dataSource.paginator = this.paginator;
          this.dataSource.sort = this.sort;
        } else if (!this.errorMessage) {
          this.errorMessage = 'Brak danych do wyświetlenia';
        }
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  }
}
