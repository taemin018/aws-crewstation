create table tbl_member
(
    id                  bigint generated always as identity primary key,
    member_name         varchar(255) not null,
    member_phone        varchar(255),
    member_email        varchar(255) unique,
    member_social_url   varchar(255),
    member_birth        varchar(8),
    member_gender       gender          default 'male',
    member_mbti         char(4),
    member_password     varchar(255),
    member_status       status          default 'active',
    member_provider     member_provider default 'crewstation',
    social_img_url       varchar(255),
    member_social_email varchar(255) unique,
    member_description  varchar(255),
    member_role         member_role     default 'member',
    chemistry_score int default 70,
    created_datetime    timestamp       default now(),
    updated_datetime    timestamp       default now()
);




alter table tbl_member add column member_description varchar(255);


alter table tbl_member
    add column  chemistry_score int default 70;

ALTER TABLE tbl_member RENAME COLUMN kakao_img_url TO social_img_url;

