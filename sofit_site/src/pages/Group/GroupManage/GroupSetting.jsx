import React, { useState, useEffect } from 'react';
import './GroupSetting.scss';
import { useNavigate, useParams } from 'react-router-dom';
import Apis from '../../../api/Apis';

const GroupSetting = ({ onDeleteGroup }) => {
    const { id } = useParams();
    const [group, setGroup] = useState(null);
    const [maxMembers, setMaxMembers] = useState(1);
    const [minMembers, setMinMembers] = useState(0); // 현재 가입된 인원
    const [isPrivate, setIsPrivate] = useState(0);
    const [password, setPassword] = useState('');
    const [isPasswordVisible, setIsPasswordVisible] = useState(0);
    const navigate = useNavigate();

    useEffect(() => {
        // 그룹 정보를 가져오는 함수 정의
        const fetchGroup = async () => {
            try {
                const response = await Apis.get(`/groups/${id}`);
                setGroup(response.data); // 서버에서 받아온 그룹 정보 설정
            } catch (error) {
                console.error('Failed to fetch group:', error);
            }
        };

        // 함수 호출
        fetchGroup();
    }, [id]);

    console.log(id)
    useEffect(() => {
        // group 정보가 업데이트 될 때 최대, 최소 인원, 공개 여부, 비밀번호 업데이트
        if (group) {
            setMaxMembers(group.groupMaxPeople); // 최대 인원 설정
            setMinMembers(group.groupNumPeople); // 현재 가입된 인원 설정
            setIsPrivate(group.groupPrivate);
            setPassword(group.groupPassword || '');
        }
    }, [group]);


    const handlePasswordChange = (e) => {
        setPassword(e.target.value);
    };

    const handleEditGroup = async () => {
      try {
          await Apis.post(`/groups/changeStatus/${id}`, {
            groupPassword: password 
          },)
              .then((res) => {
                  console.log(res)
              }).catch((err) => {
                  console.error('Failed to edit group:', err);
              });
              
      } catch (error) {
          console.error('Failed to edit group:', error);
      }
      window.location.href = '/groups';
  };
  

    const handleDeleteGroup = async () => {
        try {
            await Apis.delete(`/groups/delete/${id}`);
            console.log('그룹 삭제 성공');
            window.location.href = '/groups';
        } catch (error) {
            console.error('Failed to delete group:', error);
        }
    };

    const handleCancelGroup = () => {
        navigate(-1); // Navigate back to the previous page
    };

    if (!group) {
        return <div>Loading...</div>;
    }

    return (
        <div className="group-setting">
            <h1>Group Settings</h1>
            {!isPrivate && (
                <div className="setting-item">
                    <label htmlFor="password">Password:</label>
                    <input
                        type={isPasswordVisible ? 'text' : 'password'}
                        id="password"
                        value={password}
                        onChange={handlePasswordChange}
                    />
                    <button onClick={() => setIsPasswordVisible(!isPasswordVisible)}>
                        {isPasswordVisible ? 'Hide Password' : 'Show Password'}
                    </button>
                </div>
            )}
            <div className="button-group">
                <button className="edit-btn" onClick={handleEditGroup}>수정 완료</button>
                <button className="delete-btn" onClick={handleDeleteGroup}>그룹 삭제</button>
                <button className="cancel-btn" onClick={handleCancelGroup}>취소</button>
            </div>
        </div>
    );
};

export default GroupSetting;
