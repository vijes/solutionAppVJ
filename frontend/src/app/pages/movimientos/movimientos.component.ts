import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-movimientos',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './movimientos.component.html',
  styleUrl: './movimientos.component.css'
})
export class MovimientosComponent implements OnInit {
  movimientos: any[] = [];
  cuentas: any[] = [];
  form: any = {
    tipoMovimiento: 'Deposito', valor: 0, cuenta: { id: '' }
  };
  // Temporary for amount input (always positive visually)
  amountInput: number = 0;
  selectedCuentaId: string = '';

  constructor(private api: ApiService, private loading: LoadingService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.api.getMovimientos().subscribe(data => this.movimientos = data.reverse()); // Show newest first
    this.api.getCuentas().subscribe(data => this.cuentas = data);
  }

  handleSubmit() {
    // Calculate actual value based on type
    let finalValue = this.amountInput;
    if (this.form.tipoMovimiento === 'Retiro') {
      finalValue = -Math.abs(this.amountInput);
    } else {
      finalValue = Math.abs(this.amountInput);
    }

    this.form.valor = finalValue;
    this.form.cuenta.id = this.selectedCuentaId;

    this.loading.show();
    this.api.createMovimiento(this.form).subscribe({
      next: () => {
        this.loading.hide();
        this.resetForm();
        this.loadData();
      },
      error: (err) => {
        this.loading.hide();
        alert('Error: ' + (err.error?.message || err.message || 'Saldo no disponible o error del servidor'));
      }
    });
  }

  resetForm() {
    this.form = { tipoMovimiento: 'Deposito', valor: 0, cuenta: { id: '' } };
    this.amountInput = 0;
    this.selectedCuentaId = '';
  }
}
