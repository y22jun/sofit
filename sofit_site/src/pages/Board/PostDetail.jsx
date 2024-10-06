import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import './PostDetail.scss';
import Apis from '../../api/Apis';
import Comment from '../../components/Comu/Comment';
import Board from './Board';

const PostDetail = () => {
    const { id } = useParams();
    const [post, setPost] = useState(null);
    const navigate = useNavigate();
    const [comments, setComments] = useState([]);

    useEffect(() => {
        Apis.get(`/board/${id}`)
            .then((response) => {
                setPost(response.data);
            })
            .catch(error => console.error('Failed to fetch post:', error));
    }, []);

    const handleEdit = () => {
        // 수정 페이지로 이동
        navigate(`/board/${id}/edit`);
    };

    const handleDelete = async () => {
        try {
            await Apis.delete(`/board/${id}`);
            console.log('포스트가 삭제되었습니다.');
            window.location.href = '/board';
        } catch (error) {
            console.error('Failed to delete post:', error);
        }
    };

    const handleBackToList = () => {
        navigate('/board');
    };

    if (!post) {
        return <div>포스트를 찾을 수 없습니다.</div>;
    }

    // 현재 사용자의 ID 가져오기
    const currentUserId = Number(sessionStorage.getItem('userId'))
    console.log(currentUserId)
    return (
        <div>
            <div className="post-detail">
                <h2>{post.title}</h2>
                <p>{post.content}</p>
                <p>작성자: {post.userInfoDto.nickname}</p>
                <p>작성일: {post.createdAt.slice(0, '10')}</p>
                <p>해시태그: {post.hashtag.join(' ')}</p>
                <div className="button-group">
                    {/* 포스트의 작성자와 현재 사용자의 ID가 일치할 때만 수정 및 삭제 버튼을 보여줌 */}
                    {post.userInfoDto.userId === currentUserId && (
                        <>
                            <button onClick={handleEdit} className="btn edit-btn">수정</button>
                            <button onClick={handleDelete} className="btn delete-btn">삭제</button>
                        </>
                    )}
                    <button onClick={handleBackToList} className="btn list-btn">목록</button>
                </div>
                <Comment boardId={id}/>
            </div>
        </div>
    );
};

export default PostDetail;
