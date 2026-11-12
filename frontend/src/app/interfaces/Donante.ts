export interface DonanteRequest{
    razonSocial: string,
    cuit: string,
    telefono: string,
    email: string,
    direccion: string
}

export interface DonanteResponse{
    id: number
    razonSocial: string,
    cuit: string,
    telefono: string,
    email: string,
    direccion: string
}