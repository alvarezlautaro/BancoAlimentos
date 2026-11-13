import { EstadoDonacion } from "../enums/EstadoDonacion";
import { ItemDonacionResponse } from "./ItemDonacion";

export interface Donacion {
    id?: number;
    fecha: string;
    estado: EstadoDonacion;
    nroRemitoProveedor: number;
    observaciones?: string;
    idDonante: number;
    nombreDonante?: string;
    items:ItemDonacionResponse[];
}

export interface DonacionRequest {
    fecha: string;
    estado: EstadoDonacion;
    nroRemitoProveedor: number | string;
    observaciones?: string;
    idDonante: number;
    items: Array<{
        cantidad: number;
        valorUnitario: number;
        fechaVencimiento: string;
        productoId?: number;
        producto?: {
            nombre: string;
            categoria: string;
            unidadMedida: string;
        };
    }>;
}
