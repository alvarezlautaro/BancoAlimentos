import { Estado } from "../enums/Estado";
import { TipoInstitucion } from "../enums/tipo-institucion";

export interface Institucion {
    id?: string;
    nombre : string;
    tipo : TipoInstitucion;
    direccion : string;
    telefono : string;
    email : string;
    estadoPago: Estado;
    estado?: Estado;
}
