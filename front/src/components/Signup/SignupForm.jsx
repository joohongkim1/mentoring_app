
import React, { useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import useAxios from 'axios-hooks';

import Container from '@material-ui/core/Container';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormLabel from '@material-ui/core/FormLabel';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import { Paper, TextField, Button, Typography, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

import Loading from '../Loading'

const useStyles = makeStyles((theme) => ({
  pageWrapper: {
    height: '100vh',
    display: 'flex',
    flexDirection: 'column',
  },
  pageContainer: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    justifyContent: 'center',
    flexGrow: '1',
  },
  boxWrapper: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: theme.spacing(3),
  },
  // inputRoot: {
  //   '&$inputFocused $inputNotchedOutline': {
  //     borderColor: theme.palette.secondary.main,
  //   },
  // },
  // inputNotchedOutline: {},
  // inputFocused: {},
  // inputLabelRoot: {
  //   '&$inputFocused': {
  //     color: theme.palette.secondary.main,
  //   },
  // },
}));


export default function SignupForm({ user }) {
  const classes = useStyles();
  const history = useHistory();
  const [auth, setAuth] = React.useState('mentee');
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [univ, setUniv] = useState('');
  const [major, setMajor] = useState('');
  const [company, setCompany] = useState('');
  const [task, setTask] = useState('');
  const [password, setPassword] = useState('');
  const [passwordConfirm, setPasswordConfirm] = useState('');
  const [emailCheck, setEmailCheck] = useState('');


  const doesPasswordMatch = () => {
    if (passwordConfirm) {
      return password === passwordConfirm;
    }
    else {
      return true;
    }
  }
  const doesEmailCheck = () => {
    if (email) {
      if (emailCheck === '') {
        return '';
      }
      else {
        return emailCheck;
      }
    }
    else {
      return false;
    }
  }

  const renderFeedbackMessage = () => {

    if (passwordConfirm) {
      if (!doesPasswordMatch()) {
        return (
          <div className="invalid-feedback">패스워드가 일치하지 않습니다.</div>
        );
      }
    }
  }
  const rendeEmailCheckMessage = () => {

    if (emailCheck !== '') {
      if (doesEmailCheck()) {
        return (
          <div className="invalid-feedback">이미 존재하는 이메일입니다.</div>
        );
      }
    }
  }

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

  const [{ data: result = {}, loading }, doSignup] = useAxios(
    {
      url: 'http://localhost:8080/api/v1/signup',
      method: 'POST',
      data: {
        "stu_auth": 0,
        "stu_id_email": email,
        "stu_major": major,
        "stu_name": name,
        "stu_password": password,
        "stu_school": univ,
        "stu_total_mileage": 0
      },
    },
    { manual: true },
  );

  const [{ data: mentoResult = {}, mentoLoading }, doMentoSignup] = useAxios(
    {
      url: 'http://localhost:8080/api/m1/signup',
      method: 'POST',
      data: {
        "mentor_auth": 0,
        "mentor_check": false,
        "mentor_company": company,
        "mentor_id_email": email,
        "mentor_identification_url": "",
        "mentor_job": task,
        "mentor_school": univ,
        "mentor_major": major,
        "mentor_name": name,
        "mentor_password": password,
        "mentor_total_mileage": 0
      },
    },
    { manual: true },
  );

  const [{ data: emailResult = {} }, doCheckEmail] = useAxios(
    {
      url: 'http://localhost:8080/api/v1/checkid/' + email,
      method: 'POST',
    },
    { manual: true },
  );
  // Mentee, Mento 선택 Radio 함수
  const handleChange = (event) => {
    setAuth(event.target.value);
  };

  const [{ data: emailMentoResult = {} }, doMentoCheckEmail] = useAxios(
    {
      url: 'http://localhost:8080/api/m1/checkid/' + email,
      method: 'POST',
    },
    { manual: true },
  );


  const onSignupClick = (event) => {
    event.preventDefault();
    // user = {
    //   "stu_auth": 0,
    //   "stu_id_email": email,
    //   "stu_major": "",
    //   "stu_name": name,
    //   "stu_password": password,
    //   "stu_school": "",
    //   "stu_total_mileage": 0
    // }
    if (auth === 'mentee') {
      doSignup();
    }
    else if (auth === 'mento') {
      doMentoSignup();
    }
  };
  function onCheckEmailClick(event) {
    event.preventDefault();
    if (auth === 'mentee') {
      doCheckEmail().then(result => {
        console.log(result.data);
        setEmailCheck(result.data);
      })
    }
    else {
      doMentoCheckEmail().then(result => {
        console.log(result.data);
        setEmailCheck(result.data);
      })
    }
  }
  const onChangeEmail = (event) => {
    setEmail(event.target.value);
    setEmailCheck('');
  }

  if (result.status === 'success' || mentoResult.state === 'success') {
    history.push('/');
    return <></>;
  }
  if (loading || mentoLoading) {
    return <Loading />;
  }

  return (
    <div className={classes.pageWrapper}>
      <Container maxWidth="md" className={classes.pageContainer}>
        {/* <Paper elevation={3}> */}
        <form className={classes.boxWrapper} onSubmit={onSignupClick}>
          <img className={classes.logo} src="../logo.png" alt="logo" />
          <Typography
            className={classes.textWelcome}
            color="textSecondary"
            variant="subtitle1"
          >
            회원가입
          </Typography>
          <FormControl component="fieldset">
            {/* <FormLabel component="auth">가입 유형</FormLabel> */}
            <RadioGroup aria-label="auth" name="auth" value={auth} onChange={handleChange}>
              <FormControlLabel value="mentee" control={<Radio />} label="Mentee" />
              <FormControlLabel value="mento" control={<Radio />} label="Mento" />
            </RadioGroup>
          </FormControl>
          <TextField
            error={result.status === 'failure' || doesEmailCheck()}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="email"
            onChange={onChangeEmail}
            label="Email"
            type="email"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          {rendeEmailCheckMessage()}
          <Button
            onClick={onCheckEmailClick}>
            이메일 중복 확인
          </Button>
          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="name"
            onChange={(event) =>
              setName(event.target.value)
            }
            label="name"
            type="text"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          {auth === 'mento' && <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="company"
            onChange={(event) =>
              setCompany(event.target.value)
            }
            label="현재 다니는 회사"
            type="text"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          }
          {auth === 'mento' && <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="job"
            onChange={(event) =>
              setTask(event.target.value)
            }
            label="직무"
            type="text"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />}

          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="password"
            onChange={(event) =>
              setPassword(event.target.value)
            }
            label="Password"
            type="password"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          <TextField
            error={result.status === 'failure' || !doesPasswordMatch()}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="passwordConfirm"
            onChange={(event) => setPasswordConfirm(event.target.value)}
            label="PasswordConfirm"
            type="password"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          {renderFeedbackMessage()}

          {auth === 'mentee' && <div>선택사항</div>}
          
          {/* 대학교 */}
          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="university"
            onChange={(event) =>
              setUniv(event.target.value)
            }
            label="대학교"
            type="text"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          {/* 전공 */}
          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="major"
            onChange={(event) =>
              setMajor(event.target.value)
            }
            label="전공"
            type="text"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          <Button
            classes={{
              root: classes.loginButtonRoot,
              label: classes.loginButtonText,
            }}
            type="submit"
            disabled={loading || email === '' || password === '' || passwordConfirm === '' || doesEmailCheck() === '' || doesEmailCheck() || !doesPasswordMatch()}
            variant="contained"
            color="secondary"
            disableElevation
            fullWidth
            size="large"
          >
            Sign Up
          </Button>
        </form>
        {/* </Paper> */}
        <Typography
          className={classes.textRegisterText}
          color="textSecondary"
          variant="body2"
        >
          <Box>
            <Link className={classes.textRegister} to="/login">
              로그인
            </Link>
            <Box>
              <Link className={classes.textRegister} to="/">
                아이디
              </Link>
              /
              <Link className={classes.textRegister} to="/">
                비밀번호 찾기
              </Link>
            </Box>
          </Box>
        </Typography>
      </Container>
    </div>
  )
}