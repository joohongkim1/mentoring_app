import React, { useState, useEffect, Component } from 'react';
import { Link, useHistory } from 'react-router-dom';

import PropTypes from 'prop-types';
import { makeStyles } from '@material-ui/core/styles';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import Checkbox from '@material-ui/core/Checkbox';
import Avatar from '@material-ui/core/Avatar';


const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));


const JasoseoList = () => {

  const classes = useStyles();
  const [checked, setChecked] = React.useState([1]);

  const handleToggle = (value) => () => {
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };


  // // 로그인 확인 작업 필요 => result 출력 필수
  // const [{ data: result = {}, loading }, doJasoseoList] = useAxios(
  //   {
  //     url: 'http://localhost:8080/api/v3/1',
  //     method: 'GET',
  //   },
  //   { manual: true },
  // );

  const jasoseo = [
    { "board_when": "aaa" },
    { "board_when": "bbb" },
    { "board_when": "ccc" },
  
  ]
  return (

    <div>
      <List dense className={classes.root}>
        {jasoseo.map((value) => {
          const labelId = `checkbox-list-secondary-label-${value["board_when"]}`;
          return (
            <ListItem key={value} button>
              <ListItemText id={labelId} primary={value["board_when"]} />
              <ListItemSecondaryAction>
                <Checkbox
                  edge="end"
                  onChange={handleToggle(value["board_when"])}
                  checked={checked.indexOf(value["board_when"]) !== -1}
                  inputProps={{ 'aria-labelledby': labelId }}
                />
              </ListItemSecondaryAction>
            </ListItem>
          );
        })}
      </List>
    </div>
  )
}

export default JasoseoList;