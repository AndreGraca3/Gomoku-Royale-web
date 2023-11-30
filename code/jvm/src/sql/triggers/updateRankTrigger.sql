create or replace function updateRank() returns trigger
    language plpgsql as
$$
declare
    newRank varchar(20);
    rank record;
begin

    if (TG_OP <> 'UPDATE' and TG_OP <> 'INSERT') then
        raise exception 'Invalid trigger operation';
    end if;

    if (TG_TABLE_NAME = 'user') then
        select name into newRank
        from rank r
        where new.mmr >= r.min_mmr
        order by r.min_mmr DESC;

        update stats set rank = newRank where user_id = new.id;
    end if;

    return null;
end;
$$;

create trigger updateRank
after update of mmr on "user"
for each row execute procedure updateRank();
