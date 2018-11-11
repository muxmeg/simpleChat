import {Component} from '@angular/core';
import {AuthService} from '../core/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  username: string;
  nameInvalid = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  logIn(): void {
    const self = this;
    if (this.username) {
      this.authService.authenticateUser(this.username).subscribe((success: boolean) => {
        if (success) {
          this.username = '';
          self.router.navigate(['/lobby']);
        } else {
          self.nameInvalid = true;
        }
      }, error => {
        console.log(error);
      });
    }
  }
}
