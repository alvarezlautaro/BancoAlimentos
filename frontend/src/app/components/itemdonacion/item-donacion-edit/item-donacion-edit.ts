import { Component, computed, inject, OnInit, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule } from '@angular/forms';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { ItemDonacionRequest } from '../../../interfaces/ItemDonacion';
import { ProductoService } from '../../../service/producto-service';
import { EmpresaService } from '../../../services/empresas/empresa';
import { Empresa } from '../../../models/empresa';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-item-donacion-edit',
  styleUrl: './item-donacion-edit.css',
  templateUrl: './item-donacion-edit.html',
})
export class ItemDonacionEdit implements OnInit{
  itemService = inject(ItemdonacionService);

  productoService = inject(ProductoService);
  donanteService = inject(EmpresaService);
  empresas = signal<Empresa[]>([]);

  textoBusquedaDonante = signal<string>('');
  donanteSeleccionado = signal<Empresa | null>(null);

  donantesFiltrados = computed(() => {
    const filtro = this.textoBusquedaDonante().toLowerCase().trim();
    if (!filtro) return [];

    return this.empresas().filter(d => 
      d.razon_social.toLowerCase().includes(filtro) || 
      d.cuit.includes(filtro)
    );
  });
  
  fb: FormBuilder = inject(FormBuilder);

  formulario = this.fb.nonNullable.group(
    {
      fechaVencimiento: [''],
      valorUnitario: [0],
      cantidad: [0],
      productoId: [0],
      donanteId: [0]
    }
  );

  ngOnInit(): void {
    this.donanteService.obtenerEmpresas().subscribe(data => this.empresas.set(data));
    this.productoService.listProductos();

    const i = this.itemService.itemById();
    if(i){
      this.formulario.patchValue(i);

      this.textoBusquedaDonante.set(i.donante);

      const prodActual = this.productoService.productos().find(p => p.nombre === i.productoNombre);
      if (prodActual) {
        this.formulario.controls.productoId.setValue(prodActual.id);
      }
      
    }
  }

  seleccionarDonante(donante: Empresa) {
    this.donanteSeleccionado.set(donante);
    this.formulario.controls.donanteId.setValue(Number(donante.id));
    this.textoBusquedaDonante.set(donante.razon_social);
  }

  onInputDonante(event: Event) {
    const valor = (event.target as HTMLInputElement).value;
    this.textoBusquedaDonante.set(valor);
    this.donanteSeleccionado.set(null);
    this.formulario.controls.donanteId.setValue(0);
  }

  save(){
    if(this.formulario.invalid) return;

    const itemActual = this.itemService.itemById();
    if(!itemActual || !itemActual.id) return;

    const productoIdForm = this.formulario.controls.productoId.value;
    const productoIdFinal = productoIdForm > 0
      ? productoIdForm
      : this.productoService.productos().find(p => p.nombre === itemActual.productoNombre)?.id ?? 0;

    const donanteIdForm = this.formulario.controls.donanteId.value;
    const donanteIdFinal = donanteIdForm > 0
      ? donanteIdForm
      : Number(this.empresas().find(d => d.razon_social === itemActual.donante)?.id ?? 0);



    // FALTA QUE AGARRE LOS DATOS NUEVOS O USE LOS QUE YA ESTABAN EN EL ACTUAL
    const itemEditado: ItemDonacionRequest = {
      fechaVencimiento: this.formulario.controls.fechaVencimiento.value || itemActual.fechaVencimiento,
      cantidad: this.formulario.controls.cantidad.value || itemActual.cantidad,
      productoId: productoIdFinal,
      donanteId: donanteIdFinal,
      valorUnitario: this.formulario.controls.valorUnitario.value || itemActual.valorUnitario
    }

    this.itemService.update(itemActual.id, itemEditado);
    this.itemService.itemById.set(null);
  }

  cancel(){
    this.itemService.itemById.set(null);
  }
}
