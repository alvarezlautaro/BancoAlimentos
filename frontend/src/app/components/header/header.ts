import { Component } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth-service';

@Component({
  imports: [RouterLink],
  selector: 'app-header',
  styleUrl: './header.css',
  templateUrl: './header.html',
})
export class Header {
  constructor(readonly auth: AuthService) {}
}
