create table RNG_TEMPLATE_KEIRO_CONDITION
(
  RTK_SID integer not null,
  RKC_IFTEMPLATE integer not null,
  RKC_IFGROUP integer not null,
  RKC_IFPOS integer not null,
  RKC_IFFORM varchar(20) ,
  RKC_IFFORM_OPR varchar(10) ,
  RKC_IFFORM_VALUE varchar(20),
  RKC_ORID integer not null
);