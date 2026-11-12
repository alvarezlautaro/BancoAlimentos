import { Service, signal } from '@angular/core';
import { DonanteResponse } from '../interfaces/Donante';

@Service()
export class DonanteService {

    donantes = signal<DonanteResponse[]>([]);

    listDonantes(){
        
    }
}
