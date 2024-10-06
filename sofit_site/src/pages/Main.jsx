import React from 'react';
import './Main.scss';

const Main = () => {
    return (
        <div className='main'>
            <header className='main-header'>
                <h1>SOFIT</h1>
                <p>소프트웨어학과 학생들을 위한 학습 커뮤니티</p>
            </header>
            <section className='main-content'>
                <div className='intro'>
                    <h1>지금 SOFIT에 가입하고 학습 여정을 시작하세요!</h1>
                    <p>SOFIT은 소프트웨어학과 학생들을 대상으로 운영하는 학습 커뮤니티로,
                    <br />그룹 스터디를 통해 서로의 성취도를 비교하며 본인의 학습을 자기주도적으로 할 수 있도록 돕습니다.</p>
                </div>
                <div className='features'>
                    <h2>주요 기능</h2>
                    <ul>
                        <li>그룹 스터디</li>
                        <li>성취도 비교</li>
                        <li>자기주도적 학습 지원</li>
                        <li>커뮤니티 활동</li>
                    </ul>
                </div>
            </section>
        </div>
    );
};

export default Main;