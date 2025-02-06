INSERT INTO mung_shop (store_name, store_address, breed_type, business_hours, closing_days, latitude, longitude, file_path)
VALUES
    ('즐거운 애견 미용실', '경기 화성시 동탄공원로1길 22-3 1층 101호', 'ALL', '10:00 AM - 18:30 PM', 'NONE', '37.2031802', '127.060017','/images/mungshop/shop1.jpg'),
    ('멍샬롬', '경기 화성시 동탄공원로 36 다경프라자 1층', 'ALL', '10:30 AM - 20:00 PM', 'SUNDAY', '37.202773', '127.0596957','/images/mungshop/shop2.jpg'),
    ('푸른펫살롱', '경기 화성시 여울로2길 30 102호', 'ALL', '10:00 AM - 18:00 PM', 'MONDAY', '37.2045269', '127.0526126','/images/mungshop/shop3.jpg'),
    ('센트럴동물병원', '경기 화성시 동탄지성로 102 하나로프라자', 'ALL', '9:30 AM - 20:00 PM', 'SUNDAY', '37.2081031', '127.0641932','/images/mungshop/shop4.jpg'),
    ('멍화만사성', '경기 화성시 동탄지성로 143 2층', 'ALL', '8:00 AM - 20:30 PM', 'TUESDAY', '37.2095214', '127.0595995','/images/mungshop/shop5.jpg');

INSERT INTO mung_shop_price (breed_type, service_type, price, mung_shop_id)
VALUES ('GOLDEN_RETRIEVER', 'Basic', 50000, 1),
       ('POODLE', 'Basic', 60000, 1),
       ('POODLE', 'Full', 100000, 1),
       ('CHIHUAHUA', 'Basic', 45000, 2),
       ('CHIHUAHUA', 'Full', 75000, 2),
       ('FRENCH_BULLDOG', 'Basic', 55000, 3),
       ('FRENCH_BULLDOG', 'Full', 90000, 3),
       ('POMERANIAN', 'Basic', 40000, 4),
       ('POMERANIAN', 'Full', 70000, 4),
       ('SIBERIAN_HUSKY', 'Basic', 60000, 4),
       ('SIBERIAN_HUSKY', 'Full', 95000, 4);

