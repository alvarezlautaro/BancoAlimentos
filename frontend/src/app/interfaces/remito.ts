export interface Remito {
  id: number;
  fecha: string;
  idInstitucion: number;
  institucionNombre?: string;
  items?: RemitoItem[];
}

export interface RemitoRequest {
  fecha: string;
  idInstitucion: number;
  items?: RemitoItem[];
}

export interface RemitoItem {
  idItemDonacion: number;
  cantidad: number;
  nombreProducto?: string;
}
export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}