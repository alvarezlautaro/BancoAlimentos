import { HttpClient } from '@angular/common/http';
import { computed, inject, Service, signal } from '@angular/core';
import { Router, RouterOutlet } from '@angular/router';
import { AuthRequest, AuthResponse } from '../interfaces/Auth';
import { tap } from 'rxjs';

const TOKEN_KEY = 'token';

@Service()
export class AuthService {
    private http = inject(HttpClient);
    private router = inject(Router);
    private url = 'http://localhost:8080/api/auth';

    private _token = signal<string | null>(localStorage.getItem(TOKEN_KEY));
    

    readonly token = this._token.asReadonly();
    readonly roles = computed(() => this.decode(this._token())?.roles ?? []);
    readonly username = computed(() => this.decode(this._token())?.sub ?? null);
    readonly isLoggedIn = computed(() => {
        const exp = this.decode(this._token())?.exp;
        return !!exp && exp * 1000 > Date.now();
    })

    login(credentials: AuthRequest){
        return this.http.post<AuthResponse>(`${this.url}/login`, credentials).pipe(
            tap(res => {
                localStorage.setItem(TOKEN_KEY, res.token);
                this._token.set(res.token);
            })
        )
    }

    logout(){
        localStorage.removeItem(TOKEN_KEY);
        this._token.set(null);
        this.router.navigate(['/login']);
    }

    hasRole(role: string): boolean{
        return this.roles().includes(`ROLE_${role}`);
    }

    hasAnyRole(roles: string[]): boolean{
        return roles.some(r => this.hasRole(r));
    }

    private decode(token: string | null): { sub: string; exp: number; roles: string[] } | null {
        if(!token) return null;

        try{
            const payload = token?.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
            return JSON.parse(atob(payload));
        }catch{
            return null;
        }
    }
}
