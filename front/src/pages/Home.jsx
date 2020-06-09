import React from 'react';
import { Box } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import Header from '../components/layout/Header';
import mainBanner from '../assets/images/main_banner.png';

const useStyles = makeStyles((theme) => ({
  page: {
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    // color : lightBlue[300]
  },
  banner: {
    width: '60%',
    height: '400px',
  },
  bannerBackground: {
    backgroundColor: '#d9d9d9',
  },
}));

const Home = () => {
  const classes = useStyles();

  return (
    <>
      <div className={classes.page}>
        <Box
          display="flex"
          flexDirection="column"
          alignItems="center"
          className={classes.bannerBackground}
        >
          <img src={mainBanner} alt="main banner" className={classes.banner} />
        </Box>
      </div>
      <div>
        <iframe
          id="pageFrame"
          name="pageFrame"
          src="http://www.jobkorea.co.kr/Starter/calendar/sub/month"
          frameborder="0"
          width="80%"
          height="1500px"
          scrolling="auto"
          style={{ marginLeft: '10%', marginTop: '5%' }}
        ></iframe>
      </div>
    </>
  );
};
export default Home;
