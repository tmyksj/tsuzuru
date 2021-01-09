create table tbl_item
(
    uuid               varchar(36)  not null,
    tbl_user_uuid      varchar(36)  not null,
    body               varchar(255) not null,
    written_date       timestamp(6) not null default 0,
    created_date       timestamp(6) not null default 0,
    last_modified_date timestamp(6) not null default 0,
    primary key (uuid),
    foreign key (tbl_user_uuid) references tbl_user (uuid) on delete cascade on update cascade
) engine = InnoDB
  default charset utf8mb4;
