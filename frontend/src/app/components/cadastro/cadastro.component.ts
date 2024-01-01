import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ProdutosService} from "../../services/produtos.service";
import {Router} from "@angular/router";
import {Produto} from "../../models/produto";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  formProduto! : FormGroup;
  id : number = 0;

  constructor(private produtosService: ProdutosService,
              private router: Router) {
    // verifica se um id foi mandado junto com a pagina
    if (history.state.codigo) {
      this.id = history.state.codigo;
    }

    this.formProduto = new FormGroup<any>({
      nome: new FormControl(history.state.nome || null),
      descricao: new FormControl(history.state.descricao || null),
      preco: new FormControl(history.state.preco || null),
    });
  }

  atualizarProduto(produto : Produto) {
    produto.codigo = this.id;

    this.produtosService.atualizar(produto).subscribe({
      next: ()  => {
        alert("Produto atualizado com sucesso");
        this.router.navigateByUrl("lista").then();
      },
      error: err => {
        alert("Falha no sistema, tente novamente mais tarde");
        console.log(err);}
    });
  }

  cadastrarProduto() {
    if (!this.formEstaValido()) {
      return;
    }

    const produto = this.formProduto.value;

    if (this.id) {
      this.atualizarProduto(produto);
      return;
    }

    this.produtosService.cadastrar(produto).subscribe({
      next: ()  => {
        alert("Produto cadastrado com sucesso");
        this.router.navigateByUrl("lista").then();
      },
      error: err => {
        alert("Falha no sistema, tente novamente mais tarde");
        console.log(err);
      }
    });
  }

  formEstaValido() : boolean {
    if (!this.formProduto.get("nome")?.value ||
      !this.formProduto.get("descricao")?.value ||
      (!this.formProduto.get("preco")?.value && this.formProduto.get("preco")?.value != 0)
    ) {
      alert("Todos os campos devem ser preenchidos");
      return false;
    }

    if (this.formProduto.get("preco")?.value < 0) {
      alert("Preço inválido");
      return false;
    }

    return true;
  }
}
