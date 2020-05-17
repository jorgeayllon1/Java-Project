-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 17 mai 2020 à 18:49
-- Version du serveur :  5.7.26
-- Version de PHP :  7.2.18

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `projet_java_edt`
--

-- --------------------------------------------------------

--
-- Structure de la table `cours`
--

DROP TABLE IF EXISTS `cours`;
CREATE TABLE IF NOT EXISTS `cours` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `nom` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `cours`
--

INSERT INTO `cours` (`id`, `nom`) VALUES
(1, 'Java POO'),
(2, 'Traitement de Signal'),
(3, 'Probabilités et statistiques'),
(4, 'Thermodynamique'),
(5, 'C++ POO'),
(6, 'Systèmes bouclés'),
(7, 'Electromagnetisme'),
(8, 'Algebre lineaire');

-- --------------------------------------------------------

--
-- Structure de la table `enseignant`
--

DROP TABLE IF EXISTS `enseignant`;
CREATE TABLE IF NOT EXISTS `enseignant` (
  `id_utilisateur` int(100) NOT NULL,
  `id_cours` int(100) NOT NULL,
  KEY `id_cours` (`id_cours`),
  KEY `id_utilisateur` (`id_utilisateur`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `enseignant`
--

INSERT INTO `enseignant` (`id_utilisateur`, `id_cours`) VALUES
(3, 1),
(3, 5);

-- --------------------------------------------------------

--
-- Structure de la table `etudiant`
--

DROP TABLE IF EXISTS `etudiant`;
CREATE TABLE IF NOT EXISTS `etudiant` (
  `id_utilisateur` int(100) NOT NULL,
  `numero` mediumtext NOT NULL,
  `id_groupe` int(100) NOT NULL,
  KEY `id_utilisateur` (`id_utilisateur`),
  KEY `id_groupe` (`id_groupe`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `etudiant`
--

INSERT INTO `etudiant` (`id_utilisateur`, `numero`, `id_groupe`) VALUES
(4, '777', 1),
(5, '111', 1);

-- --------------------------------------------------------

--
-- Structure de la table `groupe`
--

DROP TABLE IF EXISTS `groupe`;
CREATE TABLE IF NOT EXISTS `groupe` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `nom` text NOT NULL,
  `id_promotion` int(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_promotion` (`id_promotion`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `groupe`
--

INSERT INTO `groupe` (`id`, `nom`, `id_promotion`) VALUES
(1, 'TD10', 1),
(2, 'TD11', 1),
(3, 'TD1', 2),
(4, 'TD2', 2),
(5, 'TD3', 3),
(6, 'TD4', 3);

-- --------------------------------------------------------

--
-- Structure de la table `promotion`
--

DROP TABLE IF EXISTS `promotion`;
CREATE TABLE IF NOT EXISTS `promotion` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `annee` year(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `promotion`
--

INSERT INTO `promotion` (`id`, `annee`) VALUES
(1, 2022),
(2, 2023),
(3, 2024);

-- --------------------------------------------------------

--
-- Structure de la table `salle`
--

DROP TABLE IF EXISTS `salle`;
CREATE TABLE IF NOT EXISTS `salle` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `nom` text NOT NULL,
  `capacite` bigint(20) NOT NULL,
  `id_site` int(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_site` (`id_site`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id`, `nom`, `capacite`, `id_site`) VALUES
(1, '445', 20, 2),
(2, '211', 20, 1);

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

DROP TABLE IF EXISTS `seance`;
CREATE TABLE IF NOT EXISTS `seance` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `semaine` int(50) NOT NULL,
  `date` date NOT NULL,
  `heure_debut` time NOT NULL,
  `heure_fin` time NOT NULL,
  `id_cours` int(100) NOT NULL,
  `id_type` int(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cours` (`id_cours`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `seance_enseignants`
--

DROP TABLE IF EXISTS `seance_enseignants`;
CREATE TABLE IF NOT EXISTS `seance_enseignants` (
  `id_seance` int(100) NOT NULL,
  `id_enseignant` int(100) NOT NULL,
  KEY `id_enseignant` (`id_enseignant`),
  KEY `id_seance` (`id_seance`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `seance_groupes`
--

DROP TABLE IF EXISTS `seance_groupes`;
CREATE TABLE IF NOT EXISTS `seance_groupes` (
  `id_seance` int(100) NOT NULL,
  `id_groupe` int(100) NOT NULL,
  KEY `id_groupe` (`id_groupe`),
  KEY `id_seance` (`id_seance`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `seance_salles`
--

DROP TABLE IF EXISTS `seance_salles`;
CREATE TABLE IF NOT EXISTS `seance_salles` (
  `id_seance` int(100) NOT NULL,
  `id_salle` int(100) NOT NULL,
  KEY `id_salle` (`id_salle`),
  KEY `id_seance` (`id_seance`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `site`
--

DROP TABLE IF EXISTS `site`;
CREATE TABLE IF NOT EXISTS `site` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `nom` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `site`
--

INSERT INTO `site` (`id`, `nom`) VALUES
(1, 'Eiffel 1'),
(2, 'Eiffel 2'),
(3, 'Eiffel 3'),
(4, 'Eiffel 4');

-- --------------------------------------------------------

--
-- Structure de la table `type_cours`
--

DROP TABLE IF EXISTS `type_cours`;
CREATE TABLE IF NOT EXISTS `type_cours` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `nom` text NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `type_cours`
--

INSERT INTO `type_cours` (`id`, `nom`) VALUES
(1, 'Cours interactif'),
(2, 'Cours magistral'),
(3, 'TD'),
(4, 'TP'),
(5, 'Projet'),
(6, 'Soutien');

-- --------------------------------------------------------

--
-- Structure de la table `utilisateur`
--

DROP TABLE IF EXISTS `utilisateur`;
CREATE TABLE IF NOT EXISTS `utilisateur` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL,
  `passwd` text NOT NULL,
  `nom` tinytext NOT NULL,
  `prenom` tinytext NOT NULL,
  `droit` tinyint(4) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `passwd`, `nom`, `prenom`, `droit`) VALUES
(1, 'admin@mail.com', 'adminmdp', 'admin_nom', 'admin_prenom', 1),
(2, 'referent@mail.com', 'referentmdp', 'referent_nom', 'referent_prenom', 2),
(3, 'profinfo@mail.com', 'profinfomdp', 'profinfo_nom', 'profinfo_prenom', 3),
(4, 'wangdavid@mail.com', 'wangdavid', 'wang', 'david', 4),
(5, 'ayllonjorge@mail.com', 'ayllonjorge', 'ayllon', 'jorge', 4),
(6, 'beaujoistheo@mail.com', 'beaujoistheo', 'beaujois', 'theo', 4);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `enseignant`
--
ALTER TABLE `enseignant`
  ADD CONSTRAINT `enseignant_ibfk_1` FOREIGN KEY (`id_cours`) REFERENCES `cours` (`id`),
  ADD CONSTRAINT `enseignant_ibfk_2` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`);

--
-- Contraintes pour la table `etudiant`
--
ALTER TABLE `etudiant`
  ADD CONSTRAINT `etudiant_ibfk_1` FOREIGN KEY (`id_utilisateur`) REFERENCES `utilisateur` (`id`),
  ADD CONSTRAINT `etudiant_ibfk_2` FOREIGN KEY (`id_groupe`) REFERENCES `groupe` (`id`);

--
-- Contraintes pour la table `groupe`
--
ALTER TABLE `groupe`
  ADD CONSTRAINT `groupe_ibfk_1` FOREIGN KEY (`id_promotion`) REFERENCES `promotion` (`id`);

--
-- Contraintes pour la table `salle`
--
ALTER TABLE `salle`
  ADD CONSTRAINT `salle_ibfk_1` FOREIGN KEY (`id_site`) REFERENCES `site` (`id`);

--
-- Contraintes pour la table `seance`
--
ALTER TABLE `seance`
  ADD CONSTRAINT `seance_ibfk_1` FOREIGN KEY (`id_cours`) REFERENCES `cours` (`id`),
  ADD CONSTRAINT `seance_ibfk_2` FOREIGN KEY (`id_type`) REFERENCES `type_cours` (`id`);

--
-- Contraintes pour la table `seance_enseignants`
--
ALTER TABLE `seance_enseignants`
  ADD CONSTRAINT `seance_enseignants_ibfk_1` FOREIGN KEY (`id_enseignant`) REFERENCES `enseignant` (`id_utilisateur`),
  ADD CONSTRAINT `seance_enseignants_ibfk_2` FOREIGN KEY (`id_seance`) REFERENCES `seance` (`id`);

--
-- Contraintes pour la table `seance_groupes`
--
ALTER TABLE `seance_groupes`
  ADD CONSTRAINT `seance_groupes_ibfk_1` FOREIGN KEY (`id_groupe`) REFERENCES `groupe` (`id`),
  ADD CONSTRAINT `seance_groupes_ibfk_2` FOREIGN KEY (`id_seance`) REFERENCES `seance` (`id`);

--
-- Contraintes pour la table `seance_salles`
--
ALTER TABLE `seance_salles`
  ADD CONSTRAINT `seance_salles_ibfk_1` FOREIGN KEY (`id_salle`) REFERENCES `salle` (`id`),
  ADD CONSTRAINT `seance_salles_ibfk_2` FOREIGN KEY (`id_seance`) REFERENCES `seance` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
