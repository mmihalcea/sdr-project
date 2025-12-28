import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {OrderDetailsResponse} from "./order-details-response.model";
import {getSeverityByStockStatusId} from 'src/app/utils/functions';
import {IdValue} from "../../utils/id-value.model";
import {UserOrdersService} from "../user-orders.service";
import {UpdateOrderStatus} from './update-order-status.request';
import {MessageService} from "primeng/api";
import {TokenStorageService} from "../../core/auth/token-storage.service";
import {Role} from "../../utils/role";

@Component({
  selector: 'app-user-order-details',
  templateUrl: './user-order-details.component.html',
  styleUrls: ['./user-order-details.component.scss']
})
export class UserOrderDetailsComponent implements OnInit {
  details: OrderDetailsResponse = new OrderDetailsResponse();
  statuses: Array<IdValue> = [];
  Role = Role;
  initialOrderStatus: number = -1;
  getSeverityByStockStatusId = getSeverityByStockStatusId;

  constructor(private activatedRoute: ActivatedRoute, private ordersService: UserOrdersService, private messageService: MessageService,
              private tokenStorage: TokenStorageService) {
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe((data) => {
      this.details = data.details[1];
      this.statuses = data.details[0];
      this.initialOrderStatus = this.details.orderStatus.id
    });
  }

  updateOrderStatus() {
    this.ordersService.updateOrderStatus(new UpdateOrderStatus(this.details.id, this.details.orderStatus.id)).subscribe(res=>{
      this.messageService.add({severity: 'success', detail: 'Informații actualizate!'});
    })
  }

  userHasRole(role: string) {
    return this.tokenStorage.getAuthorities().includes(role);
  }
}
