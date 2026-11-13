import { EstadoDonacion } from "../enums/EstadoDonacion";
import { ItemDonacionResponse } from "./ItemDonacion";

export interface Donacion {
    id?: number;
    fecha: string;
    estado: EstadoDonacion;
    nroRemitoProveedor: number;
    observaciones?: string;
    idDonante?: number;
    nombreDonante?: string;
    items:ItemDonacionResponse[];
}
