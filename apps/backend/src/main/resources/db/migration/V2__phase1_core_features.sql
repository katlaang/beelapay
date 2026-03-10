alter table app_user
    add column if not exists country_residency varchar(3),
    add column if not exists parent_user_id uuid,
    add column if not exists daily_limit numeric(19,2),
    add column if not exists per_tx_limit numeric(19,2);

create table if not exists otp_code (
    id uuid primary key,
    phone_number varchar(20) not null,
    purpose varchar(30) not null,
    otp_hash varchar(100) not null,
    attempt_count integer not null,
    max_attempts integer not null,
    expires_at timestamp not null,
    verified_at timestamp,
    created_at timestamp not null
);

alter table wallet
    add constraint uq_wallet_user_currency unique (user_id, currency_code);

alter table ledger_entry
    add column if not exists channel varchar(30) not null default 'API';

create table if not exists internal_transfer (
    id uuid primary key,
    source_wallet_id uuid not null,
    target_wallet_id uuid not null,
    amount numeric(19,2) not null,
    reference varchar(100) not null unique,
    channel varchar(30) not null,
    status varchar(30) not null,
    created_at timestamp not null,
    updated_at timestamp not null,
    constraint fk_transfer_source_wallet foreign key (source_wallet_id) references wallet(id),
    constraint fk_transfer_target_wallet foreign key (target_wallet_id) references wallet(id)
);

create table if not exists audit_event (
    id uuid primary key,
    event_type varchar(60) not null,
    actor_id uuid,
    reference_id varchar(100),
    context varchar(500),
    created_at timestamp not null
);
