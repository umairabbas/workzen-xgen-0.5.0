-
- DB2 Table structure for table 'xgen_types'
-

CONNECT TO sample USER db2admin USING db2admin ;

DROP TABLE xgen_types;

CREATE TABLE xgen_types (
   pkey           INTEGER NOT NULL,
   boolean_type   SMALLINT,
   char_type      CHAR(1),
   byte_type      SMALLINT,
   short_type     SMALLINT,
   int_type       INTEGER,
   long_type      BIGINT,
   float_type     FLOAT,
   double_type    DOUBLE,
   decimal_type   DECIMAL(5,5),
   string_type    VARCHAR(255),
   text_type      LONG VARCHAR,
   date_type      DATE,
   time_type      TIME,
   timestamp_type TIMESTAMP,
   tcn            INTEGER,
   PRIMARY KEY (pkey)
);

DROP TABLE xgen_lob_types;

CREATE TABLE xgen_lob_types (
   pkey           INTEGER NOT NULL,
   blob_type      BLOB,
   clob_type      CLOB,
   tcn            INTEGER,
   PRIMARY KEY (pkey)
);

INSERT INTO xgen_types (pkey) VALUES(1) ;
UPDATE xgen_types SET boolean_type = 0 WHERE pkey = 1;
UPDATE xgen_types SET char_type    = 'Y' WHERE pkey = 1;
UPDATE xgen_types SET byte_type    = 127 WHERE pkey = 1;
UPDATE xgen_types SET short_type   = 32767 WHERE pkey = 1;
UPDATE xgen_types SET int_type     = 21474836 WHERE pkey = 1;
UPDATE xgen_types SET long_type    = 9223372036854775 WHERE pkey = 1;
UPDATE xgen_types SET float_type   = 11.123456789E+28 WHERE pkey = 1;
UPDATE xgen_types SET double_type  = 1.1E+29 WHERE pkey = 1;
UPDATE xgen_types SET decimal_type = 12345.12345 WHERE pkey = 1;
UPDATE xgen_types SET string_type  = 'hello world' WHERE pkey = 1;
UPDATE xgen_types SET text_type    = 'more text' WHERE pkey = 1;
UPDATE xgen_types SET date_type      = '2001-12-12' WHERE pkey = 1;
UPDATE xgen_types SET time_type      = '2001-12-12 01:00:00' WHERE pkey = 1;
UPDATE xgen_types SET timestamp_type = '2001-12-12 01:00:00' WHERE pkey = 1;
UPDATE xgen_types SET tcn = 1 WHERE pkey = 1;

INSERT INTO xgen_lob_types (pkey) VALUES(1) ;
UPDATE xgen_lob_types SET blob_type = 'some blob stuff' WHERE pkey = 1;
UPDATE xgen_lob_types SET clob_type = 'some clob stuff' WHERE pkey = 1;
UPDATE xgen_lob_types SET tcn = 1 WHERE pkey = 1;



