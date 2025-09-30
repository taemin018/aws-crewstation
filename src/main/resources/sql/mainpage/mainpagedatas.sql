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
values (4, 'public', 5, 20, 13);


insert into tbl_diary
(post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
    overriding system value
values (5, 'public', 5, 20, 13);

-- 다이어리 나러 경로
insert into tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
values ('2025-01-01', '2025-01-10', 1, 1), -- 한국 여행
       ('2025-02-15', '2025-02-20', 2, 2); -- 일본 여행

select * from tbl_post;


-- 1) 멤버 (작성자)
insert into tbl_member (id, member_name, member_phone, member_email, member_birth, member_password, member_description)
values (2, 'testUser', '01012345678', 'test@ac.kr', '2000-01-01', '1234', '더미 유저')
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

insert into tbl_post_section_file(post_section_id, image_type)
values ( 1, 'main'),
       ( 2, 'main'),
       ( 3, 'main'),
       ( 4, 'main'),
       ( 5, 'main'),
       ( 6, 'main'),
       ( 7, 'main'),
       ( 8, 'main');


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
         join tbl_purchase tp on p.id = tp.post_id;



insert into tbl_file(file_origin_name, file_path, file_name,file_size)
values ('1qweasd123cs.png', '2025/09/25/1qweasd123cs.png', '1qweasd123cs.png',1000),
       ('ansnaxxc.png', '2025/09/25/ansnaxxc.png', 'ansnaxxc.png',1000);


insert into tbl_post_section (post_content, post_id)
values ('multi',3),('multi',4),('multi',5);

select * from tbl_member;

insert into tbl_address(address_zip_code, address_detail, address, member_id)
values (06226,'서울특별시 강남구 역삼동 771','서울특별시 강남구 역삼로 234 (역삼동)',2);

insert into tbl_crew (crew_name, crew_description, crew_member_count, created_datetime, updated_datetime)
values ('test', '테스트 입니다1', '5', '2025-09-26','2025-09-27');

insert into tbl_crew (crew_name, crew_description, crew_member_count, created_datetime, updated_datetime)
values ('test1', '테스트 입니다1', '3', '2025-09-24','2025-09-27');

select * from tbl_crew_file;
select * from tbl_crew;
select * from tbl_file;
select * from tbl_diary;
select * from view_file_post_section_file;
select * from view_file_post_section_file;


select d.post_id,
       f.file_path



from tbl_diary d
        join tbl_file f
            on f.id = d.post_id;







insert into tbl_like (post_id, member_id)
values ('1','1');

