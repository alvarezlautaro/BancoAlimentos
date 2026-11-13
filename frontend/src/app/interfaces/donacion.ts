import { ItemDonacionResponse } from "./ItemDonacion";

export interface Donacion {
    id?: number;
    fecha: string;
    estado: string;
    nroRemitoProveedor: number;
    observaciones?: string;
    idDonante?: number;
    nombreDonante?: string;
    items:ItemDonacionResponse[];
}
