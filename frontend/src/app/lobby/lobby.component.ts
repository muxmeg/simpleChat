import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatMessage} from '../shared/model/chatMessage';
import {LobbyService} from './lobby.service';
import {AuthService} from '../core/auth.service';
import {Message} from '@stomp/stompjs';
import {LobbyUserUpdate} from '../shared/model/lobbyUserUpdate';

@Component({
  selector: 'app-lobby',
  templateUrl: 'lobby.component.html',
  styleUrls: ['./lobby.component.css']
})

export class LobbyComponent implements OnInit, OnDestroy {
  username: string;
  users: string[];
  messages: ChatMessage[];
  userMessage: string;
  private subscriptions: any = [];

  constructor(private lobbyService: LobbyService, private authService: AuthService) {
  }

  ngOnInit(): void {
    this.username = this.authService.username;
    this.lobbyService.joinLobby({username: this.username, sessionId: '111'})
    .subscribe(() => {
      this.lobbyService.findLobbyMessages().subscribe((value: ChatMessage[]) => {
        this.messages = value;
        const subscription = this.lobbyService.subscribeForMessages().subscribe((message: Message) => {
          const chatMessage: ChatMessage = JSON.parse(message.body);
          if (chatMessage.sender !== this.username) {
            this.messages.push(chatMessage);
          }
        });
        this.subscriptions.push(subscription);
      });
      this.lobbyService.findLobbyUsers().subscribe((value: string[]) => {
        this.users = value;
        const subscription = this.lobbyService.subscribeForUserUpdates().subscribe((message: Message) => {
          const chatUserUpdate: LobbyUserUpdate = JSON.parse(message.body);
          if (chatUserUpdate.joined) {
            this.users.push(chatUserUpdate.username);
          } else {
            const idx = this.users.indexOf(chatUserUpdate.username);
            if (idx !== -1) {
              this.users.splice(idx, 1);
            }
          }
        });
        this.subscriptions.push(subscription);
      });
    });
  }

  sendMessage(): void {
    if (this.userMessage) {
      const newMessage: ChatMessage = {body: this.userMessage, sender: this.username,
        date: new Date()};
      this.messages.push(newMessage);
      this.lobbyService.postMessage(newMessage);
      this.userMessage = null;
    }
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => {
      subscription.unsubscribe();
    });
  }


}
