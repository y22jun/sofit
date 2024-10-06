import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import GroupHead from './GroupHead';
import GroupFilter from './GroupFilter';
import './Group.scss';
import PaginationG from './PaginationG';
import GroupDetail from './GroupDetail';
import MyGroups from './MyGroups';
import Apis from '../../api/Apis';

const Group = () => {
  const [groups, setGroups] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const [selectedGroup, setSelectedGroup] = useState(null);
  const [joinedGroups, setJoinedGroups] = useState([]);
  
  const groupsPerPage = 10;
  const navigate = useNavigate();

  const indexOfLastGroup = currentPage * groupsPerPage;
  const indexOfFirstGroup = indexOfLastGroup - groupsPerPage;
  const currentGroups = groups.slice(indexOfFirstGroup, indexOfLastGroup);

  useEffect(() => {
    Apis.get('/groups/all')
      .then((response) => {
        setGroups(response.data.data);
      })
      .catch((error) => {
        console.error('Error fetching groups:', error);
      });
  }, []);

  useEffect(() => {
    Apis.get('/groups/myGroups')
      .then((response) => {
        setJoinedGroups(response.data.data.map(group => group.groupId));
      })
      .catch((error) => {
        console.error('Error fetching myGroups:', error);
      });
  }, []);

  const paginate = (pageNumber) => {
    if (pageNumber > 0 && pageNumber <= Math.ceil(groups.length / groupsPerPage)) {
      setCurrentPage(pageNumber);
    }
  };

  const handleGroupClick = (id) => {
    const group = groups.find((group) => group.groupId === id);
    console.log(group);
    setSelectedGroup(group);
  };

  const handleSubscribe = (groupId) => {
    if (!joinedGroups.includes(groupId)) {
      setJoinedGroups([...joinedGroups, groupId]);
      alert('You have successfully joined the group!');
    }
  };

  const handleNavigateToGroup = (id) => {
    navigate(`/group/groupview/${id}`, { state: { groups } });
  };

  return (
    <div className="group">
      <MyGroups joinedGroups={joinedGroups} groups={groups} />
      <GroupHead setGroups={setGroups} />
      <div className="content">
        <GroupFilter groups={currentGroups} onGroupClick={handleGroupClick} />
        <PaginationG
          itemsPerPage={groupsPerPage}
          totalItems={groups.length}
          paginate={paginate}
          currentPage={currentPage}
        />
      </div>
      {selectedGroup && (
        <GroupDetail
          group={selectedGroup}
          onSubscribe={() => handleSubscribe(selectedGroup.groupId)}
          isJoined={joinedGroups.includes(selectedGroup.groupId)}
          onNavigateToGroup={handleNavigateToGroup}
        />
      )}
    </div>
  );
};

export default Group;
