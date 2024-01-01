import { Component } from '@angular/core';
import {Produto} from "../../models/produto";
import {FormControl, FormGroup} from "@angular/forms";
import {ProdutosService} from "../../services/produtos.service";

@Component({
  selector: 'app-cadastro',
  templateUrl: './cadastro.component.html',
  styleUrls: ['./cadastro.component.css']
})
export class CadastroComponent {
  formProduto! : FormGroup;

  constructor(private produtosService: ProdutosService) {
    this.formProduto = new FormGroup<any>({
      nome: new FormControl(null),
      descricao: new FormControl(null),
      preco: new FormControl(null),
    })
  }

  cadastrar() {
    const produto = this.formProduto.value;

    this.produtosService.cadastrar(produto).subscribe({
      next: res => {
        console.log("Produto cadastrado")
      },
      error: err => console.log(err)
    });
  }
}
