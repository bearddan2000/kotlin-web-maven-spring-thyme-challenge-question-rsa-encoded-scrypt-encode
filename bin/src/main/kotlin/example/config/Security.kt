package example.config

import example.config.MySimpleUrlAuthenticationSuccessHandler
import org.springframework.security.crypto.password.PasswordEncoder
import example.model.User;
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.provisioning.InMemoryUserDetailsManager

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.context.annotation.Bean

import kotlin.collections.Collection;

@Configuration
@EnableWebSecurity
class Security : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var accessDeniedHandler :AccessDeniedHandler

    // roles admin allow to access /admin/**
    // roles user allow to access /user/**
    // custom 403 access denied handler
    @Throws(Exception::class)
    override fun configure(http :HttpSecurity) {
  		http
  			.authorizeRequests()
        .antMatchers("/user").access("hasRole('USER') or hasRole('SUPER')")
        .antMatchers("/admin").access("hasRole('ADMIN') or hasRole('SUPER')")
        .antMatchers("/super").access("hasRole('SUPER')")
  				.antMatchers("/login", "/register", "/reset", "/api/register", "/api/reset").permitAll()
  				.anyRequest().authenticated()
  				.and()
  			.formLogin()
  				.loginPage("/login")
          .loginProcessingUrl("/login")
          .successHandler(myAuthenticationSuccessHandler())
  				.permitAll()
    }

    @Bean
    fun myAuthenticationSuccessHandler() :AuthenticationSuccessHandler {
        return MySimpleUrlAuthenticationSuccessHandler()
    }

    @Bean
  	fun passwordEncoder() :PasswordEncoder
  	{
      
    try {
      return example.security.RSAPasswordEncoder()
    } catch(e :Exception) {}
    return org.springframework.security.crypto.scrypt.SCryptPasswordEncoder()
  	}

    @Bean
    fun inMemoryUserDetailsManager() :InMemoryUserDetailsManager {
      return userDetailsService() as InMemoryUserDetailsManager
    }

  	override fun userDetailsService() :UserDetailsService {

      var userDetailsList = mutableListOf<UserDetails>()

      val challenge = example.model.Challenge(0, "1900")

      val pass = passwordEncoder().encode("pass")

      userDetailsList.add(User("user", pass, User.assignRole("USER"), challenge))
      userDetailsList.add(User("admin", pass, User.assignRole("ADMIN"), challenge))
      userDetailsList.add(User("super", pass, User.assignRole("SUPER"), challenge))

      return InMemoryUserDetailsManager(userDetailsList)
  	}

    @Throws(Exception::class)
    @Bean
    override fun authenticationManagerBean() : AuthenticationManager  {
        return super.authenticationManagerBean();
    }

}
