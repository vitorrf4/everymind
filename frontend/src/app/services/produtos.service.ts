import { Injectable } from '@angular/core';
import {environment} from "../../enviroments/enviroment";
import {HttpClient} from "@angular/common/http";
import {Produto} from "../models/produto";

@Injectable({
  providedIn: 'root'
})
export class ProdutosService {
  apiURL = environment.apiURL;

  constructor(private http: HttpClient) { }

  listarTodos() {
    return this.http.get<Produto[]>(`${this.apiURL}/produtos`);
  }

  listarPorId(id: string) {
    return this.http.get<Produto>(`${this.apiURL}/produtos/${id}`);
  }

  cadastrar(produto: Produto) {
    return this.http.post(`${this.apiURL}/produtos`, produto);
  }

  atualizar(produto: Produto) {
    return this.http.put(`${this.apiURL}/produtos`, produto);
  }

  deletar(id: string) {
    return this.http.delete(`${this.apiURL}/produtos/${id}`);
  }
}
