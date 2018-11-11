import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';
import {tap} from 'rxjs/operators';

/**
 * Authorization service.
 */

@Injectable()
export class AuthService {
  private readonly REST_SERVICE_URL = environment.rest_url + 'auth/';
  public username: string;

  constructor(private http: HttpClient) {
  }

  /**
   * Login user by user name.
   * @param username target user name.
   * @returns was user logged in or not.
   */
  authenticateUser(username: string): Observable<boolean> {
    return this.http.post<boolean>(this.REST_SERVICE_URL, username)
    .pipe(tap(result => this.username = username));
  }

  /**
   * Is current user authenticated.
   * @returns authentication status.
   */
  isAuthenticated(): boolean {
    return !!this.username;
  }
}
