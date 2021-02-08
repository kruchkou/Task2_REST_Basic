INSERT INTO gift_certificate (name, description, price, duration)
values ('First Cert', 'This is first certificate', 50, 10);
INSERT INTO gift_certificate (name, description, price, duration)
values ('Second Cert', 'That is second certificate', 100, 20);

INSERT INTO tag (name)
values ('first');
INSERT INTO tag (name)
values ('second');
INSERT INTO tag (name)
values ('third');

INSERT INTO gift_tag(gift, tag)
values (1, 1);
INSERT INTO gift_tag(gift, tag)
values (1, 2);
INSERT INTO gift_tag(gift, tag)
values (2, 2);