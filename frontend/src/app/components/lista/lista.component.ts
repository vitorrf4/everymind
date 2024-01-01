import { Component } from '@angular/core';
import {Produto} from "../../models/produto";
import {ProdutosService} from "../../services/produtos.service";

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent {
  produtos : Produto[] = [];

  constructor(private produtosService: ProdutosService) {
    this.produtosService.listarTodos().subscribe(res => {
      console.log(res);
      this.produtos = res;
    });
  }
}
