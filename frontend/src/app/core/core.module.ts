import {HttpClientModule} from '@angular/common/http';
import {NgModule, Optional, SkipSelf} from '@angular/core';
import {AuthService} from './auth.service';
import {LobbyService} from './lobby.service';

// for all singleton services
@NgModule({
  imports: [
    // angular
    HttpClientModule
  ],
  declarations: [],
  providers: [
    AuthService,
    LobbyService
  ]
})
export class CoreModule {
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error('CoreModule is already loaded. Import only once in AppModule!');
    }
  }
}
