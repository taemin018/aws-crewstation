select * from tbl_member;
select * from tbl_diary;
select * from tbl_like;
select * from tbl_post_section;
select * from tbl_file;
select * from tbl_post_section_file;

-- 1. 회원 (일기 작성자 & 좋아요 누르는 사람)
INSERT INTO tbl_member (member_name, member_email, member_password, social_img_url)
VALUES ('Alice', 'alice@test.com', 'pass123', 'https://dummyimage.com/100x100/000/fff&text=Alice');

-- 2. 게시글 (Alice가 작성한 다이어리 글)
INSERT INTO tbl_post (post_title, member_id)
VALUES ('My First Diary', 1);

INSERT INTO tbl_country (country_name)
VALUES ('Korea');

INSERT INTO tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
VALUES ('2025-01-01', '2025-01-31', 1, 1);

-- 3. 일기 (게시글과 1:1 연결)
-- diary_country_path_id는 FK 제약조건 있으니, 테스트용으로 미리 존재하는 id=1 이 있다고 가정
INSERT INTO tbl_diary (post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
VALUES (1, 'public', 5, 2, 1);

-- 4. 좋아요 (Alice 본인이 자기 글 좋아요 누른 상황)
INSERT INTO tbl_like (post_id, member_id)
VALUES (1, 1);

-- 5. 게시글 섹션 (본문 작성)
INSERT INTO tbl_post_section (post_content, post_id)
VALUES ('This is my first diary content', 1);

-- 6. 파일 (대표 이미지)
INSERT INTO tbl_file (file_origin_name, file_path, file_name, file_size)
VALUES (
             'diary1.png',
             '/images/diary1.png',
             'diary1_20240926.png',
             '2048'
         );

-- 7. 섹션-파일 연결 (대표 이미지 = main)
INSERT INTO tbl_post_section_file (file_id, post_section_id, image_type)
VALUES (1, 1, 'main');