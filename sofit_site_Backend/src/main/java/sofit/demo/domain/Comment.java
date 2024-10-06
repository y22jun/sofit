package sofit.demo.domain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Table(name = "COMMENT")
@Getter
@NoArgsConstructor
@Entity
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @Column
    private String content;

    private int isRemoved = 0;

    @OneToMany(mappedBy = "parent")
    private List<Comment> childList = new ArrayList<>();

    public void updateContent(String content) {
        this.content = content;
    }

    public void remove() {
        this.isRemoved = 1;
    }

    @Builder
    public Comment(User user, Board board, Comment parent, String content) {
        this.user = user;
        this.board = board;
        this.parent = parent;
        this.content = content;
        this.isRemoved = 0;
    }

    public void confirmUser(User user) {
        this.user = user;
        user.addComment(this);
    }

    public void confirmBoard(Board board) {
        this.board = board;
        board.addComment(this);
    }

    public void confirmParent(Comment parent) {
        this.parent = parent;
        this.addChild(this);
    }

    public void addChild(Comment child) {
        childList.add(child);
    }

    // isRemoved 메서드 추가
    public boolean isRemoved() {
        return this.isRemoved == 1;
    }

    public List<Comment> findRemovableList() {
        List<Comment> result = new ArrayList<>();

        Optional.ofNullable(this.parent).ifPresentOrElse(
            parentComment -> { // 대댓글인 경우 (부모가 존재하는 경우)
                if (parentComment.isRemoved() && parentComment.isAllChildRemoved()) {
                    result.addAll(parentComment.getChildList());
                    result.add(parentComment);
                }
            },
            () -> { // 댓글인 경우
                if (isAllChildRemoved()) {
                    result.add(this);
                    result.addAll(this.getChildList());
                }
            } // Null
        );

        return result;
    }

    // 모든 자식 댓글이 삭제되었는지 판단
    private boolean isAllChildRemoved() {
        return getChildList().stream()
                .map(Comment::isRemoved) // 지워졌는지 여부로 바꾼다
                .filter(isRemove -> !isRemove) // 지워졌으면 true, 안지워졌으면 false이다. 따라서 filter에 걸러지는 것은 false인 녀석들이고, 있다면 false를 없다면 orElse를 통해 true를 반환한다.
                .findAny() // 지워지지 않은게 하나라도 있다면 false를 반환
                .orElse(true); // 모두 지워졌다면 true를 반환
    }
}
