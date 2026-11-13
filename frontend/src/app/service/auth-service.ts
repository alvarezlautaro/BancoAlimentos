import { HttpClient } from '@angular/common/http';
import { computed, inject, Service, signal } from '@angular/core';
import { Router } from '@angular/router';
import { AuthRequest, AuthResponse } from '../interfaces/Auth';
import { tap } from 'rxjs';

const TOKEN_KEY = 'token';
const ROLES_KEY = 'roles';

@Service()
export class AuthService {
    private http = inject(HttpClient);
    private router = inject(Router);
    private url = 'http://localhost:8080/api/auth';

    private _roles = signal<string[]>(this.loadRoles());
    private _token = signal<string | null>(localStorage.getItem(TOKEN_KEY));
    

    readonly token = this._token.asReadonly();
    readonly roles = this._roles.asReadonly();
    readonly username = computed(() => this.decode(this._token())?.sub ?? null);
    readonly isLoggedIn = computed(() => {
        const exp = this.decode(this._token())?.exp;
        return !!exp && exp * 1000 > Date.now() && this.roles().length > 0;
    })

    login(credentials: AuthRequest){
        return this.http.post<AuthResponse>(`${this.url}/login`, credentials).pipe(
            tap(res => {
                localStorage.setItem(TOKEN_KEY, res.token);
                localStorage.setItem(ROLES_KEY, JSON.stringify(res.roles));
                this._token.set(res.token);
                this._roles.set(res.roles);
            })
        )
    }

    logout(){
        localStorage.removeItem(TOKEN_KEY);
        localStorage.removeItem(ROLES_KEY);
        this._token.set(null);
        this._roles.set([]);
        this.router.navigate(['/login']);
    }

    hasRole(role: string): boolean{
        return this.roles().includes(`ROLE_${role}`);
    }

    hasAnyRole(roles: string[]): boolean{
        return roles.some(r => this.hasRole(r));
    }

    hasAuthority(authority: string): boolean {
        return this.roles().includes(authority);
    }

    private loadRoles(): string[] {
        const storedRoles = localStorage.getItem(ROLES_KEY);
        if (!storedRoles) return [];

        try {
            const roles: unknown = JSON.parse(storedRoles);
            return Array.isArray(roles) && roles.every(role => typeof role === 'string') ? roles : [];
        } catch {
            localStorage.removeItem(ROLES_KEY);
            return [];
        }
    }

    private decode(token: string | null): { sub: string; exp: number } | null {
        if(!token) return null;

        try{
            const payload = token?.split('.')[1].replace(/-/g, '+').replace(/_/g, '/');
            return JSON.parse(atob(payload));
        }catch{
            return null;
        }
    }
}
