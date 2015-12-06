CREATE TABLE ScoreBoard(
    name varchar(20) not null,
    score decimal not null,
    time int not null,
    timestamp timestamp default current_timestamp
);
