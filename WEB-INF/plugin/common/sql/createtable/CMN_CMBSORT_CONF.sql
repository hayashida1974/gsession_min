create table CMN_CMBSORT_CONF
(
  CSC_USER_SKEY1     integer      not null,
  CSC_USER_ORDER1    integer      not null,
  CSC_USER_SKEY2     integer      not null,
  CSC_USER_ORDER2    integer      not null,
  CSC_GROUP_SKBN     integer      not null,
  CSC_GROUP_SKEY1    integer      not null,
  CSC_GROUP_ORDER1   integer      not null,
  CSC_GROUP_SKEY2    integer      not null,
  CSC_GROUP_ORDER2   integer      not null,
  CSC_AUID           integer      not null,
  CSC_ADATE          timestamp    not null,
  CSC_EUID           integer      not null,
  CSC_EDATE          timestamp    not null
) ;
