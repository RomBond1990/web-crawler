-- noinspection SqlDialectInspectionForFile

CREATE SCHEMA IF NOT EXISTS `crawler`;
USE `crawler`;

CREATE TABLE `link` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `link_depth` int(11) DEFAULT NULL,
  `name` varchar(1000) NOT NULL,
  `seed` varchar(255) DEFAULT NULL,
  `fk_parent_link_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_parent_link` FOREIGN KEY (`fk_parent_link_id`) REFERENCES `link` (`id`)
);

CREATE TABLE `word_counter` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `count` int(11) NOT NULL DEFAULT '0',
  `word` varchar(255) NOT NULL,
  `fk_link_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_link` FOREIGN KEY (`fk_link_id`) REFERENCES `link` (`id`)
);


INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('1', '0', 'https://en.wikipedia.org/wiki/Elon_Musk', 'httpsenwikipediaorgwikiElonMusk', NULL);
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('2', '1', 'https://en.wikipedia.org/wiki/Fellow_of_the_Royal_Society', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('3', '1', 'https://en.wikipedia.org/wiki/File:Elon_Musk_Royal_Society_(crop1).jpg', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('4', '1', 'https://en.wikipedia.org/wiki/Royal_Society', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('5', '1', 'https://en.wikipedia.org/wiki/Pretoria', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('6', '1', 'https://en.wikipedia.org/wiki/South_Africa', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('7', '1', 'https://en.wikipedia.org/wiki/University_of_Pennsylvania', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('8', '1', 'https://en.wikipedia.org/wiki/Bachelor_of_Science', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('9', '1', 'https://en.wikipedia.org/wiki/Bachelor_of_Arts', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('10', '1', 'https://en.wikipedia.org/w/index.php?title=Elon_Musk&action=edit', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('11', '1', 'https://en.wikipedia.org/wiki/SpaceX', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('12', '1', 'https://en.wikipedia.org/wiki/Tesla,_Inc.', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('13', '1', 'https://en.wikipedia.org/wiki/The_Boring_Company', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('14', '1', 'https://en.wikipedia.org/wiki/X.com', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('15', '1', 'https://en.wikipedia.org/wiki/Neuralink', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('16', '1', 'https://en.wikipedia.org/wiki/OpenAI', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('17', '1', 'https://en.wikipedia.org/wiki/Zip2', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('18', '1', 'https://en.wikipedia.org/wiki/SolarCity', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('19', '1', 'https://en.wikipedia.org/wiki/Justine_Musk', 'httpsenwikipediaorgwikiElonMusk', '1');
INSERT INTO `link` ( id, link_depth, name, seed, fk_parent_link_id)
VALUES ('20', '1', 'https://en.wikipedia.org/wiki/Talulah_Riley', 'httpsenwikipediaorgwikiElonMusk', '1');

INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ( '1', '271', 'Elon', '1');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('2', '140', 'tesla', '1');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('3', '2', 'Elon', '2');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('4', '0', 'tesla', '2');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('5', '23', 'Elon', '3');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('6', '0', 'tesla', '3');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('7', '0', 'Elon', '4');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('8', '0', 'tesla', '4');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('9', '1', 'Elon', '5');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('10', '0', 'tesla', '5');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('11', '0', 'Elon', '6');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('12', '0', 'tesla', '6');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('13', '5', 'Elon', '7');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('14', '2', 'tesla', '7');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('15', '0', 'Elon', '8');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('16', '0', 'tesla', '8');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('17', '0', 'Elon', '9');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('18', '0', 'tesla', '9');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('19', '678', 'Elon', '10');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('20', '276', 'tesla', '10');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('21', '54', 'Elon', '11');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('22', '18', 'tesla', '11');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('23', '97', 'Elon', '12');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('24', '879', 'tesla', '12');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('25', '73', 'Elon', '13');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('26', '14', 'tesla', '13');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('27', '16', 'Elon', '14');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('28', '8', 'tesla', '14');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('29', '38', 'Elon', '15');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('30', '8', 'tesla', '15');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('31', '27', 'Elon', '16');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('32', '10', 'tesla', '16');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('33', '18', 'Elon', '17');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('34', '9', 'tesla', '17');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('35', '20', 'Elon', '18');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('36', '81', 'tesla', '18');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('37', '8', 'Elon', '19');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('38', '6', 'tesla', '19');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('39', '12', 'Elon', '20');
INSERT INTO `word_counter` (id, count, word, fk_link_id) VALUES ('40', '5', 'tesla', '20');
