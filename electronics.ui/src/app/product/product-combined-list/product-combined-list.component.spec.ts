import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductCombinedListComponent } from './product-combined-list.component';

describe('ProductCombinedListComponent', () => {
  let component: ProductCombinedListComponent;
  let fixture: ComponentFixture<ProductCombinedListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProductCombinedListComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProductCombinedListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
