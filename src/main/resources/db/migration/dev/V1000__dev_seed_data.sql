-- =========================================================
-- PLAYERS
-- =========================================================

INSERT INTO player (
    id,
    name,
    nickname,
    birthdate,
    is_regular_member,
    grade_average,
    last_grade
)
VALUES
    (1001, 'João Silva', 'Jota', '1992-03-10', true, 8.10, 8.50),
    (1002, 'Carlos Mendes', 'Carlão', '1989-07-22', true, 7.50, 7.80),
    (1003, 'Pedro Lima', 'Pedrinho', '1995-01-11', false, 6.90, 7.10),
    (1004, 'Ricardo Alves', 'Rick', '1991-05-03', true, 8.30, 8.70),
    (1005, 'André Costa', 'Deco', '1990-09-17', false, 7.00, 7.20),
    (1006, 'Lucas Ferreira', 'Luquinhas', '1998-02-14', false, 6.50, 6.90),
    (1007, 'Tiago Rocha', 'Tiagão', '1993-08-09', true, 8.00, 8.20),
    (1008, 'Felipe Gomes', 'Felipinho', '1996-06-25', false, 7.20, 7.30),
    (1009, 'Bruno Martins', 'Brunera', '1988-12-12', true, 8.50, 9.00),
    (1010, 'Eduardo Souza', 'Dudu', '1994-10-02', false, 6.80, 7.00),
    (1011, 'Marcos Vinicius', 'MV', '1997-04-30', true, 7.60, 7.90),
    (1012, 'Henrique Lopes', 'Henri', '1990-11-21', false, 7.10, 7.40),
    (1013, 'Gabriel Santos', 'Gabigol', '1999-01-15', false, 8.20, 8.40),
    (1014, 'Fernando Melo', 'Nando', '1987-03-08', true, 7.70, 7.80),
    (1015, 'Paulo Henrique', 'PH', '1993-07-19', false, 6.90, 7.10),
    (1016, 'Rafael Teixeira', 'Rafa', '1992-09-27', true, 8.00, 8.10),
    (1017, 'Vinicius Barros', 'Vini', '1995-02-05', false, 7.40, 7.50),
    (1018, 'Leonardo Castro', 'Léo', '1991-12-18', true, 8.60, 8.80),
    (1019, 'Matheus Oliveira', 'Mats', '1996-08-28', false, 7.30, 7.60),
    (1020, 'Diego Fernandes', 'DG', '1989-04-01', true, 8.10, 8.30);

-- =========================================================
-- USERS
-- =========================================================

INSERT INTO app_user (
    id,
    username,
    password,
    user_role,
    id_player
)
VALUES
    (2001, 'admin', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'ADMIN', 1001),
    (2002, 'carlos', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1002),
    (2003, 'rick', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1004),
    (2004, 'tiago', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1007),
    (2005, 'bruno', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1009),
    (2006, 'mv', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1011),
    (2007, 'nando', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1014),
    (2008, 'rafa', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1016),
    (2009, 'leo', '$2a$10$7sNclhK1vZk6uY6s8M6kV.Kj8z9g0JzQ6d0M8vP8J7fGxYf6g6b4G', 'PLAYER', 1018);

-- =========================================================
-- PLAYER POSITIONS
-- =========================================================

INSERT INTO player_position (id_player, position_name)
VALUES
    (1001, 'MIDFIELDER'),
    (1002, 'DEFENDER'),
    (1003, 'STRIKER'),
    (1004, 'MIDFIELDER'),
    (1005, 'GOALKEEPER'),
    (1006, 'WINGBACK'),
    (1007, 'DEFENDER'),
    (1008, 'STRIKER'),
    (1009, 'MIDFIELDER'),
    (1010, 'GOALKEEPER'),
    (1011, 'WINGBACK'),
    (1012, 'DEFENDER'),
    (1013, 'STRIKER'),
    (1014, 'MIDFIELDER'),
    (1015, 'DEFENDER'),
    (1016, 'WINGBACK'),
    (1017, 'STRIKER'),
    (1018, 'MIDFIELDER'),
    (1019, 'GOALKEEPER'),
    (1020, 'DEFENDER');

-- =========================================================
-- MATCHES
-- =========================================================

INSERT INTO football_match (
    id,
    match_start_date,
    match_end_date,
    max_players
)
VALUES
    (
        3001,
        DATEADD('DAY', -14, CURRENT_TIMESTAMP),
        DATEADD('HOUR', 2, DATEADD('DAY', -14, CURRENT_TIMESTAMP)),
        14
    );

INSERT INTO football_match (
    id,
    match_start_date,
    match_end_date,
    max_players
)
VALUES
    (
        3002,
        DATEADD('DAY', -7, CURRENT_TIMESTAMP),
        DATEADD('HOUR', 2, DATEADD('DAY', -7, CURRENT_TIMESTAMP)),
        14
    );

INSERT INTO football_match (
    id,
    match_start_date,
    match_end_date,
    max_players
)
VALUES
    (
        3003,
        DATEADD('DAY', 3, CURRENT_TIMESTAMP),
        DATEADD('HOUR', 2, DATEADD('DAY', 3, CURRENT_TIMESTAMP)),
        14
    );

