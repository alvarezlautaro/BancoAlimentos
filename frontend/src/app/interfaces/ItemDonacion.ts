export interface ItemDonacionRequest {
  fechaVencimiento: string;
  valorUnitario: number;
  cantidad: number;
  productoId: number;
}

export interface ItemDonacionResponse {
  id: number;
  fechaVencimiento: string;
  valorUnitario: number;
  cantidad: number;
  productoNombre: string;
  donante: string;
}