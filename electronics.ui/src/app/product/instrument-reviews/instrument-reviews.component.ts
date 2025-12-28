import {Component, Input, OnInit} from '@angular/core';
import {ProductReviewResponse} from './instrument-review-response.model';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ProductService} from '../product.service';

@Component({
  selector: 'app-product-reviews',
  templateUrl: './instrument-reviews.component.html',
  styleUrls: ['./instrument-reviews.component.scss']
})
export class InstrumentReviewsComponent implements OnInit {

  @Input() reviews: Array<ProductReviewResponse> = [];
  @Input() instrumentId = -1;
  displayDialog = false;
  reviewForm: FormGroup = new FormGroup({});

  constructor(private fb: FormBuilder, private instrumentService: ProductService) {
  }

  ngOnInit(): void {
    this.initForm();
  }

  showDialog() {
    this.displayDialog = true;
  }

  onSubmit() {

  }

  initForm(): void {
    this.reviewForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      rating: [0, Validators.required]
    });
  }

  addReview() {
    if (this.reviewForm.valid) {
      this.instrumentService.addReviewForProduct(this.instrumentId, this.reviewForm.value).subscribe(res => {
        this.reviews = res.reviews;
      });
    }
  }
}
