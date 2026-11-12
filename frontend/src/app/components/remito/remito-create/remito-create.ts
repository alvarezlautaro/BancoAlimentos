import { Component, inject } from '@angular/core';
import { FormBuilder, FormControl, ReactiveFormsModule, Validators } from '@angular/forms';
import { ItemDonacionResponse } from '../../../interfaces/ItemDonacion';
import { RemitoResponse } from '../../../interfaces/remito';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { RemitoService } from '../../../service/remito-service';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-remito-create',
  styleUrl: './remito-create.css',
  templateUrl: './remito-create.html',
})
export class RemitoCreate {
  private readonly remitoService = inject(RemitoService);
  readonly itemDonacionService = inject(ItemdonacionService);
  private readonly fb = inject(FormBuilder);

  formulario = this.fb.nonNullable.group({
    fecha: ['', Validators.required],
    institucionId: ['', [Validators.required, Validators.min(1)]],
    itemIds: this.fb.nonNullable.array<FormControl<number>>([]),
  });

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

  add() {
    if (this.formulario.invalid) return;

    const items = this.formulario.controls.itemIds.controls
      .map((control) => this.itemDonacionService.items().find((item) => item.id === control.value))
      .filter((item): item is ItemDonacionResponse => item !== undefined);

    const remito: RemitoResponse = {
      fecha: new Date(this.formulario.controls.fecha.value),
      items,
      institucion: { id: Number(this.formulario.controls.institucionId.value) },
    };

    this.remitoService.add(remito);
    this.formulario.reset();
    this.formulario.controls.itemIds.clear();
  }
}
