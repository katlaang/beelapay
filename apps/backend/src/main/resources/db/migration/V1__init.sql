create table app_user (
    id uuid primary key,
    phone_number varchar(20) not null unique,
    full_name varchar(150) not null,
    role varchar(30) not null,
    status varchar(30) not null,
    created_at timestamp not null
);

create table wallet (
    id uuid primary key,
    user_id uuid not null,
    currency_code varchar(3) not null,
    balance numeric(19,2) not null default 0,
    status varchar(30) not null,
    created_at timestamp not null,
    constraint fk_wallet_user foreign key (user_id) references app_user(id)
);

create table ledger_entry (
    id uuid primary key,
    wallet_id uuid not null,
    entry_type varchar(30) not null,
    amount numeric(19,2) not null,
    reference varchar(100) not null,
    created_at timestamp not null,
    constraint fk_ledger_wallet foreign key (wallet_id) references wallet(id)
);
