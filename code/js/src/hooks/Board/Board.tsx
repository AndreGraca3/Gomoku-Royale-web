import {createContext, useContext, useEffect, useState} from "react";
import userData from "../../data/userData";
import {BoardType} from "../../types/board";
import {homeLinks} from "../../index";

// The state that will be in the context
type BoardContextType = {
    board: BoardType | undefined;
    setBoard: (b: BoardType | undefined) => void;
};

// Create a context for the defined types
// This happens only once
const BoardContext = createContext<BoardContextType>({
    board: undefined,
    setBoard: () => {},
});

export function BoardProvider({ children }: { children: React.ReactNode }) {
    const [board, setBoard] = useState(undefined);

    return (
        <BoardContext.Provider value={{ board: board, setBoard: setBoard }}>
            {children}
        </BoardContext.Provider>
    );
}

export function useBoard(): BoardType | undefined {
    return useContext(BoardContext).board;
}

export function useSetBoard(): (b: BoardType | undefined) => void {
    return useContext(BoardContext).setBoard;
}
