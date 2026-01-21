import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {HomeComponent} from './home/home.component';
import {RegisterResolver} from './register/register.resolver';
import {ShoppingCartResolver} from './shopping-cart/shopping-cart.resolver';
import {AnyRoleGuard} from './core/auth/guards/any-role.guard';
import {UserGuard} from './core/auth/guards/user.guard';
import {AdminGuard} from "./core/auth/guards/admin.guard";
import {AdminOrUserGuard} from "./core/auth/guards/admin-or-user.guard";
import {ChartResolver} from "./chart/chart.resolver";

const routes: Routes = [
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent}, {
    path: 'login',
    loadChildren: () => import('./login/login.module').then(m => m.LoginModule)
  }, {
    path: 'register',
    loadChildren: () => import('./register/register.module').then(m => m.RegisterModule),
    resolve: {counties: RegisterResolver}
  },
  {
    path: 'product',
    loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
    canActivate: []
  },
  {
    path: 'profile',
    loadChildren: () => import('./user-profile/user-profile.module').then(m => m.UserProfileModule),
    canActivate: [AnyRoleGuard]
  },
  {
    path: 'orders',
    loadChildren: () => import('./user-orders/user-orders.module').then(m => m.UserOrdersModule),
    canActivate: [AnyRoleGuard]
  },
  {
    path: 'shopping-cart',
    loadChildren: () => import('./shopping-cart/shopping-cart.module').then(m => m.ShoppingCartModule),
    canActivate: [UserGuard], resolve: {products: ShoppingCartResolver}
  },
  {
    path: 'order-map',
    loadChildren: () => import('./order-map/order-map.module').then(m => m.OrderMapModule),
    canActivate: [AdminGuard]
  },
  {
    path: 'quiz',
    loadChildren: () => import('./quiz/quiz.module').then(m => m.QuizModule),
    canActivate: [AdminOrUserGuard]
  },
  {
    path: 'chart',
    loadChildren: () => import('./chart/admin-chart.module').then(m => m.AdminChartModule),
    canActivate: [AdminOrUserGuard],
    resolve: {chartData: ChartResolver}
  }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
