DELETE FROM PROGRAMS;

INSERT INTO PROGRAMS (ID, NAME, VOLUME, TEMPERATURE, WASHING_TIME) VALUES
(1, 'daily', 100, 60, 2400),
(2, 'quick', 100, 60, 900),
(3, 'delicate', 100, 30, 2400),
(4, 'sports', 100, 40, 1800);

DELETE FROM ACTIONS;

INSERT INTO ACTIONS (ID, NAME, DESCRIPTION) VALUES
(1, 'drain', 'Drain water'),
(2, 'unlock', 'Unlock the machine'),
(3, 'run', 'Run a washing program');