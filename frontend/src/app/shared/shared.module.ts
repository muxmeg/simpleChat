import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';

// shared components and model
@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [],
  exports: [
    CommonModule,
    FormsModule
  ],
  entryComponents: [],
  providers: []
})
export class SharedModule {
}
