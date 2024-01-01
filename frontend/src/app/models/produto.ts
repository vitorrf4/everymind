export class Produto {
  private _codigo : number = 0;
  private _nome : string = "";
  private _descricao : string = "";
  private _preco : number = 0.0;

  get codigo(): number {
    return this._codigo;
  }

  set codigo(value: number) {
    this._codigo = value;
  }

  get nome(): string {
    return this._nome;
  }

  set nome(value: string) {
    this._nome = value;
  }

  get descricao(): string {
    return this._descricao;
  }

  set descricao(value: string) {
    this._descricao = value;
  }

  get preco(): number {
    return this._preco;
  }

  set preco(value: number) {
    this._preco = value;
  }
}
