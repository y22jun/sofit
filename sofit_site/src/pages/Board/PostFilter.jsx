// PostFilter.js
import React from 'react';
import './PostFilter.scss';

const PostFilter = ({ posts, onPostClick }) => {

  console.log(posts)
  return (
    <div className="post-filter">
      <table>
        <thead>
          <tr>
            <th>제목</th>
            <th>내용</th>
            <th>작성자</th>
            <th>해시태그</th>
            <th>작성일</th>
          </tr>
        </thead>
        <tbody>
          {posts.map(post => (
            <tr key={post.boardId} onClick={() => onPostClick(post.boardId)}>
              <td>{post.title}</td>
              <td>{post.content.length > 10 ? post.content.slice(0, 10) + '...' : post.content}</td>
              <td>{post.userInfoDto.nickname}</td>
              <td>{post.hashtag.join(' ')}</td>
              <td>{post.createdAt.slice(0,'10')}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default PostFilter;
