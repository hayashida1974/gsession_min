create table WML_FILTER_CONDITION
(
  WFT_SID         integer      not null,
  WFC_NUM         integer      not null,
  WFC_TYPE        integer      not null,
  WFC_EXPRESSION  integer      not null,
  WFC_TEXT        varchar(256) not null,
  primary key(WFT_SID, WFC_NUM)
);