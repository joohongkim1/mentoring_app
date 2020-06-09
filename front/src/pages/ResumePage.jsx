import React from 'react';
import AppBar from '@material-ui/core/AppBar';
import Button from '@material-ui/core/Button';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardHeader from '@material-ui/core/CardHeader';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import StarIcon from '@material-ui/icons/StarBorder';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import Link from '@material-ui/core/Link';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import Box from '@material-ui/core/Box';
import styled from 'styled-components';

import { useHistory } from 'react-router-dom';

const useStyles = makeStyles((theme) => ({
  '@global': {
    ul: {
      margin: 0,
      padding: 0,
      listStyle: 'none',
    },
  },
  appBar: {
    borderBottom: `1px solid ${theme.palette.divider}`,
  },
  toolbar: {
    flexWrap: 'wrap',
  },
  toolbarTitle: {
    flexGrow: 1,
  },
  link: {
    margin: theme.spacing(1, 1.5),
  },
  heroContent: {
    padding: theme.spacing(8, 0, 6),
  },
  cardHeader: {
    backgroundColor:
      theme.palette.type === 'light'
        ? theme.palette.grey[200]
        : theme.palette.grey[700],
  },
  cardPricing: {
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'baseline',
    marginBottom: theme.spacing(2),
  },
  writeJasoseoButton: {
    margin: '100px'
  }
}));

const tiers = [
  {
    title: '삼성전자 S/W',
    subtitle:
      '삼성전자를 지원한 이유와 입사 후 회사에서 이루고 싶은 꿈을 기술하십시오.',
    description: [
      '매번 새로운 기능을 적용한 스마트폰을 출시할때마다 국내뿐만 아니라 해외 언론들로부터...',
    ],
    buttonText: '전체보기',
    buttonVariant: 'contained',
  },
  {
    title: '현대오토에버',
    subtitle:
      '업(業)에 대한 본인의 가치관과 신념은 무엇인지 구체적으로 기술 바랍니다.',
    description: [
      '안정적인 ICT 기반을 만드는 사람이 되고 싶어 지원했습니다. 대학교 때부터 취미삼아...',
    ],
    buttonText: '전체보기',
    buttonVariant: 'contained',
  },
  {
    title: 'SK C&C',
    subtitle:
      '높은 목표 설정 자발적으로 최고 수준의 목표를 세우고 끈질기게 성취한 경험에 대해 서술해..',
    description: [
      '앞서 1번 항목에서 말씀드린 문제를 겪은 뒤, 교수님께서 제게 과제를 주셨습니다. apk파일이었...',
    ],
    buttonText: '전체보기',
    buttonVariant: 'contained',
  },
];

export default function ResumePage() {
  const classes = useStyles();
  const history = useHistory();
  function goToWriteJasoseo(){
    history.push('/jasoseowrite');
  }
  return (
    <React.Fragment>
      <CssBaseline />

      {/* Hero unit */}
      <Container maxWidth="sm" component="main" className={classes.heroContent}>
        <Typography
          component="h1"
          variant="h2"
          align="center"
          color="textPrimary"
          gutterBottom
          style={{ fontFamily: 'Recipe korea', marginTop: '40px' }}
        >
          자소서 목록
        </Typography>
      </Container>
      {/* End hero unit */}
      <Container maxWidth="md" component="main">
        <Grid container spacing={5} alignItems="flex-end">
          {tiers.map((tier) => (
            // Enterprise card is full width at sm breakpoint
            <Grid
              item
              key={tier.title}
              xs={12}
              sm={tier.title === 'Enterprise' ? 12 : 6}
              md={4}
            >
              <Card>
                <div
                  title={tier.title}
                  subheader={tier.subheader}
                  titleTypographyProps={{ align: 'center' }}
                  subheaderTypographyProps={{ align: 'center' }}
                  className={classes.cardHeader}
                  style={{
                    fontFamily: 'Recipe Korea',
                    height: '50px',
                    textAlign: 'center',
                    paddingTop: '5%',
                    fontSize: '21px',
                  }}
                >
                  {tier.title}
                </div>
                <CardContent>
                  <div className={classes.cardPricing}>
                    <Typography
                      style={{
                        fontFamily: 'Gmarket',
                      }}
                    >
                      {tier.subtitle}
                    </Typography>
                  </div>
                  <div>{tier.description}</div>
                </CardContent>
                <CardActions>
                  <Button
                    fullWidth
                    variant={tier.buttonVariant}
                    color="primary"
                  >
                    {tier.buttonText}
                  </Button>
                </CardActions>
              </Card>
            </Grid>
          ))}
        </Grid>
        <div className={classes.writeJasoseoButton}>
          <Button
            fullWidth
            variant="contained"
            onClick={goToWriteJasoseo}
            >자소서 작성하기</Button>
        </div>
      </Container>
    </React.Fragment>
  );
}
