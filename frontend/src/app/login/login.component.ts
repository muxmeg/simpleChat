import {Component} from '@angular/core';
import {AuthService} from '../core/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: 'login.component.html',
  styleUrls: ['./login.component.css']
})

export class LoginComponent {

  userName: string;
  nameInvalid = false;

  constructor(private authService: AuthService, private router: Router) {
  }

  logIn(): void {
    const self = this;
    if (this.userName) {
      this.authService.authenticateUser(this.userName).subscribe((success: boolean) => {
        if (success) {
          this.userName = '';
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
