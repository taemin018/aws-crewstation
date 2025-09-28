-- 멤버
insert into tbl_member (member_name, member_phone, member_email, member_birth, member_password, member_description)
values ('test', '01012341234', 'test@ac.kr', '20000113', '1234', '테스트 데이터 입니다.');

insert into tbl_member (member_name, member_phone, member_email, member_birth, member_password, member_description)
values ('test2', '01012341234', 'test2@ac.kr', '20000113', '1234', '테스트 데이터 입니다.');

SELECT id FROM tbl_diary_country_path ORDER BY id;   -- 경로 존재 확인
SELECT id FROM tbl_post ORDER BY id;                 -- 포스트 존재 확인
SELECT * FROM tbl_diary ORDER BY post_id;

select id, country_start_date, country_end_date, member_id, country_id
from tbl_diary_country_path
order by id;

-- 다이어리
insert into tbl_diary
(post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
    overriding system value
values (1, 'public', 5, 20, 13);


insert into tbl_diary
(post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
    overriding system value
values (2, 'public', 5, 20, 13);

-- 다이어리 나러 경로
insert into tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
values ('2025-01-01', '2025-01-10', 1, 1), -- 한국 여행
       ('2025-02-15', '2025-02-20', 2, 2); -- 일본 여행

select * from tbl_post;


-- 1) 멤버 (작성자)
insert into tbl_member (id, member_name, member_phone, member_email, member_birth, member_password, member_description)
values (1, 'testUser', '01012345678', 'test@ac.kr', '2000-01-01', '1234', '더미 유저')
on conflict do nothing;

-- 2) 게시글
insert into tbl_post (id, post_title, member_id, created_datetime, updated_datetime)
values (1, '테스트 다이어리 제목', 1, now(), now())
on conflict do nothing;

-- 3) 다이어리
insert into tbl_diary (post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
values (1, 'public', 5, 2, 1)
on conflict do nothing;

-- 4) 게시글 섹션 + 파일 (이미지)
insert into tbl_post_section (id, post_content, post_id)
values (1, '본문 내용입니다', 1)
on conflict do nothing;

insert into tbl_file (id, file_origin_name, file_path, file_name, created_datetime, updated_datetime)
values (1, 'origin.png', '/2025/09/27/', 'file1.png', now(), now())
on conflict do nothing;

insert into tbl_post_section_file (file_id, post_section_id, image_type)
values (1, 1, 'main')
on conflict do nothing;

-- 5) 크루 + 크루 다이어리
insert into tbl_crew (id, crew_name, crew_description, crew_member_count, created_datetime, updated_datetime)
values (1, '여행크루', '테스트 크루입니다', 10, now(), now())
on conflict do nothing;

insert into tbl_crew_diary (crew_id, diary_id)
values (1, 1)
on conflict do nothing;

-- 6) 좋아요
insert into tbl_like (post_id, member_id)
values (1, 1)
on conflict do nothing;


insert into tbl_post (post_title, member_id)
values ('test1', 1),
       ('test2', 1),
       ('test3', 2),
       ('test4', 2),
       ('test5', 2),
       ('test6', 1),
       ('test7', 2),
       ('test8', 2),
       ('test9', 2),
       ('test10', 1),
       ('test11', 2),
       ('test12', 2),
       ('test13', 2),
       ('test14', 1);

insert into tbl_post_section (post_content, post_id)
values ('test1', 1),
       ('test2', 2),
       ('test3', 3),
       ('test4', 4),
       ('test5', 5),
       ('test6', 6),
       ('test7', 7),
       ('test8', 8),
       ('test9', 9),
       ('test10', 10),
       ('test11', 11),
       ('test12', 12),
       ('test13', 13),
       ('test14', 14);

insert into tbl_file(file_origin_name, file_path, file_name)
values ('origin1', 'path1', 'name1'),
       ('origin2', 'path2', 'name2'),
       ('origin3', 'path3', 'name3'),
       ('origin4', 'path4', 'name4'),
       ('origin5', 'path5', 'name5'),
       ('origin6', 'path6', 'name6'),
       ('origin7', 'path7', 'name7'),
       ('origin8', 'path8', 'name8'),
       ('origin9', 'path9', 'name9'),
       ('origin10', 'path10', 'name10'),
       ('origin11', 'path11', 'name11'),
       ('origin12', 'path12', 'name12'),
       ('origin13', 'path13', 'name13'),
       ('origin14', 'path14', 'name14');

insert into tbl_post_section_file(file_id, post_section_id, image_type)
values (1, 1, 'main'),
       (2, 2, 'main'),
       (3, 3, 'main'),
       (4, 4, 'main'),
       (5, 5, 'main'),
       (6, 6, 'main'),
       (7, 7, 'main'),
       (8, 8, 'main'),
       (9, 9, 'main'),
       (10, 10, 'main'),
       (11, 11, 'main'),
       (12, 12, 'main'),
       (13, 13, 'main'),
       (14, 14, 'main');

insert into tbl_purchase (post_id, purchase_limit_time, purchase_product_count, purchase_country,
                          purchase_product_price, purchase_delivery_method)
values (1, 24, 10, '호주', 10000, 'direct'),
       (2, 24, 10, '미국', 10000, 'parcel'),
       (3, 24, 10, '한국', 10000, 'direct'),
       (4, 24, 10, '일본', 10000, 'parcel'),
       (5, 24, 10, '필리핀', 10000, 'direct'),
       (6, 24, 10, '캐나다', 10000, 'parcel'),
       (7, 24, 10, '보라카이', 10000, 'direct'),
       (8, 24, 10, '화와이', 10000, 'parcel'),
       (9, 24, 10, '런던', 10000, 'direct'),
       (10, 24, 10, '영국', 10000, 'parcel'),
       (11, 24, 10, '중국', 10000, 'direct'),
       (12, 24, 10, '대만', 10000, 'parcel'),
       (13, 24, 10, '홍콩', 10000, 'direct'),
       (14, 24, 10, '러시아', 10000, 'parcel');
set timezone = 'Asia/Seoul';
select now();

select purchase_limit_time, created_datetime
from tbl_post p
         join tbl_purchase tp on p.id = tp.post_id
WHERE p.created_datetime + (tp.purchase_limit_time || 'hour')::interval > NOW();
;
select p.created_datetime + (tp.purchase_limit_time || 'hour')::interval, now()
from tbl_post p
         join tbl_purchase tp on p.id = tp.post_id



insert into tbl_file(file_origin_name, file_path, file_name,file_size)
values ('1qweasd123cs.png', '2025/09/25/1qweasd123cs.png', '1qweasd123cs.png',1000),
       ('ansnaxxc.png', '2025/09/25/ansnaxxc.png', 'ansnaxxc.png',1000);


insert into tbl_post_section (post_content, post_id)
values ('multi',1),('multi',1),('multi',1);

select * from tbl_member;

insert into tbl_address(address_zip_code, address_detail, address, member_id)
values (06226,'서울특별시 강남구 역삼동 771','서울특별시 강남구 역삼로 234 (역삼동)',2);

insert into tbl_crew (crew_name, crew_description, crew_member_count, created_datetime, updated_datetime)
values ('test', '테스트 입니다1', '5', '2025-09-26','2025-09-27');

insert into tbl_crew (crew_name, crew_description, crew_member_count, created_datetime, updated_datetime)
values ('test1', '테스트 입니다1', '3', '2025-09-24','2025-09-27');

insert into tbl_like (post_id, member_id)
values ('1','1');

insert into tbl_diary (diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
values ('public','')




