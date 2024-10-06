// PostList.js
import React from 'react';


 const PostList = () => {
  // 더미 데이터
  const dummyData = [
  ];

  return (
    <div className="post-list">
      {dummyData.map(post => (
        <span key={post.id}>{post.id} {post.title} {post.content}</span>
      ))}
    </div>
  );
}

export default PostList;
