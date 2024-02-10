package br.reserfy.service;

import br.reserfy.controller.IconeController;
import br.reserfy.domain.dto.icone.IconeDTO;
import br.reserfy.domain.dto.icone.IconeDTOMapper;
import br.reserfy.domain.dto.icone.IconeUpdateDTO;
import br.reserfy.domain.dto.icone.IconeUpdateDTOMapper;
import br.reserfy.domain.entity.Icone;
import br.reserfy.repository.IIconeRepository;
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
public class IconeService {
    private final Logger logger = Logger.getLogger(IconeService.class.getName());
    @Autowired
    private IconeDTOMapper mapper;
    @Autowired
    private IconeUpdateDTOMapper updateMapper;
    @Autowired
    private IIconeRepository repository;

    @Autowired
    PagedResourcesAssembler<IconeDTO> assembler;


    @Transactional(readOnly = true)
    public PagedModel<EntityModel<IconeDTO>> findAll(Pageable pageable) {
        logger.info("Buscando todas as icones");

        var icones = repository.findAll(pageable);

        var dtoList = icones.map(u -> mapper.apply(u));
        dtoList.forEach(u -> u.add(linkTo(methodOn(IconeController.class).findById(u.getId())).withSelfRel()));

        Link link = linkTo(methodOn(IconeController.class)
                .findAll(pageable.getPageNumber(), pageable.getPageSize(), "asc")).withSelfRel();

        return assembler.toModel(dtoList, link);
    }

    @Transactional(readOnly = true)
    public IconeDTO findById(String id) {
        logger.info("Buscando icone de id: " + id);
        var icone = repository.findById(id)
                .map(mapper)
                .orElseThrow(() -> new ObjectNotFoundException("Icone de id: " + id + " não encontrado"));
        icone.add(linkTo(methodOn(IconeController.class).findById(id)).withSelfRel());

        return icone;
    }

    @Transactional
    public IconeDTO add(IconeDTO data) {
        logger.info("Adicionando um novo icone");
        iconeAlreadyRegistered(data.getNome());

        Icone icone = repository.save(new Icone(null, data.getNome(), data.getNomeIcone()));

        return mapper.apply(icone)
                .add(linkTo(methodOn(IconeController.class).findById(icone.getId())).withSelfRel());
    }

    @Transactional
    public IconeUpdateDTO update(IconeUpdateDTO data) {
        logger.info("Atualizando icone de id: " + data.getId());
        iconeAlreadyRegistered(data.getNome());

        Icone updatedicone = repository.findById(data.getId())
                .orElseThrow(() -> new ObjectNotFoundException("Icone de id: " + data.getId() + " não encontrado."));

        updatedicone.setNome(data.getNome());
        updatedicone.setNomeIcone(data.getNomeIcone());

        return updateMapper.apply(repository.save(updatedicone))
                .add(linkTo(methodOn(IconeController.class).findById(data.getId())).withSelfRel());
    }

    @Transactional
    public Boolean delete(String id) {
        logger.info("Deletando icone");
        if (repository.findById(id).isPresent()) {
            repository.deleteById(id);
            return true;
        }
        throw new ObjectNotFoundException("icone de id: " + id + " não encontrado.");
    }

    @Transactional(readOnly = true)
    public void iconeAlreadyRegistered(String nome) {
        if (repository.findByNome(nome).isPresent()) {
            logger.info("O icone com o nome: " + nome + " já existe!");
            throw new DataIntegratyViolationException("O icone com o nome: " + nome + " já existe!");
        }
    }


}