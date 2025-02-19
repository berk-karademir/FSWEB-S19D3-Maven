package com.workintech.s19d2.service;


import com.workintech.s19d2.repository.MemberRepository;
import com.workintech.s19d2.repository.RoleRepository;
import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationService {

    private MemberRepository memberRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    public AuthenticationService(MemberRepository memberRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member register(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // Kullanıcı varsa hata fırlatıyoruz
        if(optionalMember.isPresent()) {
            throw new RuntimeException("This email already exists, check your email! " + email);
        }

        // Yeni üye oluşturuyoruz
        Member newUserMember = new Member();
        String encodedPassword = passwordEncoder.encode(password);

        List<Role> roleList = new ArrayList<>();

        // Rolü belirliyoruz
        // (Varsayalım ki rolü kullanıcı kaydederken belirlemek istiyoruz)
        addRoleUser(roleList);
        newUserMember.setEmail(email);
        newUserMember.setPassword(encodedPassword);
        newUserMember.setRoles(roleList);

        memberRepository.save(newUserMember);

        return newUserMember;
    }

    public Member registerAdmin(String email, String password) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        // Kullanıcı varsa hata fırlatıyoruz
        if(optionalMember.isPresent()) {
            throw new RuntimeException("This email already exists, check your email! " + email);
        }

        // Yeni üye oluşturuyoruz
        Member newUserMember = new Member();
        String encodedPassword = passwordEncoder.encode(password);

        List<Role> roleList = new ArrayList<>();


        addRoleAdmin(roleList);
        newUserMember.setEmail(email);
        newUserMember.setPassword(encodedPassword);
        newUserMember.setRoles(roleList);

        memberRepository.save(newUserMember);

        return newUserMember;
    }





    private void addRoleUser(List<Role> roleList) {
        Optional<Role> roleUser = roleRepository.findByAuthority("USER");
        if(roleUser.isEmpty()) {
            Role roleUserEntity = new Role();
            roleUserEntity.setAuthority("USER");
            roleList.add(roleRepository.save(roleUserEntity));
        } else {
            roleList.add(roleUser.get());
        }
    }

    private void addRoleAdmin(List<Role> roleList) {
        Optional<Role> roleAdmin = roleRepository.findByAuthority("ADMIN");
        if(roleAdmin.isEmpty()) {
            Role roleAdminEntity = new Role();
            roleAdminEntity.setAuthority("ADMIN");
            roleList.add(roleRepository.save(roleAdminEntity));
        } else {
            roleList.add(roleAdmin.get());
        }
    }

}
