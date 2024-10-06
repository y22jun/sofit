package sofit.demo.domain;

import jakarta.persistence.*;
import lombok.*;

@Table(name = "alarm")
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Alarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver")
    private String receiver;

    @Column(name = "sender")
    private String sender;


    @Setter
    @Column(name = "isRead", columnDefinition = "boolean default false")
    private boolean isRead;

    @Column(name = "content")
    private String content;

    @Builder
    public Alarm(String receiver, String sender, Long groupId) {
        this.receiver = receiver;
        this.sender = sender;
        this.content = sender + "님이" + groupId + " 번 그룹에 가입신청을 하셨습니다.";
    }

    public void updateRead() {
        this.isRead = true;
    }
}
