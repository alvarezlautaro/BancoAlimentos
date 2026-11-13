import { Estado } from "../enums/Estado";
import { ItemDonacionResponse } from "./ItemDonacion";

export interface Donacion {
    id?: number;
    fecha: string;
    estado: Estado;
    nroRemitoProveedor: number;
    observaciones?: string;
    idDonante?: number;
    nombreDonante?: string;
    items:ItemDonacionResponse[];
}
