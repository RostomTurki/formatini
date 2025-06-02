
CREATE TABLE `formations` (
  `idformations` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(45) DEFAULT NULL,
  `description` varchar(45) DEFAULT NULL,
  `prix` double DEFAULT NULL,
  `formateur` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idformations`),
  KEY `formateur_idx` (`formateur`),
  CONSTRAINT `formateur` FOREIGN KEY (`formateur`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `inscriptions` (
  `idformations` int DEFAULT NULL,
  `iduser` varchar(255) DEFAULT NULL,
  KEY `idformations_idx` (`idformations`),
  KEY `iduser_idx` (`iduser`),
  CONSTRAINT `idformations` FOREIGN KEY (`idformations`) REFERENCES `formations` (`idformations`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `iduser` FOREIGN KEY (`iduser`) REFERENCES `user` (`email`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `user` (
  `username` varchar(45) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(32) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`email`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
