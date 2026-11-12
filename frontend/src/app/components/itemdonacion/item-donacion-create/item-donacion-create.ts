import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { DonanteService } from '../../../service/donante-service';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { DonanteResponse } from '../../../interfaces/Donante';
import { ItemDonacionRequest } from '../../../interfaces/ItemDonacion';
import { ProductoService } from '../../../service/producto-service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-item-donacion-create',
  styleUrl: './item-donacion-create.css',
  templateUrl: './item-donacion-create.html',
})
export class ItemDonacionCreate implements OnInit{
  itemService = inject(ItemdonacionService);
  fb: FormBuilder = inject(FormBuilder);

  productoService = inject(ProductoService);
  donanteService = inject(DonanteService);
  textoBusquedaDonante = signal<string>('');
  donanteSeleccionado = signal<DonanteResponse | null>(null);

  donantesFiltrados = computed(() => {
    const filtro = this.textoBusquedaDonante().toLowerCase().trim();
    if (!filtro) return [];

    return this.donanteService.donantes().filter(d => 
      d.razonSocial.toLowerCase().includes(filtro)
    );
  });

  formulario = this.fb.nonNullable.group({
      fechaVencimiento: ['', Validators.required],
      valorUnitario: [0, [Validators.required, Validators.min(0.01)]],
      cantidad: [1, [Validators.required, Validators.min(1)]],
      productoId: [0, [Validators.required, Validators.min(1)]],
      donanteId: [0, [Validators.required, Validators.min(1)]]
    });

    ngOnInit(): void {
    this.itemService.listItems();
    this.donanteService.listDonantes();
  }

  seleccionarDonante(donante: DonanteResponse) {
    this.donanteSeleccionado.set(donante);
    this.formulario.controls.donanteId.setValue(donante.id);
    this.textoBusquedaDonante.set(donante.razonSocial);
  }

  //Si un usuario elimina o cambia el texto despues de elegir un donante, invalida la seleccion
  onInputDonante(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.textoBusquedaDonante.set(valor);
    this.donanteSeleccionado.set(null);
    this.formulario.controls.donanteId.setValue(0);
  }

  add(){
    if(this.formulario.invalid) return;

    const nuevoItem: ItemDonacionRequest = {
      fechaVencimiento: this.formulario.controls.fechaVencimiento.value,
      valorUnitario: this.formulario.controls.valorUnitario.value,
      cantidad: this.formulario.controls.cantidad.value,
      productoId: this.formulario.controls.productoId.value,
      donanteId: this.formulario.controls.donanteId.value
    }

    this.itemService.add(nuevoItem);

    //resetea el formulario y el buscador de donante
    this.formulario.reset();
    this.textoBusquedaDonante.set('');
    this.donanteSeleccionado.set(null);
  }


}
