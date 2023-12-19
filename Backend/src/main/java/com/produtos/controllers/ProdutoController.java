package com.produtos.controllers;

import com.produtos.models.Produto;
import com.produtos.models.ValidaProduto;
import com.produtos.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/produtos")
public class ProdutoController {
    private final ProdutoRepository repository;
    private final ValidaProduto validaProduto;

    @Autowired
    public ProdutoController(ProdutoRepository repository, ValidaProduto validaProduto) {
        this.repository = repository;
        this.validaProduto = validaProduto;
    }

    @GetMapping
    public ResponseEntity<List<Produto>> getProdutos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProduto(@PathVariable Long id) {
        var produto = repository.findById(id);

        return produto.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        if (validaProduto.camposSaoInvalidos(produto))
            return ResponseEntity.badRequest().build();

        Produto savedProduto = repository.save(produto);

        URI produtoLocation = URI.create("produtos/" + savedProduto.getCodigo());

        return ResponseEntity.created(produtoLocation).body(savedProduto);
    }

    @PutMapping
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) {
        if (validaProduto.camposSaoInvalidos(produto) || produto.getCodigo() == null )
            return ResponseEntity.badRequest().build();

        if (!repository.existsById(produto.getCodigo()))
            return ResponseEntity.notFound().build();

        repository.save(produto);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduto(@PathVariable Long id) {
        var produtoDb = repository.findById(id);
        if (produtoDb.isEmpty())
            return ResponseEntity.notFound().build();

        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
