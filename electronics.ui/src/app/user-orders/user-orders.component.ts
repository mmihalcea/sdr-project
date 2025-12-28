import {Component, OnInit} from '@angular/core';
import {UserOrdersService} from "./user-orders.service";
import {Observable} from "rxjs";
import {PrimeNGConfig, SelectItem} from 'primeng/api';
import {TokenStorageService} from "../core/auth/token-storage.service";
import {Role} from '../utils/role';

@Component({
  selector: 'app-user-orders',
  templateUrl: './user-orders.component.html',
  styleUrls: ['./user-orders.component.scss']
})
export class UserOrdersComponent implements OnInit {

  userOrders$: Observable<any> = new Observable<any>();
  sortOptions: SelectItem[] = [];
  sortOrder = 0;
  sortField = '';

  constructor(private userOrdersService: UserOrdersService, private primengConfig: PrimeNGConfig, private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    if (this.userHasRole(Role.USER) || this.userHasRole(Role.TEACHER)) {
      this.userOrders$ = this.userOrdersService.getUserOrders();
    } else if (this.userHasRole(Role.ADMIN)) {
      this.userOrders$ = this.userOrdersService.getAllOrders();
    }


    this.sortOptions = [
      {label: 'Descrescător', value: '!priceTotal'},
      {label: 'Crescător', value: 'priceTotal'}
    ];

    this.primengConfig.ripple = true;
  }

  onSortChange(event: any) {
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

}
