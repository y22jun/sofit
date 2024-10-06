package sofit.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import sofit.demo.domain.Group;
import sofit.demo.domain.GroupMember;
import sofit.demo.domain.User;

import java.util.List;

public interface GroupMemberRepository extends JpaRepository<GroupMember,Long> {

    GroupMember findByUserAndGroup(User user, Group group);
    List<GroupMember> findByGroup(Group group);
    List<GroupMember> findByUser(User user);
}
