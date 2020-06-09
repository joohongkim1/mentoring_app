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
import Chip from '@material-ui/core/Chip';
import { makeStyles } from '@material-ui/core/styles';

import Loading from '../Loading';

const useStyles = makeStyles((theme) => ({
  analytics: {
    height: '90px',
    display: 'flex',
    flexDirection: 'column',
    
  },
  analyticsResult: {
    display: 'flex',
    flexDirection: 'row',
  },
  resultChip: {
    margin: '5px'
  }
}));

// {
//   "board_content": "string",
//   "board_question": "string",
//   "board_when": "string",
//   "experience_no": 0
// }

const JasoseoWrite = () => {
  const classes = useStyles();
  const history = useHistory();
  const [content, setContent] = useState('');
  const [question, setQuestion] = useState('');
  const [title, setTitle] = useState('');
  const [experience, setExperience] = useState(1);
  const [keywords, setKeywords] = useState([])
  const token = window.localStorage.token;

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

  const [{ data: resultAnalystics = {} }, doAnalytics] = useAxios(
    {
      url: 'http://k02a1041.p.ssafy.io:8081/A104/last/analytics/profile',
      method: 'POST',
      data: {
        value: content
      },
    },
    { manual: true },
  );

  function doContentAnalytics(event) {
    event.preventDefault();
    doAnalytics().then(result => {
      console.log(result['data'])
      setKeywords(result['data']['핵심역량']);
    })
  }

  const [{ data: result = {}, loading }, doSaveJasoseo] = useAxios(
    {
      url: 'http://localhost:8080/api/v3/save' + experience,
      method: 'POST',
      data: {
        "board_content": content,
        "board_question": question,
        "board_when": title,
        "experience_no": experience
      },
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token
      }
    },
    { manual: true },
  );

  // if (result.status === 'success') {
  //   // popup 띄우기
  //   history.push('/');
  //   return <></>;
  // }
  if (loading) {
    return <Loading />;
  }
  function onClickSaveJasoseo(event) {
    event.preventDefault();
    doSaveJasoseo();
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
          onChange={(event) => setQuestion(event.target.value)}
          label="제목: ex)2020상반기_삼성전자"
          type="text"
          variant="outlined"
          fullWidth
          margin="normal"
        />
        <TextField
          error={result.status === 'failure'}
          InputLabelProps={inputLabelProps}
          InputProps={inputProps}
          name="title"
          onChange={(event) => setTitle(event.target.value)}
          label="문항"
          type="text"
          variant="outlined"
          fullWidth
          margin="normal"
        />
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
        <div className={classes.analytics}>

          <Button
            variant="contained"
            disableElevation
            fullWidth
            size="large"
            onClick={doContentAnalytics}>
            핵심 키워드 분석하기
        </Button>
          <div className={classes.analyticsResult}>
            {keywords.map((keyword) => <Chip className={classes.resultChip} label={keyword} variant="contained" />)}
          </div>
        </div>
        <Button
          variant="contained"
          color="secondary"
          disableElevation
          fullWidth
          size="large"
          onClick={onClickSaveJasoseo}>
          저장하기
        </Button>
      </Container>
    </div>
  )

}

export default JasoseoWrite;