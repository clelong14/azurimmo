-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3307
-- Généré le : mer. 10 déc. 2025 à 10:55
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
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `appartement`
--

INSERT INTO `appartement` (`id`, `description`, `nb_pieces`, `numero`, `surface`, `batiment_id`) VALUES
(1, 'Studio cosy avec balcon', 1, 101, 28.5, 1),
(2, 'Appartement 2 pièces vue mer', 2, 102, 45, 1),
(3, 'Grand T3 lumineux', 3, 201, 67.3, 2),
(4, 'Duplex moderne', 4, 301, 82.1, 2),
(5, 'Petit appartement rénové', 1, 5, 22, 3),
(6, 'Appartement lumineux avec balcon, séjour, 2 chambres et cuisine équipée.', 3, 210, 72.6, 2),
(7, 'Studio étudiant', 2, 120, 30, 1),
(8, 'Appartement lumineux, cuisine ouverte, grand séjour', 3, 200, 50, 6),
(9, 'Appartement étudaint, lumineux', 3, 103, 75.5, 5),
(10, 'Appartement lumineux', 3, 110, 75.5, 1);

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
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Déchargement des données de la table `batiment`
--

INSERT INTO `batiment` (`id`, `adresse`, `ville`) VALUES
(1, '12 Rue des Palmiers', 'Nice'),
(2, '45 Avenue du Soleil', 'Cannes'),
(3, '3 Impasse des Oliviers', 'Antibes'),
(4, '10 Rue de la Saline', 'Cherbourg'),
(5, '4 Avenue Jean-François Millet', 'Cherbourg'),
(6, '27 Quai Alexandre III', 'Cherbourg');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `appartement`
--
ALTER TABLE `appartement`
  ADD CONSTRAINT `FK5kn64l7l2m25kkryy4n50ikyb` FOREIGN KEY (`batiment_id`) REFERENCES `batiment` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
