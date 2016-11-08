USE DrinksItDB;

-- Belongs to master Bar
INSERT into users (userName, passwordHash) values ("DrinksItWSAdmin", "9yt9LJDNuz9FsPfi1tEBzrSHw3g0Ufo+7Y5qji5fxCbHxav1tkKXG8iMFqmIRW4viziuWAjzPoXOj3EkHup7rQ==", 0);

-- Belongs to Bar 1
INSERT into users (userName, passwordHash, bar_id) values ("denis", "bPNDr/NSWqI2bB0g4yclU9yt/2CK8KhdnDdfw2CF7rNGLdTq5DAhd5hJjPXJyBmcD7/WZ8YcS/zgB+8EypHS4Q==", 1);
