INSERT INTO t_user (username, password, email)
SELECT 'alice', '123456', 'alice@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'alice');

INSERT INTO t_user (username, password, email)
SELECT 'bob', '123456', 'bob@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'bob');
