import Checkbox from '@mui/material/Checkbox';
import FormControlLabel from '@mui/material/FormControlLabel';
import { Typography, Select, MenuItem, InputLabel, Stack } from '@mui/material';
import FormControl from '@mui/material/FormControl';

function AdditionalInfo({ maxStickers, setMaxStickers, isUnusualSticks, setIsUnusualSticks }) {
    const handleChange = (event) => {
        setMaxStickers(event.target.value);
    };


    return (
        <Stack direction="row" spacing={"25px"} alignItems={"center"}>
            <FormControlLabel
                value="start"
                control={<Checkbox sx={{
                    color: "black",
                    '&.Mui-checked': {
                        color: "var(--hover-btn)",
                    }
                }} />}
                label={<Typography color={"black"}>Use unusual stickers</Typography>}
                labelPlacement="start"
                style={{ margin: 0 }}
                onClick={() => { setIsUnusualSticks(!isUnusualSticks) }}
            />
            <Stack direction="row" spacing={"5px"} alignItems={"center"} width={"fit-content"}>
                <Typography color={"black"}>Generate a word from</Typography>
                <FormControl sx={{ height: 42, minWidth: 60 }} size="small">
                    <InputLabel id="demo-simple-select-label">N</InputLabel>
                    <Select
                        labelId="demo-simple-select-label"
                        id="demo-simple-select"
                        value={maxStickers}
                        label="N"
                        onChange={handleChange}
                        sx={{
                            '&.Mui-focused .MuiOutlinedInput-notchedOutline': {
                                borderColor: 'var(--hover-btn)'
                            }
                        }}
                    >
                        <MenuItem value={1}>1</MenuItem>
                        <MenuItem value={2}>2</MenuItem>
                        <MenuItem value={3}>3</MenuItem>
                        <MenuItem value={4}>4</MenuItem>
                        <MenuItem value={5}>5</MenuItem>
                    </Select>
                </FormControl>
                <Typography color={"black"}>stickers</Typography>
            </Stack>
        </Stack>
    );
}

export default AdditionalInfo;