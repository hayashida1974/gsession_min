create table ADR_BELONG_LABEL
(
  ADR_SID     integer         not null,
  ALB_SID     integer         not null,
  ABL_AUID    integer         not null,
  ABL_ADATE   timestamp      not null,
  ABL_EUID    integer         not null,
  ABL_EDATE   timestamp      not null,
  primary key (ADR_SID, ALB_SID)
);