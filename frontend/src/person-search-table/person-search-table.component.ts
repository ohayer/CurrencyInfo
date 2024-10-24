import { Component, OnInit, ViewChild } from '@angular/core';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { RestUrl } from '../api/RestApi';

// Angular Material Modules
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatTableModule } from '@angular/material/table';
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
    CommonModule,
    MatPaginatorModule,
    MatTableModule,
    MatFormFieldModule,
    MatInputModule,
  ],
})
export class PersonSearchTableComponent implements OnInit {
  displayedColumns: string[] = ['id', 'name', 'date', 'responseCode'];
  dataSource!: MatTableDataSource<PersonSearchInfo>;
  errorMessage: string = '';
  isLoading: boolean = true;

  @ViewChild(MatPaginator) paginator!: MatPaginator;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.fetchData();
  }

  fetchData() {
    this.isLoading = true;
    this.http.get<PersonSearchInfo[]>(RestUrl.getPersonSearchInfo)
      .pipe(
        catchError(error => {
          this.errorMessage = 'Nie można załadować danych z serwera';
          return of([]);
        })
      )
      .subscribe(data => {
        this.isLoading = false;
        if (data && data.length > 0) {
          // Create a new MatTableDataSource with the data
          this.dataSource = new MatTableDataSource<PersonSearchInfo>(data);

          // Assign the paginator after the dataSource is created
          this.dataSource.paginator = this.paginator;

          // Set up filtering
          this.dataSource.filterPredicate = (data: PersonSearchInfo, filter: string) => {
            return data.name.toLowerCase().includes(filter);
          };
        } else if (!this.errorMessage) {
          this.errorMessage = 'Brak danych do wyświetlenia';
        }
      });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    if (this.dataSource) {
      this.dataSource.filter = filterValue;
    }
  }
}
