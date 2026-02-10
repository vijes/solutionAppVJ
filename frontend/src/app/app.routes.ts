import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { ClientesComponent } from './pages/clientes/clientes.component';
import { CuentasComponent } from './pages/cuentas/cuentas.component';
import { MovimientosComponent } from './pages/movimientos/movimientos.component';
import { ReportesComponent } from './pages/reportes/reportes.component';

export const routes: Routes = [
  {
    path: 'home',
    component: HomeComponent,
    children: [
      { path: 'clientes', component: ClientesComponent },
      { path: 'cuentas', component: CuentasComponent },
      { path: 'movimientos', component: MovimientosComponent },
      { path: 'reportes', component: ReportesComponent },
      { path: '', redirectTo: 'clientes', pathMatch: 'full' }
    ]
  },
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: '**', redirectTo: 'home' }
];
