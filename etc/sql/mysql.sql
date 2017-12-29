#
# Table structure for table 'x2j_typetest' MySql 3.23
#

DROP TABLE IF EXISTS xgen_types;

CREATE TABLE xgen_types (
   pkey           INT(8) NOT NULL,
   boolean_type   INT(1),
   char_type      CHAR,
   byte_type      TINYINT(4),
   short_type     SMALLINT(6),
   int_type       INT,
   long_type      BIGINT(20),
   float_type     FLOAT,
   double_type    DOUBLE,
   decimal_type   DECIMAL(5,5),
   string_type    VARCHAR(255),	
   text_type      TEXT,
   date_type      DATE DEFAULT '0000-00-00',
   time_type      TIME DEFAULT '00:00:00',
   timestamp_type DATETIME,
   tcn            INT(8),
   PRIMARY KEY (pkey)
);

DROP TABLE IF EXISTS xgen_lob_types;

CREATE TABLE xgen_lob_types (
   pkey           INT(8) NOT NULL,
   blob_type			BLOB,
   clob_type			TEXT,
   tcn            INT(8),
   PRIMARY KEY (pkey)
);


INSERT INTO xgen_types VALUES ( 
'1',
'0',
'Y',
'127',
'32767',
'2147483647',
'9223372036854775807',
'1.123456789e+30',
'1.123456789e+30',
'12345.12345',
'hello world',
'longer text',
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




