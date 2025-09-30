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
   ('홍콩 the K Hotel 숙박권 할인 안내','10월 5일부터 한달간 홍콩 the K Hotel 숙박권을 crewstation회원분께 20%할인된 가격으로 제공됩니다. 많은 이용바랍니다.','1');

select * from tbl_notice;