INSERT INTO t_user (username, password, email)
SELECT 'alice', '$2a$10$JvRGGTicRNoIc1q99CQNFuRXsZQJrHgLHzusRDL.CFUcanJqm1/cC', 'alice@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'alice');

INSERT INTO t_user (username, password, email)
SELECT 'bob', '$2a$10$JvRGGTicRNoIc1q99CQNFuRXsZQJrHgLHzusRDL.CFUcanJqm1/cC', 'bob@example.com'
WHERE NOT EXISTS (SELECT 1 FROM t_user WHERE username = 'bob');
