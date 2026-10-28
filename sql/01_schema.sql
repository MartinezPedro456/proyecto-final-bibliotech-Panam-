
create table category (
    id   bigint generated always as identity primary key,
    name varchar(100) not null
);

create table system_user (
    id            bigint generated always as identity primary key,
    username      varchar(50)  not null unique,
    password_hash varchar(60)  not null,
    role          varchar(20)  not null
);

create table book (
    id          bigint generated always as identity primary key,
    title       varchar(200) not null,
    author      varchar(150) not null,
    isbn        varchar(13)  not null unique,
    category_id bigint references category(id),
    year        smallint
);

create table copy (
    id      bigint generated always as identity primary key,
    book_id bigint not null references book(id),
    barcode varchar(50) not null unique,
    status  varchar(20) not null default 'available'
);

create table member (
    id          bigint generated always as identity primary key,
    full_name   varchar(120) not null,
    document_id varchar(20)  not null unique,
    phone       varchar(20),
    status      varchar(20)  not null default 'active'
);

create table loan (
    id          bigint generated always as identity primary key,
    copy_id     bigint not null references copy(id),
    member_id   bigint not null references member(id),
    loan_date   date   not null default current_date,
    due_date    date   not null,
    return_date date
);

create table fine (
    id        bigint generated always as identity primary key,
    loan_id   bigint not null references loan(id),
    amount    numeric(10,2) not null default 0,
    paid      boolean not null default false,
    paid_date date
);

create table reservation (
    id          bigint generated always as identity primary key,
    book_id     bigint not null references book(id),
    member_id   bigint not null references member(id),
    reserved_at timestamptz not null default now(),
    status      varchar(20) not null default 'pending'
);