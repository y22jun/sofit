import React, { useState } from 'react';
import axios from 'axios';
import './Signup.scss';
import { useNavigate } from 'react-router-dom';

const SignUp = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [nickName, setNickName] = useState('');
    const [emailCheck, setEmailCheck] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        const response = await axios.post('/signUp', {
            email: email,
            password: password,
            nickname: nickName
        });
        console.log('signup: ', response.data);
        // 여기에 회원가입 로직을 추가합니다.
        navigate('/login');
    };

    const handleEmailAuth = async () => {
        const response = await axios.post('/email/path', {
            email: email
        });
        console.log('email auth: ', response.data);
    };

    const handleEmailCheck = async () => {
        const response = await axios.post('/email/check', {
            email: email,
            authNum: emailCheck
        });
        console.log('인증코드 체크:', response.data);
    };

    return (
        <div className="signup-container">
            <h2>Sign Up</h2>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label>Email:</label>
                    <input
                        type="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                </div>

                <div className="email-verification">
                    <button type='button' className="auth-button" onClick={handleEmailAuth}>Email 인증</button>
                    <input
                        className="email-check-input"
                        value={emailCheck}
                        onChange={(e) => setEmailCheck(e.target.value)}
                        placeholder="인증코드 입력"
                        required
                    />
                    <button type='button' className="check-button" onClick={handleEmailCheck}>인증코드 확인</button>
                </div>

                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Confirm Password:</label>
                    <input
                        type="password"
                        value={confirmPassword}
                        onChange={(e) => setConfirmPassword(e.target.value)}
                        required
                    />
                </div>
                <div className="form-group">
                    <label>Nickname</label>
                    <input
                        value={nickName}
                        onChange={(e) => setNickName(e.target.value)}
                        required
                    />
                </div>
                <div className='button-group'>
                    <button type="submit">Sign Up</button>
                </div>
            </form>
        </div>
    );
};

export default SignUp;
