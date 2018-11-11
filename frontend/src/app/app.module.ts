import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {LoginComponent} from './login/login.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {CoreModule} from './core/core.module';
import {SharedModule} from './shared/shared.module';
import {AppRoutingModule} from './app-routing.module';
import {StompConfig, StompService} from '@stomp/ng2-stompjs';
import {environment} from '../environments/environment';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,

    // core & shared
    CoreModule,
    SharedModule,

    // routing
    AppRoutingModule
  ],
  providers: [StompService, {
      provide: StompConfig,
      useValue: {
        url: environment.ws_url,
        headers: {},
        heartbeat_in: 0,
        heartbeat_out: 20000,
        reconnect_delay: 5000,
        debug: true
      }
    }],
  bootstrap: [AppComponent]
})
export class AppModule { }
