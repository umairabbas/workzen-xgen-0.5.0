# ORACLE #

#DROP TABLE xgen_types;

CREATE TABLE xgen_types (
 pkey INTEGER NOT NULL,
 boolean_type NUMBER(1),
 char_type CHAR,
 byte_type NUMBER(3),
 short_type NUMBER(5),
 int_type NUMBER(10),
 long_type NUMBER(20),
 float_type NUMBER(38,8),  
 double_type NUMBER(38,8),
 decimal_type NUMBER(38,8), 
 string_type VARCHAR2(255),
 text_type VARCHAR2(1000),
 date_type DATE,
 time_type DATE,
 timestamp_type DATE,
 tcn NUMBER(8),
 PRIMARY KEY(pkey)
);

#DROP TABLE xgen_lob_types;

CREATE TABLE xgen_lob_types (
 pkey INTEGER NOT NULL,
 blob_type BLOB,
 clob_type CLOB,
 tcn NUMBER(8),
 PRIMARY KEY(pkey)
);

# NOTE: even though the precision is set to 38, 1.1E+29 only creates 30 places ???

INSERT INTO xgen_types (pkey) VALUES(1);
UPDATE xgen_types SET boolean_type   = 0 WHERE pkey = 1;
UPDATE xgen_types SET byte_type      = 127 WHERE pkey = 1;
UPDATE xgen_types SET char_type      = 'Y';
UPDATE xgen_types SET short_type     = 32767 WHERE pkey = 1;
UPDATE xgen_types SET int_type       = 21474836 WHERE pkey = 1;
UPDATE xgen_types SET long_type      = 9223372036854775 WHERE pkey = 1;
UPDATE xgen_types SET float_type     = 11.123456789E+28 WHERE pkey = 1;
UPDATE xgen_types SET double_type    = 1.1E+29 WHERE pkey = 1;
UPDATE xgen_types SET decimal_type   = 1.1E+29 WHERE pkey = 1;
UPDATE xgen_types SET string_type    = 'hello world' WHERE pkey = 1;
UPDATE xgen_types SET text_type      = 'some longer text' WHERE pkey = 1;
UPDATE xgen_types SET date_type      = '12 Dec 2001' WHERE pkey = 1;
UPDATE xgen_types SET time_type      = TO_DATE('12-Dec-2001 01:00:00','DD-MON-YYYY HH:MI:SS') WHERE pkey = 1;
UPDATE xgen_types SET timestamp_type = TO_DATE('12-Dec-2001 01:00:00','DD-MON-YYYY HH:MI:SS') WHERE pkey = 1;
UPDATE xgen_types SET tcn = 1 WHERE pkey = 1;

INSERT INTO xgen_lob_types (pkey) VALUES(1);
UPDATE xgen_lob_types SET blob_type      = 'AC183E6A' WHERE pkey = 1;
UPDATE xgen_lob_types SET clob_type      = 'clobber' WHERE pkey = 1;

COMMIT;
