create table WML_MAIL_TEMPLATE_FILE
(
  WTP_SID   integer not null,
  BIN_SID    bigint not null,
  primary key(WTP_SID, BIN_SID)
);
