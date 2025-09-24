/*from tbl_member tm join
        view_post_purchase vpp
on tm.id = vpp.member_id
        join tbl_post_section tps
on vpp.id = tps.post_id and vpp.post_status = 'active'
        join view_file_post_section_file vfps
on tps.id = vfps.post_section_id and vfps.image_type = 'main'
*/
select *
from tbl_purchase;

insert into tbl_member (member_name, member_phone, member_email, member_birth, member_password, member_description)
values ('test','01012341234','test@ac.kr','20000113','1234','테스트 데이터 입니다.');

insert into tbl_member (member_name, member_phone, member_email, member_birth, member_password, member_description)
values ('test2','01012341234','test2@ac.kr','20000113','1234','테스트 데이터 입니다.');

insert into tbl_post (post_title,member_id)
values ('test1',1),
       ('test2',1),
       ('test3',2),
       ('test4',2),
       ('test5',2),
       ('test6',1),
       ('test7',2),
       ('test8',2),
       ('test9',2),
       ('test10',1),
       ('test11',2),
       ('test12',2),
       ('test13',2),
       ('test14',1);

insert into tbl_post_section (post_content, post_id)
values ('test1',1),
       ('test2',2),
       ('test3',3),
       ('test4',4),
       ('test5',5),
       ('test6',6),
       ('test7',7),
       ('test8',8),
       ('test9',9),
       ('test10',10),
       ('test11',11),
       ('test12',12),
       ('test13',13),
       ('test14',14);

insert into tbl_file(file_origin_name, file_path, file_name)
values ('origin1','path1','name1'),
       ('origin2','path2','name2'),
       ('origin3','path3','name3'),
       ('origin4','path4','name4'),
       ('origin5','path5','name5'),
       ('origin6','path6','name6'),
       ('origin7','path7','name7'),
       ('origin8','path8','name8'),
       ('origin9','path9','name9'),
       ('origin10','path10','name10'),
       ('origin11','path11','name11'),
       ('origin12','path12','name12'),
       ('origin13','path13','name13'),
       ('origin14','path14','name14');

insert into tbl_post_section_file(file_id, post_section_id, image_type)
values(1,1,'main'),
      (2,2,'main'),
      (3,3,'main'),
      (4,4,'main'),
      (5,5,'main'),
      (6,6,'main'),
      (7,7,'main'),
      (8,8,'main'),
      (9,9,'main'),
      (10,10,'main'),
      (11,11,'main'),
      (12,12,'main'),
      (13,13,'main'),
      (14,14,'main');

insert into tbl_purchase (post_id, purchase_limit_time, purchase_product_count, purchase_country,purchase_product_price,  purchase_delivery_method)
values (1,24,10,'호주',10000,'direct'),
       (2,24,10,'미국',10000,'parcel'),
       (3,24,10,'한국',10000,'direct'),
       (4,24,10,'일본',10000,'parcel'),
       (5,24,10,'필리핀',10000,'direct'),
       (6,24,10,'캐나다',10000,'parcel'),
       (7,24,10,'보라카이',10000,'direct'),
       (8,24,10,'화와이',10000,'parcel'),
       (9,24,10,'런던',10000,'direct'),
       (10,24,10,'영국',10000,'parcel'),
       (11,24,10,'중국',10000,'direct'),
       (12,24,10,'대만',10000,'parcel'),
       (13,24,10,'홍콩',10000,'direct'),
       (14,24,10,'러시아',10000,'parcel');
