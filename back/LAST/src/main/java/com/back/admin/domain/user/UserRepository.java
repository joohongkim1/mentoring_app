package com.back.admin.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {

    @Query("select u from User u where u.user_id_email=:user_id_email")
    User findByUser_id_email(@Param("user_id_email") String user_id_email);

    @Query("select u from User u where u.user_no=:user_no")
    User findByUser_no(@Param("user_no") Long user_no);

    @Query("select u from User u where u.user_id_email=:user_id_email")
    List<User> checkByUser_id_email(@Param("user_id_email") String user_id_email);

    @Query("select u from User u where u.user_name=:user_name and u.user_id_email=:user_id_email")
    List<User> findByNameEmail(@Param("user_name") String user_name, @Param("user_id_email") String user_id_email);

    @Modifying
    @Query("update User u set u.user_password =:user_password where u.user_id_email =:user_id_email")
    void updatePass(@Param("user_id_email") String user_id_email, @Param("user_password") String user_password);

    @Modifying
    @Query("update User u set u.user_auth=:user_auth where u.user_id_email=:user_id_email")
    void change_User_auth(@Param("user_id_email") String user_id_email, @Param("user_auth") int user_auth);

    @Modifying
    @Query("select u from User u where u.user_auth=:user_auth")
    List<User> findByUser_auth(@Param("user_auth") int user_auth);
}
