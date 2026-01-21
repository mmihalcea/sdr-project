import {AfterViewInit, Component, Input, OnInit} from '@angular/core';
import {ProductService} from '../product.service';
import {ActivatedRoute} from '@angular/router';
import {FilterService, SelectItem} from 'primeng/api';
import {TokenStorageService} from '../../core/auth/token-storage.service';
import {ShoppingCartService} from '../../shopping-cart/shopping-cart.service';
import {ProductItem} from './product-item.model';
import {getSeverityByStockStatusId} from '../../utils/functions'
import {Role} from 'src/app/utils/role';
import {DialogService} from 'primeng/dynamicdialog';
import {AddToCartDialogComponent} from "../add-to-cart-dialog/add-to-cart-dialog.component";
import {IdName} from "../../utils/id-name.model";

@Component({
  selector: 'app-product-list',
  templateUrl: './product-list.component.html',
  styleUrls: ['./product-list.component.scss']
})
export class ProductListComponent implements OnInit {
  @Input() products: Array<ProductItem> = [];
  @Input() hideFilters: boolean = false;
  priceSortOptions: SelectItem[] = [];
  categoryOptions: IdName[] = [];
  sortOrder = 0;
  Role = Role;
  sortField = '';
  getSeverityByStockStatusId = getSeverityByStockStatusId;

  constructor(private productService: ProductService, private activatedRoute: ActivatedRoute,
              private tokenStorage: TokenStorageService, private shoppingCartService: ShoppingCartService, private dialogService: DialogService, private filterService: FilterService) {
  }

  ngOnInit(): void {
    this.priceSortOptions = [
      {label: 'Descrescător', value: '!price'},
      {label: 'Crescător', value: 'price'}
    ];

    this.products.forEach(i => {
      if (this.categoryOptions.filter(e => e.id === i.category.id).length === 0) {
        this.categoryOptions.push(i.category);
      }
    });
  }


  onSortChange(event: any): void {
    let value = event.value;

    if (value.indexOf('!') === 0) {
      this.sortOrder = -1;
      this.sortField = value.substring(1, value.length);
    } else {
      this.sortOrder = 1;
      this.sortField = value;
    }
  }

  userHasRole(role: string) {
    return this.tokenStorage.getAuthorities().includes(role);
  }

  addToShoppingCart(event: Event, id: number) {
    this.dialogService.open(AddToCartDialogComponent, {
      data: {
        id: id
      }
    })
    event.stopPropagation();
  }
}
