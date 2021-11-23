package example.repository;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.beans.factory.annotation.Autowired;

@Repository
class UserRepositoryImpl() : UserRepository {

    @Autowired
    private lateinit var inMemoryUserDetailsManager :InMemoryUserDetailsManager;


    @Autowired
    private lateinit var passwordEncoder :PasswordEncoder;

    override fun findUserByName(name :String) :UserDetails {
        return inMemoryUserDetailsManager.loadUserByUsername(name);
    }

    override fun resetPassword(name :String, newPassword  :String) :example.model.User? {
      var user :example.model.User = example.model.User();
      val userExists :Boolean = inMemoryUserDetailsManager.userExists(name);

      if (userExists){
        user.user = name;
        user.pass = newPassword;
        inMemoryUserDetailsManager.deleteUser(name);
        this.save(user)
        return user;
      }

      return null;
    }

    override fun save(user :example.model.User) {
      val name :String = user.user;
      val password :String = user.pass;

      val grantedAuthoritiesList = example.model.User.assignRole("USER");

      inMemoryUserDetailsManager
      .createUser(org.springframework.security.core.userdetails.User(name, passwordEncoder.encode(password), grantedAuthoritiesList));

    }
}
