create table WML_TEMPFILE
(
  WMD_MAILNUM         bigint            not null,
  WTF_SID             bigint            not null,
  WTF_FILE_NAME       varchar(260)      not null,
  WTF_FILE_PATH       varchar(20)       not null,
  WTF_FILE_EXTENSION  varchar(20),
  WTF_FILE_SIZE       bigint            not null,
  WTF_AUID            integer           not null,
  WTF_ADATE           timestamp         not null,
  WTF_EUID            integer           not null,
  WTF_EDATE           timestamp         not null,
  WTF_JKBN            integer           not null,
  WTF_HTMLMAIL        integer           not null,
  WTF_CHARSET         varchar(50),
  primary key(WMD_MAILNUM, WTF_SID)
);