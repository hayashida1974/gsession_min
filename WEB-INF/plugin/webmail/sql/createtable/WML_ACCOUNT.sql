create table WML_ACCOUNT
(
  WAC_SID                integer       not null,
  WAC_TYPE               integer       not null,
  USR_SID                integer,
  WAC_NAME               varchar(200)  not null,
  WAC_ADDRESS            varchar(256)  not null,
  WAC_SEND_HOST          varchar(100),
  WAC_SEND_PORT          integer,
  WAC_SEND_USER          varchar(256),
  WAC_SEND_PASS          varchar(100),
  WAC_SEND_SSL           integer,
  WAC_RECEIVE_TYPE       integer,
  WAC_RECEIVE_HOST       varchar(100),
  WAC_RECEIVE_PORT       integer,
  WAC_RECEIVE_USER       varchar(256),
  WAC_RECEIVE_PASS       varchar(100),
  WAC_RECEIVE_SSL        integer,
  WAC_DISK               integer       not null,
  WAC_DISK_SIZE          integer       not null,
  WAC_BIKO               varchar(1000),
  WAC_ORGANIZATION       varchar(100),
  WAC_SIGN               varchar(1000),
  WAC_SIGN_POINT_KBN     integer       not null,
  WAC_SIGN_DSP_KBN       integer       not null,
  WAC_AUTOTO             varchar(256),
  WAC_AUTOCC             varchar(256),
  WAC_AUTOBCC            varchar(256),
  WAC_DELRECEIVE         integer       not null,
  WAC_RERECEIVE          integer       not null,
  WAC_APOP               integer       not null,
  WAC_SMTP_AUTH          integer       not null,
  WAC_POPBSMTP           integer       not null,
  WAC_ENCODE_SEND        integer       not null,
  WAC_AUTORECEIVE        integer       not null,
  WAC_SEND_MAILTYPE      integer       not null,
  WAC_RECEIVE_DATE       timestamp,
  WAC_JKBN               integer       not null,
  WAC_AUTO_RECEIVE_TIME  integer       not null,
  WAC_THEME              integer       not null,
  WAC_CHECK_ADDRESS      integer       not null,
  WAC_CHECK_FILE         integer       not null,
  WAC_COMPRESS_FILE      integer       not null,
  WAC_TIMESENT           integer       not null,
  WAC_QUOTES             integer       not null,
  WAC_DISK_SPS           integer       not null,
  WAC_AUTORECEIVE_AP     integer,
  WAC_TIMESENT_DEF       integer,
  WAC_COMPRESS_FILE_DEF  integer,
  WAC_SIGN_AUTO          integer       not null,
  WAC_TOP_CMD            integer       not null,
  WAC_ACCOUNT_ID         varchar(100)  not null,
  WAC_AUTH_TYPE          integer       not null,
  WAC_AUTO_SAVE_MINUTE   integer       not null,
  COT_SID                integer,
  primary key(WAC_SID)
);