import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {UserProfileComponent} from './user-profile.component';
import {UserProfileResolver} from "./user-profile.resolver";

const routes: Routes = [{path: '', component: UserProfileComponent, resolve: {userDetails: UserProfileResolver}}];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserProfileRoutingModule {
}
