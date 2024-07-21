INSERT INTO mung_shop (storeName, storeAddress, breeds, businessHours, closingDays, latitude, longitude, filePath)
VALUES
    ('즐거운 애견 미용실', '경기 화성시 동탄공원로1길 22-3 1층 101호', '모든 견종', '10:00 AM - 18:30 PM', '휴무없음', '37.2031802', '127.060017','/images/mungshop/shop1.jpg'),
    ('멍샬롬', '경기 화성시 동탄공원로 36 다경프라자 1층', '모든 견종', '10:30 AM - 20:00 PM', '일요일', '37.202773', '127.0596957','/images/mungshop/shop2.jpg'),
    ('푸른펫살롱', '경기 화성시 여울로2길 30 102호', '모든 견종', '10:00 AM - 18:00 PM', '월/화요일', '37.2045269', '127.0526126','/images/mungshop/shop3.jpg'),
    ('센트럴동물병원', '경기 화성시 동탄지성로 102 하나로프라자', '모든 견종', '9:30 AM - 20:00 PM', '일요일', '37.2081031', '127.0641932','/images/mungshop/shop4.jpg'),
    ('멍화만사성', '경기 화성시 동탄지성로 143 2층', '모든 견종', '8:00 AM - 20:30 PM', '화요일', '37.2095214', '127.0595995','/images/mungshop/shop5.jpg');

INSERT INTO mymung (mungName, breed, weight, age, gender, fixed, userId)
VALUES
    ('싼쵸', '치와와', 4, 5, '수컷', 'Yes', 1),
    ('코코', '말티푸', 2, 1, '암컷', 'No', 1);

INSERT INTO mung_shop_price (breeds, serviceType, price, mung_shop_id)
VALUES ('Golden Retriever', 'Basic grooming', 50000, 1),
       ('Labrador Retriever', 'Full grooming', 80000, 1),
       ('Poodle', 'Basic grooming', 60000, 1),
       ('Poodle', 'Full grooming', 100000, 1),
       ('Chihuahua', 'Basic grooming', 45000, 2),
       ('Chihuahua', 'Full grooming', 75000, 2),
       ('French Bulldog', 'Basic grooming', 55000, 3),
       ('French Bulldog', 'Full grooming', 90000, 3),
       ('Pomeranian', 'Basic grooming', 40000, 4),
       ('Pomeranian', 'Full grooming', 70000, 4),
       ('Siberian Husky', 'Basic grooming', 60000, 4),
       ('Siberian Husky', 'Full grooming', 95000, 4);

