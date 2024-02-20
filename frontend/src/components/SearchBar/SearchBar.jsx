import "./SearchBar.css";
import Button from '@mui/material/Button';
import { styled } from '@mui/material/styles';
import SettingsSuggestIcon from '@mui/icons-material/SettingsSuggest';
import Stack from '@mui/material/Stack';
import { useState } from "react";
import { toast } from "react-hot-toast";

function SearchBar({ setStickersBlocks, isUnusualSticks, maxStickers }) {
    const [inputValue, setInputValue] = useState("");


    async function generateCombinations() {
        const toastId = toast.loading('Generating...');
        try {
            let stickersBlocks = await fetch(`http://localhost:8080/generateWord?maxStickers=${maxStickers}&isUnusualSticks=${isUnusualSticks}&wordToGenerate=${inputValue}`).then((response) => response.json());

            if (stickersBlocks.message) {
                toast.error(`Error: ${stickersBlocks.message}`, {
                    id: toastId,
                });
                return;
            }

            setStickersBlocks(stickersBlocks);

            toast.success('Word generated successfully', {
                id: toastId,
            });
        } catch (err) {
            toast.error(`Error: ${err.toString()}`, {
                id: toastId,
            });
        }
    }


    const StyledButton = styled(Button)({
        boxShadow: 'none',
        textTransform: 'none',
        fontSize: 16,
        padding: '6px 12px',
        border: '2px solid black',
        color: "black",
        lineHeight: 1.5,
        backgroundColor: 'var(--main-color)',
        borderRadius: "0 100px 100px 0",
        fontFamily: [
            'Roboto'
        ].join(','),
        '&:hover': {
            backgroundColor: 'var(--hover-btn)',
            boxShadow: 'none',
        }
    });

    return (
        <Stack direction="row" spacing={-1}>
            <input className="search-bar-left" placeholder="Enter word to generate" value={inputValue} onChange={(ev) => { setInputValue(ev.target.value) }} />
            <StyledButton variant="contained" startIcon={<SettingsSuggestIcon />} onClick={() => { generateCombinations() }}>
                Generate
            </StyledButton>
        </Stack>
    );
}

export default SearchBar;