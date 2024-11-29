 create table CMN_FIREWALL_CONF(
    CFC_ALLOW_IP          text           not null default '',
    CFC_ALLOW_MBL         integer        not null default 0,
    CFC_ALLOW_MBL_PLUGIN  integer        not null default 0,
    CFC_ALLOW_ANP         integer        not null default 0
 ) ;
