import "./StickersCol.css";
import { Stack, Typography } from "@mui/material";

function StickersCol({ col }) {
    function getStickerMarketLink(stickerName) {
        return `https://steamcommunity.com/market/listings/730/${encodeURI(stickerName)}`;
    }


    return (
        <Stack direction="column" spacing={"15px"} alignItems={"center"}>
            <Typography color={"black"} fontSize={24}>{col.colName}</Typography>
            <Stack direction="column" spacing={"10px"} alignItems={"center"} className="stickers-col">
                {col.stickersList.map((sticker, index) =>
                    <a href={getStickerMarketLink(sticker.stickerName)} key={`Link ${col.colName} ${sticker.stickerName} ${index}`}>
                        <img src={sticker.stickerSrc} alt={sticker.stickerName} key={`Image ${col.colName} ${sticker.stickerName} ${index}`} />
                    </a>
                )}
            </Stack>
        </Stack>
    );
}

export default StickersCol;