create table tbl_guest
(
    id bigint generated always as identity primary key,
    member_id bigint not null,
    guest_phone varchar(255) not null,
    guest_order_number varchar(255) not null,
    address_zip_code char(5)      not null,
    address_detail   varchar(255) not null,
    address          varchar(255) not null,
    constraint fk_guest_member foreign key (member_id)
        references tbl_member (id)
);


select * from tbl_guest;