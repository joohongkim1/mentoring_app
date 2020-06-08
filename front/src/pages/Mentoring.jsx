/* eslint-disable no-shadow */
import React, { useEffect, useState, useRef } from 'react';
import io from 'socket.io-client';
import Peer from 'simple-peer';
import styled from 'styled-components';
import { Box, Grid } from '@material-ui/core';
import { makeStyles } from '@material-ui/styles';
import Connecting from '../assets/images/connecting.png';

const Container = styled.div`
  height: 100vh;
  width: 100%;
  display: flex;
  flex-direction: column;
  background-color: #595959;
`;

const Row = styled.div`
  display: flex;
  width: 100%;
`;

const Video = styled.video`
  border: 1px solid blue;
  width: 100%;
  /* max-width: 550px; */
  /* height: 80%; */
`;

const Connenct = styled.img`
  border: 1px solid blue;
  width: 100%;
  background-color: #ffffff;
  /* height: 100%; */
`;
const useStyles = makeStyles((theme) => ({
  page: {
    marginTop: theme.spacing(15),
    marginBottom: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    backgroundColor: '#222222',
  },
  // videoGrid: {
  //   backgroundColor: '#FFFFFF',
  // }
}));
const Mentoring = () => {
  const [yourID, setYourID] = useState('');
  const [users, setUsers] = useState({});
  const [stream, setStream] = useState();
  const [receivingCall, setReceivingCall] = useState(false);
  const [caller, setCaller] = useState('');
  const [callerSignal, setCallerSignal] = useState();
  const [callAccepted, setCallAccepted] = useState(false);

  const userVideo = useRef();
  const partnerVideo = useRef();
  const socket = useRef();

  const classes = useStyles();

  useEffect(() => {
    socket.current = io.connect(process.env.REACT_APP_RTC_SERVER_URL);
    navigator.mediaDevices
      .getUserMedia({ video: true, audio: true })
      .then((stream) => {
        setStream(stream);
        if (userVideo.current) {
          userVideo.current.srcObject = stream;
        }
      });

    socket.current.on('yourID', (id) => {
      setYourID(id);
    });
    socket.current.on('allUsers', (users) => {
      setUsers(users);
    });

    socket.current.on('hey', (data) => {
      setReceivingCall(true);
      setCaller(data.from);
      setCallerSignal(data.signal);
    });
  }, []);

  function callPeer(id) {
    const peer = new Peer({
      initiator: true,
      trickle: false,
      config: {
        iceServers: [
          {
            urls: 'stun:numb.viagenie.ca',
            username: 'gunhyuck11@gmail.com',
            credential: '19930330',
          },
          {
            urls: 'turn:numb.viagenie.ca',
            username: 'gunhyuck11@gmail.com',
            credential: '19930330',
          },
        ],
      },
      stream,
    });

    peer.on('signal', (data) => {
      socket.current.emit('callUser', {
        userToCall: id,
        signalData: data,
        from: yourID,
      });
    });

    peer.on('stream', (stream) => {
      if (partnerVideo.current) {
        partnerVideo.current.srcObject = stream;
      }
    });

    socket.current.on('callAccepted', (signal) => {
      setCallAccepted(true);
      peer.signal(signal);
    });
  }

  function acceptCall() {
    setCallAccepted(true);
    const peer = new Peer({
      initiator: false,
      trickle: false,
      stream,
    });
    peer.on('signal', (data) => {
      socket.current.emit('acceptCall', { signal: data, to: caller });
    });

    peer.on('stream', (stream) => {
      partnerVideo.current.srcObject = stream;
    });

    peer.signal(callerSignal);
  }

  let UserVideo = <Connenct src={Connecting} alt="connecting" />;
  if (stream) {
    UserVideo = <Video playsInline muted ref={userVideo} autoPlay />;
  }

  let PartnerVideo = <Connenct src={Connecting} alt="connecting" />;
  if (callAccepted) {
    PartnerVideo = <Video playsInline ref={partnerVideo} autoPlay />;
  }

  let incomingCall;
  if (receivingCall) {
    incomingCall = (
      <div>
        <h1>{caller} is calling you</h1>
        <button type="button" onClick={acceptCall}>
          Accept
        </button>
      </div>
    );
  }
  return (
    <Container>
      {/* <Box display="flex" justifyContent="space-around">
        {UserVideo}
        {PartnerVideo}
      </Box>
      <Box display="flex" justifyContent="space-around">
        <Box width="50%">
          <img src={Connecting} alt="connecting" style={{ width: '100%' }} />
        </Box>
      </Box> */}
      <Box maxWidth="1400px">
        <Grid
          container
          direction="row"
          justify="space-evenly"
          // alignItems="center"
          spacing={3}
        >
          <Grid item sm={5} xs={false}>
            {UserVideo}
          </Grid>
          <Grid item sm={5} xs={11}>
            {PartnerVideo}
          </Grid>
        </Grid>
      </Box>
      <Row>
        {Object.keys(users).map((key) => {
          if (key === yourID) {
            return null;
          }
          return (
            <button type="button" onClick={() => callPeer(key)}>
              Call {key}
            </button>
          );
        })}
      </Row>
      <Row>{incomingCall}</Row>
    </Container>
  );
};

export default Mentoring;
