import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonSearchTableComponent } from './person-search-table.component';

describe('PersonSearchTableComponent', () => {
  let component: PersonSearchTableComponent;
  let fixture: ComponentFixture<PersonSearchTableComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PersonSearchTableComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PersonSearchTableComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
