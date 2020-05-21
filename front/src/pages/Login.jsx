/* eslint-disable object-curly-newline */
import React, { useState } from 'react';
import Container from '@material-ui/core/Container';
import { Paper, TextField, Button, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';
import { Link, useHistory } from 'react-router-dom';
import GoogleLogin from 'react-google-login';
import useAxios from 'axios-hooks';
import Loading from '../components/Loading';

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

const Login = () => {
  const classes = useStyles();
  const history = useHistory();
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
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
  const [{ data: result = {}, loading }, doLogin] = useAxios(
    {
      url: '/auth/login',
      method: 'POST',
      data: {
        email,
        password,
      },
    },
    { manual: true },
  );
  const onLoginClick = (event) => {
    event.preventDefault();
    doLogin();
  };

  const responseGoogle = (response) => {
    console.log(response);
  };
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
        <form className={classes.boxWrapper} onSubmit={onLoginClick}>
          <img className={classes.logo} src="../logo.png" alt="logo" />
          <Typography
            className={classes.textWelcome}
            color="textSecondary"
            variant="subtitle1"
          >
            로그인
          </Typography>
          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="email"
            onChange={(event) => setEmail(event.target.value)}
            label="Email"
            type="email"
            variant="outlined"
            fullWidth
            margin="normal"
          />
          <TextField
            error={result.status === 'failure'}
            InputLabelProps={inputLabelProps}
            InputProps={inputProps}
            name="password"
            onChange={(event) => setPassword(event.target.value)}
            label="Password"
            type="password"
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
            disabled={loading || email === '' || password === ''}
            variant="contained"
            color="secondary"
            disableElevation
            fullWidth
            size="large"
          >
            Log In
          </Button>
        </form>
        <p>- 또는 -</p>
        {/* 929777188415-7f3v0m82ge8os10uc3minp2lv45vftf8.apps.googleusercontent.com */}
        <GoogleLogin
          clientId="929777188415-7f3v0m82ge8os10uc3minp2lv45vftf8.apps.googleusercontent.com"
          // render={(renderProps) => (
          //   <Button
          //     onClick={renderProps.onClick}
          //     disabled={renderProps.disabled}
          //   >
          //     This is my custom Google button
          //   </Button>
          // )}
          buttonText="구글 계정으로 로그인하기"
          onSuccess={responseGoogle}
          onFailure={responseGoogle}
          cookiePolicy={'single_host_origin'}
        />
        {/* </Paper> */}
        <Typography
          className={classes.textRegisterText}
          color="textSecondary"
          variant="body2"
        >
          <Link className={classes.textRegister} to="/register">
            Register
          </Link>
        </Typography>
      </Container>
    </div>
  );
};

export default Login;
