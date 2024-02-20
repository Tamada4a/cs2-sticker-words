import "./StickersCol.css";
import { Stack, Typography } from "@mui/material";

function StickersCol({ col }) {
    return (
        <Stack direction="column" spacing={"15px"} alignItems={"center"}>
            <Typography color={"black"} fontSize={24}>{col.colName}</Typography>
            <Stack direction="column" spacing={"10px"} alignItems={"center"} className="stickers-col">
                {col.stickersList.map((sticker, index) => <img src={sticker.stickerSrc} alt={sticker.stickerName} key={`${col.colName} ${sticker.stickerName} ${index}`} />)}
            </Stack>
        </Stack>
    );
}

export default StickersCol;