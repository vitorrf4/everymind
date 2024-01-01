import { Component } from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {ProdutosService} from "../../services/produtos.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  formProduto! : FormGroup;

  constructor(private produtosService: ProdutosService,
              private router: Router) {
    this.formProduto = new FormGroup<any>({
      nome: new FormControl(null),
      descricao: new FormControl(null),
      preco: new FormControl(null),
    });
  }

  cadastrarProduto() {
    if (!this.formEstaValido()) return;

    const produto = this.formProduto.value;

    this.produtosService.cadastrar(produto).subscribe({
      next: ()  => {
        alert("Produto cadastrado com sucesso");
        this.router.navigateByUrl("lista").then();
      },
      error: err => console.log(err)
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
