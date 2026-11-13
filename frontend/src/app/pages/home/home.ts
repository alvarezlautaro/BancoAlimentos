import { DatePipe } from '@angular/common';
import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { RouterLink } from '@angular/router';
import { AuthService } from '../../service/auth-service';
import { Donacion } from '../../interfaces/donacion';
import { DonacionService } from '../../service/donacion-service';
import { RemitoService } from '../../service/remito-service';

@Component({
  imports: [RouterLink, DatePipe],
  selector: 'app-home',
  styleUrl: './home.css',
  templateUrl: './home.html',
})
export class Home implements OnInit {
  auth = inject(AuthService);
  private donacionService = inject(DonacionService);
  readonly remitoService = inject(RemitoService);

  donaciones = signal<Donacion[]>([]);
  errorDonaciones = signal('');

  readonly ultimasDonaciones = computed(() => [...this.donaciones()]
    .sort((a, b) => (b.id ?? -1) - (a.id ?? -1) || b.fecha.localeCompare(a.fecha))
    .slice(0, 5));

  readonly ultimosRemitos = computed(() => [...this.remitoService.remitos()]
    .sort((a, b) => b.fecha.localeCompare(a.fecha))
    .slice(0, 5));

  ngOnInit(): void {
    if (!this.auth.hasRole('USER_DEPOSITO')) return;

    this.donacionService.obtenerDonaciones().subscribe({
      next: donaciones => this.donaciones.set(donaciones),
      error: () => this.errorDonaciones.set('No se pudieron cargar las donaciones recientes.'),
    });
    this.remitoService.listRemitos();
  }
}
