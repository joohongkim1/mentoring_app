import React, { useState, useEffect, Component } from 'react';
import Header from '../layout/Header';
import { Link, useHistory } from 'react-router-dom';
import useAxios from 'axios-hooks';

import Container from '@material-ui/core/Container';
import Chip from '@material-ui/core/Chip';
import Divider from '@material-ui/core/Divider';
import { Paper, TextField, Button, Typography, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

const useStyles = makeStyles((theme) => ({
  divContainer: {
    margin: '15px'
  },
  divAnalytics: {
    margin: '15px',
    color: 'red'
  },
  analyticsResult: {
    display: 'flex',
    flexDirection: 'row',
  },
  resultChip: {
    margin: '5px'
  }
}));

const JasoseoDetail = () => {
  const classes = useStyles();

  const [content, setContent] = useState('[멈추지  않고 성장하는 기업과 함께하다]\n 제게 직장은 평생에 걸쳐 인생의 비전을 펼치며 함께 성장하는 동반관계입니다. 그리고 자신의 자리에서 대신할 수 없는 사람이 되는 것이  제가 가진 직업관입니다.          학과 과정과 다양한 대내외 활동을 통해 광고와 마케팅 분야에 계속해서 관심을 가져왔습니다. 역동적이고, 늘 자신을 단련해야 하는 광고  산업에 매력을 느꼈고, 미디어플래너라는 목표를 가지게 되었습니다.          또한, 단지 광고를 만드는 일뿐만 아니라, 마케팅 솔루션 제안을 통해 클라이언트에게 고객가치를 제공하는 미디어렙사의 사업분야에  매료되었습니다. 자신의 자리에서 최선을 다하는 것이 내 조직뿐만 아니라 고객사와 함께 ‘Win-Win’하는 동력이 된다는 것은 그 무엇보다  큰 비전이자 동기라고 생각합니다.          최근 기술력이 상향표준화 된 상황 속에서 광고와 미디어의 중요성은 더욱 커져가고 있습니다. 그리고 메조미디어는 온라인 광고, 마케팅  분야에서 국내 정상의 위치에 서있습니다. 메조미디어는 기업과 함께 성장하는 인재라는 인생의 목표를 이룰 수 있는 가장 멋진 무대입니다.  메조미디어와 함께 성장하며 새로운 솔루션, 새로운 가치를 만드는 일에 이바지하고 싶습니다.');
  const [question, setQuestion] = useState('본인의 비전 및 장래모습에 대해 기술하시오');
  const [title, setTitle] = useState('2015상반기_(주)메조미디어');
  const [keywords, setKeywords] = useState(['성장', '고객', '비전', '목표'])

  const [titleText, setTitleText] = useState(false)
  const [questionText, setQuestionText] = useState(false)
  const [contentText, setContentText] = useState(false)

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

  function doContentAnalytics(event) {
    event.preventDefault();
    doAnalytics().then(result => {
      console.log(result['data'])
      setKeywords(result['data']['핵심역량']);
    })
  }

  function onClickTitle(event) {
    setTitleText(!titleText);
  }
  function onClickQuestion(event) {
    setQuestionText(!questionText);
  }
  function onClickContent(event) {
    setContentText(!contentText);
    setKeywords([])
  }
  function handleKeyPressTitle(e) {
    // if(e.charCode === 13) { //  deprecated
    //   this.handleClick();
    // }

    if (e.key === "Enter") {
      setTitleText(!titleText);
    }
  };
  function handleKeyPressQuestion(e) {
    // if(e.charCode === 13) { //  deprecated
    //   this.handleClick();
    // }

    if (e.key === "Enter") {
      setQuestionText(!questionText);
    }
  };
  function handleKeyPressContent(e) {
    // if(e.charCode === 13) { //  deprecated
    //   this.handleClick();
    // }

    if (e.key === "Enter") {
      setContentText(!contentText);
    }
  };

  return (
    <div>
      <Header />
      <Container maxWidth="sm">
        <div className={classes.divContainer}>
          {!titleText && <div onClick={onClickTitle}>
            <Typography variant="h6">{title}</Typography>
          </div>}

          {titleText && <div onKeyPress={handleKeyPressTitle}>
            <TextField
              InputLabelProps={inputLabelProps}
              InputProps={inputProps}
              name="title"
              onChange={(event) => setTitle(event.target.value)}
              label="제목: ex)2020상반기_삼성전자"
              defaultValue={title}
              type="text"
              variant="outlined"
              fullWidth
              margin="normal"></TextField>
          </div>}

        </div>
        <Divider />
        <div className={classes.divContainer}>
          {!questionText && <div onClick={onClickQuestion}>
            <Typography variant="h6">{question}</Typography>
          </div>}

          {questionText && <div onKeyPress={handleKeyPressQuestion}>
            <TextField
              InputLabelProps={inputLabelProps}
              InputProps={inputProps}
              name="question"
              onChange={(event) => setQuestion(event.target.value)}
              label="문항"
              defaultValue={question}
              type="text"
              variant="outlined"
              fullWidth
              margin="normal"></TextField>
          </div>}

        </div>
        <Divider />
        <div className={classes.divContainer}>
          {!contentText && <div onClick={onClickContent}>
            <Typography variant="body1">{content}</Typography>
            <Divider />
            <div className={classes.divContainer}>
              <Typography variant="h5">자소서의 핵심가치</Typography>
            </div>
            <div className={classes.analyticsResult}>
              {keywords.map((keyword) => <Chip className={classes.resultChip} label={keyword} variant="contained" />)}
            </div>
          </div>}

          {contentText && <div onKeyPress={handleKeyPressContent}>
            <TextField
              InputLabelProps={inputLabelProps}
              InputProps={inputProps}
              name="question"
              onChange={(event) => setContent(event.target.value)}
              label="내용"
              defaultValue={content}
              type="text"
              multiline
              rows={30}
              variant="outlined"
              fullWidth
              margin="normal"></TextField>
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

          }


        </div>

      </Container>
    </div>
  )

}

export default JasoseoDetail;