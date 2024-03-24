insert into users (id, username, password, role) values (100, 'ana@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_ADMIN');
insert into users (id, username, password, role) values (101, 'bia@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_CLIENT');
insert into users (id, username, password, role) values (102, 'bob@gmail.com', '$2a$12$I.gv1eKk0BUH8rA7ZPxone94I6RdKli8NS.eagEYW8OSV18rnIqyy', 'ROLE_CLIENT');

insert into CLIENTS (id, name, cpf, user_id) values (21, 'Beatriz Rodrigues', '09191773016', 101);
insert into CLIENTS (id, name, cpf, user_id) values (22, 'Rodrigo Silva', '98401203015', 102);

insert into PARKING_SPOTS (id, code, status) values (100, 'A-01', 'TAKEN');
insert into PARKING_SPOTS (id, code, status) values (200, 'A-02', 'TAKEN');
insert into PARKING_SPOTS (id, code, status) values (300, 'A-03', 'TAKEN');
insert into PARKING_SPOTS (id, code, status) values (400, 'A-04', 'TAKEN');
insert into PARKING_SPOTS (id, code, status) values (500, 'A-05', 'TAKEN');

insert into CLIENTS_HAVE_PARKING_SPOTS (receipt_number, vehicle_registration, vehicle_brand, vehicle_model, vehicle_color, checkin_date, id_client, id_parkingspot)
values ('20230313', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-13 10:15:00', 22, 100);
insert into CLIENTS_HAVE_PARKING_SPOTS (receipt_number, vehicle_registration, vehicle_brand, vehicle_model, vehicle_color, checkin_date, id_client, id_parkingspot)
values ('20230314', 'SIE-1020', 'FIAT', 'SIENA', 'BRANCO', '2023-03-14 10:15:00', 21, 200);
insert into CLIENTS_HAVE_PARKING_SPOTS (receipt_number, vehicle_registration, vehicle_brand, vehicle_model, vehicle_color, checkin_date, id_client, id_parkingspot)
values ('20230315', 'FIT-1020', 'FIAT', 'PALIO', 'VERDE', '2023-03-14 10:15:00', 22, 300);