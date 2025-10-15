-- V3__Insert_Initial_Anime_Data.sql

INSERT INTO anime.anime (name, url, created_at)
VALUES
    ('Spirited Away', 'https://www.ghibli.jp/works/senntochihiro/', NOW()),
    ('Demon Slayer: Kimetsu no Yaiba', 'https://demonslayer-anime.com/', NOW()),
    ('One Punch Man', 'https://onepunchman-anime.net/', NOW()),
    ('Cowboy Bebop', 'https://www.cowboy-bebop.net/', NOW());



-- V3__Insert_second.sql

INSERT INTO anime.anime (name, url, created_at)
VALUES
    ('anime1', 'https://www.anime1.jp/works/senntochihiro/', NOW()),
    ('anime2', 'https://anime2.com/', NOW()),
    ('anime3', 'https://anime3.net/', NOW());
