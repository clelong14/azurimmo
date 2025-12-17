-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : mer. 17 déc. 2025 à 09:59
-- Version du serveur : 11.5.2-MariaDB
-- Version de PHP : 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `azurimmo`
--

-- --------------------------------------------------------

--
-- Structure de la table `appartement`
--

DROP TABLE IF EXISTS `appartement`;
CREATE TABLE IF NOT EXISTS `appartement` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) DEFAULT NULL,
  `nb_pieces` int(11) DEFAULT NULL,
  `numero` int(11) DEFAULT NULL,
  `surface` float DEFAULT NULL,
  `batiment_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK5kn64l7l2m25kkryy4n50ikyb` (`batiment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `appartement`
--

INSERT INTO `appartement` (`id`, `description`, `nb_pieces`, `numero`, `surface`, `batiment_id`) VALUES
(1, 'Studio lumineux avec balcon', 2, 101, 45.5, 1),
(2, 'T3 rénové avec cuisine équipée', 3, 102, 65, 1),
(3, 'T4 avec vue dégagée', 4, 103, 85.5, 1),
(4, 'T2 avec parking', 2, 201, 50, 2),
(5, 'Grand T3 avec terrasse', 3, 202, 72.5, 2),
(6, 'Studio étudiant', 1, 301, 38, 3),
(7, 'T4 familial avec cave', 4, 302, 95, 3),
(8, 'T2 moderne', 2, 401, 55.5, 4),
(9, 'T5 de standing', 5, 501, 120, 5);

-- --------------------------------------------------------

--
-- Structure de la table `batiment`
--

DROP TABLE IF EXISTS `batiment`;
CREATE TABLE IF NOT EXISTS `batiment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adresse` varchar(255) DEFAULT NULL,
  `ville` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `batiment`
--

INSERT INTO `batiment` (`id`, `adresse`, `ville`) VALUES
(1, '15 Rue de la Paix', 'Paris'),
(2, '28 Avenue des Champs', 'Lyon'),
(3, '42 Boulevard Victor Hugo', 'Marseille'),
(4, '7 Rue du Commerce', 'Toulouse'),
(5, '33 Avenue de la République', 'Nice');

-- --------------------------------------------------------

--
-- Structure de la table `contrat`
--

DROP TABLE IF EXISTS `contrat`;
CREATE TABLE IF NOT EXISTS `contrat` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_debut` datetime(6) DEFAULT NULL,
  `date_fin` datetime(6) DEFAULT NULL,
  `montant_brut` double DEFAULT NULL,
  `montant_charges` double DEFAULT NULL,
  `appartement_id` bigint(20) DEFAULT NULL,
  `locataire_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK1ahw6ibj07960n52ulfdoc96g` (`appartement_id`),
  KEY `FKp7hols6tlsatgb3ve32kbn5sv` (`locataire_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `contrat`
--

INSERT INTO `contrat` (`id`, `date_debut`, `date_fin`, `montant_brut`, `montant_charges`, `appartement_id`, `locataire_id`) VALUES
(1, '2023-01-01 00:00:00.000000', '2024-12-31 00:00:00.000000', 850, 80, 1, 1),
(2, '2023-03-15 00:00:00.000000', '2025-03-14 00:00:00.000000', 1200, 120, 2, 2),
(3, '2023-06-01 00:00:00.000000', '2024-05-31 00:00:00.000000', 1500, 150, 3, 3),
(4, '2023-02-01 00:00:00.000000', '2024-01-31 00:00:00.000000', 950, 90, 4, 4),
(5, '2023-09-01 00:00:00.000000', '2025-08-31 00:00:00.000000', 1350, 130, 5, 5),
(6, '2023-04-15 00:00:00.000000', '2024-04-14 00:00:00.000000', 700, 70, 6, 6),
(7, '2023-07-01 00:00:00.000000', '2025-06-30 00:00:00.000000', 1650, 160, 7, 7),
(8, '2023-05-01 00:00:00.000000', '2024-04-30 00:00:00.000000', 1050, 100, 8, 8),
(9, '2023-10-01 00:00:00.000000', '2025-09-30 00:00:00.000000', 2200, 200, 9, 9);

-- --------------------------------------------------------

--
-- Structure de la table `intervention`
--

DROP TABLE IF EXISTS `intervention`;
CREATE TABLE IF NOT EXISTS `intervention` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_prevue` datetime(6) DEFAULT NULL,
  `date_realisee` datetime(6) DEFAULT NULL,
  `description` varchar(255) DEFAULT NULL,
  `type` varchar(255) DEFAULT NULL,
  `appartement_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf6nntanyntpovh90a0u2hbj9v` (`appartement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `intervention`
--

INSERT INTO `intervention` (`id`, `date_prevue`, `date_realisee`, `description`, `type`, `appartement_id`) VALUES
(1, '2023-02-15 00:00:00.000000', '2023-02-15 00:00:00.000000', 'Réparation fuite robinet cuisine', 'Plomberie', 1),
(2, '2023-03-20 00:00:00.000000', '2023-03-22 00:00:00.000000', 'Remplacement tableau électrique', 'Électricité', 2),
(3, '2023-11-01 00:00:00.000000', NULL, 'Entretien chaudière', 'Chauffage', 3),
(4, '2023-04-10 00:00:00.000000', '2023-04-12 00:00:00.000000', 'Rafraîchissement salon', 'Peinture', 4),
(5, '2023-05-15 00:00:00.000000', '2023-05-16 00:00:00.000000', 'Débouchage canalisation', 'Plomberie', 5),
(6, '2023-06-20 00:00:00.000000', '2023-06-20 00:00:00.000000', 'Changement serrure porte entrée', 'Serrurerie', 6),
(7, '2023-07-05 00:00:00.000000', NULL, 'Remplacement double vitrage', 'Vitrerie', 7),
(8, '2023-08-10 00:00:00.000000', '2023-08-11 00:00:00.000000', 'Installation prise électrique', 'Électricité', 8),
(9, '2023-09-15 00:00:00.000000', '2023-09-15 00:00:00.000000', 'Réparation chasse d\'eau', 'Plomberie', 1),
(10, '2023-10-20 00:00:00.000000', NULL, 'Contrôle radiateurs', 'Chauffage', 2),
(11, '2023-11-05 00:00:00.000000', NULL, 'Vérification installation électrique', 'Électricité', 9),
(12, '2023-12-01 00:00:00.000000', NULL, 'Peinture chambre', 'Peinture', 3);

-- --------------------------------------------------------

--
-- Structure de la table `locataire`
--

DROP TABLE IF EXISTS `locataire`;
CREATE TABLE IF NOT EXISTS `locataire` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `adresse_mail` varchar(255) DEFAULT NULL,
  `date_naissance` datetime(6) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `locataire`
--

INSERT INTO `locataire` (`id`, `adresse_mail`, `date_naissance`, `nom`, `prenom`, `telephone`) VALUES
(1, 'jean.dupont@gmail.com', '1985-03-15 00:00:00.000000', 'Dupont', 'Jean', '0601020304'),
(2, 'sophie.martin@gmail.com', '1990-07-22 00:00:00.000000', 'Martin', 'Sophie', '0602030405'),
(3, 'pierre.bernard@gmail.com', '1988-11-30 00:00:00.000000', 'Bernard', 'Pierre', '0603040506'),
(4, 'marie.dubois@gmail.com', '1992-05-18 00:00:00.000000', 'Dubois', 'Marie', '0604050607'),
(5, 'lucas.leroy@gmail.com', '1987-09-25 00:00:00.000000', 'Leroy', 'Lucas', '0605060708'),
(6, 'emma.moreau@gmail.com', '1995-01-12 00:00:00.000000', 'Moreau', 'Emma', '0606070809'),
(7, 'thomas.simon@gmail.com', '1989-04-08 00:00:00.000000', 'Simon', 'Thomas', '0607080910'),
(8, 'camille.laurent@gmail.com', '1993-12-03 00:00:00.000000', 'Laurent', 'Camille', '0608091011'),
(9, 'hugo.lefevre@gmail.com', '1986-06-20 00:00:00.000000', 'Lefevre', 'Hugo', '0609101112'),
(10, 'lea.roux@gmail.com', '1991-02-14 00:00:00.000000', 'Roux', 'Léa', '0610111213');

-- --------------------------------------------------------

--
-- Structure de la table `loyer`
--

DROP TABLE IF EXISTS `loyer`;
CREATE TABLE IF NOT EXISTS `loyer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `date_paiement` datetime(6) DEFAULT NULL,
  `montant_paye` double DEFAULT NULL,
  `contrat_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKonui4jewf3vd1plwsrj93ryvp` (`contrat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `loyer`
--

INSERT INTO `loyer` (`id`, `date_paiement`, `montant_paye`, `contrat_id`) VALUES
(1, '2023-01-05 00:00:00.000000', 930, 1),
(2, '2023-02-05 00:00:00.000000', 930, 1),
(3, '2023-03-05 00:00:00.000000', 930, 1),
(4, '2023-04-05 00:00:00.000000', 930, 1),
(5, '2023-03-20 00:00:00.000000', 1320, 2),
(6, '2023-04-20 00:00:00.000000', 1320, 2),
(7, '2023-05-20 00:00:00.000000', 1320, 2),
(8, '2023-06-05 00:00:00.000000', 1650, 3),
(9, '2023-07-05 00:00:00.000000', 1650, 3),
(10, '2023-08-05 00:00:00.000000', 1650, 3),
(11, '2023-02-05 00:00:00.000000', 1040, 4),
(12, '2023-03-05 00:00:00.000000', 1040, 4),
(13, '2023-09-05 00:00:00.000000', 1480, 5),
(14, '2023-10-05 00:00:00.000000', 1480, 5),
(15, '2023-04-20 00:00:00.000000', 770, 6),
(16, '2023-05-20 00:00:00.000000', 770, 6),
(17, '2023-07-05 00:00:00.000000', 1810, 7),
(18, '2023-08-05 00:00:00.000000', 1810, 7),
(19, '2023-05-05 00:00:00.000000', 1150, 8),
(20, '2023-06-05 00:00:00.000000', 1150, 8),
(21, '2023-10-05 00:00:00.000000', 2400, 9),
(22, '2023-11-05 00:00:00.000000', 2400, 9);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `appartement`
--
ALTER TABLE `appartement`
  ADD CONSTRAINT `FK5kn64l7l2m25kkryy4n50ikyb` FOREIGN KEY (`batiment_id`) REFERENCES `batiment` (`id`);

--
-- Contraintes pour la table `contrat`
--
ALTER TABLE `contrat`
  ADD CONSTRAINT `FK1ahw6ibj07960n52ulfdoc96g` FOREIGN KEY (`appartement_id`) REFERENCES `appartement` (`id`),
  ADD CONSTRAINT `FKp7hols6tlsatgb3ve32kbn5sv` FOREIGN KEY (`locataire_id`) REFERENCES `locataire` (`id`);

--
-- Contraintes pour la table `intervention`
--
ALTER TABLE `intervention`
  ADD CONSTRAINT `FKf6nntanyntpovh90a0u2hbj9v` FOREIGN KEY (`appartement_id`) REFERENCES `appartement` (`id`);

--
-- Contraintes pour la table `loyer`
--
ALTER TABLE `loyer`
  ADD CONSTRAINT `FKonui4jewf3vd1plwsrj93ryvp` FOREIGN KEY (`contrat_id`) REFERENCES `contrat` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
