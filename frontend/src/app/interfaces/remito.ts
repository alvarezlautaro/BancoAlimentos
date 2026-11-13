export interface Remito {
  externalId: string;
  fecha: string;
  idInstitucion: number;
}

export interface RemitoRequest {
  fecha: string;
  idInstitucion: number;
}
export interface PageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    size: number;
    number: number;
}