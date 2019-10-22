insert into users (id,active,email,first_name,last_name,password,user_name,role_id,status)
values
(null,1,'krishnakc@gmail.com','Krishna','KC','$2a$10$e408/gia2hvSw.rgtrobk.wOMRiS.PBCutttzHbCnsxuDObf2rOgK','admin1',1,false),
(null,1,'alican@gmail.com','Alican','Ozer','$2a$10$nDJmxV2woGtsE.y9ofYH/.7N4Lo/xBt5FcnQqdktGpzRwqmH7S77S','seller1',2,false),
(null,1,'zayed@gmail.com','Zayed','Haussan','$2a$10$NRWAxnj0D/Nx39/5Fa3qF.jIBOaPvZ2Yc18VLnMi1wyS/hfH2HK4e','buyer1',3,false);


-- roles
insert into roles (id, role) values (1, 'ROLE_ADMIN');
insert into roles (id, role) values (2, 'ROLE_SELLER');
insert into roles (id, role) values (3, 'ROLE_BUYER');

insert into products (id,code,cover_image,description,name,price,category_id,user_id,quantity)
values
(null,'A1', 'product-5.jpg','Apple best phone','Iphone 6',500,1,2,3),
(null,'SM1','product-4.jpg','Sony best phone','Sony Microsoft',300,1,2,4),
(null,'L1',  'product-3.jpg','LG best Phone','LG Leon 2015',400,1,2,5),
(null,'N1', 'product-2.jpg','Nokia best cell phone','Nokia Lumia 1320',899,1,2,2),
(null,'S1','product-1.jpg','Best cell phone ever','Samsung Galaxy s5- 2015',700,1,2,8),
(null,'B2','blazer2.jpeg','Blazer for winter','Blazer2',40,3,2,6),
(null,'B1','blazer1.jpeg','Best blazer for men','Blazer1',30,3,2,7);

insert  into categories(id,category_name)
values
        (null,'Electronics'),
        (null,'Kitchen'),
        (null,'Clothes');

insert into credit values(null, 20000, 1234567891234567, 'c02', '12/24');
insert into credit values(null, 20000, 2234567891234567, 'c02', '12/24');
insert into credit values(null, 20000, 3234567891234567, 'c02', '12/24');


