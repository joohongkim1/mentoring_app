import React from 'react';
import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import { Paper, TextField, Button, Typography } from '@material-ui/core';
import { makeStyles } from '@material-ui/core/styles';

import { Link } from 'react-router-dom';

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
  

export default function SignupPage({ user, onChangeField }) {
    const classes = useStyles();
    const onChange = e => {
        onChangeField(e.target.value);
    }

    return (
        <div className={classes.pageWrapper}>
            <form onSubmit="a">
                <div>
                    <input
                        type="email"
                        name="email"
                        value={user.email}
                        placeholder="이메일"
                        onChange={onChange}
                    />
                </div>
                <div>
                    <input
                        type="text"
                        name="nickname"
                        value={user.nickname}
                        placeholder="닉네임"
                        onChange={onChange}
                    />
                </div>
                <div>
                    <input
                        type="password"
                        name="password"
                        value={user.password}
                        placeholder="비밀번호"
                        onChange={onChange}
                    />
                </div>
                <div>
                    <input
                        type="password"
                        name="passwordConfirm"
                        value={user.passwordConfirm}
                        placeholder="비밀번호 확인"
                        onChange={onChange}
                    />
                </div>
                <div>
                    <FormControl component="fieldset">
                        <RadioGroup aria-label="authority" name="authority" value={user.authority} onChange={onChange}>
                            <FormControlLabel value="mentee" control={<Radio />} label="멘티" />
                            <FormControlLabel value="mento" control={<Radio />} label="멘토" />
                        </RadioGroup>
                    </FormControl>
                </div>

                <Button type="submit" variant="contained" color="primary">
                    Sign Up
                </Button>

            </form>
            <Link to="/login">
            Login
          </Link>

        </div>
    )
}