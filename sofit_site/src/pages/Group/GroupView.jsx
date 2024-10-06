import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './GroupView.scss';
import Apis from '../../api/Apis';
import WeekAssignment from './GroupManage/WeekAssignment';
import Subomission from './GroupManage/Subomission';
import GroupMemberCard from './GroupManage/GroupMemberCard';

const GroupView = () => {
    const { id } = useParams();
    const [groupData, setGroupData] = useState({});
    const [istRegister, setIsRegister] = useState(false);
    const [isProgress, setProgress] = useState(false);
    const [isLeader, setIsLeader] = useState(null);
    const [finalGoal, setFinalGoal] = useState('');
    const [checkpointId, setCheckpointId] = useState(null);
    const [existingCheckpoint, setExistingCheckpoint] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        const fetchGroupData = async () => {
            try {
                const response = await Apis.get(`/groups/${id}`);
                setGroupData(response.data);
                setIsLeader(response.data.data[0].groupIsLeader);
                
                // Check for existing checkpoint
                const checkpointResponse = await Apis.get(`/info/${id}`);
                if (checkpointResponse.data.data) {
                    setExistingCheckpoint(checkpointResponse.data.data.goal);
                    setCheckpointId(checkpointResponse.data.data.id);
                }
            } catch (error) {
                console.error('Error fetching groups or checkpoint:', error);
            }
        };

        fetchGroupData();
    }, [id]);

    const handleGroupManagementClick = (groupId) => {
        navigate(`/groups/groupview/${groupId}/settings`);
    };

    const handleGroupLeaveClick = async () => {
        try {
            await Apis.delete(`/groups/leave/${id}`);
            console.log('그룹 탈퇴 성공');
            window.location.href = '/groups';
        } catch (error) {
            console.error('Failed to delete group:', error);
        }
    };

    const handleFinalGoalRegister = async () => {
        try {
            await Apis.post(`/checkpoint/${id}`, { title: finalGoal });
            console.log('최종 목표 등록 성공');
            const response = await Apis.get(`/info/${id}`);
            console.log('Checkpoint ID:', response.data.data.id);
            setCheckpointId(response.data.data.id);
            setExistingCheckpoint(response.data.data);
        } catch (error) {
            console.error('Failed to register final goal or fetch checkpoint ID:', error);
        }
    };

    if (!groupData.data) {
        return <div>Group not found</div>;
    }

    return (
        <div>
            {groupData.data.map((group) => (
                <div className="week--container" key={group.groupId}>
                    <div>
                        <h1>그룹명 : {group.groupName}</h1>
                        <div className="week-topbtn--box">
                            {isLeader === 1 ? (
                                <div className="week-topbtn--box">
                                    <button
                                        onClick={() => {
                                            setProgress(!isProgress);
                                        }}
                                    >
                                        그룹원 &nbsp;{!istRegister ? '▲' : '▼'}
                                    </button>

                                    <button
                                        onClick={() => {
                                            setIsRegister(!istRegister);
                                        }}
                                    >
                                        과제 등록 &nbsp;{!istRegister ? '▲' : '▼'}
                                    </button>
                                    <button onClick={() => handleGroupManagementClick(group.groupId)}>
                                        그룹 설정
                                    </button>
                                </div>
                            ) : (
                                <div className="week-topbtn--box">
                                    <button
                                        onClick={() => {
                                            setProgress(!isProgress);
                                        }}
                                    >
                                        그룹원 &nbsp;{!istRegister ? '▲' : '▼'}
                                    </button>

                                    <button
                                        onClick={() => {
                                            setIsRegister(!istRegister);
                                        }}
                                    >
                                        과제 제출 &nbsp;{!istRegister ? '▲' : '▼'}
                                    </button>
                                    <button onClick={() => handleGroupLeaveClick(group.groupId)}>
                                        그룹 탈퇴
                                    </button>
                                </div>
                            )}
                        </div>
                    </div>

                    {isProgress && <GroupMemberCard checkpointId={checkpointId}/>}
                    {istRegister && (
                        <div className="register--wrapper">
                            {isLeader === 1 ? (
                                <>
                                    <div className="register--input--box">
                                        <label className="register--input--label">최종 목표</label>
                                        {existingCheckpoint ? (
                                            <input
                                                type="text"
                                                value={existingCheckpoint}
                                                readOnly
                                                className="register--input--input"
                                            />
                                        ) : (
                                            <>
                                                <input
                                                    type="text"
                                                    value={finalGoal}
                                                    onChange={(e) => setFinalGoal(e.target.value)}
                                                    placeholder="  최종 목표를 입력해주세요."
                                                    className="register--input--input"
                                                />
                                                <button className="register--input--btn" onClick={handleFinalGoalRegister}>
                                                    등록
                                                </button>
                                            </>
                                        )}
                                    </div>
                                    {checkpointId && (
                                        <WeekAssignment checkpointId={checkpointId} />
                                    )}
                                </>
                            ) : (
                                <>
                                    <div className="register--input--box disabled">
                                        <label className="register--input--label">최종 목표</label>
                                        <input
                                            type="text"
                                            value={existingCheckpoint}
                                            readOnly
                                            className="register--input--input"
                                            disabled
                                        />
                                    </div>
                                    <Subomission checkpointId={checkpointId}/>
                                </>
                            )}
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
};

export default GroupView;
