package com.produtos.models;

import org.springframework.stereotype.Component;

@Component
public class ValidaProduto {
    public boolean camposSaoInvalidos(Produto prod) {
        return prod == null || prod.getNome() == null || prod.getNome().isBlank() ||
                prod.getDescricao() == null || prod.getDescricao().isBlank() ||
                prod.getPreco() == null || prod.getPreco() < 0 || prod.getPreco() > Long.MAX_VALUE;
    }
}
