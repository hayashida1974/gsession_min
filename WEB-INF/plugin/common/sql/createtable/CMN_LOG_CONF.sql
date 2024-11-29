create table CMN_LOG_CONF
(
        LGC_PLUGIN      varchar(20)     not null,
        LGC_LEVEL_ERROR integer         not null,
        LGC_LEVEL_WARN  integer         not null,
        LGC_LEVEL_INFO  integer         not null,
        LGC_LEVEL_TRACE integer         not null,
        LGC_AUID        integer         ,
        LGC_ADATE       timestamp      ,
        LGC_EUID        integer         ,
        LGC_EDATE       timestamp      ,
        primary key (LGC_PLUGIN)
);