-- =========================================================
-- MATCH PLAYERS
-- =========================================================

INSERT INTO match_player (
    id,
    id_player,
    id_match,
    team,
    goals_scored,
    subscription_date,
    was_present
)
VALUES
    (5001,1001,3001,1,2,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5002,1002,3001,1,1,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5003,1003,3001,1,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5004,1004,3001,1,1,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5005,1005,3001,1,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5006,1006,3001,1,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5007,1007,3001,1,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),

    (5008,1008,3001,2,2,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5009,1009,3001,2,1,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5010,1010,3001,2,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5011,1011,3001,2,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5012,1012,3001,2,1,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5013,1013,3001,2,2,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),
    (5014,1014,3001,2,0,DATEADD('DAY', -15, CURRENT_TIMESTAMP),true),

    (5015,1015,3001,NULL,0,DATEADD('DAY', -14, CURRENT_TIMESTAMP),false),
    (5016,1016,3001,NULL,0,DATEADD('DAY', -14, CURRENT_TIMESTAMP),false);

INSERT INTO match_player (
    id,
    id_player,
    id_match,
    team,
    goals_scored,
    subscription_date,
    was_present
)
VALUES
    (5017,1001,3002,1,1,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5018,1004,3002,1,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5019,1007,3002,1,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5020,1009,3002,1,2,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5021,1011,3002,1,1,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5022,1013,3002,1,3,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5023,1015,3002,1,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),

    (5024,1002,3002,2,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5025,1003,3002,2,1,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5026,1005,3002,2,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5027,1006,3002,2,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5028,1008,3002,2,1,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5029,1010,3002,2,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),
    (5030,1012,3002,2,0,DATEADD('DAY', -8, CURRENT_TIMESTAMP),true),

    (5031,1017,3002,NULL,0,DATEADD('DAY', -7, CURRENT_TIMESTAMP),false),
    (5032,1018,3002,NULL,0,DATEADD('DAY', -7, CURRENT_TIMESTAMP),false),
    (5033,1019,3002,NULL,0,DATEADD('DAY', -7, CURRENT_TIMESTAMP),false);

INSERT INTO match_player (
    id,
    id_player,
    id_match,
    team,
    goals_scored,
    subscription_date,
    was_present
)
VALUES
    (5034,1001,3003,1,0,CURRENT_TIMESTAMP,false),
    (5035,1002,3003,1,0,CURRENT_TIMESTAMP,false),
    (5036,1004,3003,1,0,CURRENT_TIMESTAMP,false),
    (5037,1007,3003,1,0,CURRENT_TIMESTAMP,false),
    (5038,1009,3003,1,0,CURRENT_TIMESTAMP,false),
    (5039,1011,3003,1,0,CURRENT_TIMESTAMP,false),
    (5040,1014,3003,1,0,CURRENT_TIMESTAMP,false),

    (5041,1003,3003,2,0,CURRENT_TIMESTAMP,false),
    (5042,1005,3003,2,0,CURRENT_TIMESTAMP,false),
    (5043,1006,3003,2,0,CURRENT_TIMESTAMP,false),
    (5044,1008,3003,2,0,CURRENT_TIMESTAMP,false),
    (5045,1010,3003,2,0,CURRENT_TIMESTAMP,false),
    (5046,1012,3003,2,0,CURRENT_TIMESTAMP,false),
    (5047,1013,3003,2,0,CURRENT_TIMESTAMP,false),

    (5048,1016,3003,NULL,0,CURRENT_TIMESTAMP,false),
    (5049,1017,3003,NULL,0,CURRENT_TIMESTAMP,false),
    (5050,1018,3003,NULL,0,CURRENT_TIMESTAMP,false),
    (5051,1019,3003,NULL,0,CURRENT_TIMESTAMP,false),
    (5052,1020,3003,NULL,0,CURRENT_TIMESTAMP,false);

-- =========================================================
-- EVALUATIONS
-- =========================================================

INSERT INTO evaluation (
    id,
    id_match,
    id_player_evaluator,
    id_player_evaluatee,
    grade
)
VALUES
    (8001,3001,1001,1008,8.5),
    (8002,3001,1002,1013,9.0),
    (8003,3001,1004,1009,8.0),
    (8004,3001,1007,1003,7.0),
    (8005,3002,1001,1013,9.5),
    (8006,3002,1004,1009,8.5),
    (8007,3002,1009,1001,8.0),
    (8008,3002,1011,1003,7.5);

-- =========================================================
-- RESTART IDENTITY
-- =========================================================

ALTER TABLE player ALTER COLUMN id RESTART WITH 2000;

ALTER TABLE app_user ALTER COLUMN id RESTART WITH 3000;

ALTER TABLE football_match ALTER COLUMN id RESTART WITH 4000;

ALTER TABLE match_player ALTER COLUMN id RESTART WITH 6000;

ALTER TABLE evaluation ALTER COLUMN id RESTART WITH 9000;