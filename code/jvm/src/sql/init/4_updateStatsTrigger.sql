create or replace function updateStats() returns trigger
    language plpgsql as
$$
begin

    if (TG_OP <> 'UPDATE') then
        raise exception 'Invalid trigger operation';
    end if;

    if (TG_TABLE_NAME = 'match' and new.state = 'FINISHED') then
        -- increment matches as "player color" for both players, since match has finished.
        update stats
        set matches_as_black = matches_as_black + 1
        where user_id = new.black_id;

        update stats
        set matches_as_white = matches_as_white + 1
        where user_id = new.white_id;

        if ((select type from board where match_id = new.id) = 'BoardDraw') then
            -- if match ended in a draw, increment draws for both players.
            update stats
            set draws = draws + 1
            where user_id = new.white_id
               or user_id = new.black_id;
        end if;

    end if;

    return new;
end;
$$;

create trigger updateStats
    after update of state
    on match
    for each row
execute procedure updateStats();