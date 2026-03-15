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

DROP TABLE IF EXISTS novel;
CREATE TABLE novel (
                       id INT AUTO_INCREMENT PRIMARY KEY COMMENT '主键ID',
                       novel_name VARCHAR(100) NOT NULL COMMENT '小说名称',
                       description VARCHAR(500) COMMENT '小说描述（30字左右）',
                       create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                       update_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                       is_deleted TINYINT(1) DEFAULT 0 COMMENT '是否已删除：0-未删除，1-已删除',
                       INDEX idx_novel_name (novel_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='小说表';

-- 3. 插入假数据（20条，描述约30字）
INSERT INTO novel (novel_name, description) VALUES
                                                ('斗破苍穹', '少年萧炎天赋尽失，偶得神秘戒指，在药老指引下重踏修炼之路，最终成就斗帝传奇'),
                                                ('完美世界', '少年石昊从大荒走出，一路征战诸天万界，最终独断万古，成为完美世界的守护者'),
                                                ('遮天', '九龙拉棺降临地球，叶凡被带入北斗星域，开启了一段横跨宇宙的修仙传奇之旅'),
                                                ('凡人修仙传', '普通山村小子韩立，凭借小绿瓶的辅助，一步步踏上修仙之路，最终飞升仙界'),
                                                ('仙逆', '平凡少年王林因资质平庸被拒之门外，偶得神秘天逆珠，从此逆天改命踏上修仙路'),
                                                ('我欲封天', '书生孟浩因科考落榜，意外踏入修真界，凭借封妖师的传承，最终成为封天强者'),
                                                ('求魔', '少年苏铭身世成谜，在寻找真相的过程中，逐渐揭开了一个关于宿命与轮回的惊天秘密'),
                                                ('一念永恒', '白小纯胆小怕死却追求长生，凭借不死功法和机智，在修真界闯出一片天地'),
                                                ('斗罗大陆', '唐三穿越到斗罗大陆，觉醒双生武魂，创建唐门，最终成为海神和修罗神双神位'),
                                                ('神墓', '辰南从神墓陵园复活而出，为了追寻爱人雨馨，踏上了一段逆天伐道的征程'),
                                                ('长生界', '萧晨被卷入长生界，在异界中历经磨难，最终揭开长生之谜，守护九州大地'),
                                                ('星辰变', '秦羽天生无法修炼内功，却意外获得流星泪，自创功法最终成为鸿蒙掌控者'),
                                                ('盘龙', '少年林雷偶然得到一枚盘龙戒指，在德林柯沃特指导下，最终成为鸿蒙掌控者'),
                                                ('莽荒纪', '纪宁死后穿越到莽荒世界，成为修仙者，在三界浩劫中力挽狂澜，最终成就永恒'),
                                                ('雪鹰领主', '东伯雪鹰为救父母，努力修炼成为超凡，最终成就领主级浑源生命，守护家乡'),
                                                ('大主宰', '少年牧尘从北灵境出发，历经磨难最终成为大主宰，守护大千世界和平与安宁'),
                                                ('元尊', '周元天生圣龙气运被夺，在夭夭帮助下，重新夺回气运，最终成为元尊拯救苍生'),
                                                ('武动乾坤', '林动偶得神秘石符，在青檀陪伴下，从青阳镇走出，最终成为位面之主守护天地'),
                                                ('绝世唐门', '霍雨浩觉醒灵眸武魂，加入唐门，在史莱克学院成长，最终成为情绪之神飞升'),
                                                ('琴帝', '叶音竹热爱音乐，以琴入道，在龙崎努斯大陆上，用音乐魔法战胜敌人守护和平');

-- ====================== 验证数据（可选执行） ======================
SELECT * FROM user_table;
SELECT * FROM photo_table;
SELECT * FROM code_table;
-- 这是后续为了ES构建的表
SELECT * FROM novel;