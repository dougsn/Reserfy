CREATE TABLE IF NOT EXISTS `users` (
  `id` char(36) PRIMARY KEY NOT NULL,
  `firstname` varchar(50) NOT NULL,
  `lastname` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL UNIQUE,
  `password` varchar(100) NOT NULL
);