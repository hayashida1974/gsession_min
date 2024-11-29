create table WML_FILTER_FWADDRESS
(
  WFT_SID               integer not null,
  WFA_NO                integer not null,
  WFA_ADDRESS      varchar(256) not null,
  primary key(WFT_SID, WFA_NO)
);