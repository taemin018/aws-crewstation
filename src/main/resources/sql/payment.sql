create table tbl_payment
(
    id               bigint generated always as identity primary key,
    member_id        bigint not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_payment_member foreign key (member_id)
        references tbl_member (id)
);