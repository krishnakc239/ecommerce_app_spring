insert into roles (id,
                   role)
values (1,
        'ROLE_ADMIN');
insert into roles (id,
                   role)
values (2,
        'ROLE_SELLER');
insert into roles (id,
                   role)
values (3,
        'ROLE_BUYER');

insert into users (id,
                   active,
                   email,
                   first_name,
                   last_name,
                   password,
                   user_name,
                   role_id,
                   status,
                   points)
values (1,
        1,
        'krishnakc2339@gmail.com',
        'Krishna',
        'KC',
        '$2a$10$e408/gia2hvSw.rgtrobk.wOMRiS.PBCutttzHbCnsxuDObf2rOgK',
        'admin1',
        1,
        false,
        0.00),
       (2,
        1,
        'aozer@mum.edu',
        'Alican',
        'Ozer',
        '$2a$10$nDJmxV2woGtsE.y9ofYH/.7N4Lo/xBt5FcnQqdktGpzRwqmH7S77S',
        'seller1',
        2,
        false,
        0.00),
       (3,
        1,
        'zayedemails@gmail.com',
        'Zayed',
        'Haussan',
        '$2a$10$NRWAxnj0D/Nx39/5Fa3qF.jIBOaPvZ2Yc18VLnMi1wyS/hfH2HK4e',
        'buyer1',
        3,
        false,
        0.00),
       (4,
        1,
        'alican.ozer@yahoo.com',
        'Cris',
        'Brown',
        '$2a$10$GQnIA9RCv18PjFLTEZbARu.lhhGU730DNUVCYQlTMrDsfJEgw2jb.',
        'buyer2',
        3,
        false,
        0.00),
       (5,
        1,
        'alican.ozer@icloud.com',
        'Michel',
        'Jackson',
        '$2a$10$toSeDJ9BIM1rylgXxvgQzeJ1a3H/LqGXDfDgBKPDy4sZAUVX7bd0K',
        'seller2',
        2,
        false,
        0.00);
insert into categories (id,
                        category_name)
values (null,
        'Electronics'),
       (null,
        'Kitchen'),
       (null,
        'Clothes');
insert into products (id,
                      code,
                      cover_image,
                      description,
                      name,
                      price,
                      category_id,
                      user_id,
                      stock)
values (1,
        'A1',
        'product-5.jpg',
        'Apple best phone',
        'Iphone 6',
        500,
        1,
        2,
        3),
       (2,
        'SM1',
        'product-4.jpg',
        'Sony best phone',
        'Sony Microsoft',
        300,
        1,
        2,
        4),
       (3,
        'L1',
        'product-3.jpg',
        'LG best Phone',
        'LG Leon 2015',
        400,
        1,
        2,
        5),
       (4,
        'N1',
        'product-2.jpg',
        'Nokia best cell phone',
        'Nokia Lumia 1320',
        899,
        1,
        2,
        2),
       (5,
        'S1',
        'product-1.jpg',
        'Best cell phone ever',
        'Samsung Galaxy s5- 2015',
        700,
        1,
        2,
        8);

insert into credit
values (1,
        20000,
        1234567890123456,
        '158',
        '01/12');
insert into credit
values (2,
        20000,
        1234567891234567,
        'c02',
        '12/24');
insert into credit
values (3,
        20000,
        2234567891234567,
        'c02',
        '12/24');
insert into credit
values (4,
        20000,
        3234567891234567,
        'c02',
        '12/24');
insert into ad
values (1,
        'h4-slide.png',
        'https://www.thymeleaf.org',
        'this is a subtitle',
        'this is a title1');
insert into ad
values (2,
        'h4-slide2.png',
        'https://www.thymeleaf.org',
        'this is a subtitle',
        'this is a title2');