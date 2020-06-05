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
  },
  bannerBackground: {
    backgroundColor: '#d9d9d9',
  },
}));

const Home = () => {
  const classes = useStyles();

  return (
    <>
      <Header />
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

      {/* Test용 */}
      <Box height={5000}>
        <p>d</p>
      </Box>
    </>
  );
};
export default Home;
