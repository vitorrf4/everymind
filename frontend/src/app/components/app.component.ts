import { Component } from '@angular/core';
import {ProdutosService} from "../services/produtos.service";
import {Produto} from "../models/produto";
import {Form, FormControl, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  produtos : Produto[] = [];
  formProduto! : FormGroup;

  constructor(private produtosService: ProdutosService) {
    this.formProduto = new FormGroup<any>({
      nome: new FormControl(null),
      descricao: new FormControl(null),
      preco: new FormControl(null),
    })

    this.produtosService.listarTodos().subscribe(res => {
      console.log(res);
      this.produtos = res;
    });
  }

  cadastrar() {
    const produto = this.formProduto.value;

    this.produtosService.cadastrar(produto).subscribe({
      next: res => {
        this.produtos.push(produto);
        console.log("Produto cadastrado")
      },
      error: err => console.log(err)
    });
  }
}
