create table CMN_DATAFOLDER
(
  CDF_NAME        varchar(20)  not null,
  CDF_SIZE         bigint  not null,
  CDF_SORT        integer  not null,
  primary key(CDF_NAME)
);