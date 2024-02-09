package br.reserfy.service;

import br.reserfy.controller.CategoriaController;
import br.reserfy.domain.dto.categoria.CategoriaDTO;
import br.reserfy.domain.dto.categoria.CategoriaDTOMapper;
import br.reserfy.domain.dto.categoria.CategoriaUpdateDTO;
import br.reserfy.domain.dto.categoria.CategoriaUpdateDTOMapper;
import br.reserfy.domain.dto.user.UserUpdateDTO;
import br.reserfy.domain.entity.Categoria;
import br.reserfy.domain.entity.User;
import br.reserfy.repository.ICategoriaRepository;
import br.reserfy.service.exceptions.DataIntegratyViolationException;
import br.reserfy.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CategoriaService {
    private final Logger logger = Logger.getLogger(CategoriaService.class.getName());
    @Autowired
    private CategoriaDTOMapper mapper;
    @Autowired
    private CategoriaUpdateDTOMapper updateMapper;
    @Autowired
    private ICategoriaRepository repository;

    @Autowired
    PagedResourcesAssembler<CategoriaDTO> assembler;


    @Transactional(readOnly = true)
    public PagedModel<EntityModel<CategoriaDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todas as categorias");

        var users = repository.findAll(pageable);

        var dtoList = users.map(u -> mapper.apply(u));
        dtoList.forEach(u -> u.add(linkTo(methodOn(CategoriaController.class).findById(u.getId())).withSelfRel()));

        Link link = linkTo(methodOn(CategoriaController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public CategoriaDTO findById(String id) {
        logger.info("Buscando categoria de id: " + id);
        var categoria = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Categoria de id: " + id + " não encontrado"));
        categoria.add(linkTo(methodOn(CategoriaController.class).findById(id)).withSelfRel());

        return categoria;
    }

    @Transactional
    public CategoriaDTO add(CategoriaDTO data) {
        logger.info("Adicionando uma nova categoria");
        qualificacaoAlreadyRegistered(data.getQualificacao());

        Categoria categoria = repository.save(new Categoria(null, data.getQualificacao(), data.getDescricao(),
                data.getUrlImagem()));

        return mapper.apply(categoria)
                .add(linkTo(methodOn(CategoriaController.class).findById(categoria.getId())).withSelfRel());
    }

    @Transactional
    public CategoriaUpdateDTO update(CategoriaUpdateDTO data) {
        logger.info("Atualizando categoria de id: " + data.getId());
        qualificacaoAlreadyRegistered(data.getQualificacao());

        Categoria updatedCategoria = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Categoria de id: " + data.getId() + " não encontrada."));

        updatedCategoria.setQualificacao(data.getQualificacao());
        updatedCategoria.setDescricao(data.getDescricao());
        updatedCategoria.setUrlImagem(data.getUrlImagem());

        return updateMapper.apply(repository.save(updatedCategoria))
                .add(linkTo(methodOn(CategoriaController.class).findById(data.getId())).withSelfRel());
    }
    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando categoria");
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Categoria de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void qualificacaoAlreadyRegistered(String qualificacao) {
        if (repository.findByQualificacao(qualificacao).isPresent()) {
            logger.info("A categoria com a qualificação: " + qualificacao + " já existe!");
            throw new DataIntegratyViolationException("A categoria com a qualificação: " + qualificacao + " já existe!");
        }
    }


}