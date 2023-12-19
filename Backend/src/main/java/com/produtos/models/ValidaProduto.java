package com.produtos.models;

public class ValidaProduto {
    public static boolean camposSaoInvalidos(Produto prod) {
        return prod.getNome() == null || prod.getNome().isBlank() ||
                prod.getDescricao() == null || prod.getDescricao().isBlank() ||
                prod.getPreco() == null || prod.getPreco() < 0 || prod.getPreco() > Long.MAX_VALUE;
    }
}
