import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './PostCreate.scss'; // PostCreate의 스타일을 그대로 활용합니다.
import Apis from '../../api/Apis';

const EditPost = () => {

  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [hashtag, setHashtag] = useState([]);
  const { id } = useParams();
  const navigate = useNavigate();
  const [post, setPost] = useState(null);

  useEffect(() => {
    Apis.get(`/board/${id}`)
      .then((response) => {
        const postData = response.data;
        setPost(postData);
        setTitle(postData.title);
        setContent(postData.content);
        setHashtag(postData.hashtag);
      })
      .catch(error => console.error('Failed to fetch post:', error));
  }, [id]);

  const handleSubmit = async (event) => {
    event.preventDefault();
    const updatedPost = {
      title,
      content,
      hashtag
    };

    try {
      const response = await Apis.post(`/board/${id}`, updatedPost);
      console.log('포스트가 수정되었습니다:', response.data);
      alert('포스트가 성공적으로 수정되었습니다.');
      navigate(`/board/${id}`);
    } catch (error) {
      console.error('Failed to update post:', error);
      alert('포스트 수정에 실패했습니다.');
    }
  };

  const handleChange = (event) => {
    const { name, value } = event.target;
    if (name === 'title') {
      setTitle(value);
    } else if (name === 'content') {
      setContent(value);
    } else if (name === 'hashtags') {
      const tags = value.split(' ').map(tag => tag.trim());
      setHashtag(tags);
    }
  };

  const handleCancel = () => {
    navigate(`/board/${id}`);
  };

  if (!post) {
    return <div>Loading...</div>;
  }

  return (
    <div className="post-create">
      <h2>포스트 수정하기</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">제목</label>
          <input
            type="text"
            id="title"
            name="title"
            value={title}
            onChange={handleChange}
          />
        </div>
        <div className="form-group">
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            name="content"
            rows="4"
            value={content}
            onChange={handleChange}
          ></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="hashtags">해시태그</label>
          <input
            type="text"
            id="hashtags"
            name="hashtags"
            value={hashtag.join(' ')}
            onChange={handleChange}
          />
        </div>
        <div className="button-group">
          <button type="submit" className="btn submit-btn">수정 완료</button>
          <button type="button" onClick={handleCancel} className="btn cancel-btn">취소</button>
        </div>
      </form>
    </div>
  );
};

export default EditPost;
