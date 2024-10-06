package sofit.demo.dto.alarm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import sofit.demo.domain.Alarm;

@Getter
@AllArgsConstructor
public class AlarmInfoDto {

    private String receiver;
    private String sender;
    private String content;
    private boolean isRead;

    public AlarmInfoDto(Alarm alarm) {
        this.receiver = alarm.getReceiver();
        this.sender = alarm.getSender();
        this.content = alarm.getContent();
        this.isRead = alarm.isRead();
    }

    public static AlarmInfoDto from(Alarm alarm) {
        return new AlarmInfoDto(alarm);
    }

}
