import NotoSansKRRegularFont from './fonts/Noto_Sans_KR/NotoSansKR-Regular.otf';
import NotoSansKRMediumFont from './fonts/Noto_Sans_KR/NotoSansKR-Medium.otf';
import NotoSansKRBoldFont from './fonts/Noto_Sans_KR/NotoSansKR-Bold.otf';
import RobotoRegularFont from './fonts/Roboto/Roboto-Regular.ttf';
import RobotoMediumFont from './fonts/Roboto/Roboto-Medium.ttf';
import RobotoBoldFont from './fonts/Roboto/Roboto-Bold.ttf';

const NotoSansKRRegular = {
  fontFamily: 'NotoSansKR',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 400,
  src: `
      local('NotoSansKR'),
      local('NotoSansKR-Medium'),
      url(${NotoSansKRRegularFont}) format('woff')
    `,
};

const NotoSansKRMedium = {
  fontFamily: 'NotoSans',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 500,
  src: `
        local('NotoSans'),
        local('NotoSans-Medium'),
        url(${NotoSansKRMediumFont}) format('woff')
      `,
};

const NotoSansKRBold = {
  fontFamily: 'NotoSansKR',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 700,
  src: `
      local('NotoSansKR'),
      local('NotoSansKR-Bold'),
      url(${NotoSansKRBoldFont}) format('woff')
    `,
};

const RobotoRegular = {
  fontFamily: 'Roboto',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 400,
  src: `
      local('Roboto'),
      local('Roboto-Regular'),
      url(${RobotoRegularFont}) format('woff')
    `,
};

const RobotoMedium = {
  fontFamily: 'Roboto',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 500,
  src: `
        local('Roboto'),
        local('Roboto-Medium'),
        url(${RobotoMediumFont}) format('woff')
      `,
};

const RobotoBold = {
  fontFamily: 'Roboto',
  fontStyle: 'normal',
  fontDisplay: 'swap',
  fontWeight: 700,
  src: `
      local('Roboto'),
      local('Roboto-Bold'),
      url(${RobotoBoldFont}) format('woff')
    `,
};

export default {
  NotoSansKRRegular,
  NotoSansKRMedium,
  NotoSansKRBold,
  RobotoRegular,
  RobotoMedium,
  RobotoBold,
};
