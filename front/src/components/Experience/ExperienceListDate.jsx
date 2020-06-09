import React, { useState, useEffect } from 'react';
import useAxios from 'axios-hooks';

// material-ui
import { makeStyles, useTheme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import { green } from '@material-ui/core/colors';

const useStyles = makeStyles((theme) => ({
  root: {
    backgroundColor: theme.palette.background.paper,
    width: 500,
    position: 'relative',
    minHeight: 200,
  },
  fab: {
    position: 'absolute',
    bottom: theme.spacing(2),
    right: theme.spacing(2),
  },
  fabGreen: {
    color: theme.palette.common.white,
    backgroundColor: green[500],
    '&:hover': {
      backgroundColor: green[600],
    },
  },
}));

const tempExperience = [
  {
    "experience_content": "동아리 회장",
    "experience_end": "2018-01-08T19:14:29.484Z",
    "experience_no": 1,
    "experience_start": "2019-01-08T19:14:29.485Z",
    "experience_tag": "도전",
    "experience_title": "동아리 회장"
  },
  {
    "experience_content": "동아리 회장",
    "experience_end": "2017-07-08T19:14:29.484Z",
    "experience_no": 1,
    "experience_start": "2017-11-08T19:14:29.485Z",
    "experience_tag": "도전",
    "experience_title": "동아리 회장"
  },
  {
    "experience_content": "동아리 회장",
    "experience_end": "2017-03-08T19:14:29.484Z",
    "experience_no": 1,
    "experience_start": "2017-06-08T19:14:29.485Z",
    "experience_tag": "도전",
    "experience_title": "동아리 회장"
  }
]

const ExperienceListDate = () => {
  const classes = useStyles();
  const [year, setYear] = React.useState(0);
  const [halfYear, setHalfYear] = React.useState(0);
  const token = window.localStorage.token;
  // useEffect(() => {
  //   const years = []
  //   for(let te in tempExperience) {
  //     str = te["experience_start"].substring(0, 3);
      
  //   }
  // });

  const [{ data: result = {}, loading }, doSaveExperience] = useAxios(
    {
      url: 'http://k02a1041.p.ssafy.io:8081/A104/api/v2/1',
      method: 'GET',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': token
      }
    },
    { manual: true },
  );

  const handleChangeYear = (event, newValue) => {
    setYear(newValue);
  };

  const handleChangeHalfYear = (event, newValue) => {
    setHalfYear(newValue);
  };

  const handleChangeIndex = (index) => {
    setYear(index);
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" color="default">
        <Tabs
          value={year}
          onChange={handleChangeYear}
          indicatorColor="primary"
          textColor="primary"
          variant="fullWidth"
          aria-label="action tabs example"
        >
          <Tab label="Item One" />
          <Tab label="Item Two" />
          <Tab label="Item Three" />
        </Tabs>
      </AppBar>
      <AppBar position="static" color="default">
        <Tabs
          value={halfYear}
          onChange={handleChangeHalfYear}
          indicatorColor="primary"
          textColor="primary"
          variant="fullWidth"
          aria-label="action tabs example"
        >
          <Tab label="Item One" />
          <Tab label="Item Two" />

        </Tabs>
      </AppBar>
    </div>
  )
}
export default ExperienceListDate;