import { Component, inject, signal } from '@angular/core';
import { AuthService } from '../../service/auth-service';
import { Router } from '@angular/router';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';

@Component({
  imports: [ReactiveFormsModule],
  selector: 'app-login',
  styleUrl: './login.css',
  templateUrl: './login.html',
})
export class Login {
  authService = inject(AuthService);
  router = inject(Router);
  fb: FormBuilder = inject(FormBuilder);

  error = signal<string>('');
  cargando = signal<boolean>(false);

  formulario = this.fb.nonNullable.group(
    {
      username: ['', Validators.required],
      password: ['', Validators.required]
    });

    login(){
      if(this.formulario.invalid) return;

      this.error.set('');
      this.cargando.set(true);

      this.authService.login(this.formulario.getRawValue()).subscribe({
        next: () => {
          this.cargando.set(false);
          this.router.navigate(['/home']);
        },
        error: (error) => {
          this.cargando.set(false);
          this.error.set(
            error.status === 401 || error.status === 403
            ? 'Usuario o contraseña incorrectos'
            : 'No se pudo conectar con el servidor'
          )
        }
      })

    }
}
