CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
create table customer (
	id uuid not null default uuid_generate_v4() primary key,
	first_name varchar (50) not null,
	surname varchar (255) not null,
	title varchar (3) not null,
	address varchar (255) not null
);

create table phone (
	id uuid not null default uuid_generate_v4() primary key,
	phone_number bigint not null,
	customer_id uuid not null,
	status boolean default false,
    last_updated timestamp not null default now(),
    foreign key (customer_id) references customer(id)
);

insert into customer (id, first_name, surname, title, address) values ('550e8400-e29b-41d4-a716-446655440000', 'John', 'Doe', 'Mr', '123 Main St, Anytown');
insert into customer (id, first_name, surname, title, address) values ('550e8400-e29b-41d4-a716-446655440001', 'Jane', 'Smith', 'Ms', '456 Elm St, Anytown');

insert into phone (phone_number, customer_id, status, last_updated) values ('0126352685', '550e8400-e29b-41d4-a716-446655440000', true, now());
insert into phone (phone_number, customer_id, last_updated) values ('0624672685', '550e8400-e29b-41d4-a716-446655440000', now());

insert into phone (phone_number, customer_id, status, last_updated) values ('0456352685', '550e8400-e29b-41d4-a716-446655440001', true, now());
insert into phone (phone_number, customer_id, last_updated) values ('0454672685', '550e8400-e29b-41d4-a716-446655440001', now());