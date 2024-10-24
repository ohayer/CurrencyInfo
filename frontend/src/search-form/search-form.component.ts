import { Component } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { CurrencyService } from '../services/currency.service';
import { CommonModule } from '@angular/common';

export interface Rate {
  currency: string;
  code: string;
  mid: number;
}

@Component({
  selector: 'app-search-form',
  standalone: true,
  templateUrl: './search-form.component.html',
  styleUrl: './search-form.component.css',
  imports: [
    ReactiveFormsModule,
    CommonModule
  ]
})
export class SearchFormComponent {
  currencyForm: FormGroup;
  value: number | null = null;
  errorMessage: string = '';
  currencies: Rate[] = [];
  isLoading: boolean = false;

  constructor(private fb: FormBuilder, private currencyService: CurrencyService) {
    this.currencyForm = this.fb.group({
      name: ['', [Validators.required, Validators.pattern('^[A-ZŁŚŻŹĆĄĘÓŃ][a-złśżźćąęóń]+\\s[A-ZŁŚŻŹĆĄĘÓŃ][a-złśżźćąęóń]+$')]],
      currency: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.currencyService.getCurrencies().subscribe(
      (response) => {
        this.currencies = response;
      },
      (error) => {
        this.errorMessage = 'Nie udało się pobrać danych z serwera.';
      }
    );
  }


  onSubmit(): void {
    if (this.currencyForm.valid) {
      this.isLoading = true;
      const name = this.currencyForm.get('name')?.value;
      const currency = this.currencyForm.get('currency')?.value;
  
      this.currencyService.getWealth(name, currency).subscribe(
        (response) => {
          this.value = response.value;
          this.errorMessage = '';
          this.isLoading = false;
        },
        (error) => {
          this.errorMessage = 'Nie udało się pobrać danych z serwera.';
          this.value = null;
          this.isLoading = false;
        }
      );
    }
  }

  get name() {
    return this.currencyForm.get('name');
  }

  get currency() {
    return this.currencyForm.get('currency');
  }
}
