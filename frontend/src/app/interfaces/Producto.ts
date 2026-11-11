import { Categoria } from '../enums/Categoria';
import { UnidadMedida } from '../enums/UnidadMedida';
export interface Producto{
    id?: number,
    nombre: string,
    categoria: Categoria,
    unidadMedida: UnidadMedida
}