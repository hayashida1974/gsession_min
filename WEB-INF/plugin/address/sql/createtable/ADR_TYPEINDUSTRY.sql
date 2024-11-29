create table ADR_TYPEINDUSTRY
(
  ATI_SID     integer         not null,
  ATI_NAME    varchar(20)     not null,
  ATI_BIKO    varchar(300),
  ATI_SORT    integer         not null,
  ATI_AUID    integer         not null,
  ATI_ADATE   timestamp      not null,
  ATI_EUID    integer         not null,
  ATI_EDATE   timestamp      not null,
  primary key (ATI_SID)
);