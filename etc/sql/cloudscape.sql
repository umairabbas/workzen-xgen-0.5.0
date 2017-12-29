--
-- Cloudscape schema
--

DROP TABLE xgen_types;

CREATE TABLE xgen_types (
   pkey           INTEGER NOT NULL,
   boolean_type   BOOLEAN,
   char_type      CHAR,
   byte_type      TINYINT,
   short_type     SMALLINT,
   int_type       INTEGER,
   long_type      LONGINT,
   float_type     REAL,
   double_type    DOUBLE PRECISION,
   decimal_type   DECIMAL,
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
   BLOB_TYPE      LONG BIT VARYING,
   CLOB_TYPE      LONG VARCHAR,
   tcn            INTEGER,
   PRIMARY KEY (pkey)
);

INSERT INTO xgen_types VALUES ( 
'1',
'true',
'Y',
'127',
'32767',
'2147483647',
'9223372036854775807',
'1.123456789e+30',
'1.123456789e+30',
'1.123456789e+30',
'hello world',
'more text',
'2001-12-12', 
'01:00:00',
'2001-12-12 01:01:01',
'1'
);

INSERT INTO xgen_lob_types VALUES (
'1',
'blob stuff',
'clob stuff',
'1'
);
