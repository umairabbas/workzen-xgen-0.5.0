# POSTGRESQL #

DROP TABLE xgen_types;

CREATE TABLE xgen_types (
   pkey           INTEGER PRIMARY KEY,
   boolean_type   BOOL,
   char_type      CHAR,
   byte_type      INT2,
   short_type     INT2,
   int_type       INT4,
   long_type      INT8,
   float_type     FLOAT8,
   double_type    NUMERIC(38,8),
   decimal_type   DECIMAL(5,5),
   string_type    VARCHAR(255),
   text_type      TEXT,
   date_type      DATE,
   time_type      TIME,
   timestamp_type TIMESTAMP,
   tcn INTEGER
);

DROP TABLE xgen_lob_types;

CREATE TABLE xgen_lob_types (
   pkey           INTEGER PRIMARY KEY,
   blob_type      LONGVARBINARY,
   clob_type      LONGVARCHAR,
   #blob_type      OID,
   #clob_type      TEXT,	
   tcn INTEGER
);


INSERT INTO xgen_types VALUES ( 
'1',
'0',
'Y',
'127',
'32767',
'2147483647',
'9223372036854775807',
'2.14748e+28',
'9.22337203685478e+28',
'12345,12345',
'hello world',
'more text',
'2001-12-12',
'01:00:00',
'2001-12-12 01:00:00'
'1'
);

INSERT INTO xgen_lob_types VALUES ( 
'1',
'some blob stuff',
'some clob stuff',
'1'
);

