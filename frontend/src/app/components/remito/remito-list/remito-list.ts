import { DatePipe } from '@angular/common';
import { Component, inject, OnInit } from '@angular/core';
import { RouterLink } from '@angular/router';
import { RemitoService } from '../../../service/remito-service';
import { RemitoEdit } from '../remito-edit/remito-edit';
import { Remito } from '../../../interfaces/remito';

@Component({
  imports: [RemitoEdit, RouterLink, DatePipe],
  selector: 'app-remito-list',
  styleUrl: './remito-list.css',
  templateUrl: './remito-list.html',
})
export class RemitoList implements OnInit {
  ngOnInit(): void {
    this.remitoService.listRemitos();
  }

  remitoService=inject(RemitoService);  
  remitos=this.remitoService.remitos;

  eliminar(id: number){
    this.remitoService.remove(id);
  }

  editar(r: Remito) {
    this.remitoService.remitoByID.set(r);
  }
}
