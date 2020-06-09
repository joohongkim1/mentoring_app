import React, { useState, useEffect, Component } from 'react';
import { Link, useHistory } from 'react-router-dom';
import useAxios from 'axios-hooks';

import Header from '../layout/Header';

import Container from '@material-ui/core/Container';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormLabel from '@material-ui/core/FormLabel';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import { Paper, TextField, Button, Typography, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

// import 'date-fns';
import DateFnsUtils from '@date-io/date-fns';
import Grid from '@material-ui/core/Grid';
import {
  MuiPickersUtilsProvider,
  KeyboardTimePicker,
  KeyboardDatePicker,
} from '@material-ui/pickers';

import Loading from '../Loading';

// import { WithContext as ReactTags } from 'react-tag-input';

// const KeyCodes = {
//   comma: 188,
//   enter: 13,
// };

// const delimiters = [KeyCodes.comma, KeyCodes.enter];

const useStyles = makeStyles((theme) => ({

}));

// {
//   "board_content": "string",
//   "board_question": "string",
//   "board_when": "string",
//   "experience_no": 0
// }

const ExperienceWrite = () => {
  const classes = useStyles();
  const history = useHistory();
  const [content, setContent] = useState('');
  const [startDate, setStartDate] = useState(new Date('2020-06-09T21:11:54'));
  const [endDate, setEndDate] = useState(new Date('2020-06-09T21:11:54'));
  const [keyword, setKeyword] = useState('');
  const [title, setTitle] = useState('');
  const [studentNumber, setStudenNumber] = useState(1);
  const token = window.localStorage.token;

  // const [tag, setTag] = useState([]);

  // const suggestions = [
  //   { id: 'Challenge', text: '도전' },
  //   { id: 'Creativity', text: '창의' },
  //   { id: 'Global', text: '글로벌' },
  //   { id: 'Passion', text: '열정' },
  //   { id: 'Communication', text: '소통' },

  // ]

  const inputProps = {
    classes: {
      root: classes.inputRoot,
      notchedOutline: classes.inputNotchedOutline,
      focused: classes.inputFocused,
    },
  };
  const inputLabelProps = {
    classes: {
      root: classes.inputLabelRoot,
      focused: classes.inputFocused,
    },
  };


  const [{ data: result = {}, loading }, doSaveExperience] = useAxios(
    {
      url: 'http://k02a1041.p.ssafy.io:8081/A104/api/v2/save/1',
      method: 'POST',
      data: {
        "experience_content": content,
        "experience_end": endDate,
        "experience_start": startDate,
        "experience_tag": keyword,
        "experience_title": title
      },
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token
      }
    },
    { manual: true },
  );

//   function handleDelete(i) {
//     const { tags } = tag;
//     setTag({
//       tags: tags.filter((tag, index) => index !== i),
//     });
//   }

//   function handleAddition(tag_temp) {
//     setTag(state => ({ tags: [tag, tag_temp] }));
//   }

//   function handleDrag(tag_temp, currPos, newPos) {
//     const tags = [tag];
//     const newTags = tags.slice();

//     newTags.splice(currPos, 1);
//     newTags.splice(newPos, 0, tag_temp);

//     // re-render
//     setTag({ tags: newTags });
// }

  // if (result.status === 'success') {
  //   // popup 띄우기
  //   history.push('/');
  //   return <></>;
  // }
  if (loading) {
    return <Loading />;
  }
  function onClickSaveExperience(event) {
    event.preventDefault();
    doSaveExperience();
  }
  return (
    <div>
      <Header/>
      <Container maxWidth="sm">
        <TextField
          error={result.status === 'failure'}
          InputLabelProps={inputLabelProps}
          InputProps={inputProps}
          name="question"
          onChange={(event) => setTitle(event.target.value)}
          label="경험요약"
          type="text"
          variant="outlined"
          fullWidth
          margin="normal"
        />
        <MuiPickersUtilsProvider utils={DateFnsUtils}>
          <Grid container justify="space-around">

            <KeyboardDatePicker
              margin="normal"
              id="date-picker-dialog"
              label="시작날짜"
              format="MM/dd/yyyy"
              value={startDate}
              onChange={(date) => setStartDate(date)}
              KeyboardButtonProps={{
                'aria-label': 'change date',
              }}
            />
            <KeyboardDatePicker
              margin="normal"
              id="date-picker-dialog"
              label="종료날짜"
              format="MM/dd/yyyy"
              value={endDate}
              onChange={(date) => setEndDate(date)}
              KeyboardButtonProps={{
                'aria-label': 'change date',
              }}
            />
          </Grid>
        </MuiPickersUtilsProvider>
        <TextField
          error={result.status === 'failure'}
          InputLabelProps={inputLabelProps}
          InputProps={inputProps}
          name="keyword"
          onChange={(event) => setKeyword(event.target.value)}
          label="핵심키워드"
          type="text"
          variant="outlined"
          fullWidth
          margin="normal"
        />
        {/* <ReactTags tags={tag}
          suggestions={suggestions}
          handleDelete={handleDelete}
          handleAddition={handleAddition}
          handleDrag={handleDrag}
          delimiters={delimiters} /> */}
        <div>내용은 STAR 기법으로 정리하시길 권해 드립니다.</div>
        <TextField
          error={result.status === 'failure'}
          InputLabelProps={inputLabelProps}
          InputProps={inputProps}
          name="content"
          onChange={(event) => setContent(event.target.value)}
          label="내용"
          type="text"
          variant="outlined"
          multiline
          rows={30}
          fullWidth
          margin="normal"
        />
        <Button
          variant="contained"
          color="secondary"
          disableElevation
          fullWidth
          size="large"
          onClick={onClickSaveExperience}>
          저장하기
        </Button>
      </Container>
    </div>
  )

}

export default ExperienceWrite;