create table tbl_post
(
    id               bigint generated always as identity primary key,
    post_title       varchar(255) not null,
    post_read_count  int       default 0,
    post_status      status    default 'active',
    member_id        bigint       not null,
    created_datetime timestamp default now(),
    updated_datetime timestamp default now(),
    constraint fk_post_member foreign key (member_id)
        references tbl_member (id)
);

-- 해외 여행 국가 (이미 tbl_country에 데이터 있다고 가정)
INSERT INTO tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
SELECT '2025-11-01','2025-11-05', m.id, c.id
FROM tbl_member m, tbl_country c
WHERE m.member_email='alice@example.com' AND c.country_name='Japan';

INSERT INTO tbl_diary_country_path (country_start_date, country_end_date, member_id, country_id)
SELECT '2025-12-10','2025-12-15', m.id, c.id
FROM tbl_member m, tbl_country c
WHERE m.member_email='bob@example.com' AND c.country_name='USA';


-- post 2개
INSERT INTO tbl_post (post_title, member_id, post_status)
VALUES
    ('오사카 여행일기', (SELECT id FROM tbl_member WHERE member_email='alice@example.com'), 'active'::status),
    ('뉴욕 여행일기', (SELECT id FROM tbl_member WHERE member_email='bob@example.com'), 'active'::status);

-- diary (post_id = tbl_post.id 참조)
INSERT INTO tbl_diary (post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
SELECT p.id, 'public'::secret, 3, 1, dcp.id
FROM tbl_post p
         JOIN tbl_diary_country_path dcp ON dcp.member_id = p.member_id
WHERE p.post_title='오사카 여행일기';

INSERT INTO tbl_diary (post_id, diary_secret, diary_like_count, diary_reply_count, diary_country_path_id)
SELECT p.id, 'private'::secret, 5, 2, dcp.id
FROM tbl_post p
         JOIN tbl_diary_country_path dcp ON dcp.member_id = p.member_id
WHERE p.post_title='뉴욕 여행일기';


-- 크루 생성
INSERT INTO tbl_crew (crew_name, crew_description, crew_member_count)
VALUES
    ('오사카 크루','오사카 여행 모임',2),
    ('뉴욕 크루','뉴욕 여행 모임',2);

-- 크루 멤버 연결 (leader / partner)
INSERT INTO tbl_crew_member (crew_role, crew_id, member_id)
VALUES
    ('leader'::crew_role,
     (SELECT id FROM tbl_crew WHERE crew_name='오사카 크루'),
     (SELECT id FROM tbl_member WHERE member_email='alice@example.com')),
    ('partner'::crew_role,
     (SELECT id FROM tbl_crew WHERE crew_name='오사카 크루'),
     (SELECT id FROM tbl_member WHERE member_email='bob@example.com')),

    ('leader'::crew_role,
     (SELECT id FROM tbl_crew WHERE crew_name='뉴욕 크루'),
     (SELECT id FROM tbl_member WHERE member_email='bob@example.com')),
    ('partner'::crew_role,
     (SELECT id FROM tbl_crew WHERE crew_name='뉴욕 크루'),
     (SELECT id FROM tbl_member WHERE member_email='alice@example.com'));


select * from tbl_post;