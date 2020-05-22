import React from 'react';
import { Button } from '@material-ui/core';
import { useHistory } from 'react-router-dom';

const Home = () => {
  const history = useHistory();
  return (
    <div>
      <p>Home</p>
      <Button
        onClick={() => {
          history.push('/login');
        }}
      >
        로그인 하기
      </Button>
    </div>
  );
};
export default Home;
