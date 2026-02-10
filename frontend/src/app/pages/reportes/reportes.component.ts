import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-reportes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './reportes.component.html',
  styleUrl: './reportes.component.css'
})
export class ReportesComponent implements OnInit {
  clientes: any[] = [];
  reporteData: any[] = [];

  filters: any = {
    clienteId: '',
    fechaInicio: '',
    fechaFin: ''
  };

  constructor(private api: ApiService, private loading: LoadingService) { }

  ngOnInit() {
    this.api.getClientes().subscribe(data => this.clientes = data);
  }

  handleSearch() {
    if (!this.filters.clienteId || !this.filters.fechaInicio || !this.filters.fechaFin) {
      alert('Por favor complete todos los campos');
      return;
    }

    // Append time to dates to match backend expectation or ISO format
    const start = this.filters.fechaInicio + "T00:00:00";
    const end = this.filters.fechaFin + "T23:59:59";

    this.loading.show();
    this.api.getReporte(this.filters.clienteId, start, end).subscribe({
      next: (data) => {
        this.loading.hide();
        this.reporteData = data;
      },
      error: (e) => {
        this.loading.hide();
        console.error(e);
      }
    });
  }
}
