// CommentForm.js
import React, { useState, useEffect } from 'react';
import './CommentForm.scss'; // SCSS 파일 임포트
import Apis from '../../api/Apis';
import { useParams } from 'react-router-dom';

const CommentForm = ({ onSubmit }) => {
  const [commentText, setCommentText] = useState('');
  const [authorName, setAuthorName] = useState('');
  const { id } = useParams();

  useEffect(() => {
    const nickname = sessionStorage.getItem('nickname');
    setAuthorName(nickname);
  }, []);

  const handleInputChange = (event) => {
    console.log((event.target.value));
    setCommentText(event.target.value);
  };


  const handleSubmit = (event) => {
    event.preventDefault();
    if (commentText.trim() === '') return;
    Apis.post(`/comment/${id}`, {
      content : commentText
    })
    .then((response) => {
      console.log(response.data)
    })
    setCommentText('');
    setAuthorName('');
    window.location.reload();
  };
  

  return (
    <form className="comment-form" onSubmit={handleSubmit}>
      <textarea
        value={commentText}
        onChange={handleInputChange}
        placeholder="Write your comment here..."
        rows="4"
        cols="50"
      />
      <br />
      <button type="submit">Submit</button>
    </form>
  );
};

export default CommentForm;
