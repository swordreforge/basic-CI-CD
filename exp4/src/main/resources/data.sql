INSERT INTO t_role (code, name)
SELECT 'ADMIN', '管理员'
WHERE NOT EXISTS (SELECT 1 FROM t_role WHERE code = 'ADMIN');

INSERT INTO t_role (code, name)
SELECT 'USER', '普通用户'
WHERE NOT EXISTS (SELECT 1 FROM t_role WHERE code = 'USER');

INSERT INTO t_permission (code, name)
SELECT 'user:read', '查看用户'
WHERE NOT EXISTS (SELECT 1 FROM t_permission WHERE code = 'user:read');

INSERT INTO t_permission (code, name)
SELECT 'user:write', '新增/修改用户'
WHERE NOT EXISTS (SELECT 1 FROM t_permission WHERE code = 'user:write');

INSERT INTO t_permission (code, name)
SELECT 'user:delete', '删除用户'
WHERE NOT EXISTS (SELECT 1 FROM t_permission WHERE code = 'user:delete');

INSERT INTO t_role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM t_role r
JOIN t_permission p ON p.code IN ('user:read', 'user:write', 'user:delete')
WHERE r.code = 'ADMIN'
  AND NOT EXISTS (
      SELECT 1 FROM t_role_permission rp WHERE rp.role_id = r.id AND rp.permission_id = p.id
  );

INSERT INTO t_role_permission (role_id, permission_id)
SELECT r.id, p.id
FROM t_role r
JOIN t_permission p ON p.code = 'user:read'
WHERE r.code = 'USER'
  AND NOT EXISTS (
      SELECT 1 FROM t_role_permission rp WHERE rp.role_id = r.id AND rp.permission_id = p.id
  );
