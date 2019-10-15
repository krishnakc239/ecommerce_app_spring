# insert into users(id, active, email, first_name, last_name, password, user_name, role_id)
# values
# (NULL, 1, 'admin@gmail.com', 'Krishna', 'KC', '$2a$10$385L2o40NsGgRNU/mjXguuBNouwXcGhLbUMv.qouWOJu1PehOLVQ2', 'krishna', 1),
# (NULL, 1, 'seller1@gmail.com', 'John', 'Brown', '$2a$10$LOkdXmHdz7I8gb0XPBHad.3aet/A2QEkMFSMqvuzuRSESLpuiQdL2', 'john', 2),
# (NULL, 1, 'robert@gmail.com', 'Robert', 'Jain', '$2a$10$0ayfVWkd2NnzHePJFnb4JujnttkP/U7H1eNVPrqkYIfu2syI7UbGu', 'robert', 3);


-- roles
insert into roles (id, role) values (1, 'ROLE_ADMIN');
insert into roles (id, role) values (2, 'ROLE_SELLER');
insert into roles (id, role) values (3, 'ROLE_BUYER');


insert into products (id,code,name,price,cover_image,description,category_id,user_id)
values
        (null,'P1','product1',20.00,'product1.jpg','this is product 1',1,2),
        (null,'P2','product2',30.00,'product2.jpg','this is product 2',1,2),
        (null,'P3','product3',40.00,'product3.jpg','this is product 3',1,2),
        (null,'P4','product4',20.00,'product1.jpg','this is product 4',1,2),
        (null,'P5','product5',30.00,'product2.jpg','this is product 5',1,2),
        (null,'P6','product6',40.00,'product3.jpg','this is product 6',1,2);

insert  into categories(id,category_name)
values
        (null,'Electronics'),
        (null,'Kitchen'),
        (null,'Clothes');

