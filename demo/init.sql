CREATE TABLE `item` (
  `item_no` varchar(10) NOT NULL,
  `type` varchar(45) DEFAULT NULL,
  `low` int(11) DEFAULT NULL,
  `hight` int(11) DEFAULT NULL,
  `alert` tinyint(1) DEFAULT NULL,
  `desc` varchar(100) DEFAULT NULL,
  `group_no` int(11) DEFAULT NULL,
  `order` int(11) DEFAULT NULL,
  PRIMARY KEY (`item_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
