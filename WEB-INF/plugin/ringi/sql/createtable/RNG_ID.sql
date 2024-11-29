create table RNG_ID
( RID_SID integer NOT NULL
, RID_FORMAT varchar(120) NOT NULL
, RID_INIT integer NOT NULL
, RID_MANUAL integer NOT NULL
, RID_RESET integer NOT NULL
, RID_ZERO integer NOT NULL
, RID_TITLE varchar(50) NOT NULL
, RID_USE_DATE timestamp
, primary key   (RID_SID)
);