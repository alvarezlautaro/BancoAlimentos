export interface ItemDonacionRequest {
  fechaVencimiento: string;
  valorUnitario: number;
  cantidad: number;
  productoId: number;
  donanteId: number
}

export interface ItemDonacionResponse {
  id: number;
  productoId?: number;
  fechaVencimiento: string;
  valorUnitario: number;
  cantidad: number;
  productoNombre: string;
  donante: string;
}