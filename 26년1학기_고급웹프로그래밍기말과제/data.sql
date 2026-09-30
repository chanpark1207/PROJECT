/* 테스트용: 학생 아이디, 비번 */
INSERT INTO student(user_id, password)
VALUES('20241234', '1234');

INSERT INTO student(user_id, password)
VALUES('20241111', '1111');

INSERT INTO student(user_id, password)
VALUES('20242222', '2222');

INSERT INTO student(user_id, password)
VALUES('20243333', '3333');

INSERT INTO student(user_id, password)
VALUES('20244444', '4444');

/* 테스트용: 게시물 */
INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '강남대 머리띠 전공책',
        '오늘 아침에 발견했어요.',
        1,
        '책',
        '머리띠',
        CURRENT_TIMESTAMP,
        '202304073',
        '/images/202304073_1_1.jpg'
    );
INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '스프링부트3(홍팍) 책 보신분 있나요',
        '어제 급하게 가다가 어디 떨군거 같은데 표지에 형광펜 조금 묻어있고 필기가 있어요',
        0,
        '책',
        '강남대',
        CURRENT_TIMESTAMP,
        '222204073',
        '/images/222204073_0_1.png'
    );

INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '무선마우스',
        '이공관에서 잃어버린거 같아요',
        0,
        '마우스',
        '강남대',
        CURRENT_TIMESTAMP,
        '2222222222',
        '/images/마우스.png'
    );

INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '모자를 찾고 있어요',
        '모자',
        0,
        '모자',
        '강남대',
        CURRENT_TIMESTAMP,
        '333333333',
        '/images/모자.png'
    );
INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '무지개 우산...',
        '어디에 두고 갔는지 전혀 기억이 안나요...',
        0,
        '우산',
        '강남대',
        CURRENT_TIMESTAMP,
        '444444444',
        '/images/우산.png'
    );
INSERT INTO post (title, content, item_type, category, location, created_at, user_id, image) VALUES
    (
        '도서관에서 텀블러를 잃어버렸어요',
        '어제 두고 가서 오늘 급히 가봤는데 없네요..ㅠ',
        0,
        '텀블러',
        '도서관',
        CURRENT_TIMESTAMP,
        '555555555',
        '/images/텀블러.png'
    );