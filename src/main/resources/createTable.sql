create table `category`(
    `id` int(11) PRIMARY KEY,
    `name` varchar(25) NOT NULL,
    `status` int DEFAULT 1,
    `order` int,
    `create_time` datetime
);