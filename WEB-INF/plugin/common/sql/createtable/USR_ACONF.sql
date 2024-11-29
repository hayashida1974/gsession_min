create table USR_ACONF
(
        UAD_EXPORT      integer,
        UAD_AUID        integer,
        UAD_ADATE       timestamp,
        UAD_EUID        integer,
        UAD_EDATE       timestamp,
        UAD_QR_KBN      integer default 1 not null
) ;