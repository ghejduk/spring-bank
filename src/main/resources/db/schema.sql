DROP TABLE IF EXISTS `account`;
CREATE TABLE `account` (
  `number` char(6) NOT NULL,
  `balance` int(11) NOT NULL DEFAULT '0',
  `owner_id` char(36) NOT NULL,
  UNIQUE KEY `account_number_uindex` (`number`),
  KEY `account_user_id_fk` (`owner_id`),
  CONSTRAINT `account_user_id_fk` FOREIGN KEY (`owner_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` char(36) NOT NULL,
  `email` varchar(100) NOT NULL,
  `firstname` varchar(100) DEFAULT NULL,
  `lastname` varchar(100) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id_uindex` (`id`),
  UNIQUE KEY `user_email_uindex` (`email`),
  KEY `user_email_index` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `transaction`;
CREATE TABLE `transaction` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `date` datetime NOT NULL,
  `from` char(6) NOT NULL,
  `to` char(6) NOT NULL,
  `amount` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `transaction_id_uindex` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1
