CREATE TABLE bookmarks
(
    id         BIGINT                                    NOT NULL,
    title      VARCHAR(200)                              NOT NULL,
    url        VARCHAR(500)                              NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    updated_at TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_bookmarks PRIMARY KEY (id)
);

CREATE TABLE anime
(
    id         BIGINT                                    NOT NULL,
    name      VARCHAR(200)                              NOT NULL,
    url        VARCHAR(500)                              NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW() NOT NULL,
    CONSTRAINT pk_anime PRIMARY KEY (id)
);



