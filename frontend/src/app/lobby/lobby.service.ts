import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {StompService} from '@stomp/ng2-stompjs';
import {ChatMessage} from '../shared/model/chatMessage';
import {Message} from '@stomp/stompjs';
import {JoinLobbyRequest} from '../shared/model/joinLobbyRequest';

/**
 * Lobby service.
 */
@Injectable()
export class LobbyService {

  private readonly REST_SERVICE_URL = environment.rest_url + 'lobby/';

  constructor(private http: HttpClient, private stompService: StompService) {
  }

  /**
   * User joins the lobby.
   *
   * @param request dto containing user name and websocket session id.
   */
  joinLobby(request: JoinLobbyRequest): Observable<void> {
    return this.http.post<void>(this.REST_SERVICE_URL + 'users/', request);
  }

  findLobbyUsers(): Observable<string[]> {
    return this.http.get<string[]>(this.REST_SERVICE_URL + 'users');
  }

  findLobbyMessages(): Observable<ChatMessage[]> {
    return this.http.get<ChatMessage[]>(this.REST_SERVICE_URL + 'messages',
      {params: {'limit': '30'}});
  }

  subscribeForMessages(): Observable<Message> {
    return this.stompService.subscribe('/topic/lobbyMessages');
  }

  subscribeForUserUpdates(): Observable<Message> {
    return this.stompService.subscribe('/topic/lobbyUsers');
  }

  postMessage(message: ChatMessage): void {
    return this.stompService.publish('/ws/lobbyMessage', JSON.stringify(message));
  }
}
