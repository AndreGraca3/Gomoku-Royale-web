INSERT INTO rank (name, icon_url, min_mmr)
VALUES ('Bronze', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-1.png', 0),
       ('Silver', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-4.png', 50),
       ('Gold', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-7.png', 100),
       ('Platinum', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-10.png', 150),
       ('Diamond', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-13.png', 200),
       ('Champion', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-16.png', 250),
       ('Grand Champion', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s15rank19.png', 300);

INSERT INTO "user" (name, email, password, role, mmr, avatar_url, created_at, rank_id)
VALUES ('Daniel', 'daniel@gmail.com', 'password123', 'user', 120, 'daniel_avatar_url', NOW(), 4),
       ('Diogo', 'diogo@gmail.com', 'password123', 'user', 80, 'diogo_avatar_url', NOW(), 2),
       ('Andre', 'andre@gmail.com', 'password', 'dev', 210, 'andre_avatar_url', NOW(), 5);

INSERT INTO token (value, created_at, last_used, user_id)
VALUES ('0Txy7bYpM9fZaEjKsLpQrVwXuT6jM0fD', NOW(), NOW(), 1),
       ('5Rz2vWqFpYhN6sTbGmCjXeZrU0gO4oA1', NOW(), NOW(), 2),
       ('9PwQ3zHsUeLmWxN7aRyV2bYjO5iK8tSf', NOW(), NOW(), 3);

INSERT INTO match (visibility, board, created_at, player1_id, player2_id, winner_id)
VALUES ('public', 'match_board_1', NOW(), 1, 2, 1),
       ('private', 'match_board_2', NOW(), 2, 3, 3),
       ('public', 'match_board_3', NOW(), 1, 3, 1);
