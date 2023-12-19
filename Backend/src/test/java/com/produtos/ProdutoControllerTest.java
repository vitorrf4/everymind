package com.produtos;

import com.produtos.controllers.ProdutoController;
import com.produtos.models.Produto;
import com.produtos.models.ValidaProduto;
import com.produtos.repositories.ProdutoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ProdutoControllerTest {
    @InjectMocks
    private ProdutoController produtoController;

    @Mock
    private ProdutoRepository produtoRepository;
    @Mock
    private ValidaProduto validaProduto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("GET /produtos - Ok")
    void whenGetProdutos_thenReturnArrayOfProdutos() {
        when(produtoRepository.findAll()).thenReturn(Arrays.asList(new Produto(), new Produto()));

        ResponseEntity<List<Produto>> response = produtoController.getProdutos();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, Objects.requireNonNull(response.getBody()).size());
    }

    @Test
    @DisplayName("GET /produtos/{id} - Ok")
    void whenGetProduto_thenReturnProduto() {
        Long produtoId = 1L;
        Produto produto = new Produto();
        ReflectionTestUtils.setField(produto, "codigo", produtoId);

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.of(produto));

        ResponseEntity<Produto> response = produtoController.getProduto(produtoId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(produtoId, Objects.requireNonNull(response.getBody()).getCodigo());
    }

    @Test
    @DisplayName("GET /produtos/{id} - Not Found")
    void whenGetProduto_givenNoProdutoWithSuchId_thenReturnNotFound() {
        Long produtoId = 1L;

        when(produtoRepository.findById(produtoId)).thenReturn(Optional.empty());

        ResponseEntity<Produto> response = produtoController.getProduto(produtoId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("POST /produtos - Created")
    void whenCreateProduto_thenReturnCreatedProduto() {
        Produto produtoToCreate = new Produto("Test Product", "Description", 10.0);
        ReflectionTestUtils.setField(produtoToCreate, "codigo", 1L);

        when(produtoRepository.save(any(Produto.class))).thenReturn(produtoToCreate);

        ResponseEntity<Produto> response = produtoController.createProduto(produtoToCreate);
        URI expectedLocation = URI.create("produtos/1");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(expectedLocation, response.getHeaders().getLocation());
        assertEquals(produtoToCreate, response.getBody());
    }

    @Test
    @DisplayName("POST /produtos - Bad Request")
    void whenCreateProduto_givenInvalidProduto_thenReturnBadRequest() {
        when(validaProduto.camposSaoInvalidos(isNull())).thenReturn(true);

        var response = produtoController.createProduto(null);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
