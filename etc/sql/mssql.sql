DROP TABLE xgen_types;

CREATE TABLE xgen_types (
   pkey           INTEGER NOT NULL,
   boolean_type   BIT,
   char_type      CHAR,
   byte_type      TINYINT,
   short_type     SMALLINT,
   int_type       INT,
   long_type      NUMERIC(28),
   float_type     FLOAT,
   double_type    NUMERIC(28,2),
   decimal_type   DECIMAL(5,5),
   string_type    VARCHAR(255),
   text_type      TEXT,
   date_type      DATETIME,
   time_type      DATETIME,
   timestamp_type DATETIME,
   tcn            INTEGER,
   PRIMARY KEY (pkey)
);

DROP TABLE xgen_lob_types;

CREATE TABLE xgen_lob_types (
   pkey           INTEGER NOT NULL,
   blob_type      IMAGE,
   clob_type      TEXT,
   tcn            INTEGER,
   PRIMARY KEY (pkey)
);


#
# The initial insert wants a value for boolean
# 

INSERT INTO xgen_types (pkey, boolean_type) VALUES(1,1);

##UPDATE xgen_types SET boolean_type = 0 WHERE pkey = 1;
UPDATE xgen_types SET char_type    = 'Y' WHERE pkey = 1;
UPDATE xgen_types SET byte_type    = 127 WHERE pkey = 1;
UPDATE xgen_types SET short_type   = 32767 WHERE pkey = 1;
UPDATE xgen_types SET int_type     = 21474836 WHERE pkey = 1;
UPDATE xgen_types SET long_type    = 9223372036854775 WHERE pkey = 1;
UPDATE xgen_types SET float_type   = 123456789.123456789 WHERE pkey = 1;
UPDATE xgen_types SET double_type  = 1.11E+24 WHERE pkey = 1;
UPDATE xgen_types SET decimal_type = .12345 WHERE pkey = 1;
UPDATE xgen_types SET string_type  = 'hello world' WHERE pkey = 1;
UPDATE xgen_types SET text_type    = 'more text' WHERE pkey = 1;
UPDATE xgen_types SET date_type      = '12 Dec 2001' WHERE pkey = 1;
UPDATE xgen_types SET time_type      = '12-Dec-2001 01:00:00' WHERE pkey = 1;
UPDATE xgen_types SET timestamp_type = '12-Dec-2001 01:00:00' WHERE pkey = 1;
UPDATE xgen_types SET tcn = 1 WHERE pkey = 1;


INSERT INTO xgen_lob_types (pkey) VALUES(1);

UPDATE xgen_lob_types SET blob_type = 'some blob stuff' WHERE pkey = 1;
UPDATE xgen_lob_types SET clob_type = 'some clob stuff' WHERE pkey = 1;
UPDATE xgen_lob_types SET tcn = 1 WHERE pkey = 1;


