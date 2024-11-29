create table ADR_LABEL
(
  ALB_SID     integer         not null,
  ALB_NAME    varchar(30)     not null,
  ALB_BIKO    varchar(300),
  ALB_SORT    integer         not null,
  ALB_AUID    integer         not null,
  ALB_ADATE   timestamp      not null,
  ALB_EUID    integer         not null,
  ALB_EDATE   timestamp      not null,
  ALC_SID     integer        not null,
  primary key (ALB_SID)
);