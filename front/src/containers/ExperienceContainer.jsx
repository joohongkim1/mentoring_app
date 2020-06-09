import React, { useState } from 'react';
import ExperienceListDate from '../components/Experience/ExperienceListDate';
import ExperienceListKeyword from '../components/Experience/ExperienceListKeyword';

const ExperienceContainer = ({mode}) => {
  if (mode==='dates'){
    return(
      <div>
        <ExperienceListDate/>
      </div>
    )
  }
  else {
    return(
      <div>
        <ExperienceListKeyword/>
      </div>
    )
  }
  
}

export default ExperienceContainer;