    create table tbl_purchase
    (
        post_id                  bigint primary key,
        purchase_product_price   int             default 1000,
        purchase_limit_time      int          not null,
        purchase_product_count   int          not null,
        purchase_country         varchar(255) not null,
        purchase_delivery_method delivery_method default 'direct',
        constraint fk_purchase_post foreign key (post_id)
            references tbl_post (id)
    );


    select * from tbl_purchase;

    select * from tbl_report;

    select * from tbl_post;

    select * from tbl_member;

    select * from tbl_report;
    insert into tbl_report (report_content, member_id)
    values ('신고사유13', 7);

    select * from tbl_post_report;


    select *
    from view_report_post_report r
    join view_post_purchase p on p.id = r.post_id;

    select * from tbl_post;
