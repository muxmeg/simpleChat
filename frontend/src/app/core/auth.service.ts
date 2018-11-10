import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';

/**
 * Authorization service.
 */

@Injectable()
export class AuthService {
  private readonly REST_SERVICE_URL = environment.rest_url + 'auth/';

  constructor(private http: HttpClient) {
  }

  /**
   * Login user by user name.
   *
   * @param userName target user name.
   * @returns was user logged in or not.
   */
  authenticateUser(userName: string): Observable<boolean> {
    return this.http.post<boolean>(this.REST_SERVICE_URL, userName);
  }
}
