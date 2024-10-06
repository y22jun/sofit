
package sofit.demo.dto.group;

public class GroupNumDto {
    private Long groupMaxPeople;

    public Long getGroupMaxPeople() {
        return groupMaxPeople;
    }

    public void setGroupMaxPeople(Long groupMaxPeople) {
        this.groupMaxPeople = groupMaxPeople;
    }
    public GroupNumDto(Long groupMaxPeople){
        this.groupMaxPeople=groupMaxPeople;
    }
    public GroupNumDto() {
        // 기본 생성자 추가
    }

}
