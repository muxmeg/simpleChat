import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {LobbyComponent} from './lobby.component';
import {LobbyRoutingModule} from './lobby-routing.module';
import {LobbyService} from './lobby.service';

@NgModule({
  imports: [
    SharedModule,
    LobbyRoutingModule
  ],
  declarations: [
    LobbyComponent
  ],
  providers: [
    LobbyService
  ]
})
export class LobbyModule {
}
