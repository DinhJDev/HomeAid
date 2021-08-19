package com.homeaid.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.homeaid.models.Member;

@Repository
public interface MemberRepository extends CrudRepository<Member, Long> {
    Member findByUsername(String username);
}