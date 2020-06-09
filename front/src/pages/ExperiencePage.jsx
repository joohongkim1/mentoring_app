import React, { useState, useEffect } from 'react';
import ExperienceContainer from '../containers/ExperienceContainer';

import Radio from '@material-ui/core/Radio';
import RadioGroup from '@material-ui/core/RadioGroup';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import FormControl from '@material-ui/core/FormControl';
import FormLabel from '@material-ui/core/FormLabel';

const Experience = () => {
  const [value, setValue] = React.useState('dates');


  const handleChange = (event) => {
    setValue(event.target.value);
  };

  return (
    <div>
      <FormControl component="fieldset">
        <RadioGroup aria-label="mode" name="mode1" value={value} onChange={handleChange}>
          <FormControlLabel value="dates" control={<Radio />} label="일정별" />
          <FormControlLabel value="keywords" control={<Radio />} label="키워드별" />
        </RadioGroup>
      </FormControl>
      <ExperienceContainer mode={value}/>
    </div>
  )
}

export default Experience;