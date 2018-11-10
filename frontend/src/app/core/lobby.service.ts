import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {JoinLobbyRequest} from '../shared/model/joinLobbyRequest';

/**
 * Lobby service.
 */
@Injectable()
export class LobbyService {

  private readonly REST_SERVICE_URL = environment.rest_url + 'lobby/';

  constructor(private http: HttpClient) {
  }

  /**
   * User joins the lobby.
   *
   * @param request dto containing user name and websocket session id.
   */
  joinLobby(request: JoinLobbyRequest): Observable<void> {
    return this.http.post<void>(this.REST_SERVICE_URL, request);
  }
}
