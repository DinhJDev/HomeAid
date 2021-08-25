package com.homeaid.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.homeaid.models.Household;
import com.homeaid.models.Member;
import com.homeaid.repositories.MemberRepository;
import com.homeaid.repositories.RoleRepository;

@Service
public class MemberService {
	private MemberRepository memberRepository;
	private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    
    public MemberService(MemberRepository memberRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    	this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }
	
	public void saveWithUserRole(Member user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        memberRepository.save(user);
    }
	
	public Member findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
	
	// Update
	public Member updateMember(Member member) {
		return this.memberRepository.save(member);
	}
	
}