insert into tbl_diary (diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
values ('public','')


select * from tbl_member;
select * from tbl_diary;
select * from tbl_like;
select * from tbl_post_section;
select * from tbl_file;
select * from tbl_post_section_file;
select * from view_post_purchase;
select * from view_file_post_section_file;


-- 1. 회원 (일기 작성자 & 좋아요 누르는 사람)
INSERT INTO tbl_member (member_name, member_email, member_password, social_img_url)
VALUES ('lisa', 'lisa@tes.com', 'pass023', 'https://dummyimage.com/100x100/000/fff&text=lisa');

-- 2. 게시글 (Alice가 작성한 다이어리 글)
INSERT INTO tbl_post (post_title, member_id)
VALUES ('My First Diary', 1);

INSERT INTO tbl_country (country_name)
VALUES ('Korea');

INSERT INTO tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
VALUES ('2025-01-01', '2025-01-31', 1, 1);

-- 3. 일기 (게시글과 1:1 연결)
INSERT INTO tbl_diary (post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
VALUES (1, 'public', 5, 2, 1);

-- 4. 좋아요 (Alice 본인이 자기 글 좋아요 누른 상황)
INSERT INTO tbl_like (post_id, member_id)
VALUES (1, 1);

-- 5. 게시글 섹션 (본문 작성)
INSERT INTO tbl_post_section (post_content, post_id)
VALUES ('This is my first diary content', 2);

-- 6. 파일 (대표 이미지)
INSERT INTO tbl_file (file_origin_name, file_path, file_name, file_size)
VALUES (
           'diary1.png',
           '/images/diary1.png',
           'diary1_20240926.png',
           '2048'
       );

-- 7. 섹션-파일 연결 (대표 이미지 = main)
INSERT INTO tbl_post_section_file (post_section_id, image_type)
VALUES (5,  'main');

insert into tbl_crew_diary ( crew_id, diary_id)
values ('4', '1');

insert into tbl_banner ( banner_order)
values ('7');

select * from tbl_member;
select * from tbl_crew;
select * from tbl_file;
select * from tbl_crew_file;

select * from tbl_crew_member;

insert into tbl_crew_file (file_id, crew_id)
values (5,3);

insert into tbl_crew_member (crew_role, crew_id, member_id)
values ('partner', 3, 2);

select * from view_file_crew_file;

-- 멤버
insert into tbl_member(member_name, social_img_url)
values ('test1', 'https://example.com/img/test1.png'),
       ('test2', 'https://example.com/img/test2.png');

-- 파일
insert into tbl_file(file_origin_name, file_path, file_name, file_size)
values ('crew1.png', '/upload/crew', 'crew1.png', '100kb'),
       ('crew2.png', '/upload/crew', 'crew2.png', '120kb');

-- 크루
insert into tbl_crew(crew_name, crew_description, crew_member_count)
values ('여행크루A', '즐거운 여행 함께해요', 2);

-- 크루 멤버 (멤버 1,2를 크루에 연결)
insert into tbl_crew_member(crew_role, crew_id, member_id)
values ('leader', 1, 1),
       ('partner', 1, 2);

-- 크루 파일 (첫 번째 파일을 크루에 연결)
insert into tbl_crew_file(file_id, crew_id)
values (2, 2);

select * from tbl_crew_file;

-- 게시글 (카드리스트 4개)
insert into tbl_post(post_title, post_read_count, post_status, member_id)
values ('일본 오사카 여행 모집', 0, 'active', 1),
       ('유럽 투어 동행 구해요', 0, 'active', 2),
       ('베트남 다낭 힐링', 0, 'active', 1),
       ('미국 서부 로드트립', 0, 'active', 2);

-- accompany (게시글과 1:1)
insert into tbl_accompany(accompany_status, accompany_age_range, post_id)
values ('short', 20, 2),
       ('long', 30, 2),
       ('short', 25, 3),
       ('long', 35, 4);

insert into tbl_accompany(accompany_status, accompany_age_range, post_id)
values ('short', 20, 1);

insert into tbl_accompany(accompany_status, accompany_age_range, post_id)
    overriding system value
select v.status::accompany_status, v.age, p.id
from tbl_post p
         join (
    values
        ('일본 오사카 여행 모집', 'short', 20),
        ('유럽 투어 동행 구해요', 'long',  30),
        ('베트남 다낭 힐링',     'short', 25),
        ('미국 서부 로드트립',   'long',  35)
) as v(title, status, age)
              on p.post_title = v.title;


select * from tbl_post;
select * from tbl_accompany;

-- 나라
insert into tbl_country(country_name)
values ('일본'),
       ('프랑스'),
       ('베트남'),
       ('미국');

-- accompany_path (각 게시글에 나라 연결)
insert into tbl_accompany_path(country_start_date, country_end_date, accompany_id, country_id)
values ('2025-10-01', '2025-10-05', 1, 1), -- 일본
       ('2025-11-10', '2025-11-20', 2, 2), -- 프랑스
       ('2025-12-01', '2025-12-07', 3, 3), -- 베트남
       ('2026-01-05', '2026-01-20', 4, 4); -- 미국


insert into tbl_accompany_path(country_start_date, country_end_date, accompany_id, country_id)
select d.start_date, d.end_date, p.id, c.id
from (values
          ('일본 오사카 여행 모집','일본','2025-10-01','2025-10-05'),
          ('유럽 투어 동행 구해요','프랑스','2025-11-10','2025-11-20'),
          ('베트남 다낭 힐링','베트남','2025-12-01','2025-12-07'),
          ('미국 서부 로드트립','미국','2026-01-05','2026-01-20')
     ) as d(post_title, country_name, start_date, end_date)
         join tbl_post    p on p.post_title    = d.post_title
         join tbl_country c on c.country_name  = d.country_name;

select * from tbl_file;
select * from tbl_banner;
select * from tbl_banner_file;

insert into tbl_banner_file (file_id, banner_id)
values (7,5);

select * from view_file_banner_file;