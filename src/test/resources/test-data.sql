CREATE TABLE discord_user(
id PRIMARY KEY,
currency,
discriminator,
username,
nickname
);

INSERT INTO discord_user
(`id`,
`currency`,
`discriminator`,
`username`
`nickname`)
VALUES
(1,
0,
"1234",
"Username",
"Nickname");