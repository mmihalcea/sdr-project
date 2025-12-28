import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {ToolbarComponent} from './component/toolbar/toolbar.component';
import {FooterComponent} from './component/footer/footer.component';
import {ToolbarModule} from "primeng/toolbar";
import {ButtonModule} from "primeng/button";
import {FlexLayoutModule} from "@angular/flex-layout";
import {DividerModule} from "primeng/divider";
import {RouterModule} from "@angular/router";
import {RippleModule} from "primeng/ripple";
import {AvatarModule} from "primeng/avatar";
import {MenuModule} from "primeng/menu";
import {BadgeModule} from "primeng/badge";


@NgModule({
  declarations: [
    ToolbarComponent,
    FooterComponent
  ],
  exports: [
    ToolbarComponent,
    FooterComponent
  ],
    imports: [
        CommonModule,
        ToolbarModule,
        ButtonModule,
        FlexLayoutModule,
        DividerModule,
        RouterModule,
        RippleModule,
        AvatarModule,
        MenuModule,
        BadgeModule
    ]
})
export class CoreModule {
}
