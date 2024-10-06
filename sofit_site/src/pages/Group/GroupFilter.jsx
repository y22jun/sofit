import React from 'react';
import './GroupFilter.scss';

const GroupFilter = ({ groups, onGroupClick }) => {

  console.log(groups)
  return (
    <div className="group-filter">
      <table>
        <thead>
          <tr>
            <th>그룹이름</th>
            <th>그룹장</th>
            <th>그룹인원</th>
            <th>공개여부</th>
            <th>생성일</th>
          </tr>
        </thead>
        <tbody>
          {groups.map(group => (
            <tr key={group.groupId} onClick={() => onGroupClick(group.groupId)}>
              <td>{group.groupName}</td>
              <td>{group.groupLeader}</td>
              <td>{group.groupNumPeople} / {group.groupMaxPeople}</td>
              <td>{group.groupPrivate ? 'No' : 'Yes'}</td>
              <td>{group.createdAt ? group.createdAt.slice(0, 10) : 'N/A'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default GroupFilter;
