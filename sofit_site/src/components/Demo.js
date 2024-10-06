import React, { useState, useEffect } from 'react';
import Stack from '@mui/material/Stack';
import Badge from '@mui/material/Badge';
import MailIcon from '@mui/icons-material/Mail';
import Apis from '../api/Apis'

const Demo = ({ className, onClick }) => {

  // const [alarmCount, setalarmCount] = useState(1);
  // useEffect(() => {
  //   Apis.get('/alarm/count')
  //   .then((response) => {
  //     setalarmCount(response.data.data)
  //   });
  // }, []);

  return (
    <Stack
      direction="row"
      spacing={2}
      className={className}
      onClick={onClick}
      style={{ fontSize: '30px' }} // 아이콘 크기 조절
    >
      <Badge badgeContent={0} color="secondary">
        <MailIcon style={{ fontSize: '30px' }} /> {/* 아이콘 크기 조절 */}
      </Badge>
    </Stack>
  );
};

export default Demo;
