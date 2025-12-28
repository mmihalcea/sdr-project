import {NgModule} from '@angular/core';

import {QuizRoutingModule} from './quiz-routing.module';
import {QuizComponent} from './quiz.component';
import {StepsModule} from "primeng/steps";
import {QuizAdminComponent} from './quiz-admin/quiz-admin.component';
import {AccordionModule} from "primeng/accordion";
import {CoreModule} from "../core/core.module";
import {SharedModule} from "../shared/shared.module";
import {ListboxModule} from "primeng/listbox";
import {InplaceModule} from "primeng/inplace";
import {CheckboxModule} from "primeng/checkbox";
import {ProductModule} from "../product/product.module";


@NgModule({
  declarations: [
    QuizComponent,
    QuizAdminComponent
  ],
    imports: [
        CoreModule,
        SharedModule,
        QuizRoutingModule,
        StepsModule,
        AccordionModule,
        ListboxModule,
        InplaceModule,
        CheckboxModule,
        ProductModule
    ]
})
export class QuizModule {
}
