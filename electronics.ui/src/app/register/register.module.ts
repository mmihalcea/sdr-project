import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';

import {RegisterRoutingModule} from './register-routing.module';
import {RegisterComponent} from './register.component';
import {SharedModule} from "../shared/shared.module";
import {AutoCompleteModule} from "primeng/autocomplete";
import {CheckboxModule} from "primeng/checkbox";


@NgModule({
  declarations: [
    RegisterComponent
  ],
    imports: [
        RegisterRoutingModule,
        SharedModule,
        AutoCompleteModule,
        CheckboxModule
    ]
})
export class RegisterModule {
}
