/*
Navicat MySQL Data Transfer

Source Server         : 本机
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : gulischool

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2021-04-02 16:45:56
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for edu_chapter
-- ----------------------------
DROP TABLE IF EXISTS `edu_chapter`;
CREATE TABLE `edu_chapter` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '章节ID',
  `course_id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程ID',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '章节名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '显示排序',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for edu_course
-- ----------------------------
DROP TABLE IF EXISTS `edu_course`;
CREATE TABLE `edu_course` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程ID',
  `teacher_id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程讲师ID',
  `subject_id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程专业ID',
  `subject_parent_id` char(19) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '课程专业父级ID',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '课程标题',
  `price` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '课程销售价格，设置为0则可免费观看',
  `lesson_num` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '总课时',
  `cover` varchar(255) CHARACTER SET utf8 NOT NULL COMMENT '课程封面图片路径',
  `buy_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '销售数量',
  `view_count` bigint(10) unsigned NOT NULL DEFAULT '0' COMMENT '浏览数量',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `status` varchar(10) COLLATE utf8mb4_bin NOT NULL DEFAULT 'Draft' COMMENT '课程状态 Draft未发布  Normal已发布',
  `is_deleted` tinyint(3) DEFAULT '0' COMMENT '逻辑删除 1（true）已删除， 0（false）未删除',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_title` (`title`),
  KEY `idx_subject_id` (`subject_id`),
  KEY `idx_teacher_id` (`teacher_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for edu_course_description
-- ----------------------------
DROP TABLE IF EXISTS `edu_course_description`;
CREATE TABLE `edu_course_description` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程ID',
  `description` text COLLATE utf8mb4_bin COMMENT '课程简介',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for edu_subject
-- ----------------------------
DROP TABLE IF EXISTS `edu_subject`;
CREATE TABLE `edu_subject` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程类别ID',
  `title` varchar(10) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '类别名称',
  `parent_id` char(19) COLLATE utf8mb4_bin DEFAULT '0' COMMENT '父级ID',
  `sort` int(10) DEFAULT '0' COMMENT '排序',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for edu_teacher
-- ----------------------------
DROP TABLE IF EXISTS `edu_teacher`;
CREATE TABLE `edu_teacher` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '讲师ID',
  `name` varchar(20) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '讲师姓名',
  `intro` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '讲师简介',
  `careet` varchar(500) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '讲师资历，一句话说明讲师资历',
  `level` int(10) DEFAULT NULL COMMENT '头衔 1高级讲师 2首席讲师',
  `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '讲师头像',
  `sort` int(10) DEFAULT NULL COMMENT '排序',
  `is_deleted` tinyint(1) DEFAULT '0' COMMENT '逻辑删除 1（true）已删除，0（false）未删除',
  `gmt_create` datetime DEFAULT NULL COMMENT '创建时间',
  `gmt_modified` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

-- ----------------------------
-- Table structure for edu_video
-- ----------------------------
DROP TABLE IF EXISTS `edu_video`;
CREATE TABLE `edu_video` (
  `id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '视频ID',
  `course_id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '课程ID',
  `chapter_id` char(19) COLLATE utf8mb4_bin NOT NULL COMMENT '章节ID',
  `title` varchar(50) COLLATE utf8mb4_bin NOT NULL COMMENT '节点名称',
  `video_source_id` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '云端视频资源',
  `video_original_name` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL COMMENT '原始文件名称',
  `sort` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '排序字段',
  `play_count` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '播放次数',
  `is_free` tinyint(1) unsigned NOT NULL DEFAULT '0' COMMENT '是否可以试听：0收费 1免费',
  `duration` float NOT NULL DEFAULT '0' COMMENT '视频时长（秒）',
  `status` varchar(20) COLLATE utf8mb4_bin NOT NULL DEFAULT 'Empty' COMMENT 'Empty未上传 Transcoding转码中  Normal正常',
  `size` bigint(20) unsigned NOT NULL DEFAULT '0' COMMENT '视频源文件大小（字节）',
  `version` bigint(20) unsigned NOT NULL DEFAULT '1' COMMENT '乐观锁',
  `gmt_create` datetime NOT NULL COMMENT '创建时间',
  `gmt_modified` datetime NOT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `idx_course_id` (`course_id`),
  KEY `idx_chapter_id` (`chapter_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
