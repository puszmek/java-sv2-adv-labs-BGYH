CREATE TABLE movies(
    id BIGINT AUTO_INCREMENT,
    title VARCHAR(255),
    date_of_release DATE,
    length BIGINT,
    CONSTRAINT pk_movies PRIMARY KEY(id)
    );