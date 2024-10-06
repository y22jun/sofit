import React, { useEffect, useState } from 'react';
import './Modal.scss';
import { useNavigate } from 'react-router-dom';
import Apis from '../api/Apis';

const Modal = ({ type }) => {
    const navigate = useNavigate();
    const [isLogin, setIsLogin] = useState(false);
    const [alarmContent, setAlarmContent] = useState([]);
    const [unreadCount, setUnreadCount] = useState(sessionStorage.getItem('unreadCount') || 0);

    const onClick = () => {
        if (isLogin) {
            setIsLogin(!isLogin);
            sessionStorage.clear();
        } else {
            navigate('/login');
        }
    }

    useEffect(() => {
        if (sessionStorage.getItem('accessToken') && sessionStorage.getItem('refreshToken') && sessionStorage.getItem('email')) {
            setIsLogin(true);
        } else {
            setIsLogin(false);
        }
    }, []);

    useEffect(() => {
        Apis.get('/alarm/list')
            .then((response) => {
                const alarms = response.data.data;
                setAlarmContent(alarms);
                const unread = alarms.filter(alarm => !alarm.read).length;
                setUnreadCount(unread);
                sessionStorage.setItem('unreadCount', unread);
            })
            .catch((error) => {
                console.error('Error fetching alarm list:', error);
            });
    }, []);

    

    return (
        <div className={`Modal_${type}_Wrapper`}>
            {type === 'notification' && (
                <div className="notification-content">
                    <h3>알림</h3>
                    {alarmContent.map((item, index) => (
                        <div key={index} className={`notification-item ${item.read ? 'read' : 'unread'}`}>
                            {item.content}
                        </div>
                    ))}
                </div>
            )}
            {type === 'profile' && (
                <div className="profile-content">
                    <div style={{ fontSize: '18px', wordBreak: 'break-word' }}>{sessionStorage.getItem('email')}</div>
                    <button className="Modal_profile_btn" onClick={onClick}>{isLogin ? '로그아웃' : '로그인'}</button>
                </div>
            )}
        </div>
    );
};

export default Modal;
