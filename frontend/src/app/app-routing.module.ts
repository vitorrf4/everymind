import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {ListaComponent} from "./components/lista/lista.component";
import {CadastroComponent} from "./components/cadastro/cadastro.component";

const routes: Routes = [
  { path: "lista", component: ListaComponent },
  { path: "cadastro", component: CadastroComponent },
  { path: "**", redirectTo: "lista" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
