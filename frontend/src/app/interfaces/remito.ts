import { Institucion } from "./institucion"
import { ItemDonacionResponse } from "./ItemDonacion"

export interface Remito {

    id?:string
    fecha:Date
    items: ItemDonacionResponse[]
    institucion: Institucion
}


export interface RemitoResponse{

    id?:string
    fecha:Date
    items: ItemDonacionResponse[]
    institucion: Institucion
}