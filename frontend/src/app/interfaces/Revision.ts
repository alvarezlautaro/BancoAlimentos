export interface Revision {
    revision: number;
    fecha: string;
    tipo: 'ADD' | 'MOD' | 'DEL';
    datos: Record<string, unknown>;
}