import {FormsModule} from '@angular/forms';
import {CommonModule} from '@angular/common';
import {NgModule} from '@angular/core';
import {LoggedInGuard} from './guards/LogInGuard';
import {StompService} from '@stomp/ng2-stompjs';

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
  providers: [LoggedInGuard]
})
export class SharedModule {
}
