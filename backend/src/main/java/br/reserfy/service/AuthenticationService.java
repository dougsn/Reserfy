package br.reserfy.service;

import br.reserfy.controller.UserController;
import br.reserfy.domain.dto.auth.AuthenticationRequest;
import br.reserfy.domain.dto.auth.AuthenticationResponse;
import br.reserfy.domain.dto.auth.RegisterRequest;
import br.reserfy.domain.dto.user.UserInfoDTO;
import br.reserfy.domain.dto.user.UserInfoDTOMapper;
import br.reserfy.domain.entity.User;
import br.reserfy.infra.jwt.JwtService;
import br.reserfy.repository.IUserRepository;
import br.reserfy.service.exceptions.AccessDeniedGenericException;
import br.reserfy.service.exceptions.DataIntegratyViolationException;
import br.reserfy.service.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class AuthenticationService {
    @Autowired
    private IUserRepository repository;
    @Autowired
    private UserInfoDTOMapper userInfoDTOMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticationResponse register(RegisterRequest request) {
        userAlreadyRegistered(request);
        Optional<User> userEntity = repository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

        userEntity.ifPresent(user -> user.getPermissions().forEach(u -> request.getPermissions().forEach(r -> {
            if (!u.getDescription().equals("ADMIN") && r.getId() == 1) {
                throw new AccessDeniedGenericException("Você não possui permissão para cadastrar um usuário com essa permissão.");
            }
        })));

        var user = new User(null, request.getFirstname(), request.getLastname(), request.getEmail(), passwordEncoder.encode(request.getPassword()),
                request.getPermissions());


        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);
    }

    @Transactional
    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        var user = repository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Credenciais incorretas."));

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );


        var jwtToken = jwtService.generateToken(user);
        return new AuthenticationResponse(jwtToken);

    }

    @Transactional(readOnly = true)
    public UserInfoDTO infoUser() {
        User userEntity = repository.findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new ObjectNotFoundException("Usuário não encontrado."));

        return userInfoDTOMapper.apply(userEntity)
                .add(linkTo(methodOn(UserController.class).findById(userEntity.getId())).withSelfRel());
    }


    @Transactional(readOnly = true)
    public void userAlreadyRegistered(RegisterRequest data) {
        Optional<User> userLogin = repository.findByEmail(data.getEmail());

        if (userLogin.isPresent())
            throw new DataIntegratyViolationException("Usuário já registrado.");
    }


}