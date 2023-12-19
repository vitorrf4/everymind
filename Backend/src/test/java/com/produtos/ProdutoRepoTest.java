package com.produtos;

import com.produtos.models.Produto;
import com.produtos.repositories.ProdutoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class ProdutoRepoTest {
    @Autowired
    private ProdutoRepository produtoRepository;

    @Test
    @DisplayName("Save Produto")
    public void whenSaveProduto_thenReturnSavedEntity() {
        Produto produto = new Produto("Teste", "Descrição de teste", 10.0);

        Produto savedProduto = produtoRepository.save(produto);

        assertEquals(savedProduto, produto);
        assertEquals(produtoRepository.count(), 1);
    }

    @Test
    @DisplayName("Find Produto")
    public void whenFindProdutoById_thenReturnProduto() {
        Produto produto = new Produto("Teste", "Descrição de teste", 10.0);
        Produto savedProduto = produtoRepository.save(produto);

        Optional<Produto> foundProduto = produtoRepository.findById(savedProduto.getCodigo());

        assertTrue(foundProduto.isPresent());
    }

    @Test
    @DisplayName("Update Produto")
    public void whenUpdateProduto_thenReturnChangedProduto() {
        Produto produto = new Produto("Teste", "Descrição de teste", 10.0);
        Produto savedProduto = produtoRepository.save(produto);

        savedProduto.setNome("Novo Nome");
        savedProduto.setDescricao("Nova Descrição");
        savedProduto.setPreco(20.0);

        Produto updatedProduto = produtoRepository.save(savedProduto);

        assertEquals(savedProduto.getCodigo(), updatedProduto.getCodigo());
        assertEquals(savedProduto.getNome(), updatedProduto.getNome());
        assertEquals(savedProduto.getDescricao(), updatedProduto.getDescricao());
        assertEquals(savedProduto.getPreco(), updatedProduto.getPreco());
    }

    @Test
    @DisplayName("Delete Produto")
    public void whenDeleteProduto_thenReturnEmptyQuery() {
        Produto produto = new Produto("Teste", "Descrição de teste", 10.0);
        Produto savedProduto = produtoRepository.save(produto);

        produtoRepository.deleteById(savedProduto.getCodigo());

        Optional<Produto> deletedProduto = produtoRepository.findById(savedProduto.getCodigo());

        assertTrue(deletedProduto.isEmpty());
    }

    @AfterEach
    public void databaseCleanup() {
        produtoRepository.deleteAll();
    }
}
