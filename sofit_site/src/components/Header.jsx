import React, { useState, useEffect } from 'react';
import { FaRegUserCircle } from "react-icons/fa";
import './Header.scss';
import Modal from './Modal';
import { useNavigate } from 'react-router-dom';
import Demo from './Demo';
import Apis from '../api/Apis';

const Header = () => {
    const [notificationActive, setNotificationActive] = useState(false);
    const [profileActive, setProfileActive] = useState(false);
    const navigate = useNavigate();

    // const [alarmContent, setalarmContent] = useState([]);
    // useEffect(() => {
    //   Apis.get('/alarm/list')
    //   .then((response) => {
    //       setalarmContent(response.data.data)
    //   });
    // }, []);

    return (
        <>
            {profileActive && <Modal type="profile" />}
            {notificationActive && <Modal type="notification" alert={alert} />}
            <div className='header'>
                <span className='left-section'>
                    <button
                        onClick={() => navigate('/')}
                        className='link-tab'
                    >SOFIT</button>
                </span>
                <div className='center-section'>
                    <button
                        onClick={() => navigate('/board')}
                        className='link-tab'
                    >Univ</button>

                    <button onClick={() => navigate('/groups')} className='link-tab'>Group</button>
                </div>
                <div className='right-section'>
                    <Demo 
                        className='link-tab'
                        onClick={() => setNotificationActive(!notificationActive)}
                    />
                    <FaRegUserCircle
                        className='link-tab'
                        onClick={() => setProfileActive(!profileActive)}
                    />

                </div>
            </div>
        </>
    );
};

export default Header;
