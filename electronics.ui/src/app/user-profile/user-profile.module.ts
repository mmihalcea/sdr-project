import {NgModule} from '@angular/core';

import {UserProfileRoutingModule} from './user-profile-routing.module';
import {UserProfileComponent} from './user-profile.component';
import {SharedModule} from "../shared/shared.module";
import {FileUploadModule} from "primeng/fileupload";
import {PasswordModule} from "primeng/password";
import {CheckboxModule} from "primeng/checkbox";


@NgModule({
  declarations: [
    UserProfileComponent
  ],
    imports: [
        UserProfileRoutingModule,
        SharedModule,
        FileUploadModule,
        PasswordModule,
        CheckboxModule
    ]
})
export class UserProfileModule {
}
