create table CMN_FILE_CONF
(
        FIC_MAX_SIZE integer   not null,
        FIC_AUID     integer   not null,
        FIC_ADATE    timestamp not null,
        FIC_EUID     integer   not null,
        FIC_EDATE    timestamp not null,
        FIC_PHOTO_SIZE varchar(4) not null
) ;