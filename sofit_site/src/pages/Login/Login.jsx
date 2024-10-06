import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { FcGoogle } from "react-icons/fc";
import './Login.scss';
import { useAuth } from './AuthContext';
import axios from "axios";

const Login = () => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const { login } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (event) => {
        event.preventDefault();
        if (login(email, password)) {
            try {
                const response = await axios.post('/login', {
                    email: email,
                    password: password,
                });
                let accessToken = response.headers.get("Authorization");
                let refreshToken = response.headers.get("Authorization-Refresh");
                let userId = response.headers.get("UserId");
                sessionStorage.setItem("accessToken", accessToken);
                sessionStorage.setItem("refreshToken", refreshToken);
                sessionStorage.setItem('email', email);
                sessionStorage.setItem('userId', userId);
                navigate('/');
            } catch (error) {
                if (error.response && error.response.status === 400) {
                    setErrorMessage('로그인에 실패하였습니다. 비밀번호 혹은 아이디를 다시 입력해주세요.');
                } else {
                    setErrorMessage('알 수 없는 오류가 발생했습니다. 나중에 다시 시도해주세요.');
                }
            }
        } else {
            setErrorMessage('로그인에 실패하였습니다. 비밀번호 혹은 아이디를 다시 입력해주세요.');
        }
    };

    return (
        <div className="login-container">
            <h2>Login</h2>
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
                <div className="form-group">
                    <label>Password:</label>
                    <input
                        type="password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </div>
                {errorMessage && <p className="error-message">{errorMessage}</p>}
                <div className="button-group">
                    <button type="submit">Login</button>
                </div>
                <div className="link-group">
                    <Link to="/signup">회원가입</Link>
                    <Link to="/forgot-password">비밀번호 찾기</Link>
                </div>
                {/* <div className="social-login-icons">
                    <FcGoogle />
                </div> */}
            </form>
        </div>
    );
};

export default Login;
