import { Component } from '@angular/core';
import {ProdutosService} from "./services/produtos.service";
import {Produto} from "./models/produto";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'produtos';
  produtos : Produto[] = [];

  constructor(private produtosService: ProdutosService) {
    this.listar();
  }

  listar() {
    this.produtosService.listarTodos().subscribe(res => {
      console.log(res);
      this.produtos = res;
    });

  }


}
