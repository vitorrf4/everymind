import { Component } from '@angular/core';
import {Produto} from "../../models/produto";
import {ProdutosService} from "../../services/produtos.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-lista',
  templateUrl: './lista.component.html',
  styleUrls: ['./lista.component.css']
})
export class ListaComponent {
  produtos : Produto[] = [];

  constructor(private produtosService: ProdutosService,
              private router: Router) {
    this.produtosService.listarTodos().subscribe(res => {
      this.produtos = res;
      this.ordernarProdutosPorMaisRecente();
    });
  }

  ordernarProdutosPorMaisRecente() {
    this.produtos.sort((a, b) => b.codigo - a.codigo);
  }

  async atualizarProduto(produto: Produto) {
    await this.router.navigate(["/cadastro"], {state: produto});
  }

  deletarProduto(id: number, index: number) {
    this.produtosService.deletar(id).subscribe({
      next: () => {
        alert(`Produto ${id} deletado`);
        this.produtos.splice(index, 1);
      },
      error: err => {
        alert("Falha no sistema, tente novamente mais tarde");
        console.log(err);
      }
    })
  }

}
