import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import './GroupHead.scss';
import Apis from '../../api/Apis';

const GroupHead = (props) => {
  const { setGroups } = props; 
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');

  useEffect(() => {
    if(searchQuery === '') {
      Apis.get('/groups/all')
      .then((response) => {
        setGroups(response.data.data);
      })
    }
  }, [searchQuery]);

  const handleCreateButtonClick = () => {
    navigate('/groups/create');
  };

  const handleSearchInputChange = (event) => {
    console.log(event.target.value)
    setSearchQuery(event.target.value);
  };

  const handleSearchButtonClick = async () => {
    try {
      const response = await Apis.get('/groups/search', {
        params: { keyword: searchQuery },
      })
      console.log(response)
      setGroups(response.data.data.content)
    } catch (error) {
      console.error('Error searching for groups:', error);
    }
  };

  return (
    <div className="group-head">
      <div className="left-head">
        <input 
          type="text" 
          placeholder="검색어 입력" 
          className="search-input"
          value={searchQuery}
          onChange={handleSearchInputChange} 
        />
        <button className="search-btn" onClick={handleSearchButtonClick}>검색</button>
      </div>
      <div className="right-head">
        <button className="create-btn" onClick={handleCreateButtonClick}>새 그룹</button>
      </div>
    </div>
  );
};

export default GroupHead;
