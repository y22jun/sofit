import React, { useState, useEffect } from 'react';
import './GroupMemberCard.scss';
import Apis from '../../../api/Apis';

const GroupMemberCard = ({ checkpointId }) => {
  const [members, setMembers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchGroupMembers = async () => {
      try {
        const response = await Apis.get(`/checkpoint/getGroupUsers/${checkpointId}`);
        const data = response.data;

        // 데이터를 처리하여 리더와 멤버를 구분
        const parsedMembers = data.map(member => {
          if (member.startsWith("리더 : ")) {
            return { nickname: member.replace("리더 : ", ""), isLeader: true };
          }
          return { nickname: member, isLeader: false };
        });

        setMembers(parsedMembers);
        setLoading(false);
      } catch (err) {
        setError(err);
        setLoading(false);
      }
    };

    fetchGroupMembers();
  }, [checkpointId]);

  if (loading) return <p>Loading...</p>;
  if (error) return <p>Error loading members: {error.message}</p>;

  return (
    <div className="card-container">
      {members.map((member, index) => (
        <div key={index} className="card">
          <p>{member.nickname}</p>
          {member.isLeader && <span className="leader-badge">Group Leader</span>}
        </div>
      ))}
    </div>
  );
};

export default GroupMemberCard;
