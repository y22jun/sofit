import React from 'react';
import { useNavigate } from 'react-router-dom';
import './MyGroups.scss';

const MyGroups = ({ joinedGroups, groups }) => {
  const navigate = useNavigate();
  const myGroups = groups.filter(group => joinedGroups.includes(group.groupId));

  const handleGroupClick = (id) => {
    navigate(`/groups/groupview/${id}`);
  };

  return (
    <div className="my-groups">
      <h2>My Groups</h2>
      <div className="my-groups-container">
        {myGroups.map(group => (
          <div key={group.groupId} className="my-group-item" onClick={() => handleGroupClick(group.groupId)}>
            <h3>{group.groupName}</h3>
            <p>Leader: {group.groupLeader}</p>
            <p>Members: {group.groupNumPeople} / {group.groupMaxPeople}</p>
          </div>
        ))}
      </div>
    </div>
  );
};

export default MyGroups;
