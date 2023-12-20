insert into users (id, username, password, role) values (100, 'ana@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (103, 'toby@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_CLIENT');


insert into clients (id, name, cpf, user_id) values (10, 'Bianca Silva', '67243086071', 101);
insert into clients (id, name, cpf, user_id) values (20, 'Roberto Gomes', '40379839075', 102);
