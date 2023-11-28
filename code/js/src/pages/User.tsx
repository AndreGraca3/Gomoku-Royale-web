import {UserStats} from "../components/UserStats/UserStats";
import React from "react";
import {user1, userStats} from "./Home";
import {UserDetails} from "../components/UserDetails/UserDetails";


export function User() {
    return (
        <div className="flex justify-center items-center">
            <div className="grid grid-cols-2 gap-x-20">
                <UserDetails user={user1}></UserDetails>
                <UserStats userStats={userStats}></UserStats>
            </div>
        </div>
    )
}