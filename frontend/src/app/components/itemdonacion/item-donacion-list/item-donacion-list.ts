import { Component, inject, OnInit } from '@angular/core';
import { ItemdonacionService } from '../../../service/itemdonacion-service';
import { FormsModule } from '@angular/forms';
import { ItemDonacionResponse } from '../../../interfaces/ItemDonacion';
import { ItemDonacionEdit } from '../item-donacion-edit/item-donacion-edit';

@Component({
  imports: [FormsModule, ItemDonacionEdit],
  selector: 'app-item-donacion-list',
  styleUrl: './item-donacion-list.css',
  templateUrl: './item-donacion-list.html',
})
export class ItemDonacionList  implements OnInit{
  itemService = inject(ItemdonacionService);
  searchId = 0; 
  
  ngOnInit(): void {
    this.itemService.listItems()
  }

  buscar(){
    this.itemService.getById(this.searchId);
  }

  editar(i: ItemDonacionResponse){
    this.itemService.itemById.set(i)
  }

  eliminar(id: number){
    this.itemService.remove(id)
  }
}
