import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => (
  <div>
    <Link to="/signup">회원가입</Link>
    <Link to="/login">로그인</Link>
    <Link to="/jasoseo">자소서 관리</Link>
    <Link to="/experience">경험 정리</Link>
    <p>Home</p>
  </div>
);
export default Home;
