import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Empresa } from '../../../models/empresa';
import { EmpresaService } from '../../../services/empresas/empresa';

@Component({
  imports: [RouterLink],
  selector: 'app-empresa-detail',
  styleUrl: './empresa-detail.css',
  templateUrl: './empresa-detail.html',
})
export class EmpresaDetail implements OnInit {
  
  empresa ?: Empresa;
  id ?: string

  constructor(
    private empresaService : EmpresaService,
    private route : ActivatedRoute,
    private router : Router,
    private cdr : ChangeDetectorRef
  ){}

  ngOnInit(): void {
    
    this.id= this.route.snapshot.params['id'];

    if(this.id){
      this.empresaService.obtenerEmpresaPorId(this.id).subscribe({
        next : (empresaEncontrada) =>{
          this.empresa = empresaEncontrada;
          this.cdr.detectChanges()
        },
        error : (e) => {
          console.log(e);
          alert('Error al buscar detalles de la empresa.')
        }
      })
    }
  }


  deleteEmpresa(){
    if(!confirm('¿Desea eliminar la empresa?')) return; 

    this.empresaService.eliminarEmpresa(this.id!).subscribe({
      next : () => {
        alert('Empresa eliminada correctamente.');
        this.router.navigate(['/empresas']);
      },
      error : (e) => {
        console.log(e);
        alert('Error al eliminar empresa')
      }
    })
  }

}

