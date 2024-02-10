CREATE TABLE IF NOT EXISTS `cidades` (
  `id` char(36) PRIMARY KEY NOT NULL,
  `nome` varchar(70) NOT NULL,
  `pais` varchar(50) NOT NULL,
  `estado` varchar(50) NOT NULL
);