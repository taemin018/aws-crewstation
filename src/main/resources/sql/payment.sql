create table tbl_payment
(
    id               bigint generated always as identity primary key,
    member_id        bigint not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_payment_member foreign key (member_id)
        references tbl_member (id)
);

alter table tbl_payment
    add column  payment_status_id bigint not null;

alter table tbl_payment
      add constraint fk_payment_payment_status foreign key (payment_status_id) references tbl_payment_status(id);