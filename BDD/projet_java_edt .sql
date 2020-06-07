-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le :  Dim 07 juin 2020 à 18:50
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

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
(8, 'Algebre lineaire'),
(9, 'C# '),
(10, 'Ondes electromagnetiques'),
(11, 'Initiation réseaux'),
(12, 'English 6');

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
(3, 5),
(9, 3),
(10, 6),
(13, 10),
(3, 11),
(14, 12);

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
(5, '111', 1),
(7, '123', 3),
(8, '321', 4),
(11, '222', 2),
(12, '1212', 2);

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
  `capacite` bigint(100) NOT NULL,
  `id_site` int(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_site` (`id_site`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `salle`
--

INSERT INTO `salle` (`id`, `nom`, `capacite`, `id_site`) VALUES
(1, '445', 100, 2),
(2, '211', 40, 1),
(3, '446', 100, 2),
(4, '324', 40, 3),
(5, '421', 100, 4);

-- --------------------------------------------------------

--
-- Structure de la table `seance`
--

DROP TABLE IF EXISTS `seance`;
CREATE TABLE IF NOT EXISTS `seance` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `semaine` int(50) NOT NULL,
  `date` date NOT NULL,
  `heure_debut` timestamp NOT NULL,
  `heure_fin` timestamp NOT NULL,
  `etat` int(3) NOT NULL,
  `id_cours` int(100) NOT NULL,
  `id_type` int(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_cours` (`id_cours`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `seance`
--

INSERT INTO `seance` (`id`, `semaine`, `date`, `heure_debut`, `heure_fin`, `etat`, `id_cours`, `id_type`) VALUES
(1, 23, '2020-06-02', '2020-06-02 06:00:00', '2020-06-02 08:00:00', 2, 1, 2),
(2, 23, '2020-06-02', '2020-06-02 08:00:00', '2020-06-02 10:00:00', 2, 4, 3),
(3, 22, '2020-05-28', '2020-05-28 08:00:00', '2020-05-28 10:00:00', 2, 3, 3),
(4, 22, '2020-05-27', '2020-05-27 08:00:00', '2020-05-27 10:00:00', 2, 6, 2),
(5, 22, '2020-05-29', '2020-05-29 06:00:00', '2020-05-29 10:00:00', 2, 2, 4),
(6, 22, '2020-05-25', '2020-05-25 08:00:00', '2020-05-25 10:00:00', 2, 3, 3),
(7, 22, '2020-05-28', '2020-05-28 16:00:00', '2020-05-28 18:00:00', 3, 1, 4),
(8, 22, '2020-05-26', '2020-05-26 10:00:00', '2020-05-26 12:00:00', 2, 10, 2),
(9, 23, '2020-06-03', '2020-06-03 12:00:00', '2020-06-03 14:00:00', 2, 1, 4),
(10, 22, '2020-05-27', '2020-05-27 14:00:00', '2020-05-27 16:00:00', 2, 11, 2),
(11, 24, '2020-06-08', '2020-06-08 08:00:00', '2020-06-08 10:00:00', 2, 3, 2),
(12, 24, '2020-06-09', '2020-06-09 06:00:00', '2020-06-09 08:00:00', 2, 2, 3),
(13, 24, '2020-06-11', '2020-06-11 16:00:00', '2020-06-11 18:00:00', 0, 5, 4),
(14, 23, '2020-06-04', '2020-06-04 16:00:00', '2020-06-04 18:00:00', 2, 4, 6),
(15, 23, '2020-06-05', '2020-06-05 14:00:00', '2020-06-05 16:00:00', 2, 1, 2),
(16, 23, '2020-06-01', '2020-06-01 10:00:00', '2020-06-01 12:00:00', 2, 10, 3),
(17, 25, '2020-06-15', '2020-06-15 08:00:00', '2020-06-15 10:00:00', 0, 3, 5),
(18, 25, '2020-06-19', '2020-06-19 12:00:00', '2020-06-19 14:00:00', 2, 12, 1),
(19, 25, '2020-06-16', '2020-06-16 08:00:00', '2020-06-16 10:00:00', 2, 1, 4),
(20, 24, '2020-06-11', '2020-06-11 06:00:00', '2020-06-11 08:00:00', 3, 4, 2),
(21, 24, '2020-06-10', '2020-06-10 16:00:00', '2020-06-10 18:00:00', 2, 6, 3);

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

--
-- Déchargement des données de la table `seance_enseignants`
--

INSERT INTO `seance_enseignants` (`id_seance`, `id_enseignant`) VALUES
(1, 3),
(3, 3),
(6, 9),
(7, 3),
(8, 13),
(9, 3),
(10, 3),
(11, 9),
(12, 10),
(2, 13),
(4, 10),
(5, 10),
(13, 3),
(14, 13),
(15, 3),
(16, 3),
(18, 14),
(19, 3),
(20, 13),
(21, 10);

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

--
-- Déchargement des données de la table `seance_groupes`
--

INSERT INTO `seance_groupes` (`id_seance`, `id_groupe`) VALUES
(1, 1),
(2, 1),
(3, 2),
(4, 3),
(5, 1),
(6, 1),
(7, 1),
(8, 1),
(9, 1),
(10, 1),
(11, 1),
(12, 1),
(13, 3),
(14, 1),
(15, 1),
(16, 1),
(17, 1),
(18, 5),
(19, 6),
(20, 1),
(21, 3);

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

--
-- Déchargement des données de la table `seance_salles`
--

INSERT INTO `seance_salles` (`id_seance`, `id_salle`) VALUES
(1, 4),
(2, 1),
(3, 2),
(4, 3),
(5, 3),
(6, 4),
(7, 2),
(8, 5),
(9, 1),
(10, 3),
(11, 1),
(12, 3),
(14, 2),
(16, 4),
(15, 3),
(17, 2),
(18, 3),
(19, 5),
(20, 3),
(21, 3);

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
(1, 'C. interactif'),
(2, 'C. magistral'),
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
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=latin1;

--
-- Déchargement des données de la table `utilisateur`
--

INSERT INTO `utilisateur` (`id`, `email`, `passwd`, `nom`, `prenom`, `droit`) VALUES
(1, 'admin@mail.com', 'e', 'z', 'admin_prenom', 1),
(2, 'referent@mail.com', 'referentmdp', 'referent_nom', 'referent_prenom', 2),
(3, 'profinfo@mail.com', 'b', 'a', 'profinfo_prenom', 3),
(4, 'wangdavid@mail.com', 'wangdavid', 'wang', 'david', 4),
(5, 'ayllonjorge@mail.com', 'ayllonjorge', 'ayllon', 'jorge', 4),
(6, 'beaujoistheo@mail.com', 'beaujoistheo', 'beaujois', 'theo', 4),
(7, 'eleve1@mail.com', 'eleve1', 'eleve1_nom', 'eleve1_prenom', 4),
(8, 'eleve2@mail.com', 'eleve2', 'eleve2_nom', 'eleve2_prenom', 4),
(9, 'profmaths@mail.com', 'profmaths', 'profmaths_nom', 'profmaths_prenom', 3),
(10, 'profelec@mail.com', 'profelec', 'profelec_nom', 'profelec_prenom', 3),
(11, 'eleve3@mail.com', 'eleve3', 'eleve3_nom', 'eleve3_prenom', 4),
(12, 'eleve4@mail.com', 'eleve4', 'eleve4_nom', 'eleve4_prenom', 4),
(13, 'profphysique@mail.com', 'profphysique', 'profphysique_nom', 'profphysique_prenom', 3),
(14, 'profanglais@mail.com', 'profanglais', 'anglais_nom', 'anglais_prenom', 3);

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
