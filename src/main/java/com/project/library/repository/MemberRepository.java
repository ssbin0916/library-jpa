package com.project.library.repository;

import com.project.library.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByLoginId(String loginId);

    List<Member> findListByLoginId(String loginId);

    List<Member> findByName(@Param("name") String name);

    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.name = :name where m.id = :id")
    int update(@Param("id") Long id, @Param("name") String name);

}
