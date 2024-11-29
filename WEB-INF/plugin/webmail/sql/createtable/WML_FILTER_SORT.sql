create table WML_FILTER_SORT
(
  WAC_SID         integer      not null,
  WFT_SID         integer      not null,
  WFS_SORT        bigint       not null,
  primary key (WAC_SID, WFT_SID)
);