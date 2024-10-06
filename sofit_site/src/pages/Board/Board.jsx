// Board.js
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import PostHead from './PostHead';
import PostFilter from './PostFilter';
import Pagination from './Pagination';
import './Board.scss';
import Apis from '../../api/Apis';
const Board = () => {
  const [posts, setPosts] = useState([]);
  const [currentPage, setCurrentPage] = useState(1);
  const postsPerPage = 10;
  const navigate = useNavigate();

  const indexOfLastPost = currentPage * postsPerPage;
  const indexOfFirstPost = indexOfLastPost - postsPerPage;
  const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);
  
  useEffect(() => {
    Apis.get('/board/all')
    .then((response) => {
      setPosts(response.data.data)
    });
  }, []);

  const paginate = pageNumber => {
    setCurrentPage(pageNumber);
  };

  const handlePostClick = (id) => {
    navigate(`/board/${id}`);
  };



  return (
    <div className="board">
      <PostHead setPosts={setPosts}/>
      <div className="content">
        <PostFilter posts={currentPosts} onPostClick={handlePostClick} />
        <Pagination
          itemsPerPage={postsPerPage}
          totalItems={posts.length}
          paginate={paginate}
          currentPage={currentPage}
        />
      </div>
    </div>
  );
};

export default Board;
