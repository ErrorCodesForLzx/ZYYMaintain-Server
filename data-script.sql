/*
 Navicat MySQL Data Transfer

 Source Server         : LOCAL
 Source Server Type    : MySQL
 Source Server Version : 80028
 Source Host           : localhost:3306
 Source Schema         : zyy_maintain

 Target Server Type    : MySQL
 Target Server Version : 80028
 File Encoding         : 65001

 Date: 05/10/2022 17:34:12
*/

DROP DATABASE IF EXISTS `zyy_maintain`;
CREATE DATABASE `zyy_maintain`;

USE zyy_maintain;

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for mt_files
-- ----------------------------
DROP TABLE IF EXISTS `mt_files`;
CREATE TABLE `mt_files` (
  `fid` int NOT NULL AUTO_INCREMENT,
  `file_name` varchar(50) DEFAULT NULL,
  `upload_name` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`fid`),
  UNIQUE KEY `fid` (`fid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for mt_record
-- ----------------------------
DROP TABLE IF EXISTS `mt_record`;
CREATE TABLE `mt_record` (
  `rid` int NOT NULL AUTO_INCREMENT,
  `start_time` varchar(100) DEFAULT NULL,
  `end_time` varchar(100) DEFAULT NULL,
  `title` varchar(50) DEFAULT NULL,
  `current_status` int DEFAULT NULL,
  `content` varchar(255) DEFAULT NULL,
  `create_user` int DEFAULT NULL,
  `img_url` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`rid`),
  KEY `mt_record_mt_status_sid_fk` (`current_status`),
  KEY `mt_record_mt_user_uid_fk` (`create_user`),
  CONSTRAINT `mt_record_mt_status_sid_fk` FOREIGN KEY (`current_status`) REFERENCES `mt_status` (`sid`),
  CONSTRAINT `mt_record_mt_user_uid_fk` FOREIGN KEY (`create_user`) REFERENCES `mt_user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Table structure for mt_status
-- ----------------------------
DROP TABLE IF EXISTS `mt_status`;
CREATE TABLE `mt_status` (
  `sid` int NOT NULL AUTO_INCREMENT,
  `sname` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`sid`),
  UNIQUE KEY `mt_status_sid_uindex` (`sid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mt_status
-- ----------------------------
BEGIN;
INSERT INTO `mt_status` VALUES (1, '未处理');
INSERT INTO `mt_status` VALUES (2, '处理中');
INSERT INTO `mt_status` VALUES (3, '完成处理');
COMMIT;

-- ----------------------------
-- Table structure for mt_user
-- ----------------------------
DROP TABLE IF EXISTS `mt_user`;
CREATE TABLE `mt_user` (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `user_group` int DEFAULT NULL,
  `token` varchar(40) DEFAULT NULL,
  PRIMARY KEY (`uid`),
  KEY `mt_user_mt_user_group_gid_fk` (`user_group`)
) ENGINE=InnoDB AUTO_INCREMENT=100003 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mt_user
-- ----------------------------
BEGIN;
INSERT INTO `mt_user` VALUES (100001, 'admin', 'admin123', 1, '');
INSERT INTO `mt_user` VALUES (100002, 'test', 'test123', 2, '');
COMMIT;

-- ----------------------------
-- Table structure for mt_user_group
-- ----------------------------
DROP TABLE IF EXISTS `mt_user_group`;
CREATE TABLE `mt_user_group` (
  `gid` int NOT NULL AUTO_INCREMENT,
  `gname` varchar(20) DEFAULT NULL,
  `has_admin_permission` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`gid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of mt_user_group
-- ----------------------------
BEGIN;
INSERT INTO `mt_user_group` VALUES (1, '管理员', 1);
INSERT INTO `mt_user_group` VALUES (2, '普通人员', 0);
COMMIT;

SET FOREIGN_KEY_CHECKS = 1;
