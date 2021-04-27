/*
 Navicat Premium Data Transfer

 Source Server         : (MySQL 8.0)192.168.249.128
 Source Server Type    : MySQL
 Source Server Version : 80023
 Source Host           : 192.168.249.128:3306
 Source Schema         : blog

 Target Server Type    : MySQL
 Target Server Version : 80023
 File Encoding         : 65001

 Date: 28/04/2021 00:21:07
*/

SET NAMES utf8mb4;
SET
FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for users
-- ----------------------------
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `id`           int(0) NOT NULL AUTO_INCREMENT,
    `name`         varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
    `age`          int(0) NULL DEFAULT NULL,
    `sex`          enum('男','女') CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    `email`        varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    `phone_number` varchar(20) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    `create_time`  datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP (0),
    `labels`       varchar(255) CHARACTER SET utf8 COLLATE utf8_bin NULL DEFAULT NULL,
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 7411 CHARACTER SET = utf8 COLLATE = utf8_bin ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of users
-- ----------------------------
INSERT INTO `users`
VALUES (10, '猫猫', 18, '男', NULL, '13212312312', '2021-04-27 23:16:22', NULL);
INSERT INTO `users`
VALUES (11, '道长', 22, '男', '111@qq.com', '13612345678', '2021-04-28 11:17:24', NULL);

SET
FOREIGN_KEY_CHECKS = 1;
