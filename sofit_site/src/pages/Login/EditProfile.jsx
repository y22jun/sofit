// EditProfile.jsx
import React, { useState } from 'react';
import './EditProfile.scss';
import { useNavigate } from 'react-router-dom';

const EditProfile = () => {
    const [name, setName] = useState('');
    const [email, setEmail] = useState('');
    const [intro, setIntro] = useState('');
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();
        // Implement submit logic here
        console.log('Name:', name);
        console.log('Email:', email);
        console.log('intro:', intro);
        navigate(-1);
    };

    return (
        <div className="edit-profile">
            <h1>프로필 수정</h1>
            <form onSubmit={handleSubmit}>
                <div className="form-group">
                    <label htmlFor="name">이름:</label>
                    <input
                        type="text"
                        id="name"
                        value={name}
                        onChange={(e) => setName(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="email">Email:</label>
                    <input
                        type="email"
                        id="email"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                </div>
                <div className="form-group">
                    <label htmlFor="intro">소개:</label>
                    <textarea
                        id="bio"
                        value={intro}
                        onChange={(e) => setIntro(e.target.value)}
                    />
                </div>
                <button type="submit">수정 완료</button>
            </form>
        </div>
    );
};

export default EditProfile;
