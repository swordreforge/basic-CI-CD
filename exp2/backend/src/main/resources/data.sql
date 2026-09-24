-- AI-assisted: 种子密码改为 BCrypt 哈希（明文均为 123456），人工已复核。注意：仅对新建库生效，已存在的明文行需手动更新。
INSERT INTO t_user (username, password, email)
SELECT 'alice', '$2b$10$PAoyWxjF.dDaarbyR.NtiexQxDJ55S2dImNSl65etZ/9Xmo2MC2gS', 'alice@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'alice');

INSERT INTO t_user (username, password, email)
SELECT 'bob', '$2b$10$Yrktg7sK/0IjsXSqbbjmfuQhZHGbNft6H.QEaryYTN9ylSQyUJ.Ma', 'bob@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'bob');
