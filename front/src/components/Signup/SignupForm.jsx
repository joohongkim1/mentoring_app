
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


export default function SignupPage() {
  const classes = useStyles();
  const history = useHistory();
  const [auth, setAuth] = React.useState('mentee');
  const [email, setEmail] = useState('');
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
      url: 'http://localhost:8080/api/vi/signup',
      method: 'POST',
      data: {
        "stu_auth": 0,
        "stu_id_email": email,
        "stu_major": "전자공학",
        "stu_name": "박재성",
        "stu_password": password,
        "stu_school": "경희대학교",
        "stu_total_mileage": 0
      },
    },
    { manual: true },
  );

  const [{ data: emailResult = {} }, doCheckEmail] = useAxios(
    {
      url: 'http://localhost:8080/api/vi/checkid/'+email,
      method: 'POST',
    },
    { manual: true },
  );
  // Mentee, Mento 선택 Radio 함수
  const handleChange = (event) => {
    setAuth(event.target.value);
  };
  const onSignupClick = (event) => {
    event.preventDefault();
    doSignup();
  };
  function onCheckEmailClick(event) {
    event.preventDefault();
    doCheckEmail().then(result =>{
      console.log(result.data);
      setEmailCheck(result.data);
    }
    )
  }
  const onChangeEmail = (event) => {
    setEmail(event.target.value);
    setEmailCheck('');
  }

  if (result.status === 'success') {
    history.push('/');
    return <></>;
  }
  if (loading) {
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
          <Button
            classes={{
              root: classes.loginButtonRoot,
              label: classes.loginButtonText,
            }}
            type="submit"
            disabled={loading || email === '' || password === '' || passwordConfirm === ''|| emailCheck === '' || doesEmailCheck() || !doesPasswordMatch()}
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