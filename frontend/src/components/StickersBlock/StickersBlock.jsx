import React from "react";
import StickersCol from "../StickersCol/StickersCol";
import { Stack } from "@mui/material";

function StickersBlock({ blockCols }) {
    return (
        <Stack direction="row" spacing={"15px"} alignItems={"start"} minWidth={"910px"}>
            {blockCols.map((col, index) => <StickersCol col={col} key={`${col.colName} ${index}`} />)}
        </Stack>
    );
}

export default StickersBlock;