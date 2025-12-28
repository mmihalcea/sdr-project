import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ButtonModule} from "primeng/button";
import {CardModule} from "primeng/card";
import {InputTextModule} from "primeng/inputtext";
import {FlexLayoutModule} from "@angular/flex-layout";
import {RippleModule} from "primeng/ripple";
import {DividerModule} from "primeng/divider";
import {DropdownModule} from "primeng/dropdown";
import {InputNumberModule} from "primeng/inputnumber";
import {BlockUIModule} from "primeng/blockui";
import {ProgressSpinnerModule} from "primeng/progressspinner";
import {DataViewModule} from "primeng/dataview";
import {BadgeModule} from "primeng/badge";
import {RatingModule} from "primeng/rating";
import {GalleriaModule} from "primeng/galleria";
import {TabViewModule} from "primeng/tabview";
import {DynamicDialogModule} from "primeng/dynamicdialog";
import {DialogModule} from "primeng/dialog";


@NgModule({
  declarations: [],
  exports: [
    CommonModule,
    FormsModule,
    ReactiveFormsModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    FlexLayoutModule,
    RippleModule,
    DividerModule,
    DropdownModule,
    InputNumberModule,
    BlockUIModule,
    ProgressSpinnerModule,
    DataViewModule,
    BadgeModule,
    RatingModule,
    GalleriaModule,
    TabViewModule,
    DynamicDialogModule,
    DialogModule
  ]
})
export class SharedModule {
}
