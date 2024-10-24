import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { RestUrl } from '../api/RestApi';
import { Rate } from '../search-form/search-form.component';

interface WealthResponse {
  value: number;
}

@Injectable({
  providedIn: 'root'
})
export class CurrencyService {
  private apiUrl = RestUrl.postWealthCalculator;

  constructor(private http: HttpClient) {}

  getWealth(name: string, currency: string): Observable<WealthResponse> {
    const body = { name, currency };
    return this.http.post<WealthResponse>(this.apiUrl, body);
  }

  getCurrencies(): Observable<Rate[]> {
    return this.http.get<Rate[]>(RestUrl.getCurriencies);
  }
}
