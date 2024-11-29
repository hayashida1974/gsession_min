create table WML_MAIL_TEMPLATE
(
  WTP_SID   integer not null,
  WTP_TYPE  integer not null,
  WAC_SID   integer,
  WTP_NAME  varchar(100) not null,
  WTP_TITLE varchar(1000),
  WTP_BODY  text,
  WTP_ORDER integer not null,
  WTP_AUID  integer not null,
  WTP_ADATE timestamp not null,
  WTP_EUID  integer not null,
  WTP_EDATE timestamp not null,
  primary key(WTP_SID)
);
