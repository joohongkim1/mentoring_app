/* eslint-disable object-curly-newline */
import React, { useState } from 'react';
import Container from '@material-ui/core/Container';
import { Paper, TextField, Button, Typography, Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Link, useHistory } from 'react-router-dom';
import GoogleLogin from 'react-google-login';
import useAxios, { configure } from 'axios-hooks';
import { useCookies } from 'react-cookie';
import LRU from 'lru-cache';
import Axios from 'axios';
import Loading from '../components/Loading';

const axios = Axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
});

const cache = new LRU({ max: 10 });

configure({ axios, cache });

const useStyles = makeStyles((theme) => ({
  submit: {
    margin: theme.spacing(1, 0, 1),
  },
  page: {
    marginTop: theme.spacing(15),
    marginBottom: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  inputText: {
    width: '90vw',
    maxWidth: '500px',
    marginTop: theme.spacing(1),
  },
  border: {
    borderColor: '#00b08b',
    // border :"1px 1px 1px 1px",
    backgroundColor: '#00b08b',
  },

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
    width: '40vw',
    maxWidth: '400px',
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    padding: theme.spacing(3),
  },
  googleLoginButton: {
    width: '40vh',
  },
  inputRoot: {
    '&$inputFocused $inputNotchedOutline': {
      borderColor: theme.palette.secondary.main,
    },
  },
  inputNotchedOutline: {},
  inputFocused: {},
  inputLabelRoot: {
    '&$inputFocused': {
      color: theme.palette.secondary.main,
    },
  },
}));

const Login = () => {
  const classes = useStyles();
  const history = useHistory();
  const [values, setValues] = useState({
    email: '',
    password: '',
  });
  // eslint-disable-next-line no-unused-vars
  const [cookies, setCookie, removeCookie] = useCookies(['token']);
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
  const [
    { data: result = {}, loading, error, response: res },
    doLogin,
  ] = useAxios(
    {
      url: '/api/v1/signin',
      method: 'POST',
      data: {
        stu_id_email: values.email,
        stu_password: values.password,
      },
    },
    { manual: true },
  );

  const handleFieldChange = (e) => {
    setValues({
      ...values,
      [e.target.name]: e.target.value,
    });
  };
  const onLoginClick = (event) => {
    event.preventDefault();
    console.log(result);
    doLogin();
  };

  const responseGoogleSuccess = (response) => {
    console.log(response);
  };

  const responseGoogleFail = (e) => {
    console.log(e);
  };
  if (result.result === '성공') {
    setCookie('token', result.token);
    history.push('/');
    return <></>;
  }

  if (loading) {
    return <Loading />;
  }
  return (
    <div className={classes.page}>
      <Container maxWidth="md" className={classes.pageContainer}>
        {/* <Paper elevation={3}> */}
        <form className={classes.boxWrapper} onSubmit={onLoginClick}>
          <Typography
            // className={classes.textWelcome}
            color="textSecondary"
            variant="title"
          >
            로그인
          </Typography>
          <TextField
            error={result.status === '실패'}
            // InputLabelProps={inputLabelProps}
            // InputProps={inputProps}
            autoFocus
            name="email"
            value={values.email}
            onChange={(event) => handleFieldChange(event)}
            label="Email"
            type="email"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          <TextField
            error={result.status === '실패'}
            // InputLabelProps={inputLabelProps}
            // InputProps={inputProps}
            name="password"
            onChange={(event) => handleFieldChange(event)}
            label="Password"
            type="password"
            variant="outlined"
            fullWidth
            margin="normal"
            helperText={result.error}
          />
          <Button
            className={classes.submit}
            type="submit"
            // disabled={loading || values.email === '' || values.password === ''}
            variant="contained"
            color="primary"
            disableElevation
            fullWidth
            size="large"
          >
            로그인 하기
          </Button>
        </form>
        {/* </Paper> */}
        <Box display="flex" justifyContent="flex-end">
          <Typography
            className={classes.textRegisterText}
            color="textSecondary"
            variant="body2"
          >
            <Link className={classes.textRegister} to="/signup">
              회원가입
            </Link>
          </Typography>
          <Typography
            className={classes.textRegisterText}
            color="textSecondary"
            variant="body2"
          >
            <Link className={classes.textRegister} to="/register">
              비밀번호 찾기
            </Link>
          </Typography>
        </Box>
      </Container>
    </div>
  );
};

export default Login;
