CREATE TABLE IF NOT EXISTS `categorias` (
  `id` char(36) PRIMARY KEY NOT NULL,
  `qualificacao` varchar(100) NOT NULL,
  `descricao` longtext NOT NULL,
  `url_imagem` varchar(100) NOT NULL
);