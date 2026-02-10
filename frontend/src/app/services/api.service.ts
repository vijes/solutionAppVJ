import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  // Clientes
  getClientes(): Observable<any[]> { return this.http.get<any[]>(`${this.baseUrl}/clientes`); }
  createCliente(cliente: any): Observable<any> { return this.http.post<any>(`${this.baseUrl}/clientes`, cliente); }
  updateCliente(id: number, cliente: any): Observable<any> { return this.http.put<any>(`${this.baseUrl}/clientes/${id}`, cliente); }
  deleteCliente(id: number): Observable<any> { return this.http.delete<any>(`${this.baseUrl}/clientes/${id}`); }

  // Cuentas
  getCuentas(): Observable<any[]> { return this.http.get<any[]>(`${this.baseUrl}/cuentas`); }
  createCuenta(cuenta: any): Observable<any> { return this.http.post<any>(`${this.baseUrl}/cuentas`, cuenta); }
  updateCuenta(id: number, cuenta: any): Observable<any> { return this.http.put<any>(`${this.baseUrl}/cuentas/${id}`, cuenta); }
  deleteCuenta(id: number): Observable<any> { return this.http.delete<any>(`${this.baseUrl}/cuentas/${id}`); }

  // Movimientos
  getMovimientos(): Observable<any[]> { return this.http.get<any[]>(`${this.baseUrl}/movimientos`); }
  createMovimiento(movimiento: any): Observable<any> { return this.http.post<any>(`${this.baseUrl}/movimientos`, movimiento); }

  // Reportes
  getReporte(clienteId: number, fechaInicio: string, fechaFin: string): Observable<any[]> {
    let params = new HttpParams()
      .set('clienteId', clienteId.toString())
      .set('fechaInicio', fechaInicio)
      .set('fechaFin', fechaFin);
    return this.http.get<any[]>(`${this.baseUrl}/reportes`, { params });
  }
}
