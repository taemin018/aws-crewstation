create table tbl_payment_status
(
    id               bigint generated always as identity primary key,
    payment_phase    phase     default 'request',
    purchase_id      bigint not null,
    payment_id       bigint not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_payment_status_purchase foreign key (purchase_id)
        references tbl_purchase (post_id),
    constraint fk_payment_status_payment foreign key (payment_id)
        references tbl_payment (id)
);