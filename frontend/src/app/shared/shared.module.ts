import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {LoggedInGuard} from './guards/LogInGuard';
import {AutoScrollDownDirective} from './directives/auto-scrollDown';

// shared components and model
@NgModule({
  imports: [
    CommonModule,
    FormsModule
  ],
  declarations: [AutoScrollDownDirective],
  exports: [
    CommonModule,
    FormsModule,
    AutoScrollDownDirective
  ],
  entryComponents: [],
  providers: [LoggedInGuard]
})
export class SharedModule {
}
