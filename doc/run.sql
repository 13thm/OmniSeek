-- 1. 创建核心数据库 seach_db（不存在则创建，适配中文/特殊字符）
CREATE DATABASE IF NOT EXISTS seach_db
DEFAULT CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- 2. 使用该数据库
USE seach_db;

-- ====================== 第一个表：用户表 (user_table) ======================
DROP TABLE IF EXISTS user_table;
CREATE TABLE user_table (
                            user_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID，自增',
                            user_name VARCHAR(50) NOT NULL COMMENT '用户名（核心用户字段）',
                            user_phone VARCHAR(20) UNIQUE COMMENT '手机号（可选，唯一约束）',
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否已删除：0-未删除，1-已删除',
                            INDEX idx_is_deleted (is_deleted),
                            INDEX idx_create_time (create_time),
                            INDEX idx_update_time (update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='用户表';

-- 插入用户表测试数据
INSERT INTO user_table (user_name, user_phone) VALUES
                                                   ('张三', '13800138000'),
                                                   ('李四', '13900139000'),
                                                   ('王五', '13700137000');

-- ====================== 第二个表：照片表 (photo_table) ======================
DROP TABLE IF EXISTS photo_table;
CREATE TABLE photo_table (
                             photo_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID，自增',
                             user_id INT COMMENT '关联用户表的user_id（外键逻辑）',
                             photo_url VARCHAR(255) NOT NULL COMMENT '照片地址（核心照片字段）',
                             photo_name VARCHAR(100) COMMENT '照片名称（辅助字段）',
                             create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                             update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                             is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否已删除：0-未删除，1-已删除',
                             FOREIGN KEY (user_id) REFERENCES user_table(user_id),
                             INDEX idx_user_id (user_id),
                             INDEX idx_is_deleted (is_deleted),
                             INDEX idx_create_time (create_time),
                             INDEX idx_update_time (update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='照片表';

-- 插入照片表测试数据（关联对应用户）
INSERT INTO photo_table (user_id, photo_url, photo_name) VALUES
                                                             (1, 'https://example.com/zhangsan_avatar.jpg', '张三的头像'),
                                                             (2, 'https://example.com/lisi_scenery.jpg', '李四的风景照'),
                                                             (3, 'https://example.com/wangwu_life.jpg', '王五的生活照');

-- ====================== 第三个表：代码块表 (code_table) ======================
DROP TABLE IF EXISTS code_table;
CREATE TABLE code_table (
                            code_id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID，自增',
                            user_id INT COMMENT '关联用户表的user_id',
                            code_content TEXT NOT NULL COMMENT '代码块内容（核心字段）',
                            code_lang VARCHAR(30) COMMENT '代码语言（辅助字段）',
                            create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                            update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                            is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否已删除：0-未删除，1-已删除',
                            FOREIGN KEY (user_id) REFERENCES user_table(user_id),
                            INDEX idx_user_id (user_id),
                            INDEX idx_is_deleted (is_deleted),
                            INDEX idx_create_time (create_time),
                            INDEX idx_update_time (update_time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代码块表';

-- 插入代码块表测试数据（关联对应用户）
INSERT INTO code_table (user_id, code_content, code_lang) VALUES
                                                              (1, 'print("Hello Python!")\n# 张三的Python代码', 'Python'),
                                                              (2, 'SELECT * FROM user_table WHERE user_id=2;\n-- 李四的SQL代码', 'SQL'),
                                                              (3, 'function sum(a,b) { return a+b; }\n// 王五的JS代码', 'JavaScript');

-- ====================== 验证数据（可选执行） ======================
SELECT * FROM user_table;
SELECT * FROM photo_table;
SELECT * FROM code_table;