import { Categoria } from '../enums/Categoria';
import { UnidadMedida } from '../enums/UnidadMedida';
export interface ProductoResponse{
    id: number,
    nombre: string,
    categoria: Categoria,
    unidadMedida: UnidadMedida
}

export interface ProductoRequest{
    nombre: string,
    categoria: Categoria,
    unidadMedida: UnidadMedida
}