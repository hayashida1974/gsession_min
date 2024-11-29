create table CMN_DATAUSED
(
  CDU_PLUGIN varchar(20) not null,
  CDU_SIZE   bigint      not null,
  primary key(CDU_PLUGIN)
);