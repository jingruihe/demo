-- shop_order_0
    -- product_order_0
    -- product_order_1
-- shop_order_1
    -- product_order_0
    -- product_order_1

CREATE DATABASE shop_order_0;
use shop_order_0;

CREATE TABLE `product_order_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `ad_config` (
  `id` bigint unsigned NOT NULL COMMENT '主键id',
  `config_key` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置key',
  `config_value` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置value',
  `type` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_item_0` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_order_id` bigint DEFAULT NULL COMMENT '订单号',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_item_1` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_order_id` bigint DEFAULT NULL COMMENT '订单号',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ################
CREATE DATABASE shop_order_1;
use shop_order_1;

CREATE TABLE `product_order_0` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_1` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `out_trade_no` varchar(64) DEFAULT NULL COMMENT '订单唯一标识',
  `state` varchar(11) DEFAULT NULL COMMENT 'NEW 未支付订单,PAY已经支付订单,CANCEL超时取消订单',
  `create_time` datetime DEFAULT NULL COMMENT '订单生成时间',
  `pay_amount` decimal(16,2) DEFAULT NULL COMMENT '订单实际支付价格',
  `nickname` varchar(64) DEFAULT NULL COMMENT '昵称',
  `user_id` bigint DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `ad_config` (
  `id` bigint unsigned NOT NULL COMMENT '主键id',
  `config_key` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置key',
  `config_value` varchar(1024) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '配置value',
  `type` varchar(128) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类型',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_item_0` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_order_id` bigint DEFAULT NULL COMMENT '订单号',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

CREATE TABLE `product_order_item_1` (
  `id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `product_order_id` bigint DEFAULT NULL COMMENT '订单号',
  `product_id` bigint DEFAULT NULL COMMENT '产品id',
  `product_name` varchar(128) DEFAULT NULL COMMENT '商品名称',
  `buy_num` int DEFAULT NULL COMMENT '购买数量',
  `user_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;