package br.reserfy.unittests.mocks;

import br.reserfy.domain.dto.categoria.CategoriaDTO;
import br.reserfy.domain.dto.categoria.CategoriaUpdateDTO;
import br.reserfy.domain.entity.Categoria;

public class MockCategoria {
    public Categoria mockEntity(String id) {
        Categoria categoria = new Categoria();
        categoria.setId(id);
        categoria.setQualificacao("Teste");
        categoria.setDescricao("Teste");
        categoria.setUrlImagem("https://teste.com");
        return categoria;
    }

    public CategoriaDTO mockDTO(String id) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(id);
        dto.setQualificacao("Teste");
        dto.setDescricao("Teste");
        dto.setUrlImagem("https://teste.com");
        return dto;
    }

    public CategoriaUpdateDTO mockDTOUpdated(String id) {
        CategoriaUpdateDTO dto = new CategoriaUpdateDTO();
        dto.setId(id);
        dto.setQualificacao("Teste");
        dto.setDescricao("Teste");
        dto.setUrlImagem("https://teste.com");
        return dto;
    }
}
