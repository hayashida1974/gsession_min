create table CMN_LOG_DEL_CONF
(
        LDC_ADL_KBN      integer    not null,
        LDC_ADL_YEAR     integer,
        LDC_ADL_MONTH    integer,
        LDC_AUID         integer    not null,
        LDC_ADATE        timestamp  not null,
        LDC_EUID         integer    not null,
        LDC_EDATE        timestamp  not null
);