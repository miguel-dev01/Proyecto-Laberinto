
--USE maze; 

CREATE TABLE `user` (
  `id` int unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `password` varchar(500) NOT NULL,
  `name` varchar(300) NOT NULL,
  `nif` varchar(20) NOT NULL,
  `email` varchar(300) NOT NULL,
  `address` varchar(500) DEFAULT NULL,
  `birthdate` date DEFAULT NULL,
  `role` varchar(50) DEFAULT 'user',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  UNIQUE KEY `nif` (`nif`),
  UNIQUE KEY `email` (`email`)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci;
