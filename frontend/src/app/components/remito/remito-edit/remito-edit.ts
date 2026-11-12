import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemDonacionResponse } from '../../../interfaces/ItemDonacion';
import { RemitoResponse } from '../../../interfaces/remito';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { RemitoService } from '../../../service/remito-service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-remito-edit',
  styleUrl: './remito-edit.css',
  templateUrl: './remito-edit.html',
})
export class RemitoEdit implements OnInit {
  readonly remitoService = inject(RemitoService);
  readonly itemDonacionService = inject(ItemdonacionService);
  private readonly fb = inject(FormBuilder);

  formulario = this.fb.nonNullable.group({
    fecha: ['', Validators.required],
    institucionId: ['', [Validators.required, Validators.min(1)]],
    itemIds: this.fb.nonNullable.array<FormControl<number>>([]),
  });

  get itemsDisponibles(): ItemDonacionResponse[] {
    const items = new Map<number, ItemDonacionResponse>();
    for (const item of [...this.itemDonacionService.items(), ...(this.remitoService.remitoById()?.items ?? [])]) {
      items.set(item.id, item);
    }
    return [...items.values()];
  }

  ngOnInit() {
    const remito = this.remitoService.remitoById();
    if (!remito) return;

    const institucion = remito.institucion as { id?: number };
    this.formulario.patchValue({
      fecha: new Date(remito.fecha).toISOString().slice(0, 10),
      institucionId: institucion.id?.toString() ?? '',
    });
    for (const item of remito.items) {
      this.formulario.controls.itemIds.push(this.fb.nonNullable.control(item.id));
    }
  }

  isItemSelected(id: number) {
    return this.formulario.controls.itemIds.controls.some((control) => control.value === id);
  }

  toggleItem(id: number, event: Event) {
    const checked = (event.target as HTMLInputElement).checked;
    const itemIds = this.formulario.controls.itemIds;
    const index = itemIds.controls.findIndex((control) => control.value === id);

    if (checked && index === -1) {
      itemIds.push(this.fb.nonNullable.control(id));
    } else if (!checked && index !== -1) {
      itemIds.removeAt(index);
    }
  }

  save() {
    if (this.formulario.invalid) return;

    const remitoActual = this.remitoService.remitoById();
    if (!remitoActual?.id) return;

    const items = this.formulario.controls.itemIds.controls
      .map((control) => this.itemsDisponibles.find((item) => item.id === control.value))
      .filter((item): item is ItemDonacionResponse => item !== undefined);

    const actualizado: Partial<RemitoResponse> = {
      fecha: new Date(this.formulario.controls.fecha.value),
      institucion: { id: Number(this.formulario.controls.institucionId.value) },
      items,
    };

    this.remitoService.update(remitoActual.id, actualizado);
    this.remitoService.remitoById.set(null);
  }

  cancel() {
    this.remitoService.remitoById.set(null);
  }
}
