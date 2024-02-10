package br.reserfy.service;

import br.reserfy.controller.CidadeController;
import br.reserfy.domain.dto.cidade.CidadeDTO;
import br.reserfy.domain.dto.cidade.CidadeDTOMapper;
import br.reserfy.domain.dto.cidade.CidadeUpdateDTO;
import br.reserfy.domain.dto.cidade.CidadeUpdateDTOMapper;
import br.reserfy.domain.entity.Cidade;
import br.reserfy.repository.ICidadeRepository;
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

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class CidadeService {
    private final Logger logger = Logger.getLogger(CidadeService.class.getName());
    @Autowired
    private CidadeDTOMapper mapper;
    @Autowired
    private CidadeUpdateDTOMapper updateMapper;
    @Autowired
    private ICidadeRepository repository;

    @Autowired
    PagedResourcesAssembler<CidadeDTO> assembler;


    @Transactional(readOnly = true)
    public PagedModel<EntityModel<CidadeDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todas as cidades");

        var cidades = repository.findAll(pageable);

        var dtoList = cidades.map(u -> mapper.apply(u));
        dtoList.forEach(u -> u.add(linkTo(methodOn(CidadeController.class).findById(u.getId())).withSelfRel()));

        Link link = linkTo(methodOn(CidadeController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public CidadeDTO findById(String id) {
        logger.info("Buscando cidade de id: " + id);
        var cidade = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Cidade de id: " + id + " não encontrada"));
        cidade.add(linkTo(methodOn(CidadeController.class).findById(id)).withSelfRel());

        return cidade;
    }

    @Transactional
    public CidadeDTO add(CidadeDTO data) {
        logger.info("Adicionando uma nova cidade");
        cidadeAlreadyRegistered(data.getNome());

        Cidade cidade = repository.save(new Cidade(null, data.getNome(), data.getPais(),
                data.getEstado()));

        return mapper.apply(cidade)
                .add(linkTo(methodOn(CidadeController.class).findById(cidade.getId())).withSelfRel());
    }

    @Transactional
    public CidadeUpdateDTO update(CidadeUpdateDTO data) {
        logger.info("Atualizando cidade de id: " + data.getId());
        cidadeAlreadyRegistered(data.getNome());

        Cidade updatedCidade = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Cidade de id: " + data.getId() + " não encontrada."));

        updatedCidade.setNome(data.getNome());
        updatedCidade.setPais(data.getPais());
        updatedCidade.setEstado(data.getEstado());

        return updateMapper.apply(repository.save(updatedCidade))
                .add(linkTo(methodOn(CidadeController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando cidade");
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("Cidade de id: " + id + " não encontrada.");
    }

    @Transactional(readOnly = true)
    public void cidadeAlreadyRegistered(String nome) {
        if (repository.findByNome(nome).isPresent()) {
            logger.info("A cidade com o nome: " + nome + " já existe!");
            throw new DataIntegratyViolationException("A cidade com o nome: " + nome + " já existe!");
        }
    }


}