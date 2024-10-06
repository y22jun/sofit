import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './PostCreate.scss';
import Apis from '../../api/Apis';

const PostCreate = () => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [hashtag, setHashtag] = useState([]);
  const navigate = useNavigate();

  const handleSubmit = async (event) => {
    event.preventDefault();

    if (!title.trim() || !content.trim()) {
      alert('제목과 내용을 모두 입력하세요.');
      return;
    }

    const formData = new FormData();

    const json = JSON.stringify({
      title: title,
      content: content,
      hashtag: hashtag,
    });

    const blob = new Blob([json], { type: 'application/json' });
    formData.append('multipartFile', undefined);
    formData.append('boardSaveDto', blob);

    try {
      const response = await Apis.post('/board/save', formData);
      console.log('게시물 정보: ', response.data);
      navigate('/board');
    } catch (error) {
      console.error('Failed to save post:', error);
      alert('포스트 저장에 실패했습니다.');
    }
  };

  const handleHashtagChange = (event) => {
    const input = event.target.value;
    const hashtagsArray = input.split(' ').filter(tag => tag !== '');
    setHashtag(hashtagsArray);
  };

  const handleCancel = () => {
    navigate('/board');
  };

  return (
    <div className="post-create">
      <h2>새로운 포스트 작성</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="title">제목</label>
          <input
            type="text"
            id="title"
            name="title"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label htmlFor="content">내용</label>
          <textarea
            id="content"
            name="content"
            rows="4"
            value={content}
            onChange={(e) => setContent(e.target.value)}
          ></textarea>
        </div>
        <div className="form-group">
          <label htmlFor="hashtags">해시태그</label>
          <input
            type="text"
            id="hashtags"
            name="hashtags"
            placeholder="해시태그를 입력하세요"
            onChange={handleHashtagChange}
          />
        </div>
        <div className="button-group">
          <button type="submit" className="btn submit-btn">
            등록
          </button>
          <button type="button" className="btn cancel-btn" onClick={handleCancel}>
            취소
          </button>
        </div>
      </form>
    </div>
  );
};

export default PostCreate;
