import React, { useState } from 'react';
import './GroupCreate.scss';
import { useNavigate } from 'react-router-dom';
import Apis from '../../api/Apis';

const GroupCreate = () => {
  const [groupName, setGroupName] = useState('');
  const [maxMembers, setMaxMembers] = useState(1);
  const [isPrivate, setIsPrivate] = useState(0);
  const [password, setPassword] = useState('');
  const [isPasswordVisible, setIsPasswordVisible] = useState(false);
  const navigate = useNavigate();

  const handleCreateGroup = () => {


    console.log('Creating group with the following details:');
    console.log('Group Name:', groupName);
    console.log('Max Members:', maxMembers);
    console.log('Public:', isPrivate);
    console.log('Password:', password);

    if (!groupName.trim()) {
      alert('그룹 이름을 입력하세요.');
      return;
    }
    
    Apis.post('/groups/create', {
      groupName: groupName,
      groupMaxPeople: maxMembers,
      groupPrivate: isPrivate,
      groupPassword: password
    }).then((response) => {
      navigate('/groups');
    });
  };

  const handleCancel = () => {
    console.log('Creation cancelled');
    navigate('/groups'); // Redirect to the group page
  };

  return (
    <div className="group-create">
      <h1>그룹 생성</h1>
      <div className="form-group">
        <label htmlFor="groupName">그룹 이름 :</label>
        <input
          type="text"
          id="groupName"
          value={groupName}
          onChange={(e) => setGroupName(e.target.value)}
        />
      </div>
      <div className="form-group">
        <label htmlFor="maxMembers">그룹 인원 설정 :</label>
        <input
          type="range"
          id="maxMembers"
          min={1}
          max={100}
          value={maxMembers}
          onChange={(e) => setMaxMembers(parseInt(e.target.value))}
        />
        <span>{maxMembers}</span>
      </div>
      <div className="form-group">
        <label>
          <input
            type="checkbox"
            checked={isPrivate === 1}
            onChange={(e) => setIsPrivate(e.target.checked ? 1 : 0)}
          />
          비공개 설정
        </label>
      </div>
      {isPrivate === 1 && (
        <div className="form-group">
          <label htmlFor="password">비밀번호 :</label>
          <input
            type={isPasswordVisible ? 'text' : 'password'}
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
          <button onClick={() => setIsPasswordVisible(!isPasswordVisible)}>
            {isPasswordVisible ? '비밀번호 숨기기' : '비밀번호 표시하기'}
          </button>
        </div>
      )}
      <div className="button-group">
        <button className="create-btn" onClick={handleCreateGroup}>생성</button>
        <button className="cancel-btn" onClick={handleCancel}>취소</button>
      </div>
    </div>
  );
};

export default GroupCreate;
