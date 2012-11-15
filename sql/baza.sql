CREATE TABLE IF NOT EXISTS `matches` (
  `id_match` int(11) NOT NULL AUTO_INCREMENT,
  `match_player1` int(11) NOT NULL,
  `match_player2` int(11) NOT NULL,
  `match_player1_points` int(11) NOT NULL,
  `match_player2_points` int(11) NOT NULL,
  `match_won` int(11) NOT NULL,
  PRIMARY KEY (`id_match`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

CREATE TABLE IF NOT EXISTS `players` (
  `id_player` int(11) NOT NULL AUTO_INCREMENT,
  `player_name` varchar(70) COLLATE utf8_bin NOT NULL,
  `player_lastname` varchar(70) COLLATE utf8_bin NOT NULL,
  `player_points` int(11) NOT NULL DEFAULT '0',
  `player_matches` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_player`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

