CREATE TABLE temperature_and_humidity (
    id          INTEGER  PRIMARY KEY AUTOINCREMENT
                         NOT NULL
                         UNIQUE,
    date        DATETIME NOT NULL
                         UNIQUE,
    temperature DOUBLE,
    humidity    DOUBLE
);