import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Event} from '@angular/router';
import {ProductDetails} from './product-details.model';
import {TokenStorageService} from '../../core/auth/token-storage.service';
import {Role} from 'src/app/utils/role';
import {AddToCartDialogComponent} from "../add-to-cart-dialog/add-to-cart-dialog.component";
import {DialogService} from "primeng/dynamicdialog";

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
  styleUrls: ['./product-details.component.scss']
})
export class ProductDetailsComponent implements OnInit {
  details: ProductDetails | null = null;
  Role = Role;
  responsiveOptions: any[] = [
    {
      breakpoint: '1024px',
      numVisible: 5
    },
    {
      breakpoint: '960px',
      numVisible: 4
    },
    {
      breakpoint: '768px',
      numVisible: 3
    },
    {
      breakpoint: '560px',
      numVisible: 1
    }
  ];

  constructor(private activatedRoute: ActivatedRoute, private tokenStorage: TokenStorageService, private dialogService: DialogService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.details = data.details;
    });
  }

  addToShoppingCart() {
    this.dialogService.open(AddToCartDialogComponent, {
      data: {
        id: this.details?.id
      }
    })
   // event.stopPropagation();
  }

  userHasRole(role: string) {
    return this.tokenStorage.getAuthorities().includes(role);
  }

}
