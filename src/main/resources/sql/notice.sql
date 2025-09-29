create table tbl_notice
(
    id               bigint generated always as identity primary key,
    notice_title     varchar(255) not null,
    notice_content   varchar(255) not null,
    member_id        bigint       not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_notice_member foreign key (member_id)
        references tbl_member (id)
);


insert into tbl_notice (notice_title, notice_content, member_id) VALUES
   ('업데이트 일정 공지','업데이트 일정 공지합니다. 25.10.16. 업데이트 공개 예정입니다.','1');