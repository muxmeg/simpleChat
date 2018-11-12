import {Component, OnDestroy, OnInit} from '@angular/core';
import {ChatMessage} from '../shared/model/chatMessage';
import {LobbyService} from './lobby.service';
import {AuthService} from '../core/auth.service';
import {LobbyUserUpdate} from '../shared/model/lobbyUserUpdate';
import {Router} from '@angular/router';

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

  constructor(private lobbyService: LobbyService, private authService: AuthService,
              private router: Router) {
  }

  ngOnInit(): void {
    this.username = this.authService.username;
    this.lobbyService.joinLobby(this.username)
    .subscribe(() => {
      this.lobbyService.findLobbyMessages().subscribe((value: ChatMessage[]) => {
        this.messages = value;
        const subscription = this.lobbyService.subscribeForMessages()
        .subscribe((chatMessage: ChatMessage) => {
          if (chatMessage.sender !== this.username) {
            this.messages.push(chatMessage);
          }
        });
        this.subscriptions.push(subscription);
      });
      this.lobbyService.findLobbyUsers().subscribe((value: string[]) => {
        this.users = value;
        const subscription = this.lobbyService.subscribeForUserUpdates()
        .subscribe((chatUserUpdate: LobbyUserUpdate) => {
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
      const newMessage: ChatMessage = {
        body: this.userMessage, sender: this.username,
        date: new Date()
      };
      this.messages.push(newMessage);
      this.lobbyService.postMessage(newMessage);
      this.userMessage = null;
    }
  }

  userLeave(): void {
    this.lobbyService.leaveLobby(this.username).subscribe(value => {
      this.authService.logoutUser();
      this.router.navigate(['/']);
    });
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => {
      subscription.unsubscribe();
    });
  }


}
