import { ChangeDetectorRef, Component, OnInit, signal } from '@angular/core';
import { Empresa } from '../../../models/empresa';
import { RouterLink } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EmpresaService } from '../../../services/empresas/empresa';

@Component({
  imports: [RouterLink, CommonModule, FormsModule],
  selector: 'app-empresa-list',
  styleUrl: './empresa-list.css',
  templateUrl: './empresa-list.html',
})
export class EmpresaList implements OnInit {

  empresas = signal<Empresa[]>([]);


  ////para la barra de navegacion
  filtroEmpresa = '';

  constructor(
    private sEmpresa : EmpresaService
  ){}

  ngOnInit(): void {
    this.cargarEmpresas();
  }

  cargarEmpresas(){
    this.sEmpresa.obtenerEmpresas().subscribe({
      next : (data) =>{
        this.empresas.set(data);
      },
      error : (e) => {
        console.log(e);
        alert('Error al cargar empresas')
      }
    })
  }

  eliminarEmpresa(id : string){
    const confirmacion = window.confirm(`Estás seguro de eliminar la empresa " ${id} " ?`);

    if(confirmacion){
      this.sEmpresa.eliminarEmpresa(id).subscribe({
        next : () => {
          alert('Empresa eliminada correctamente');
          this.cargarEmpresas();
        },
        error : (e) => {
          alert('No se pudo eliminar la empresa');
          console.log(e);
        }
      })
    }
  }
/* 
  get empresasFiltradasXrazonSocial(){
    const lista = this.empresas();

    if(this.filtradoNombre){
      return lista.filter( e => e.razon_social.toLocaleLowerCase().includes(this.filtradoNombre.toLocaleLowerCase()))
    }else{
      return lista;
    }
  } */


  get empresasFiltradas(){
    const lista = this.empresas();
    const filtro = this.filtroEmpresa.toLowerCase().trim();

    if(!filtro){
      return lista;
    }else{
      return lista.filter(empresa => {
        const idCoincide = empresa.id?.toString().toLowerCase().includes(filtro);
        const razonCoincide = empresa.razon_social.toString().toLowerCase().includes(filtro);
        const cuitCoincide = empresa.cuit.toString().toLowerCase().includes(filtro);
        const emailCoincide = empresa.email.toLowerCase().includes(filtro);      
        const direccionCoincide = empresa.direccion.toLowerCase().includes(filtro); 

        return idCoincide || razonCoincide || cuitCoincide;
      })
    }
  } 
}
