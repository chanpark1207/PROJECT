CREATE TABLE post (
                      numbers BIGINT AUTO_INCREMENT PRIMARY KEY,
                      title VARCHAR(100) NOT NULL,
                      content TEXT NOT NULL,
                      item_type TINYINT NOT NULL,
                      category VARCHAR(50) NOT NULL,
                      location VARCHAR(100) NOT NULL,
                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                      user_id VARCHAR(20) NOT NULL,
                      image VARCHAR(255)
);

CREATE TABLE student (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         user_id VARCHAR(20) UNIQUE,
                         password VARCHAR(100)
);

CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         content VARCHAR(500) NOT NULL,
                         user_id VARCHAR(20),
                         create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                         post_id BIGINT,

                         FOREIGN KEY (post_id) REFERENCES post(numbers)
);