import React, {useState, useEffect} from 'react';
import CommentList from './CommentList';
import CommentForm from './CommentForm';
import Apis from '../../api/Apis';
import { useParams, useNavigate } from 'react-router-dom';

const Comment = () => {
    const [comments, setComments] = useState([])
    const { id } = useParams();

    useEffect(() => {
      Apis.get(`/comment/${id}`)
      .then((response) => {
        setComments(response.data)
      });
    }, []);
    
      const handleReply = (commentIndex, replyText, time) => {
        const updatedComments = [...comments];
        updatedComments[commentIndex].replies.push({
          author: 'Anonymous', // 대댓글 작성자를 더미로 설정
          time: time,
          text: replyText,
        });
        setComments(updatedComments);
      };
    
      const handleSubmitComment = (commentText, authorName) => {
        const newComment = {
          author: authorName,
          text: commentText,
          replies: [],
        };
        setComments((prevComment) => [...prevComment, newComment]);
      };

      return (
        <div>
          <CommentForm onSubmit={handleSubmitComment} />
          <CommentList comments={comments} onReply={handleReply}/>
        </div>
      );
    };
    
export default Comment;