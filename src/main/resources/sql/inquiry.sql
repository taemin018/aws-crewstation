create table tbl_inquiry
(
    id               bigint generated always as identity primary key,
    inquiry_title    varchar(255) not null,
    inquiry_content  varchar(255) not null,
    member_id        bigint       not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_inquiry_member foreign key (member_id)
        references tbl_member (id)
);

select * from tbl_inquiry;

insert into tbl_inquiry (inquiry_title, inquiry_content, member_id)
VALUES ('질문있어요','여행 크루가 연락이 끊겼어요',1);