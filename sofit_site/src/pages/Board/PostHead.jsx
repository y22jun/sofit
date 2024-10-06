import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './PostHead.scss';
import Apis from '../../api/Apis';

const PostHead = (props) => {
  const { setPosts } = props; 
  const navigate = useNavigate();
  const [searchQuery, setSearchQuery] = useState('');
  
  useEffect(() => {
    if(searchQuery === '') {
      Apis.get('/board/all')
      .then((response) => {
        setPosts(response.data.data)
      })
    }
  }, [searchQuery])

  const handleWriteButtonClick = () => {
    navigate('/board/create');
  };

  const handleSearchInputChange = (event) => {
    console.log(event.target.value);
    setSearchQuery(event.target.value);
  };

  const handleSearchButtonClick = async () => {
    try {
      const response = await Apis.get('/board/search', {
        params: { keyword: searchQuery },
      })
      console.log(response.data)
      setPosts(response.data.content)
    } catch (error) {
      console.error('Error searching for posts:', error);
    }
  };

  return (
    <div className="post-head">
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
        <button className="write-btn" onClick={handleWriteButtonClick}>글 작성</button>
      </div>
    </div>
  );
};

export default PostHead;
