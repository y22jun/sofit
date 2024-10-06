import React, { useState } from 'react';
import './GroupDetail.scss';
import Apis from '../../api/Apis';

const GroupDetail = ({ group, onSubscribe, isJoined, onNavigateToGroup }) => {
  const [password, setPassword] = useState('');
  const [showPasswordInput, setShowPasswordInput] = useState(false);
  const [error, setError] = useState('');

  const handleSubscribeClick = () => {
    if (!group.groupPrivate) {
      Apis.post(`/groups/join/${group.groupId}`)
      console.log(`${group.groupID} 그룹 가입 성공`)
      window.location.reload();
      onSubscribe();
    } else {
      setShowPasswordInput(true);
    }
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handlePasswordSubmit = () => {
    Apis.post('/groups/password/' + group.groupId, {
      groupPassword : password,
    }).then((response) => {
      if(response.data.statusCode === 200) {
        onSubscribe();
        setShowPasswordInput(false); // Close password entry window upon successful submission
        window.location.reload();
        setError('');
      } else {
          setError('Incorrect password. Please try again.');
      }
    })
  };

  //mymayjun@naver.com
  const handleNavigateToGroup = () => {
    onNavigateToGroup(group.groupId);
  };

  return (
    <div className="group-detail">
      <h2>{group.groupName}</h2>
      <p><strong>Leader:</strong> {group.groupLeader}</p>
      <p><strong>Members:</strong> {group.groupNumPeople} / {group.groupMaxPeople}</p>
      <p><strong>Public:</strong> {group.groupPrivate ? 'Yes' : 'No'}</p>
      <p><strong>Creation Date:</strong> {group.creationDate}</p>
      {showPasswordInput && !group.isPublic && (
        <div className="password-section">
          <input
            type="password"
            placeholder="Enter password"
            value={password}
            onChange={handlePasswordChange}
          />
          <button onClick={handlePasswordSubmit}>Submit</button>
          {error && <p className="error">{error}</p>}
        </div>
      )}
      {!showPasswordInput && (
        isJoined ? (
          <button onClick={handleNavigateToGroup} className="navigate-button">
            이미 가입된 그룹입니다
          </button>
        ) : (
          <button onClick={handleSubscribeClick} className="subscribe-button">
            그룹 가입 신청
          </button>
        )
      )}
    </div>
  );
};

export default GroupDetail;