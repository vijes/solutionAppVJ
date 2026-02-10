import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common'; // ensured
import { FormsModule } from '@angular/forms';
import { ApiService } from '../../services/api.service';
import { LoadingService } from '../../services/loading.service';

@Component({
  selector: 'app-clientes',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './clientes.component.html',
  styleUrl: './clientes.component.css'
})
export class ClientesComponent implements OnInit {
  clientes: any[] = [];
  filteredClientes: any[] = [];
  form: any = {
    nombre: '', genero: 'Masculino', edad: 0, identificacion: '',
    direccion: '', telefono: '', contrasena: '', estado: true
  };
  editingId: number | null = null;
  searchTerm: string = '';
  showModal: boolean = false;

  constructor(private api: ApiService, private loading: LoadingService) { }

  ngOnInit() {
    this.loadData();
  }

  loadData() {
    this.api.getClientes().subscribe({
      next: (data: any[]) => {
        this.clientes = data;
        this.filterClientes();
      },
      error: (e: any) => console.error(e)
    });
  }

  filterClientes() {
    this.filteredClientes = this.clientes.filter(c =>
      c.nombre.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  onSearchChange() {
    this.filterClientes();
  }

  openModal() {
    this.resetForm();
    this.showModal = true;
    this.editingId = null;
  }

  closeModal() {
    this.showModal = false;
    this.resetForm();
  }

  handleSubmit() {
    this.loading.show();
    if (this.editingId) {
      this.api.updateCliente(this.editingId, this.form).subscribe({
        next: () => {
          this.loading.hide();
          this.closeModal();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    } else {
      this.api.createCliente(this.form).subscribe({
        next: () => {
          this.loading.hide();
          this.closeModal();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    }
  }

  handleEdit(cliente: any) {
    this.form = { ...cliente };
    // Password usually not sent back, so keep empty or handle appropriately. 
    // Backend triggers update even if password null? Let's assume we send what we have.
    this.editingId = cliente.id;
    this.showModal = true;
  }

  handleDelete(id: number) {
    if (confirm('Are you sure?')) {
      this.loading.show();
      this.api.deleteCliente(id).subscribe({
        next: () => {
          this.loading.hide();
          this.loadData();
        },
        error: () => this.loading.hide()
      });
    }
  }

  resetForm() {
    this.form = { nombre: '', genero: 'Masculino', edad: 0, identificacion: '', direccion: '', telefono: '', contrasena: '', estado: true };
    this.editingId = null;
  }
}
