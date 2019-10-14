insert into users(id,email,password,user_name,first_name,last_name,active,role_id)
values
       (NULL,'admin@gmail.com','admin','admin','krishna','kc',1,1),
       (NULL,'seller@gmail.com','seller','seller','seller','kc',1,2),
       (NULL,'buyer@gmail.com','buyer','buyer','buyer','kc',1,3);

-- roles
insert into roles (id, role) values (1, 'ROLE_ADMIN');
insert into roles (id, role) values (2, 'ROLE_SELLER');
insert into roles (id, role) values (3, 'ROLE_BUYER');


insert into products (id,code,name,price,cover_image,description,category_id,user_id)
values
        (null,'P1','product1',20.00,'product1.jpg','this is product 1',1,2),
        (null,'P2','product2',30.00,'product2.jpg','this is product 2',1,2),
        (null,'P3','product3',40.00,'product3.jpg','this is product 3',1,2);

insert  into categories(id,category_name)
values
        (null,'Electronics'),
        (null,'Kitchen'),
        (null,'Clothes');

