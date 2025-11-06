package co.founders.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import co.founders.auth.model.AppUser;

public interface UserRepository extends JpaRepository<AppUser, java.util.UUID> {

    @org.springframework.data.jpa.repository.Query("SELECT u FROM AppUser u WHERE u.email = :email AND u.password = :password")
    AppUser findAppUserByEmailAndPassword(@org.springframework.data.repository.query.Param("email") String email, @org.springframework.data.repository.query.Param("password") String password);


    @org.springframework.data.jpa.repository.Query("SELECT u FROM AppUser u WHERE u.email = :email")
    AppUser findAppUserByEmail(@org.springframework.data.repository.query.Param("email") String email);

}
