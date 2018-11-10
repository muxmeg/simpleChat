import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LobbyComponent} from './lobby.component';

const routes: Routes = [{
  path: '',
  component: LobbyComponent
}, {
  path: '**',
  redirectTo: ''
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class LobbyRoutingModule {
}
