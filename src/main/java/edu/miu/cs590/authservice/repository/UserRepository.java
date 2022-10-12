package edu.miu.cs590.authservice.repository;

import edu.miu.cs590.authservice.entity.User;
import edu.miu.cs590.authservice.exception.UsernameNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username) throws UsernameNotFoundException;
}
