import React from 'react';
import { Link } from 'react-router-dom';
import Header from '../components/layout/Header';
import { Box } from '@material-ui/core';

const Home = () => (
  <>
    <Header />
    <Link to="/signup">회원가입</Link>
    <Link to="/login">로그인</Link>
    <p>Home</p>

    {/* Test용 */}
    <Box height={5000}></Box>
  </>
);
export default Home;
