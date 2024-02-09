package br.reserfy.infra.config;

//@Component
//public class CommandLineAppStartupRunner implements CommandLineRunner {
//
//    @Autowired
//    IUserRepository userRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @Override
//    public void run(String... args) throws Exception {
//        if (userRepository.findByRole(Role.ADMIN).isEmpty()) {
//            var user = User.builder()
//                    .name("Administrador")
//                    .email("administrador@extension.com")
//                    .password(passwordEncoder.encode("extension"))
//                    .role(Role.ADMIN)
//                    .build();
//            userRepository.save(user);
//        }
//    }
//}
