import './MainPage.css'
import SearchBar from '../../components/SearchBar/SearchBar'
import AdditionalInfo from '../../components/AdditionalInfo/AdditionalInfo';
import StickersBlock from '../../components/StickersBlock/StickersBlock';
import { Stack, Divider, Typography } from '@mui/material';
import { useState } from 'react';
import { Toaster } from "react-hot-toast";

function MainPage() {
  const [stickersBlocks, setStickersBlocks] = useState(null);
  const [isUnusualSticks, setIsUnusualSticks] = useState(false);
  const [maxStickers, setMaxStickers] = useState("");


  return (
    <>
      <main>
        <Stack direction="column" spacing={"85px"} alignItems={"center"} className="wrapper">
          <Typography color={"black"} variant='h4'>Generate any words by CS2 stickers</Typography>
          <Stack direction="column" spacing={"85px"} alignItems={"center"}>
            <Stack direction="column" spacing={"15px"} alignItems={"center"}>
              <SearchBar setStickersBlocks={setStickersBlocks} isUnusualSticks={isUnusualSticks} maxStickers={maxStickers} />
              <AdditionalInfo maxStickers={maxStickers} setMaxStickers={setMaxStickers} setIsUnusualSticks={setIsUnusualSticks} isUnusualSticks={isUnusualSticks} />
            </Stack>
            <Stack direction="column" spacing={"55px"} alignItems={"center"}>
              {
                stickersBlocks !== null ? stickersBlocks.map((block, index) =>
                  <Stack direction="column" spacing={"55px"} alignItems={"center"} key={`Block ${index}`}>
                    <StickersBlock blockCols={block.colsList} key={`Block ${block.charKey} ${index}`} />
                    {index !== (stickersBlocks.length - 1) ? <Divider orientation="horizontal" variant="middle" sx={{ backgroundColor: "var(--main-color)" }} key={`Divider ${block.charKey} ${index}`} flexItem /> : <></>}
                  </Stack>
                ) : <></>
              }
            </Stack>
          </Stack>
        </Stack>
      </main>
      <Toaster position="top-right" reverseOrder={false}></Toaster>
    </>
  )
}

export default MainPage
