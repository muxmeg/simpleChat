import {NgModule} from '@angular/core';
import {SharedModule} from '../shared/shared.module';
import {LobbyComponent} from './lobby.component';
import {LobbyService} from '../core/lobby.service';
import {LobbyRoutingModule} from './lobby-routing.module';

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
