import {Component, OnInit} from '@angular/core';
import {TokenStorageService} from '../../auth/token-storage.service';
import {Router} from '@angular/router';
import {MenuItem, PrimeIcons} from 'primeng/api';
import {ShoppingCartService} from '../../../shopping-cart/shopping-cart.service';
import {Role} from 'src/app/utils/role';
import {ProductService} from "../../../product/product.service";

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.scss']
})
export class ToolbarComponent implements OnInit {
  username: string | null = null;
  items: MenuItem[] = [];
  Role = Role;
  orderCount = 0;

  constructor(private tokenStorage: TokenStorageService, private router: Router, private shoppingCartService: ShoppingCartService, private productService: ProductService) {
    this.tokenStorage.username$.subscribe(username => {
      this.username = username;
      this.updateMenuItems();
    });
  }

  ngOnInit(): void {
    this.shoppingCartService.orderLinesUpdate$.subscribe(res => {
      this.orderCount = res.size;
    })


    this.username = this.tokenStorage.getUsername();
    this.updateMenuItems();
  }

  logout() {
    this.tokenStorage.logout();
    this.router.navigate(['login']);
  }

  userHasRole(role: string) {
    return this.tokenStorage.getAuthorities().includes(role);
  }

  private updateMenuItems() {
    this.items = [
      {label: 'Profil', icon: PrimeIcons.USER, routerLink: ['/profile']},
      {label: 'Logout', icon: PrimeIcons.SIGN_OUT, command: () => this.logout()},
    ];
    if (!this.userHasRole(Role.ADMIN)) {
      this.items.splice(1, 0, {label: 'Comenzile mele', icon: PrimeIcons.LIST, routerLink: ['/orders']});
      if (!this.userHasRole(Role.TEACHER)) {
        this.items.splice(1, 0, {label: 'Chestionar', icon: PrimeIcons.QUESTION, routerLink: ['/quiz']});
      }

    }
    if (this.userHasRole(Role.ADMIN)) {
      this.items.splice(1, 0, {label: 'Grafice', icon: PrimeIcons.CHART_BAR, routerLink: ['/chart']});
      this.items.splice(1, 0, {label: 'Comenzi', icon: PrimeIcons.LIST, routerLink: ['/orders']});
      this.items.splice(1, 0, {
        label: 'Administrare chestionar',
        icon: PrimeIcons.QUESTION,
        routerLink: ['/quiz/manage']
      });
    }
  }

  insertAllProd(){
    this.productService.addAllProduct().subscribe(res => {})
    }
}
