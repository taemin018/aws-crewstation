create table tbl_post_section_file
(
    file_id         bigint primary key,
    post_section_id bigint not null,
    image_type      image_type default 'sub',
    constraint fk_post_section_file_post_section foreign key (post_section_id)
        references tbl_post_section (id),
    constraint fk_post_section_file_file foreign key (file_id)
        references tbl_file (id)
);

insert into tbl_post_section(post_content, post_id)
values ('test',14);

-- 1) 파일 생성 (file_size는 NOT NULL이라 반드시 값 넣어야 함)
INSERT INTO tbl_file (file_name, file_origin_name, file_path, file_size, created_datetime, updated_datetime)
VALUES ('dummy-main.jpg', 'dummy-main.jpg', '/images/dummy-main.jpg', 1024, NOW(), NOW());

-- 예를 들어 반환된 id가 50이라고 하자

-- 2) 섹션 생성 (post_id=14에 연결)
INSERT INTO tbl_post_section (post_content, post_id)
VALUES ('test', 14);


-- 예를 들어 반환된 id가 30이라고 하자

-- 3) 섹션-파일 매핑 (대표 이미지니까 image_type='main')
INSERT INTO tbl_post_section_file (file_id, post_section_id, image_type)
VALUES (50, 30, 'main');


ALTER TABLE tbl_post_section_file
    ALTER COLUMN file_id DROP IDENTITY;