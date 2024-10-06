import React, { useState } from 'react';
import './ReplyForm.scss'; // 필요한 경우 SCSS 파일을 추가로 임포트

const ReplyForm = ({ onSubmit, commentIndex }) => {
  const [replyText, setReplyText] = useState('');

  const handleInputChange = (event) => {
    setReplyText(event.target.value);
  };

  const handleSubmit = (event) => {
    event.preventDefault();
    if (replyText.trim() === '') return; // 빈 대댓글은 제출하지 않음
    const time = new Date().toLocaleString(); // 현재 시간을 받아옴
    onSubmit(commentIndex, replyText, time); // 시간 정보를 함께 전달
    setReplyText('');
  };

  return (
    <form className="reply-form" onSubmit={handleSubmit}>
      <textarea
        name="replyText"
        value={replyText}
        onChange={handleInputChange}
        placeholder="Write your reply here..."
        rows="2"
        cols="50"
      />
      <button type="submit">Submit Reply</button>
    </form>
  );
};

export default ReplyForm;
