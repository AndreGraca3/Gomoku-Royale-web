TRUNCATE board CASCADE;
TRUNCATE match CASCADE;
TRUNCATE token CASCADE;
TRUNCATE "user" CASCADE;
TRUNCATE rank CASCADE;

INSERT INTO rank (name, icon_url, min_mmr)
VALUES ('Bronze', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-1.png', 0),
       ('Silver', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-4.png', 50),
       ('Gold', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-7.png', 100),
       ('Platinum', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-10.png', 150),
       ('Diamond', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-13.png', 200),
       ('Champion', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s4-16.png', 250),
       ('Grand Champion', 'https://trackercdn.com/cdn/tracker.gg/rocket-league/ranks/s15rank19.png', 300);

INSERT INTO "user" (name, email, password, role, mmr, avatar_url, created_at, rank)
VALUES ('Daniel', 'daniel@gmail.com', 'password123', 'user', 120, 'daniel_avatar_url', NOW(), 'Bronze'),
       ('Diogo', 'diogo@gmail.com', 'password123', 'user', 80, 'diogo_avatar_url', NOW(), 'Silver'),
       ('Andre', 'andre@gmail.com', 'password', 'admin', 210, 'andre_avatar_url', NOW(), 'Grand Champion');

INSERT INTO token (token_value, created_at, last_used, user_id)
VALUES ('0Txy7bYpM9fZaEjKsLpQrVwXuT6jM0fD', NOW(), NOW(), 1),
       ('5Rz2vWqFpYhN6sTbGmCjXeZrU0gO4oA1', NOW(), NOW(), 2),
       ('9PwQ3zHsUeLmWxN7aRyV2bYjO5iK8tSf', NOW(), NOW(), 3);

INSERT INTO match (id, isprivate, variant, created_at, black_id, white_id, state)
VALUES ('81e598e9-5031-4858-8d2c-f7f77b1264d3', false, 'FreeStyle', NOW(), 1, 2, 'WAITING'),
       ('bf4b911a-38ad-45a9-8680-135a25e40e99', true, 'FreeStyle', NOW(), 2, 3, 'WAITING'),
       ('71180c7a-2e18-4f36-a497-ba4b6842d96a', false, 'FreeStyle',NOW(), 1, 3, 'WAITING');

INSERT INTO board (match_id, turn, size, stones, type)
VALUES ('81e598e9-5031-4858-8d2c-f7f77b1264d3', 'B', 15, '', 'FreeStyleBoard'),
       ('bf4b911a-38ad-45a9-8680-135a25e40e99', 'B', 19, '', 'FreeStyleBoard'),
       ('71180c7a-2e18-4f36-a497-ba4b6842d96a', 'B', 15, '', 'FreeStyleBoard');
