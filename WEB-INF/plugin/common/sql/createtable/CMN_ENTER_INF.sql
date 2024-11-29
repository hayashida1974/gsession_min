create table CMN_ENTER_INF
(
        ENI_NAME         varchar(50),
        ENI_NAME_KN      varchar(100),
        ENI_KISYU        integer         not null,
        ENI_URL          varchar(100),
        ENI_BIKO         varchar(1000),
        BIN_SID          bigint,
        MENU_BIN_SID     bigint,
        ENI_IMG_KBN      integer         not null,
        ENI_MENU_IMG_KBN integer         not null,
        ENI_AUID         integer         not null,
        ENI_ADATE        timestamp       not null,
        ENI_EUID         integer         not null,
        ENI_EDATE        timestamp       not null
);
