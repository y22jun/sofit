import React, { useState } from 'react';
import './CommentList.scss'; // SCSS 파일 임포트
import ReplyForm from './ReplyForm';

const CommentList = ({ comments, onReply }) => {
  const [replyIndex, setReplyIndex] = useState(null); // 대댓글을 작성할 댓글의 인덱스

  const handleReply = (commentIndex) => {
    setReplyIndex(commentIndex); // 대댓글을 작성할 댓글의 인덱스 설정
  };

  return (
    <div className="comment-list">
      {comments.map((comment, commentId) => (
        <li key={commentId}>
          <div className="comment-info">
            <span className="comment-author">

              {comment.userInfoDto ? comment.userInfoDto.nickname : 'Anonymous'}
            </span>
            <span className="comment-time">{' '}{comment.createdAt.slice(0, 10)}</span>
          </div>
          <div className="comment-text">{comment.content}</div>
          {/* <button onClick={() => handleReply(commentId)}>Reply</button>
          {replyIndex === commentId && ( // 대댓글 입력 상태일 때만 입력란을 표시
            <ReplyForm onSubmit={onReply} commentIndex={commentId} />
          )} */}
          {comment.replies && (
            <div className='reply-list'>
              {comment.replies.map((reply, replyIndex) => (
                <>
                  <div className="comment-info" key={replyIndex}>
                    <span className="comment-author">{reply.author}</span>
                    <span className="comment-time">{reply.time}</span>
                  </div>
                  <div className="comment-text">{reply.text}</div>
                </>
              ))}
            </div>
          )}
        </li>
      ))}
    </div>
  );
};

export default CommentList;
