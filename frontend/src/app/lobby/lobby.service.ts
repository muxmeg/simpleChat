import {Injectable} from '@angular/core';
import {Observable} from 'rxjs/internal/Observable';
import {environment} from '../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {StompService} from '@stomp/ng2-stompjs';
import {ChatMessage} from '../shared/model/chatMessage';
import {map} from 'rxjs/operators';
import {LobbyUserUpdate} from '../shared/model/lobbyUserUpdate';

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
   * @param username name of the user joined.
   */
  joinLobby(username: string): Observable<void> {
    return this.http.put<void>(this.REST_SERVICE_URL + 'users/' + username, {});
  }

  /**
   * User leaves the lobby.
   * @param username name of the user left.
   */
  leaveLobby(username: string): Observable<void> {
    return this.http.delete<void>(this.REST_SERVICE_URL + 'users/' + username);
  }

  /**
   * Find users in lobby.
   * @returns list of usernames.
   */
  findLobbyUsers(): Observable<string[]> {
    return this.http.get<string[]>(this.REST_SERVICE_URL + 'users');
  }

  /**
   * Find message history.
   * @returns list of chat messages.
   */
  findLobbyMessages(limit: number): Observable<ChatMessage[]> {
    return this.http.get<ChatMessage[]>(this.REST_SERVICE_URL + 'messages',
      {params: {'limit': limit.toString()}});
  }

  /**
   * Subscribe for new messages in lobby.
   * @returns observable for new chat messages.
   */
  subscribeForMessages(): Observable<ChatMessage> {
    return this.stompService.subscribe('/topic/lobbyMessages')
    .pipe(map(message => JSON.parse(message.body)));
  }

  /**
   * Subscribe for user status updates in lobby.
   * @returns observable user status updates.
   */
  subscribeForUserUpdates(): Observable<LobbyUserUpdate> {
    return this.stompService.subscribe('/topic/lobbyUsers')
    .pipe(map(message => JSON.parse(message.body)));
  }

  /**
   * Send message to lobby.
   * @param message message to send.
   */
  postMessage(message: ChatMessage): void {
    return this.stompService.publish('/ws/lobbyMessage', JSON.stringify(message));
  }
}
