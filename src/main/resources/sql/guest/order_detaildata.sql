-- 1. 판매자 회원 (member)
INSERT INTO tbl_member (member_name, member_phone, member_email)
VALUES ('홍길동', '01011112222', 'seller@test.com');

-- 2. 구매자 회원 (member)
INSERT INTO tbl_member (member_name)
VALUES ('익명의게스트');

-- 2. 구매자 회원 (guest)
INSERT INTO tbl_guest (member_id, guest_phone, guest_order_number, address_zip_code, address_detail,address)
VALUES ('2', '01099993333', '2025093022321855389', '11234', '101동 1001호', '서울시 동작구 무슨로');

-- 3. 게시글 (post)
INSERT INTO tbl_post (post_title, member_id)
VALUES ('테스트 러닝화', 1);  -- member_id = 판매자(홍길동)

-- 4. 구매설정 (purchase)
INSERT INTO tbl_purchase (post_id, purchase_product_price,purchase_limit_time, purchase_product_count, purchase_country, purchase_delivery_method)
VALUES (1, 12000,10,3, '대한민국', 'direct');

-- 6. 결제 상태 (payment_status)
INSERT INTO tbl_payment_status (payment_phase, purchase_id, member_id, created_datetime, updated_datetime)
VALUES ('success', 1,2, now(), now());

-- 7. 파일 (file)
INSERT INTO tbl_file (file_origin_name, file_path, file_name, file_size)
VALUES ('gift-shop-post-img2.jpg', '/images', 'gift-shop-post-img2.png', '200KB');


-- 8. 게시글 섹션 (post_section)
INSERT INTO tbl_post_section (post_id)
VALUES (1);

-- 9. 게시글 섹션 파일 (post_section_file, 메인 이미지 연결)
INSERT INTO tbl_post_section_file (file_id, post_section_id, image_type)
VALUES (1, 1, 'main');

SELECT * FROM tbl_payment_status WHERE purchase_id = 1;


select * from tbl_payment_status;
select * from tbl_member;

SELECT DISTINCT payment_phase FROM tbl_payment_status;



TRUNCATE TABLE
    tbl_post_section_file,
    tbl_post_section,
    tbl_file,
    tbl_payment_status,
    tbl_payment,
    tbl_guest,
    tbl_purchase,
    tbl_post,
    tbl_member
    RESTART IDENTITY CASCADE;