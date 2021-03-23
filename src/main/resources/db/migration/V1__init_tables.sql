create schema dinote;

drop table if exists dinote.ingredient;
create table dinote.ingredient (
    id bigserial primary key,
    name varchar(50) not null unique,
    updated_on timestamp with time zone,
    created_on timestamp with time zone
);

drop table if exists dinote.user;
create table dinote.user (
    id bigserial primary key,
    name varchar(50) not null unique,
    password varchar(100) not null,
    email varchar(50) not null,
    updated_on timestamp with time zone,
    created_on timestamp with time zone
);

drop table if exists dinote.recipe;
create table dinote.recipe (
    id bigserial primary key,
    name varchar(100) not null,
    description varchar(200) not null,
    views_counter int default 0,
    user_id bigint,
    updated_on timestamp with time zone,
    created_on timestamp with time zone,
    constraint fk_user foreign key (user_id) references dinote.user(id)
);

drop table if exists dinote.ingredient_metadata;
create table dinote.ingredient_metadata (
    id bigserial primary key,
    ingredient_id bigint,
    recipe_id bigint,
    amount int,
    measure varchar(50),
    updated_on timestamp with time zone,
    created_on timestamp with time zone,
    constraint fk_ingredient foreign key (ingredient_id) references dinote.ingredient(id),
    constraint fk_recipe foreign key (recipe_id) references dinote.recipe(id)
);

drop table if exists dinote.cooking_step;
create table dinote.cooking_step (
    id bigserial primary key,
    description varchar(200),
    recipe_id bigint,
    required_time int,
    updated_on timestamp with time zone,
    created_on timestamp with time zone,
    constraint fk_recipe foreign key (recipe_id) references dinote.recipe(id)
);

drop table if exists dinote.rating;
create table dinote.rating (
     id bigserial primary key,
     rate int,
     user_id bigint,
     recipe_id bigint,
     updated_on timestamp with time zone,
     created_on timestamp with time zone,
     constraint fk_user foreign key (user_id) references dinote.user(id),
     constraint fk_recipe foreign key (recipe_id) references dinote.recipe(id)
);

drop table if exists dinote.salt;
create table dinote.salt (
    hash text
);

insert into dinote.salt values (md5('12comm@())D(1313U4fz___cc+-#)$!!!sjaw390'));

create function forbid_salt_update() returns trigger as $forbid_salt_update$
    begin
        RAISE EXCEPTION 'Salt update is not allowed';
    end;
$forbid_salt_update$ language plpgsql;
create trigger forbid_salt_update before insert or update on dinote.salt
    for each row execute procedure forbid_salt_update();
