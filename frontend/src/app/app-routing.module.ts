import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from './login/login.component';
import {LoggedInGuard} from './shared/guards/LogInGuard';

const routes: Routes = [{
  path: '',
  component: LoginComponent
},
  {
  path: 'lobby',
    canActivate: [LoggedInGuard],
    loadChildren: '../app/lobby/lobby.module#LobbyModule'
},
  {
  path: '**',
  redirectTo: ''
}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {enableTracing: true})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
