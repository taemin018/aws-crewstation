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
   ('업데이트 일정 공지','업데이트 일정 공지합니다. 10월 16일부터 홈페이지가 대폭 수정되어 업데이트 됩니다. 많은 관심 부탁드립니다.','1');

select * from tbl_notice;