package sofit.demo.domain;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import java.util.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Table(name = "BOARD")
@Getter
@NoArgsConstructor
@Entity
public class Board extends BaseTimeEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = true)
    private String filePath;

    @Builder
    public Board(String title, String content, List<String> hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "hashtag", joinColumns = @JoinColumn(name = "board_id"))
    private List<String> hashtag = new ArrayList<>();


    @OneToMany(mappedBy = "board", orphanRemoval = true)
    private List<Comment> commentList = new ArrayList<>();

    public void confirmWriter(User user) {
        this.user = user;
        user.addBoard(this);
    }

    public void addComment(Comment comment){
        //comment의 Post 설정은 comment에서 함
        commentList.add(comment);
    }

    public void updateTitle(String title) {
        this.title = title;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateHashtag(List<String> hashtag) {
        this.hashtag = hashtag;
    }

    public void updateFilePath(String filePath) {
        this.filePath = filePath;
    }
}
