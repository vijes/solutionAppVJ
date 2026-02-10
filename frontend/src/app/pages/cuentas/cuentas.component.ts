import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-cuentas',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cuentas.component.html',
  styleUrl: './cuentas.component.css'
})
export class CuentasComponent implements OnInit {
  cuentas: any[] = [];
  clientes: any[] = [];
  form: any = {
    numeroCuenta: '', tipoCuenta: 'Ahorro', saldoInicial: 0, estado: true, cliente: { id: '' }
  };
  editingId: number | null = null;
  // Temporary variable to bind select
  selectedClienteId: string = '';

  constructor(private api: ApiService, private loading: LoadingService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.api.getCuentas().subscribe(data => this.cuentas = data);
    this.api.getClientes().subscribe(data => this.clientes = data);
  }

  handleSubmit() {
    this.form.cliente.id = this.selectedClienteId;
    this.loading.show();
    if (this.editingId) {
      this.api.updateCuenta(this.editingId, this.form).subscribe({
        next: () => {
          this.loading.hide();
          this.resetForm();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    } else {
      this.api.createCuenta(this.form).subscribe({
        next: () => {
          this.loading.hide();
          this.resetForm();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    }
  }

  handleEdit(cuenta: any) {
    this.form = { ...cuenta, cliente: { id: cuenta.cliente?.id || '' } };
    this.selectedClienteId = cuenta.cliente?.id;
    this.editingId = cuenta.id;
  }

  handleDelete(id: number) {
    if (confirm('Are you sure?')) {
      this.loading.show();
      this.api.deleteCuenta(id).subscribe({
        next: () => {
          this.loading.hide();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    }
  }

  resetForm() {
    this.form = { numeroCuenta: '', tipoCuenta: 'Ahorro', saldoInicial: 0, estado: true, cliente: { id: '' } };
    this.selectedClienteId = '';
    this.editingId = null;
  }
}
