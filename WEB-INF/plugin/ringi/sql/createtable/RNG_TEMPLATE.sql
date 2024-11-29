create table RNG_TEMPLATE
(
  RTP_SID          integer            not null,
  RTP_TYPE         integer            not null,
  USR_SID          integer,
  RTP_TITLE        varchar(100)       not null,
  RTP_RNG_TITLE    varchar(100),
  RTP_SORT         integer            not null,
  RTP_AUID         integer            not null,
  RTP_ADATE        timestamp          not null,
  RTP_EUID         integer            not null,
  RTP_EDATE        timestamp          not null,
  RTC_SID          integer            not null,
  RTP_VER          integer            not null,
  RTP_MAXVER_KBN   integer            not null,
  RTP_JKBN         integer            not null,
  RTP_IDFORMAT_SID integer            not null,
  RTP_FORM         text                       ,
  RCT_SID          integer            not null,
  RCT_USR_SID      integer            not null,
  RTP_BIKO         varchar(1000)      ,
  RTP_IDMANUAL     integer     not null,
  RTP_SPEC_VER     integer     not null,
  RCT_VER          integer     not null default 0,
  RTP_USE_APICONNECT integer     not null,
  RTP_APICONNECT_COMMENT varchar(100) ,

  primary key (RTP_SID, RTP_VER)
) ;